package com.Da_Technomancer.crossroads.blocks.rotary;

import com.Da_Technomancer.crossroads.ambient.particles.CRParticles;
import com.Da_Technomancer.crossroads.api.CRProperties;
import com.Da_Technomancer.crossroads.api.Capabilities;
import com.Da_Technomancer.crossroads.api.MiscUtil;
import com.Da_Technomancer.crossroads.api.templates.ModuleTE;
import com.Da_Technomancer.crossroads.blocks.CRBlocks;
import com.Da_Technomancer.crossroads.blocks.CRTileEntity;
import com.Da_Technomancer.crossroads.effects.beam_effects.PlaceEffect;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class RotaryDrillTileEntity extends ModuleTE{

	public static final BlockEntityType<RotaryDrillTileEntity> TYPE = CRTileEntity.createType(RotaryDrillTileEntity::new, CRBlocks.rotaryDrill, CRBlocks.rotaryDrillGold);

	private static final DamageSource DRILL = new DamageSource("drill");

	public RotaryDrillTileEntity(BlockPos pos, BlockState state){
		super(TYPE, pos, state);
	}

	public static final double ENERGY_USE_IRON = 3D;
	public static final double ENERGY_USE_GOLD = 5D;
	private static final double SPEED_PER_HARDNESS = .2D;
	private static final float DAMAGE_PER_SPEED = 0.5F;
	public static final double[] INERTIA = {50, 100};

	public boolean isGolden(){
		return getBlockState().getBlock() == CRBlocks.rotaryDrillGold;
	}

	@Override
	protected boolean useRotary(){
		return true;
	}

	@Override
	protected double getMoInertia(){
		return INERTIA[isGolden() ? 1 : 0];
	}

	private Direction getFacing(){
		BlockState state = getBlockState();
		if(state.getBlock() instanceof RotaryDrill){
			return state.getValue(CRProperties.FACING);
		}
		setRemoved();
		return Direction.UP;
	}

	@Override
	public void setBlockState(BlockState stateIn){
		super.setBlockState(stateIn);
		axleOpt.invalidate();
		axleOpt = LazyOptional.of(() -> axleHandler);
	}

	@Override
	public void serverTick(){
		super.serverTick();

		double axleSpeed = axleHandler.getSpeed();
		axleHandler.addEnergy(-(isGolden() ? ENERGY_USE_GOLD : ENERGY_USE_IRON), false);
		if(Math.abs(axleSpeed) >= 0.05D){
			if(level.getGameTime() % 4 == 0){//Activate 5 times per second
				Direction facing = getFacing();
				BlockPos targetPos = worldPosition.relative(facing);
				BlockState targetState = level.getBlockState(targetPos);
				if(!targetState.isAir()){
					float hardness = targetState.getDestroySpeed(level, targetPos);
					if(hardness >= 0 && Math.abs(axleSpeed) >= hardness * SPEED_PER_HARDNESS){
						FakePlayer fakePlayer = PlaceEffect.getBlockFakePlayer((ServerLevel) level);
						ItemStack tool = new ItemStack(Items.IRON_PICKAXE);//This shouldn't make a difference as we call the drops method directly, but some blocks may add a tool requirement in the loot table
						level.destroyBlock(targetPos, false);//Don't drop items; we do that separately on the next line
						//Make sure to call through this method, as it is often overriden with extra effects
						//By calling directly, we shortcut any tool requirement that isn't explicitly in the loot table
						targetState.getBlock().playerDestroy(level, fakePlayer, targetPos, targetState, null, tool);
					}else if(level.random.nextInt(5) == 0){
						//Spawn particles to show that the speed is too low to break the block
						CRParticles.summonParticlesFromServer((ServerLevel) level, ParticleTypes.ANGRY_VILLAGER, 1, worldPosition.getX() + 0.5D + facing.getStepX() * 0.5D, worldPosition.getY() + 0.5D + facing.getStepY() * 0.5D, worldPosition.getZ() + 0.5D + facing.getStepZ() * 0.5D, 0.2D, 0.2D, 0.2D, 0, 0, 0, 0, 0, 0, false);
					}
				}

				List<LivingEntity> ents = level.getEntitiesOfClass(LivingEntity.class, new AABB(worldPosition.relative(facing)), EntitySelector.ENTITY_STILL_ALIVE);
				for(LivingEntity ent : ents){
					ent.hurt(isGolden() ? new EntityDamageSource("drill", FakePlayerFactory.get((ServerLevel) level, new GameProfile(null, "drill_player_" + MiscUtil.getDimensionName(level)))) : DRILL, (float) Math.abs(axleHandler.getSpeed()) * DAMAGE_PER_SPEED);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side){
		if(cap == Capabilities.AXLE_CAPABILITY && (side == null || side == getFacing().getOpposite())){
			return (LazyOptional<T>) axleOpt;
		}
		return super.getCapability(cap, side);
	}
}
