package com.Da_Technomancer.crossroads.tileentities.beams;

import com.Da_Technomancer.crossroads.API.beams.BeamUnit;
import com.Da_Technomancer.crossroads.API.templates.BeamRenderTE;
import com.Da_Technomancer.crossroads.blocks.CrossroadsBlocks;
import com.Da_Technomancer.essentials.blocks.EssentialsProperties;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

public class BeamRedirectorTileEntity extends BeamRenderTE{

	private boolean redstone;
	private Direction dir = null;

	private Direction getDir(){
		if(dir == null){
			BlockState state = world.getBlockState(pos);
			if(state.getBlock() != CrossroadsBlocks.beamRedirector){
				return Direction.NORTH;
			}
			dir = state.get(EssentialsProperties.FACING);
		}
		return dir;
	}

	public void setRedstone(boolean redstone){
		if(this.redstone != redstone){
			this.redstone = redstone;
			markDirty();
		}
	}

	@Override
	public void resetBeamer(){
		super.resetBeamer();
		dir = null;
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT nbt){
		super.writeToNBT(nbt);
		nbt.setBoolean("reds", redstone);
		return nbt;
	}

	@Override
	public void readFromNBT(CompoundNBT nbt){
		super.readFromNBT(nbt);
		redstone = nbt.getBoolean("reds");
	}

	@Override
	protected void doEmit(BeamUnit out){
		Direction facing = getDir();
		if(beamer[facing.getIndex()].emit(redstone ? out : null, world)){
			refreshBeam(facing.getIndex());
		}
		if(beamer[facing.getOpposite().getIndex()].emit(redstone ? null : out, world)){
			refreshBeam(facing.getOpposite().getIndex());
		}
	}

	@Override
	protected boolean[] inputSides(){
		boolean[] input = new boolean[] {true, true, true, true, true, true};
		Direction facing = getDir();
		input[facing.getIndex()] = false;
		input[facing.getOpposite().getIndex()] = false;
		return input;
	}

	@Override
	protected boolean[] outputSides(){
		boolean[] output = new boolean[6];
		Direction facing = getDir();
		output[facing.getIndex()] = true;
		output[facing.getOpposite().getIndex()] = true;
		return output;
	}
} 
