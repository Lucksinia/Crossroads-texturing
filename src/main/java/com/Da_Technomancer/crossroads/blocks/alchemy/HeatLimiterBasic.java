package com.Da_Technomancer.crossroads.blocks.alchemy;

import com.Da_Technomancer.crossroads.API.CRProperties;
import com.Da_Technomancer.crossroads.blocks.CRBlocks;
import com.Da_Technomancer.crossroads.tileentities.alchemy.HeatLimiterBasicTileEntity;
import com.Da_Technomancer.essentials.ESConfig;
import com.Da_Technomancer.essentials.blocks.ESProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class HeatLimiterBasic extends ContainerBlock{

	public HeatLimiterBasic(){
		super(CRBlocks.getRockProperty());
		String name = "heat_limiter_basic";
		setRegistryName(name);
		CRBlocks.toRegister.add(this);
		CRBlocks.blockAddQue(this);
		registerDefaultState(defaultBlockState().setValue(CRProperties.ACTIVE, false));
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn){
		return new HeatLimiterBasicTileEntity();
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state){
		return BlockRenderType.MODEL;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder){
		builder.add(CRProperties.ACTIVE, ESProperties.FACING);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit){
		if(!worldIn.isClientSide){
			TileEntity te;
			if(ESConfig.isWrench(playerIn.getItemInHand(hand))){
				if(playerIn.isShiftKeyDown()){
					worldIn.setBlockAndUpdate(pos, state.cycle(CRProperties.ACTIVE));
				}else{
					worldIn.setBlockAndUpdate(pos, state.cycle(ESProperties.FACING));
				}
			}else if((te = worldIn.getBlockEntity(pos)) instanceof HeatLimiterBasicTileEntity){
				NetworkHooks.openGui((ServerPlayerEntity) playerIn, (INamedContainerProvider) te, buf -> {buf.writeFloat(((HeatLimiterBasicTileEntity) te).setting); buf.writeUtf(((HeatLimiterBasicTileEntity) te).expression); buf.writeBlockPos(pos);});
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context){
		return defaultBlockState().setValue(ESProperties.FACING, context.getNearestLookingDirection());
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
		tooltip.add(new TranslationTextComponent("tt.crossroads.heat_limiter.desc_cable"));
		tooltip.add(new TranslationTextComponent("tt.crossroads.heat_limiter.desc_purpose"));
		tooltip.add(new TranslationTextComponent("tt.crossroads.heat_limiter.desc_mode"));
		tooltip.add(new TranslationTextComponent("tt.crossroads.heat_limiter.desc_ui"));
	}
}
