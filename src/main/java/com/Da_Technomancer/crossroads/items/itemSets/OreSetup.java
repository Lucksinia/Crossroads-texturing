package com.Da_Technomancer.crossroads.items.itemSets;

import com.Da_Technomancer.crossroads.API.EnergyConverters;
import com.Da_Technomancer.crossroads.API.MiscUtil;
import com.Da_Technomancer.crossroads.Main;
import com.Da_Technomancer.crossroads.ModConfig;
import com.Da_Technomancer.crossroads.blocks.BasicBlock;
import com.Da_Technomancer.crossroads.blocks.ModBlocks;
import com.Da_Technomancer.crossroads.items.BasicItem;
import com.Da_Technomancer.crossroads.items.ModItems;
import com.Da_Technomancer.crossroads.items.crafting.ItemRecipePredicate;
import com.Da_Technomancer.crossroads.items.crafting.ModCrafting;
import com.Da_Technomancer.crossroads.items.crafting.OreDictCraftingStack;
import com.Da_Technomancer.crossroads.items.crafting.RecipeHolder;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public final class OreSetup{

	public static BasicItem ingotTin;
	public static BasicItem nuggetTin;
	public static BasicBlock blockTin;
	public static BasicBlock oreTin;

	public static BasicItem ingotCopper;
	public static BasicItem nuggetCopper;
	public static BasicBlock blockCopper;
	public static BasicBlock oreCopper;

	public static BasicItem ingotBronze;
	public static BasicItem nuggetBronze;
	public static BasicBlock blockBronze;

	public static BasicItem gemRuby;
	public static BasicBlock blockRuby;
	public static BasicBlock oreRuby;

	public static BasicItem ingotCopshowium;
	public static BasicItem nuggetCopshowium;
	public static BasicBlock blockCopshowium;

	public static BasicItem voidCrystal;
	public static BasicBlock oreVoid;

	public static final HashMap<String, OreProfile> metalStages = new HashMap<>();

	protected static void init(){
		//Register CR metal ores, blocks, ingots, nuggets manually
		ingotTin = new BasicItem("ingot_tin", "ingotTin");
		blockTin = new BasicBlock("block_tin", Material.IRON, 5, "blockTin"){
			@Override
			public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon){
				return true;
			}
		};
		nuggetTin = new BasicItem("nugget_tin", "nuggetTin");
		oreTin = new BasicBlock("ore_tin", Material.ROCK, 3, "oreTin");

		ingotCopper = new BasicItem("ingot_copper", "ingotCopper");
		blockCopper = new BasicBlock("block_copper", Material.IRON, 5, "blockCopper"){
			@Override
			public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon){
				return true;
			}
		};
		nuggetCopper = new BasicItem("nugget_copper", "nuggetCopper");
		oreCopper = new BasicBlock("ore_copper", Material.ROCK, 3, "oreCopper");

		ingotBronze = new BasicItem("ingot_bronze", "ingotBronze");
		blockBronze = new BasicBlock("block_bronze", Material.IRON, 5, "blockBronze"){
			@Override
			public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon){
				return true;
			}
		};
		nuggetBronze = new BasicItem("nugget_bronze", "nuggetBronze");

		gemRuby = new BasicItem("gem_ruby", "gemRuby");
		blockRuby = new BasicBlock("block_ruby", Material.ROCK, 5, "blockRuby"){
			@Override
			public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon){
				return true;
			}
		};
		oreRuby = new BasicBlock("ore_ruby", Material.ROCK, 3, "oreRuby"){
			@Override
			public int quantityDroppedWithBonus(int fortune, Random random){
				if(fortune > 0){
					return Math.max(random.nextInt(fortune + 2) - 1, 0) + 1;
				}
				return 1;
			}

			@Override
			public Item getItemDropped(IBlockState state, Random rand, int fortune){
				return gemRuby;
			}
		};

		ingotCopshowium = new BasicItem("ingot_copshowium", "ingotCopshowium");
		blockCopshowium = new BasicBlock("block_copshowium", Material.IRON, 5, "blockCopshowium"){
			@Override
			public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon){
				return true;
			}
		};
		nuggetCopshowium = new BasicItem("nugget_copshowium", "nuggetCopshowium");

		voidCrystal = new BasicItem("void_crystal");
		oreVoid = new BasicBlock("ore_void", Material.ROCK, 3){
			@Override
			public int quantityDroppedWithBonus(int fortune, Random random){
				if(fortune > 0){
					return Math.max(random.nextInt(fortune + 2) - 1, 0) + 1;
				}
				return 1;
			}

			@Override
			public Item getItemDropped(IBlockState state, Random rand, int fortune){
				return voidCrystal;
			}
		};


		String[] rawInput = ModConfig.getConfigStringList(ModConfig.processableOres, true);

		//It's a HashMap instead of an ArrayList just in case a user decides to (incorrectly) list a metal twice
		HashMap<String, Color> metals = new HashMap<>(rawInput.length);
		Pattern pattern = Pattern.compile("\\w++ \\p{XDigit}{6}+");

		for(String raw : rawInput){
			//An enormous amount of input sanitization is involved here because the average config tweaker is slightly better at following instructions than the average walrus. And not one of those clever performing walruses (walri?) in aquariums, but a stupid walrus
			//Unless of course you're reading this because you're having trouble editing the config option, in which was you are way smarter than a clever walrus, thoroughly above average, and a genius, and the insults above definitely don't apply to you

			//Check for stupid whitespace
			raw = raw.trim();
			//Check the basic structure
			if(!pattern.matcher(raw).matches()){
				continue;
			}
			int spaceIndex = raw.length() - 7;
			String metal = "" + Character.toUpperCase(raw.charAt(0));
			Color col;
			//Make sure they aren't trying to register a one character metal
			//First character is capitalized for OreDict
			metal += raw.substring(1, spaceIndex);

			String colorString = '#' + raw.substring(spaceIndex + 1);
			try{
				col = Color.decode(colorString);
			}catch(NumberFormatException e){
				//Pick a random color because the user messed up, and if the user ends up with hot-pink lead that's their problem
				col = Color.getHSBColor((float) Math.random(), 1F, 1F);
			}

			//We survived user-input sanitization hell! Hazah!
			//This for-loop could have been like four lines if we could trust users to not ram flaming knives up their own bums and then blame the devs when they get mocked in the ER
			metals.put(metal, col);
		}

		ModelResourceLocation dustModel = new ModelResourceLocation(Main.MODID + ":ore_dust", "inventory");
		ModelResourceLocation gravelModel = new ModelResourceLocation(Main.MODID + ":ore_gravel", "inventory");
		ModelResourceLocation clumpModel = new ModelResourceLocation(Main.MODID + ":ore_clump", "inventory");

		for(Map.Entry<String, Color> type : metals.entrySet()){
			String lowercaseMetal = type.getKey().toLowerCase();


			//Register dust, clump, gravel, and liquid
			Item dust = new Item(){
				@Override
				public String getItemStackDisplayName(ItemStack stack){
					return String.format(super.getItemStackDisplayName(stack), getMatName(type.getKey()));
				}
			}.setRegistryName(Main.MODID, "dust_" + lowercaseMetal).setCreativeTab(ModItems.TAB_CROSSROADS).setTranslationKey("dust_metal");
			ModItems.toRegister.add(dust);
			ModItems.toClientRegister.put(Pair.of(dust, 0), dustModel);
			ModCrafting.toRegisterOreDict.add(Pair.of(dust, new String[] {"dust" + type.getKey()}));
			Item gravel = new Item(){
				@Override
				public String getItemStackDisplayName(ItemStack stack){
					return String.format(super.getItemStackDisplayName(stack), getMatName(type.getKey()));
				}
			}.setRegistryName(Main.MODID, "gravel_" + lowercaseMetal).setCreativeTab(ModItems.TAB_CROSSROADS).setTranslationKey("gravel_metal");
			ModItems.toRegister.add(gravel);
			ModItems.toClientRegister.put(Pair.of(gravel, 0), gravelModel);
			Item clump = new Item(){
				@Override
				public String getItemStackDisplayName(ItemStack stack){
					return String.format(super.getItemStackDisplayName(stack), getMatName(type.getKey()));
				}
			}.setRegistryName(Main.MODID, "clump_" + lowercaseMetal).setCreativeTab(ModItems.TAB_CROSSROADS).setTranslationKey("clump_metal");
			ModItems.toRegister.add(clump);
			ModItems.toClientRegister.put(Pair.of(clump, 0), clumpModel);

			Fluid fluid = new Fluid(lowercaseMetal, new ResourceLocation(Main.MODID, "blocks/molten_metal_still"), new ResourceLocation(Main.MODID, "blocks/molten_metal_flow")){
				@Override
				public String getLocalizedName(FluidStack stack){
					return String.format(super.getLocalizedName(stack), getMatName(type.getKey()));
				}
			}.setDensity(3000).setTemperature(1500).setLuminosity(15).setViscosity(1300).setColor(type.getValue()).setUnlocalizedName("molten_metal");
			FluidRegistry.registerFluid(fluid);
			BlockFluidClassic fluidBlock = (BlockFluidClassic) new BlockFluidClassic(fluid, Material.LAVA){
				@Override
				public String getLocalizedName(){
					return String.format(super.getLocalizedName(), getMatName(type.getKey()));
				}
			}.setTranslationKey("molten_metal").setRegistryName(Main.MODID + ":molten_metal_" + lowercaseMetal);
			fluid.setBlock(fluidBlock);
			FluidRegistry.addBucketForFluid(fluid);
			ModBlocks.toRegister.add(fluidBlock);


			RecipeHolder.millRecipes.put(new OreDictCraftingStack("ore" + type.getKey()), new ItemStack[] {new ItemStack(dust, 2), new ItemStack(Blocks.SAND, 1)});
			RecipeHolder.millRecipes.put(new OreDictCraftingStack("ingot" + type.getKey()), new ItemStack[] {new ItemStack(dust, 1)});
			RecipeHolder.crucibleRecipes.put(new OreDictCraftingStack("ingot" + type.getKey()), new FluidStack(fluid, EnergyConverters.INGOT_MB));
			RecipeHolder.crucibleRecipes.put(new OreDictCraftingStack("nugget" + type.getKey()), new FluidStack(fluid, EnergyConverters.INGOT_MB / 9));
			RecipeHolder.crucibleRecipes.put(new OreDictCraftingStack("dust" + type.getKey()), new FluidStack(fluid, EnergyConverters.INGOT_MB));
			RecipeHolder.crucibleRecipes.put(new OreDictCraftingStack("ore" + type.getKey()), new FluidStack(fluid, 2 * EnergyConverters.INGOT_MB));
			RecipeHolder.stampMillRecipes.put(new OreDictCraftingStack("ore" + type.getKey()), new ItemStack(gravel, 3));
			RecipeHolder.oreCleanserRecipes.put(new ItemRecipePredicate(gravel, 0), new ItemStack(clump, 1));
			RecipeHolder.blastFurnaceRecipes.put(new ItemRecipePredicate(gravel, 0), Pair.of(new FluidStack(fluid, EnergyConverters.INGOT_MB), 2));
			RecipeHolder.blastFurnaceRecipes.put(new ItemRecipePredicate(clump, 0), Pair.of(new FluidStack(fluid, EnergyConverters.INGOT_MB), 1));

			OreProfile profile = new OreProfile(dust, gravel, clump, fluid, fluidBlock, type.getValue());
			metalStages.put(type.getKey(), profile);
		}
	}

	protected static void initCrafting(){
		for(Map.Entry<String, OreProfile> ent : metalStages.entrySet()){
			if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
				registerColor(ent.getValue());
			}

			ItemStack ingot = MiscUtil.getOredictStack("ingot" + ent.getKey(), 1);
			if(!ingot.isEmpty()){
				GameRegistry.addSmelting(new ItemStack(ent.getValue().dust, 1), ingot, .7F);
				RecipeHolder.fluidCoolingRecipes.put(ent.getValue().molten, Pair.of(EnergyConverters.INGOT_MB, Triple.of(ingot, 1500D, 250D)));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private static void registerColor(OreProfile profile){
		IItemColor itemColoring = (ItemStack stack, int tintIndex) -> tintIndex == 0 ? profile.col.getRGB() : -1;
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColoring, profile.dust, profile.gravel, profile.clump);
	}

	public static class OreProfile{

		public final Item dust;
		public final Item gravel;
		public final Item clump;
		public final Fluid molten;
		public final BlockFluidClassic moltenBlock;
		public final Color col;


		private OreProfile(Item dust, Item gravel, Item clump, Fluid molten, BlockFluidClassic fluidBlock, Color col){
			this.dust = dust;
			this.gravel = gravel;
			this.clump = clump;
			this.molten = molten;
			this.moltenBlock = fluidBlock;
			this.col = col;
		}
	}

	private static String getMatName(String oreName){
		//So this is my mad scheme for getting the material names for things defined via config
		//Step 1: Assume it has an ingot registered in the oreDict
		//Step 2: Get that ingot's name
		//Step 3: Assume the name takes the format "matName ingot"
		//Step 4: Hope this works in other languages
		ItemStack ingot = MiscUtil.getOredictStack("ingot" + oreName, 1);
		if(!ingot.isEmpty()){
			String ingotName = ingot.getItem().getItemStackDisplayName(ingot);
			return ingotName.substring(0, ingotName.lastIndexOf(' ')).trim();
		}

		//Default to returning the oreName back, because atleast it's something
		return oreName;
	}
}
