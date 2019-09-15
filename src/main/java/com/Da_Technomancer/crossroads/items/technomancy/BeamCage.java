package com.Da_Technomancer.crossroads.items.technomancy;

import java.util.List;

import javax.annotation.Nullable;

import com.Da_Technomancer.crossroads.API.beams.BeamUnit;
import com.Da_Technomancer.crossroads.API.beams.EnumBeamAlignments;
import com.Da_Technomancer.crossroads.items.CrossroadsItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BeamCage extends Item{

	public static final int CAPACITY = 1024;

	public BeamCage(){
		String name = "beam_cage";
		setTranslationKey(name);
		setRegistryName(name);
		maxStackSize = 1;
		setCreativeTab(CrossroadsItems.TAB_CROSSROADS);
		CrossroadsItems.toRegister.add(this);
		CrossroadsItems.itemAddQue(this);
	}

	public static BeamUnit getStored(ItemStack stack){
		CompoundNBT nbt = stack.getTagCompound();
		if(nbt == null){
			return null;
		}
		BeamUnit stored = new BeamUnit(nbt.getInteger("stored_" + EnumBeamAlignments.ENERGY.name().toLowerCase()), nbt.getInteger("stored_" + EnumBeamAlignments.POTENTIAL.name().toLowerCase()), nbt.getInteger("stored_" + EnumBeamAlignments.STABILITY.name().toLowerCase()), nbt.getInteger("stored_" + EnumBeamAlignments.VOID.name().toLowerCase()));
		return stored.getPower() == 0 ? null : stored;
	}

	public static void storeBeam(ItemStack stack, @Nullable BeamUnit toStore){
		CompoundNBT nbt = stack.getTagCompound();
		if(nbt == null){
			stack.setTagCompound(new CompoundNBT());
			nbt = stack.getTagCompound();
		}
		nbt.setInteger("stored_" + EnumBeamAlignments.ENERGY.name().toLowerCase(), toStore == null ? 0 : toStore.getEnergy());
		nbt.setInteger("stored_" + EnumBeamAlignments.POTENTIAL.name().toLowerCase(), toStore == null ? 0 : toStore.getPotential());
		nbt.setInteger("stored_" + EnumBeamAlignments.STABILITY.name().toLowerCase(), toStore == null ? 0 : toStore.getStability());
		nbt.setInteger("stored_" + EnumBeamAlignments.VOID.name().toLowerCase(), toStore == null ? 0 : toStore.getVoid());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubItems(ItemGroup tab, NonNullList<ItemStack> list){
		if(isInCreativeTab(tab)){
			list.add(new ItemStack(this, 1));
			ItemStack stack = new ItemStack(this, 1);
			storeBeam(stack, new BeamUnit(CAPACITY, CAPACITY, CAPACITY, CAPACITY));
			list.add(stack);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag advanced){
		BeamUnit stored = getStored(stack);
		tooltip.add("Energy stored: " + (stored == null ? 0 : stored.getEnergy()));
		tooltip.add("Potential stored: " + (stored == null ? 0 : stored.getPotential()));
		tooltip.add("Stability stored: " + (stored == null ? 0 : stored.getStability()));
		tooltip.add("Void stored: " + (stored == null ? 0 : stored.getVoid()));
	}
}
