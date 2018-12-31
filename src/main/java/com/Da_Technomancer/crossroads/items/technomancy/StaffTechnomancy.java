package com.Da_Technomancer.crossroads.items.technomancy;

import com.Da_Technomancer.crossroads.API.beams.BeamUnit;
import com.Da_Technomancer.crossroads.API.beams.EnumBeamAlignments;
import com.Da_Technomancer.crossroads.API.effects.IEffect;
import com.Da_Technomancer.crossroads.items.ModItems;
import com.Da_Technomancer.crossroads.render.RenderUtil;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class StaffTechnomancy extends BeamUsingItem{

	public StaffTechnomancy(){
		String name = "staff_technomancy";
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ModItems.TAB_CROSSROADS);
		setMaxStackSize(1);
		ModItems.toRegister.add(this);
		ModItems.itemAddQue(this);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count){
		super.onUsingTick(stack, player, count);
		if(!player.world.isRemote && (getMaxItemUseDuration(stack) - count) % 5 == 0){
			if(!stack.hasTagCompound()){
				return;
			}
			ItemStack cage = player.getHeldItem(player.getActiveHand() == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
			if(cage.getItem() != ModItems.beamCage || !cage.hasTagCompound()){
				player.resetActiveHand();
				return;
			}

			NBTTagCompound cageNbt = cage.getTagCompound();
			NBTTagCompound nbt = stack.getTagCompound();
			int energy = nbt.getInteger(EnumBeamAlignments.ENERGY.name());
			int potential = nbt.getInteger(EnumBeamAlignments.POTENTIAL.name());
			int stability = nbt.getInteger(EnumBeamAlignments.STABILITY.name());
			int voi = nbt.getInteger(EnumBeamAlignments.VOID.name());
			if(energy <= cageNbt.getInteger("stored_" + EnumBeamAlignments.ENERGY.name()) && potential <= cageNbt.getInteger("stored_" + EnumBeamAlignments.POTENTIAL.name()) && stability <= cageNbt.getInteger("stored_" + EnumBeamAlignments.STABILITY.name()) && voi <= cageNbt.getInteger("stored_" + EnumBeamAlignments.VOID.name())){
				if(energy + potential + stability + voi > 0){
					cageNbt.setInteger("stored_" + EnumBeamAlignments.ENERGY.name(), cageNbt.getInteger("stored_" + EnumBeamAlignments.ENERGY.name()) - energy);
					cageNbt.setInteger("stored_" + EnumBeamAlignments.POTENTIAL.name(), cageNbt.getInteger("stored_" + EnumBeamAlignments.POTENTIAL.name()) - potential);
					cageNbt.setInteger("stored_" + EnumBeamAlignments.STABILITY.name(), cageNbt.getInteger("stored_" + EnumBeamAlignments.STABILITY.name()) - stability);
					cageNbt.setInteger("stored_" + EnumBeamAlignments.VOID.name(), cageNbt.getInteger("stored_" + EnumBeamAlignments.VOID.name()) - voi);
					BeamUnit mag = new BeamUnit(energy, potential, stability, voi);

					double heldOffset = .22D * (player.getActiveHand() == EnumHand.MAIN_HAND ^ player.getPrimaryHand() == EnumHandSide.LEFT ? 1D : -1D);
					Vec3d start = new Vec3d(player.posX - (heldOffset * Math.cos(Math.toRadians(player.rotationYaw))), player.posY + 2.1D, player.posZ - (heldOffset * Math.sin(Math.toRadians(player.rotationYaw))));
					double[] end = new double[] {player.posX, player.getEyeHeight() + player.posY, player.posZ};
					BlockPos endPos = null;
					Vec3d look = player.getLookVec().scale(0.2D);
					EnumFacing effectDir = null;

					for(double d = 0; d < 32; d += 0.2D){
						end[0] += look.x;
						end[1] += look.y;
						end[2] += look.z;

						List<Entity> ents = player.world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(end[0] - 0.1D, end[1] - 0.1D, end[2] - 0.1D, end[0] + 0.1D, end[1] + 0.1D, end[2] + 0.1D), EntitySelectors.IS_ALIVE);
						if(!ents.isEmpty()){
							RayTraceResult res = ents.get(0).getEntityBoundingBox().calculateIntercept(start, new Vec3d(end[0], end[1], end[2]));
							if(res != null && res.hitVec != null){
								end[0] = res.hitVec.x;
								end[1] = res.hitVec.y;
								end[2] = res.hitVec.z;
							}
							break;
						}

						BlockPos newEndPos = new BlockPos(end[0], end[1], end[2]);
						//Speed things up a bit by not rechecking blocks
						if(newEndPos.equals(endPos) || player.world.isOutsideBuildHeight(newEndPos)){
							continue;
						}
						endPos = newEndPos;
						IBlockState state = player.world.getBlockState(endPos);
						AxisAlignedBB bb = state.getBoundingBox(player.world, endPos).offset(endPos);
						if(state.getBlock().canCollideCheck(state, true) && state.getBlock().isCollidable() && bb.minX <= end[0] && bb.maxX >= end[0] && bb.minY <= end[1] && bb.maxY >= end[1] && bb.minZ <= end[2] && bb.maxZ >= end[2]){
							RayTraceResult res = bb.calculateIntercept(start, new Vec3d(end[0], end[1], end[2]));
							if(res != null && res.hitVec != null){
								end[0] = res.hitVec.x;
								end[1] = res.hitVec.y;
								end[2] = res.hitVec.z;
								effectDir = res.sideHit;
							}
							break;
						}
					}


					IEffect effect = EnumBeamAlignments.getAlignment(mag).getMixEffect(mag.getRGB());
					if(effect != null && endPos != null && !player.world.isOutsideBuildHeight(endPos)){
						effect.doEffect(player.world, endPos, Math.min(64, mag.getPower()), effectDir);
					}

					Vec3d beamVec = new Vec3d(end[0] - start.x, end[1] - start.y, end[2] - start.z);
					RenderUtil.addBeam(player.world.provider.getDimension(), start.x, start.y, start.z, beamVec.length(), (float) Math.toDegrees(Math.atan2(-beamVec.y, Math.sqrt(beamVec.x * beamVec.x + beamVec.z * beamVec.z))), (float) Math.toDegrees(Math.atan2(-beamVec.x, beamVec.z)), (byte) Math.round(Math.sqrt(mag.getPower())), mag.getRGB().getRGB());
				}
			}
		}
	}

	@Override
	public void preChanged(ItemStack stack, EntityPlayer player){

	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if (slot == EntityEquipmentSlot.MAINHAND){
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 9, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -3.1D, 0));
		}

		return multimap;
	}
}
