package com.Da_Technomancer.crossroads.API.heat;

import com.Da_Technomancer.crossroads.API.effects.BlockEffect;
import com.Da_Technomancer.crossroads.API.effects.DirtEffect;
import com.Da_Technomancer.crossroads.API.effects.IEffect;
import com.Da_Technomancer.crossroads.API.effects.SlimeEffect;
import net.minecraft.block.Blocks;

public enum HeatInsulators{

	WOOL(.25D, 300D, new BlockEffect(Blocks.FIRE.getDefaultState())),
	SLIME(.2D, 500D, new SlimeEffect()),
	DIRT(.5D, 42D, new DirtEffect()),
	ICE(.001D, 0D, new BlockEffect(Blocks.WATER.getDefaultState())),
	OBSIDIAN(.015D, 2_000D, new BlockEffect(Blocks.LAVA.getDefaultState())),
	CERAMIC(.05D, 3_000D, new BlockEffect(Blocks.LAVA.getDefaultState())),
	DENSUS(0, 10_000D, new BlockEffect(Blocks.LAVA.getDefaultState()));

	private final double rate;
	private final double limit;
	private final IEffect effect;

	HeatInsulators(double rate, double limit, IEffect effect){
		this.rate = rate;
		this.limit = limit;
		this.effect = effect;
	}

	public double getRate(){
		return rate;
	}

	public double getLimit(){
		return limit;
	}

	public IEffect getEffect(){
		return effect;
	}

	/**This will return the name with all but the first char being lowercase,
	 * so COPPER becomes Copper, which is good for oreDict and registry
	 */
	@Override
	public String toString(){
		String name = name();
		char char1 = name.charAt(0);
		name = name.substring(1);
		name = name.toLowerCase();
		name = char1 + name;
		return name;
	}
}
