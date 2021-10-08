package com.Da_Technomancer.crossroads.tileentities.electric;

import com.Da_Technomancer.crossroads.API.CRProperties;
import com.Da_Technomancer.crossroads.API.packets.CRPackets;
import com.Da_Technomancer.crossroads.API.packets.IIntReceiver;
import com.Da_Technomancer.crossroads.API.packets.SendIntToClient;
import com.Da_Technomancer.crossroads.Crossroads;
import com.Da_Technomancer.crossroads.blocks.CRBlocks;
import com.Da_Technomancer.crossroads.blocks.electric.TeslaCoilTop;
import com.Da_Technomancer.crossroads.items.CRItems;
import com.Da_Technomancer.crossroads.items.LeydenJar;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ObjectHolder(Crossroads.MODID)
public class TeslaCoilTileEntity extends BlockEntity implements TickableBlockEntity, IIntReceiver{

	@ObjectHolder("tesla_coil")
	private static BlockEntityType<TeslaCoilTileEntity> type = null;

	public static final int CAPACITY = 2000;

	private int stored = 0;
	private Boolean hasJar = null;
	public boolean redstone = false;

	public TeslaCoilTileEntity(){
		super(type);
	}

	public float getRedstone(){
		return stored;
	}

	public void syncState(){
		int message = 0;
		if(redstone){
			message |= 1;
		}
		message |= stored << 1;
		CRPackets.sendPacketAround(level, worldPosition, new SendIntToClient((byte) 0, message, worldPosition));
	}

	public void setStored(int storedIn){
		int prev = stored;
		stored = storedIn;
		if(!level.isClientSide && prev >= TeslaCoilTop.TeslaCoilVariants.DECORATIVE.joltAmt ^ storedIn >= TeslaCoilTop.TeslaCoilVariants.DECORATIVE.joltAmt){
			syncState();
		}
		setChanged();
	}

	public int getStored(){
		return stored;
	}

	public int getCapacity(){
		return hasJar() ? CAPACITY + LeydenJar.MAX_CHARGE : CAPACITY;
	}

	@Override
	public void receiveInt(byte identifier, int message, @Nullable ServerPlayer sendingPlayer){
		if(identifier == 0){
			redstone = (message & 1) == 1;
			stored = message >>> 1;
		}
	}

	private boolean hasJar(){
		if(hasJar == null){
			BlockState state = level.getBlockState(worldPosition);
			if(state.getBlock() != CRBlocks.teslaCoil){
				setRemoved();
				return false;
			}
			hasJar = level.getBlockState(worldPosition).getValue(CRProperties.ACTIVE);
		}
		return hasJar;
	}

	@Override
	public void tick(){
		if(!redstone && level.random.nextInt(10) == 0 && stored > 0){
			BlockEntity topTE = level.getBlockEntity(worldPosition.above());
			if(topTE instanceof TeslaCoilTopTileEntity){
				((TeslaCoilTopTileEntity) topTE).jolt(this);
			}
		}

		if(level.isClientSide){
			return;
		}

		if(!redstone && stored > 0){
			Direction facing = level.getBlockState(worldPosition).getValue(CRProperties.HORIZ_FACING);
			BlockEntity te = level.getBlockEntity(worldPosition.relative(facing));
			LazyOptional<IEnergyStorage> energyOpt;
			if(te != null && (energyOpt = te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite())).isPresent()){
				IEnergyStorage storage = energyOpt.orElseThrow(NullPointerException::new);
				int moved = storage.receiveEnergy(stored, false);
				if(moved > 0){
					setStored(getStored() - moved);
					setChanged();
				}
			}
		}
	}

	public void addJar(ItemStack stack){
		setStored(Math.min(stored + LeydenJar.getCharge(stack), CAPACITY + LeydenJar.MAX_CHARGE));
		hasJar = true;
		setChanged();
	}

	@Override
	public CompoundTag save(CompoundTag nbt){
		super.save(nbt);
		nbt.putInt("stored", stored);
		nbt.putBoolean("reds", redstone);
		return nbt;
	}

	@Override
	public void load(BlockState state, CompoundTag nbt){
		super.load(state, nbt);
		stored = nbt.getInt("stored");
		redstone = nbt.getBoolean("reds");
	}

	@Override
	public CompoundTag getUpdateTag(){
		CompoundTag nbt = super.getUpdateTag();
		nbt.putInt("stored", stored);
		nbt.putBoolean("reds", redstone);
		return nbt;
	}

	@Nonnull
	public ItemStack removeJar(){
		ItemStack out = new ItemStack(CRItems.leydenJar, 1);
		LeydenJar.setCharge(out, Math.min(stored, LeydenJar.MAX_CHARGE));
		setStored(stored - Math.min(stored, LeydenJar.MAX_CHARGE));
		hasJar = false;
		setChanged();
		return out;
	}

	public void rotate(){
		optIn.invalidate();
		optIn = LazyOptional.of(EnergyHandlerIn::new);
		optOut.invalidate();
		optOut = LazyOptional.of(EnergyHandlerOut::new);
	}

	@Override
	public void setRemoved(){
		super.setRemoved();
		optIn.invalidate();
		optOut.invalidate();
	}

	private LazyOptional<IEnergyStorage> optIn = LazyOptional.of(EnergyHandlerIn::new);
	private LazyOptional<IEnergyStorage> optOut = LazyOptional.of(EnergyHandlerOut::new);

	@SuppressWarnings("unchecked")
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side){
		if(cap == CapabilityEnergy.ENERGY){
			return (LazyOptional<T>) (side == level.getBlockState(worldPosition).getValue(CRProperties.HORIZ_FACING) ? optOut : optIn);
		}
		return super.getCapability(cap, side);
	}

	protected class EnergyHandlerIn implements IEnergyStorage{

		@Override
		public int receiveEnergy(int maxReceive, boolean simulate){
			int toInsert = Math.min(maxReceive, getMaxEnergyStored() - stored);

			if(!simulate){
				setStored(stored + toInsert);
				setChanged();
			}
			return toInsert;
		}

		@Override
		public int extractEnergy(int maxExtract, boolean simulate){
			return 0;
		}

		@Override
		public int getEnergyStored(){
			return stored;
		}

		@Override
		public int getMaxEnergyStored(){
			return hasJar() ? CAPACITY + LeydenJar.MAX_CHARGE : CAPACITY;
		}

		@Override
		public boolean canExtract(){
			return false;
		}

		@Override
		public boolean canReceive(){
			return true;
		}
	}

	private class EnergyHandlerOut implements IEnergyStorage{

		@Override
		public int receiveEnergy(int maxReceive, boolean simulate){
			return 0;
		}

		@Override
		public int extractEnergy(int maxExtract, boolean simulate){
			int toExtract = Math.min(stored, maxExtract);
			if(!simulate){
				setStored(stored - toExtract);
				setChanged();
			}
			return toExtract;
		}

		@Override
		public int getEnergyStored(){
			return stored;
		}

		@Override
		public int getMaxEnergyStored(){
			return getCapacity();
		}

		@Override
		public boolean canExtract(){
			return true;
		}

		@Override
		public boolean canReceive(){
			return false;
		}
	}
}
