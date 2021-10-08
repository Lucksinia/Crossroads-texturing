package com.Da_Technomancer.crossroads.blocks.technomancy;

import com.Da_Technomancer.crossroads.API.MiscUtil;
import com.Da_Technomancer.crossroads.CRConfig;
import com.Da_Technomancer.crossroads.blocks.CRBlocks;
import com.Da_Technomancer.crossroads.tileentities.technomancy.LodestoneTurbineTileEntity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;
import java.util.List;

public class LodestoneTurbine extends BaseEntityBlock{

	public LodestoneTurbine(){
		super(CRBlocks.getMetalProperty());
		String name = "lodestone_turbine";
		setRegistryName(name);
		CRBlocks.toRegister.add(this);
		CRBlocks.blockAddQue(this);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockGetter worldIn){
		return new LodestoneTurbineTileEntity();
	}

	@Override
	public RenderShape getRenderShape(BlockState state){
		return RenderShape.MODEL;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
		tooltip.add(new TranslatableComponent("tt.crossroads.lodestone_turbine.desc", CRConfig.lodestoneTurbinePower.get()));
		tooltip.add(new TranslatableComponent("tt.crossroads.lodestone_turbine.limit", LodestoneTurbineTileEntity.MAX_SPEED));
		tooltip.add(new TranslatableComponent("tt.crossroads.boilerplate.inertia", LodestoneTurbineTileEntity.INERTIA));
		tooltip.add(new TranslatableComponent("tt.crossroads.lodestone_turbine.quip").setStyle(MiscUtil.TT_QUIP));
	}
}
