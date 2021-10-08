package com.Da_Technomancer.crossroads.tileentities.technomancy;

import com.Da_Technomancer.crossroads.API.CRProperties;
import com.Da_Technomancer.crossroads.API.Capabilities;
import com.Da_Technomancer.crossroads.API.rotary.IAxleHandler;
import com.Da_Technomancer.crossroads.API.rotary.RotaryUtil;
import com.Da_Technomancer.crossroads.CRConfig;
import com.Da_Technomancer.crossroads.Crossroads;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Crossroads.MODID)
public class HamsterWheelTileEntity extends BlockEntity implements TickableBlockEntity{

	@ObjectHolder("hamster_wheel")
	public static BlockEntityType<HamsterWheelTileEntity> type = null;

	public float angle = 0;
	public float nextAngle = 0;

	public HamsterWheelTileEntity(){
		super(type);
	}

	@Override
	public void tick(){
		Direction facing = getBlockState().getValue(CRProperties.HORIZ_FACING);
		BlockEntity te = level.getBlockEntity(worldPosition.relative(facing));
		LazyOptional<IAxleHandler> axleOpt;
		if(te != null && (axleOpt = te.getCapability(Capabilities.AXLE_CAPABILITY, facing.getOpposite())).isPresent()){
			IAxleHandler axle = axleOpt.orElseThrow(NullPointerException::new);
			if(level.isClientSide){
				angle = axle.getAngle(0);
				nextAngle = axle.getAngle(1F);
				return;
			}
			axle.addEnergy(CRConfig.hamsterPower.get() * RotaryUtil.getCCWSign(facing), true);
		}else if(level.isClientSide){
			nextAngle = angle;
		}
	}
}
