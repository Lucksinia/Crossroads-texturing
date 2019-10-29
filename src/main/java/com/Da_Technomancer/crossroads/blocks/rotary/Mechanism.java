package com.Da_Technomancer.crossroads.blocks.rotary;

import com.Da_Technomancer.crossroads.API.rotary.RotaryUtil;
import com.Da_Technomancer.crossroads.blocks.CrossroadsBlocks;
import com.Da_Technomancer.crossroads.tileentities.rotary.mechanisms.MechanismTileEntity;
import com.Da_Technomancer.essentials.EssentialsConfig;
import com.Da_Technomancer.essentials.blocks.redstone.IReadable;
import com.Da_Technomancer.essentials.blocks.redstone.RedstoneUtil;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;

import java.util.ArrayList;
import java.util.List;

public class Mechanism extends ContainerBlock implements IReadable{

	private static final VoxelShape BREAK_ALL_BB = Block.makeCuboidShape(5, 5, 5, 11, 11, 11);

	public Mechanism(){
		super(Block.Properties.create(Material.IRON).hardnessAndResistance(1).sound(SoundType.METAL));
		String name = "mechanism";
		setRegistryName(name);
		CrossroadsBlocks.toRegister.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn){
		return new MechanismTileEntity();
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player){
		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof MechanismTileEntity)){
			return ItemStack.EMPTY;
		}
		MechanismTileEntity mte = (MechanismTileEntity) te;
		Vec3d relVec = target.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());

		for(int i = 0; i < 7; i++){
			if(mte.boundingBoxes[i] != null && voxelContains(mte.boundingBoxes[i], relVec)){
				return mte.members[i].getDrop(mte.mats[i]);
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
		//Used for selection. Adds break all cube if the axle slot is empty
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof MechanismTileEntity){
			MechanismTileEntity mte = (MechanismTileEntity) te;
			if(mte.members[6] != null){
				return getCollisionShape(state, worldIn, pos, context);
			}else{
				return VoxelShapes.or(getCollisionShape(state, worldIn, pos, context), BREAK_ALL_BB);
			}
		}
		return BREAK_ALL_BB;//Shouldn't happen unless network weirdness.
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof MechanismTileEntity){
			MechanismTileEntity mte = (MechanismTileEntity) te;
			VoxelShape shape = VoxelShapes.empty();
			for(VoxelShape s : mte.boundingBoxes){
				if(s != null){
					shape = VoxelShapes.or(s);
				}
			}
			return shape;
		}
		return VoxelShapes.empty();//Shouldn't happen unless network weirdness.
	}

	@Override
	public boolean removedByPlayer(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid){
		RotaryUtil.increaseMasterKey(false);
		if(worldIn.isRemote){
			return false;
		}

		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof MechanismTileEntity){
			MechanismTileEntity gear = (MechanismTileEntity) te;
			float reDist = player.isCreative() ? 5F : 4.5F;
			Vec3d start = new Vec3d(player.prevPosX, player.prevPosY + (double) player.getEyeHeight(), player.prevPosZ).subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
			Vec3d end = start.add(player.getLook(0F).x * reDist, player.getLook(0F).y * reDist, player.getLook(0F).z * reDist);

			int out = getAimedSide(gear, start, end);

			if(out == -1){
				return false;
			}

			if(out == 7){
				if(willHarvest){
					for(int i = 0; i < 7; i++){
						if(gear.members[i] != null){
							spawnAsEntity(worldIn, pos, gear.members[i].getDrop(gear.mats[i]));
						}
					}
				}
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				return true;
			}else{
				if(willHarvest){
					spawnAsEntity(worldIn, pos, gear.members[out].getDrop(gear.mats[out]));
				}
				gear.setMechanism(out, null, null, null, false);
				if(gear.members[0] == null && gear.members[1] == null && gear.members[2] == null && gear.members[3] == null && gear.members[4] == null && gear.members[5] == null && gear.members[6] == null){
					worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
					return true;
				}

				return false;
			}
		}else{
			return super.removedByPlayer(state, worldIn, pos, player, willHarvest, fluid);
		}
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder){
		ArrayList<ItemStack> drops = new ArrayList<>();
		TileEntity te = builder.get(LootParameters.BLOCK_ENTITY);
		if(te instanceof MechanismTileEntity){
			MechanismTileEntity mte = (MechanismTileEntity) te;
			for(int i = 0; i < 7; i++){
				if(mte.members[i] != null){
					drops.add(mte.members[i].getDrop(mte.mats[i]));
				}
			}
		}
		return drops;
	}

	/**
	 *
	 * @param te The TileEntity aimed at
	 * @param start Start vector, subtract position first
	 * @param end End vector, subtract position first
	 * @return The index of the aimed component, 7 if the breakall cube, -1 if none
	 */
	private int getAimedSide(MechanismTileEntity te, Vec3d start, Vec3d end){
		for(int i = 0; i < te.boundingBoxes.length; i++){
			if(te.boundingBoxes[i] != null && te.boundingBoxes[i].rayTrace(start, end, BlockPos.ZERO) != null){
				return i;
			}
		}

		//Include break-all-cube if no axle
		if(te.boundingBoxes[6] == null && BREAK_ALL_BB.rayTrace(start, end, BlockPos.ZERO) != null){
			return 7;
		}
		return -1;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack){
		neighborChanged(state, world, pos, this, pos, false);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving){
		RotaryUtil.increaseMasterKey(true);//

		if(worldIn.isRemote){
			return;
		}

		TileEntity rawTE = worldIn.getTileEntity(pos);
		if(!(rawTE instanceof MechanismTileEntity)){
			return;
		}
		MechanismTileEntity te = (MechanismTileEntity) rawTE;

		for(Direction side : Direction.values()){
			if(te.members[side.getIndex()] != null && !RotaryUtil.solidToGears(worldIn, pos.offset(side), side.getOpposite())){
				spawnAsEntity(worldIn, pos, te.members[side.getIndex()].getDrop(te.mats[side.getIndex()]));
				te.setMechanism(side.getIndex(), null, null, null, false);
			}
		}
		if(te.members[0] == null && te.members[1] == null && te.members[2] == null && te.members[3] == null && te.members[4] == null && te.members[5] == null && te.members[6] == null){
			worldIn.destroyBlock(pos, false);
		}

		te.updateRedstone();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state){
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit){
		if(EssentialsConfig.isWrench(playerIn.getHeldItem(hand))){
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof MechanismTileEntity){
				MechanismTileEntity mte = (MechanismTileEntity) te;
				if(mte.axleAxis != null){
					RotaryUtil.increaseMasterKey(false);
					if(!worldIn.isRemote){
						mte.setMechanism(6, mte.members[6], mte.mats[6], Direction.Axis.values()[(mte.axleAxis.ordinal() + 1) % 3], false);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasComparatorInputOverride(BlockState state){
		return true;
	}

	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos){
		TileEntity te = worldIn.getTileEntity(pos);
		return te instanceof MechanismTileEntity ? RedstoneUtil.clampToVanilla(((MechanismTileEntity) te).getRedstone()) : 0;
	}

	@Override
	public float read(World world, BlockPos pos, BlockState blockState){
		TileEntity te = world.getTileEntity(pos);
		return te instanceof MechanismTileEntity ? ((MechanismTileEntity) te).getRedstone() : 0;
	}

	/**
	 * You would think this would be built into the VoxelShape class, but noooooooo
	 * "Contans" is defined as either inside the shape, or on the edge of the shape
	 * @param shape The voxelshape to check if contains the passed point
	 * @param point The 3 dimensional point to check if is contained by the passed shape
	 * @return Whether the passed VoxelShape contains the passed point
	 */
	public static boolean voxelContains(VoxelShape shape, Vec3d point){
		final boolean[] contained = new boolean[1];//We use a size 1 array because lambdas aren't supposed to use non-final fields
		shape.forEachBox((double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) -> {
			if(!contained[0]){
				contained[0] = xMin <= point.x && xMax >= point.x && yMin <= point.y && yMax >= point.y && zMin <= point.z && zMax >= point.z;
			}
		});
		return contained[0];
	}
}
