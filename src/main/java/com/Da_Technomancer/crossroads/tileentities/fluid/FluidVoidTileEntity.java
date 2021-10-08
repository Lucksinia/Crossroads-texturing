package com.Da_Technomancer.crossroads.tileentities.fluid;

import com.Da_Technomancer.crossroads.Crossroads;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

@ObjectHolder(Crossroads.MODID)
public class FluidVoidTileEntity extends BlockEntity{

	@ObjectHolder("fluid_void")
	private static BlockEntityType<FluidVoidTileEntity> type = null;

	public FluidVoidTileEntity(){
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing){
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return (LazyOptional<T>) mainOpt;
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public void setRemoved(){
		super.setRemoved();
		mainOpt.invalidate();
	}

	private final LazyOptional<IFluidHandler> mainOpt = LazyOptional.of(VoidHandler::new);

	private static class VoidHandler implements IFluidHandler{

		@Override
		public int getTanks(){
			return 1;
		}

		@Nonnull
		@Override
		public FluidStack getFluidInTank(int tank){
			return FluidStack.EMPTY;
		}

		@Override
		public int getTankCapacity(int tank){
			return 10_000;
		}

		@Override
		public boolean isFluidValid(int tank, @Nonnull FluidStack stack){
			return true;
		}

		@Override
		public int fill(FluidStack resource, FluidAction action){
			return resource.getAmount();
		}

		@Nonnull
		@Override
		public FluidStack drain(FluidStack resource, FluidAction action){
			return FluidStack.EMPTY;
		}

		@Nonnull
		@Override
		public FluidStack drain(int maxDrain, FluidAction action){
			return FluidStack.EMPTY;
		}
	}
}
