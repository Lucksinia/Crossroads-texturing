package com.Da_Technomancer.crossroads.API.effects.mechArm;

import com.Da_Technomancer.crossroads.entity.EntityArmRidable;
import com.Da_Technomancer.crossroads.tileentities.technomancy.MechanicalArmTileEntity;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MechArmReleaseEntityEffect implements IMechArmEffect{

	@Override
	public boolean onTriggered(World world, BlockPos pos, double posX, double posY, double posZ, EnumFacing side, EntityArmRidable ent, MechanicalArmTileEntity te){
		if(ent.getPassengers().size() != 0){
			Entity passenger = ent.getPassengers().get(0);
			passenger.dismountRidingEntity();
			
			double newAngle0 = te.angle[0] + (te.motionData[0][0] / 20D);
			double newAngle1 = te.angle[1] + (te.motionData[1][0] / 20D);
			double newAngle2 = te.angle[2] + (te.motionData[2][0] / 20D);
			
			double lengthCross = Math.sqrt(Math.pow(MechanicalArmTileEntity.LOWER_ARM_LENGTH, 2) + Math.pow(MechanicalArmTileEntity.UPPER_ARM_LENGTH, 2) - (2D * MechanicalArmTileEntity.LOWER_ARM_LENGTH * MechanicalArmTileEntity.UPPER_ARM_LENGTH * Math.cos(newAngle2)));
			double thetaD = newAngle1 + newAngle2 + Math.asin(Math.sin(newAngle2 * MechanicalArmTileEntity.LOWER_ARM_LENGTH / lengthCross));
			double holder = -Math.cos(thetaD) * lengthCross;

			double newPosX = (holder * Math.cos(newAngle0)) + .5D + (double) pos.getX();
			double newPosY = (-Math.sin(thetaD) * lengthCross) + 1D + (double) pos.getY();
			double newPosZ = (holder * Math.sin(newAngle0)) + .5D + (double) pos.getZ();
			
			passenger.setVelocity(20D * (newPosX - posX), 20D * (newPosY - posY), 20D * (newPosZ - posZ));
			passenger.velocityChanged = true;
			return true;
		}
		return false;
	}
}