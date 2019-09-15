package com.Da_Technomancer.crossroads.fluids;

import com.Da_Technomancer.crossroads.Crossroads;
import com.Da_Technomancer.crossroads.blocks.CrossroadsBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockMoltenTin extends BlockFluidClassic{

	protected static final FluidMoltenTin MOLTEN_TIN = new FluidMoltenTin();

	public BlockMoltenTin(){
		super(MOLTEN_TIN, Material.LAVA);
		MOLTEN_TIN.setBlock(this);
		String name = "molten_tin";
		setTranslationKey(name);
		setRegistryName(name);
		CrossroadsBlocks.toRegister.add(this);
	}

	@Override
	public int getLightValue(BlockState state, IBlockAccess world, BlockPos pos){
		return 15;
	}

	/**
	 * For normal use.
	 */
	public static Fluid getMoltenTin(){
		return FluidRegistry.getFluid("tin");
	}

	private static class FluidMoltenTin extends Fluid{

		private FluidMoltenTin(){
			super("tin", new ResourceLocation(Crossroads.MODID + ":blocks/moltentin_still"), new ResourceLocation(Crossroads.MODID + ":blocks/moltentin_flow"));
			setDensity(3000);
			setTemperature(600);
			setViscosity(1300);
		}
	}
}
