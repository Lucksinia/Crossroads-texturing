package com.Da_Technomancer.crossroads.api.beams;

import com.Da_Technomancer.crossroads.api.MiscUtil;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Arrays;

/**
 * An immutable class that represents one beam pulse lasting one cycle. It stores the energy, potential, stability, and void values and has several helper methods
 * For a mutable version, see BeamUnitStorage
 */
public class BeamUnit{

	public static final BeamUnit EMPTY = new BeamUnit(0, 0, 0, 0);

	private final int[] contents = new int[4];//0: Energy, 1: Potential, 2: stability, 3: Void

	private EnumBeamAlignments alignmentCache;

	public BeamUnit(int[] magic){
		this(magic[0], magic[1], magic[2], magic[3]);
	}

	public BeamUnit(int energy, int potential, int stability, int voi){
		contents[0] = energy;
		contents[1] = potential;
		contents[2] = stability;
		contents[3] = voi;

		if(energy < 0 || potential < 0 || stability < 0 || voi < 0){
			throw new IllegalArgumentException("Negative BeamUnit input! EN: " + energy + "; PO: " + potential + "; ST: " + stability + "; VO: " + voi);
		}
	}

	public int getEnergy(){
		return contents[0];
	}

	public int getPotential(){
		return contents[1];
	}

	public int getStability(){
		return contents[2];
	}

	public int getVoid(){
		return contents[3];
	}

	public int getPower(){
		return contents[0] + contents[1] + contents[2] + contents[3];
	}

	public boolean isEmpty(){
		return contents[0] == 0 && contents[1] == 0 && contents[2] == 0 && contents[3] == 0;
	}

	/**
	 * @return A size four array containing energy, potential, stability, and void in that order. Changes to the array will not write back to the BeamUnit
	 */
	public int[] getValues(){
		return Arrays.copyOf(contents, 4);
	}

	/**
	 * Returns the RGB value when ignoring void
	 * @return The color of this beam if void were 0. Value of the returned color is 1 unless the beam is pure void or empty
	 */
	@Nonnull
	public Color getValuedRGB(){
		if(contents[0] == 0 && contents[1] == 0 && contents[2] == 0){
			return Color.BLACK;
		}
		double top = Math.max(contents[0], Math.max(contents[1], contents[2]));

		return new Color((int) Math.round(255D * ((double) contents[0]) / top), (int) Math.round(255D * ((double) contents[1]) / top), (int) Math.round(255D * ((double) contents[2]) / top));
	}

	/**
	 * Returns RGB with void.
	 * @return The color of this beam
	 */
	@Nonnull
	public Color getRGB(){
		if(contents[0] == 0 && contents[1] == 0 && contents[2] == 0){
			return Color.BLACK;
		}

		double mult = ((double) (contents[0] + contents[1] + contents[2])) / (double) getPower();

		Color col = getValuedRGB();
		return new Color((int) Math.round(((double) col.getRed()) * mult), (int) Math.round(((double) col.getGreen()) * mult), (int) Math.round(((double) col.getBlue()) * mult));
	}

	@Nonnull
	public EnumBeamAlignments getAlignment(){
		if(alignmentCache == null){
			if(isEmpty()){
				alignmentCache = EnumBeamAlignments.NO_MATCH;
			}else{
				alignmentCache = EnumBeamAlignments.getAlignment(getValuedRGB());
			}
		}
		return alignmentCache;
	}

	public BeamUnit mult(double multiplier, boolean floor){
		return mult(multiplier, multiplier, multiplier, multiplier, floor);
	}

	public BeamUnit mult(double e, double p, double s, double v, boolean floor){
		return floor ? new BeamUnit((int) Math.floor(e * (double) contents[0]), (int) Math.floor(p * (double) contents[1]), (int) Math.floor(s * (double) contents[2]), (int) Math.floor(v * (double) contents[3])) : new BeamUnit(MiscUtil.safeRound(e * (double) contents[0]), MiscUtil.safeRound(p * (double) contents[1]), MiscUtil.safeRound(s * (double) contents[2]), MiscUtil.safeRound(v * (double) contents[3]));
	}

	@Override
	public String toString(){
		if(isEmpty()){
			return "En: 0; Po: 0; St: 0; Vo: 0";
		}
		Color col = getRGB();
		return EnumBeamAlignments.getAlignment(this).getLocalName(contents[3] != 0) + "-R: " + col.getRed() + ", G: " + col.getGreen() + ", B: " + col.getBlue() + "-En: " + contents[0] + ", Po: " + contents[1] + ", St: " + contents[2] + ", Vo: " + contents[3];
	}

	@Override
	public boolean equals(Object other){
		if(other instanceof BeamUnit o){
			return o == this || o.contents[0] == contents[0] && o.contents[1] == contents[1] && o.contents[2] == contents[2] && o.contents[3] == contents[3];
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return ((contents[0] & 0xFF) << 24) + ((contents[1] & 0xFF) << 16) + ((contents[2] & 0xFF) << 8) + (contents[3] & 0xFF);
	}

	public void writeToNBT(@Nonnull String key, CompoundTag nbt){
		nbt.putIntArray(key, contents);
	}

	public static BeamUnit readFromNBT(@Nonnull String key, CompoundTag nbt){
		if(nbt.contains(key)){
			return new BeamUnit(nbt.getIntArray(key));
		}
		return BeamUnit.EMPTY;
	}
}
