package com.Da_Technomancer.crossroads.blocks.witchcraft;

import com.Da_Technomancer.crossroads.ambient.particles.CRParticles;
import com.Da_Technomancer.crossroads.ambient.particles.ColorParticleData;
import com.Da_Technomancer.crossroads.ambient.sounds.CRSounds;
import com.Da_Technomancer.crossroads.api.CRProperties;
import com.Da_Technomancer.crossroads.api.Capabilities;
import com.Da_Technomancer.crossroads.api.MiscUtil;
import com.Da_Technomancer.crossroads.api.heat.HeatUtil;
import com.Da_Technomancer.crossroads.api.templates.InventoryTE;
import com.Da_Technomancer.crossroads.blocks.CRBlocks;
import com.Da_Technomancer.crossroads.blocks.CRTileEntity;
import com.Da_Technomancer.crossroads.gui.container.BrewingVatContainer;
import com.Da_Technomancer.essentials.api.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;

public class BrewingVatTileEntity extends InventoryTE{

	public static final BlockEntityType<BrewingVatTileEntity> TYPE = CRTileEntity.createType(BrewingVatTileEntity::new, CRBlocks.brewingVat);

	public static final int[] TEMP_TIERS = {100, 115, 125};
	public static final int[] SPEED_MULT = {1, 2, 0};
	public static final int[] HEAT_DRAIN = {1, 2, 2};
	public static final int REQUIRED = 400;
	private int progress = 0;

	public BrewingVatTileEntity(BlockPos pos, BlockState state){
		super(TYPE, pos, state, 7);//Index 0: Ingredient; 1-3: Input potions; 4-6: Output potions
	}

	@Override
	public void addInfo(ArrayList<Component> chat, Player player, BlockHitResult hit){
		chat.add(Component.translatable("tt.crossroads.boilerplate.progress", progress, REQUIRED));
		super.addInfo(chat, player, hit);
	}

	@Override
	protected boolean useHeat(){
		return true;
	}

	public int getProgess(){
		return progress;
	}

	@Override
	public void serverTick(){
		super.serverTick();

		ItemStack created = ItemStack.EMPTY;

		//Only allow crafting if all inputs are present, all input potions are the same item, and all outputs are empty
		if(!inventory[0].isEmpty() && !inventory[1].isEmpty() && BlockUtil.sameItem(inventory[1], inventory[2]) && BlockUtil.sameItem(inventory[1], inventory[3]) && inventory[4].isEmpty() && inventory[5].isEmpty() && inventory[6].isEmpty()){
			created = BrewingRecipeRegistry.getOutput(inventory[1], inventory[0]);
		}

		if(created.isEmpty()){
			progress = 0;
		}

		//Actually advance the progress and consume heat
		int tier = HeatUtil.getHeatTier(temp, TEMP_TIERS);
		if(tier >= 0){
			temp -= HEAT_DRAIN[tier];

			if(!created.isEmpty()){
				progress += SPEED_MULT[tier];
				if(progress >= REQUIRED){
					progress = 0;

					//Consume the ingredients and create the output
					inventory[0].shrink(1);
					inventory[1].shrink(1);
					inventory[2].shrink(1);
					inventory[3].shrink(1);
					inventory[4] = created.copy();
					inventory[5] = created.copy();
					inventory[6] = created.copy();
				}
			}

			setChanged();
		}
	}

	private static ColorParticleData bubbleParticle;
	private static ColorParticleData steamParticle;

