package com.Da_Technomancer.crossroads.blocks.fluid;

import com.Da_Technomancer.crossroads.api.CRProperties;
import com.Da_Technomancer.crossroads.api.MiscUtil;
import com.Da_Technomancer.crossroads.api.alchemy.EnumTransferMode;
import com.Da_Technomancer.crossroads.blocks.heat.RedstoneHeatCableTileEntity;
import com.Da_Technomancer.essentials.api.ITickableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class RedstoneFluidTube extends FluidTube{

	public RedstoneFluidTube(){
		super("redstone_fluid_tube");
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
		return new RedstoneFluidTubeTileEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> type){
		return ITickableTileEntity.createTicker(type, RedstoneFluidTubeTileEntity.TYPE);
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack){
		neighborChanged(state, world, pos, this, pos, false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
		super.createBlockStateDefinition(builder);
		builder.add(CRProperties.REDSTONE_BOOL);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
		if(state.getValue(CRProperties.REDSTONE_BOOL)){
			return super.getShape(state, worldIn, pos, context);
		}else{
			return SHAPES[0];//Core only
		}
	}

	@Override
	protected boolean evaluate(EnumTransferMode value, BlockState state, @Nullable IConduitTE<EnumTransferMode> te){
		return super.evaluate(value, state, te) && state.getValue(CRProperties.REDSTONE_BOOL);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context){
		return super.getStateForPlacement(context).setValue(CRProperties.REDSTONE_BOOL, context.getLevel().hasNeighborSignal(context.getClickedPos()));
	}

	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving){
		boolean inverted = false;
		if(worldIn.getBlockEntity(pos) instanceof RedstoneFluidTubeTileEntity cableTE){
			inverted = cableTE.isInverted();
		}
		boolean isPowered = worldIn.hasNeighborSignal(pos) != inverted;
		boolean statePowered = state.getValue(CRProperties.REDSTONE_BOOL);
		if(isPowered != statePowered){
			worldIn.setBlockAndUpdate(pos, state.setValue(CRProperties.REDSTONE_BOOL, isPowered));
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag advanced){
		super.appendHoverText(stack, world, tooltip, advanced);
		tooltip.add(Component.translatable("tt.crossroads.redstone_fluid_tube.control"));
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand hand, BlockHitResult hit){
		//Invert when sneak-wrenching
		if(playerIn != null && hand != null && playerIn.isCrouching()){
			BlockEntity te = worldIn.getBlockEntity(pos);
			if(te instanceof RedstoneFluidTubeTileEntity cableTE){
				boolean inverted = !cableTE.isInverted();
				cableTE.setInverted(inverted);
				if(inverted){
					MiscUtil.displayMessage(playerIn, Component.translatable("tt.crossroads.redstone_fluid_tube.wrench.invert"));
				}else{
					MiscUtil.displayMessage(playerIn, Component.translatable("tt.crossroads.redstone_fluid_tube.wrench.uninvert"));
				}
				neighborChanged(state, worldIn, pos, this, pos, false);
			}
			return InteractionResult.SUCCESS;
		}
		return super.use(state, worldIn, pos, playerIn, hand, hit);
	}
}
