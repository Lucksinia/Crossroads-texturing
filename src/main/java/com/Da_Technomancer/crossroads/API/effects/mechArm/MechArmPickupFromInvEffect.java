package com.Da_Technomancer.crossroads.API.effects.mechArm;

import com.Da_Technomancer.crossroads.entity.EntityArmRidable;
import com.Da_Technomancer.crossroads.tileentities.technomancy.MechanicalArmTileEntity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MechArmPickupFromInvEffect implements IMechArmEffect{

	@Override
	public boolean onTriggered(World world, BlockPos pos, double posX, double posY, double posZ, EnumFacing side, EntityArmRidable ent, MechanicalArmTileEntity arm){
		TileEntity te = world.getTileEntity(pos);
		Boolean holdingStack = ent.getPassengers().isEmpty() ? false : ent.getPassengers().get(0) instanceof EntityItem ? true : null;
		if(te == null || holdingStack == null || !te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)){
			return false;
		}
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);

		ItemStack heldStack = holdingStack ? ((EntityItem) ent.getPassengers().get(0)).getEntityItem().copy() : ItemStack.EMPTY;
		int startSize = heldStack.getCount();

		for(int i = 0; i < handler.getSlots(); i++){
			ItemStack invStack = handler.getStackInSlot(i);
			if(heldStack.isEmpty() || heldStack.getMaxStackSize() > heldStack.getCount()){
				if(heldStack.isEmpty() || (ItemStack.areItemsEqual(heldStack, invStack) && ItemStack.areItemStackTagsEqual(heldStack, invStack))){
					int change = Math.min(heldStack.isEmpty() ? 64 : heldStack.getMaxStackSize() - heldStack.getCount(), invStack.getCount());
					ItemStack extracted = handler.extractItem(i, change, false);
					change = Math.min(change, extracted.getCount());
					if(heldStack.isEmpty()){
						heldStack = extracted;
					}else{
						heldStack.grow(change);
					}
				}
			}else{
				break;
			}
		}
		if(!heldStack.isEmpty()){
			if(holdingStack){
				((EntityItem) ent.getPassengers().get(0)).setEntityItemStack(heldStack);
			}else{
				EntityItem heldItemEnt = new EntityItem(world, posX, posY, posZ, heldStack);
				heldItemEnt.startRiding(ent, true);
				world.spawnEntity(heldItemEnt);
			}
		}

		return heldStack.getCount() > startSize;
	}
}