	@Override
	public void clientTick(){
		super.clientTick();

		BlockState state;
		long gametime = level.getGameTime();
		if(gametime % 4 == 0 && (state = getBlockState()).getBlock() instanceof BrewingVat){
			int powerLevel = state.getValue(CRProperties.POWER_LEVEL_4);

			if(powerLevel == 1 || powerLevel == 2){
				//1: Running slow, mild bubbling
				//2: Running fast, vigorous bubbling
				if(bubbleParticle == null){
					bubbleParticle = new ColorParticleData(CRParticles.COLOR_GAS, Color.CYAN);
				}
				CRParticles.summonParticlesFromClient(level, bubbleParticle, (int) (powerLevel * 1.5), worldPosition.getX() + 0.5, worldPosition.getY() + 12.5/16, worldPosition.getZ() + 0.5, 0.35, 0, 0.35, 0, 0.01 * powerLevel, 0, 0, 0, 0, false);
				if(gametime % 40 == 0){
					CRSounds.playSoundClientLocal(level, worldPosition, CRSounds.WATER_BUBBLING, SoundSource.BLOCKS, 0.2F * powerLevel, 1);
				}
			}else if(powerLevel == 3){
				//3: too hot, steam
				if(steamParticle == null){
					steamParticle = new ColorParticleData(CRParticles.COLOR_SOLID, Color.LIGHT_GRAY);
				}
				CRParticles.summonParticlesFromClient(level, steamParticle, 2, worldPosition.getX() + 0.5, worldPosition.getY() + 12.5/16, worldPosition.getZ() + 0.5, 0.25, 0, 0.25, 0, 0.06, 0, 0, 0.02, 0, false);
				if(gametime % 24 == 0){
					CRSounds.playSoundClientLocal(level, worldPosition, CRSounds.STEAM_RELEASE, SoundSource.BLOCKS, 0.1F, 1);
				}
			}
		}
	}

	@Override
	public int getMaxStackSize(int slot){
		//Any slot (other than ingredient) can only hold 1 item
		//The potion brewing helper tends to misbehave unless all the ingredients are in stacks of 1
		return slot == 0 ? super.getMaxStackSize(slot) : 1;
	}

	@Override
	public void load(CompoundTag nbt){
		super.load(nbt);
		progress = nbt.getInt("prog");
	}

	@Override
	public void saveAdditional(CompoundTag nbt){
		super.saveAdditional(nbt);
		nbt.putInt("prog", progress);
	}

	@Override
	public void setRemoved(){
		super.setRemoved();
		itemOpt.invalidate();
	}

	@Override
	public void setChanged(){
		super.setChanged();
		if(level != null && !level.isClientSide){
			//Update the blockstate in the world
			BlockState state = getBlockState();
			BlockState newState = state.setValue(CRProperties.POWER_LEVEL_4, HeatUtil.getHeatTier(temp, TEMP_TIERS)+1);
			for(int i = 0; i < 3; i++){
				newState = newState.setValue(CRProperties.SLOT_FILLED[i], !inventory[i + 1].isEmpty() || !inventory[4 + i].isEmpty());
			}
			if(state != newState){
				level.setBlock(worldPosition, newState, MiscUtil.BLOCK_FLAGS_VISUAL);
			}
		}
	}

	private final LazyOptional<IItemHandler> itemOpt = LazyOptional.of(ItemHandler::new);

	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing){
		if(capability == Capabilities.HEAT_CAPABILITY && facing != Direction.UP){
			return (LazyOptional<T>) heatOpt;
		}

		if(capability == ForgeCapabilities.ITEM_HANDLER){
			return (LazyOptional<T>) itemOpt;
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction){
		return index >= 4 && index < 7;//Output slots
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack){
		if(!super.canPlaceItem(index, stack)){
			return false;
		}
		if(index == 0){
			return BrewingRecipeRegistry.isValidIngredient(stack);
		}
		if(index > 0 && index < 4){
			if(stack.getCount() > 1){
				//BrewingRecipeRegistry.isValidInput only passes if the stacksize is 1
				stack = stack.copy();
				stack.setCount(1);
			}
			return BrewingRecipeRegistry.isValidInput(stack);
		}
		return false;
	}

	@Override
	public Component getDisplayName(){
		return Component.translatable("container.crossroads.brewing_vat");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player playerEntity){
		return new BrewingVatContainer(id, playerInventory, createContainerBuf());
	}
}
