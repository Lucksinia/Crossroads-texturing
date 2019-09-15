package com.Da_Technomancer.crossroads.integration.GuideAPI;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageImage;
import amerifrance.guideapi.page.PageText;
import com.Da_Technomancer.crossroads.Crossroads;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * TODO I intend to re-write/re-format most of the entries in the Technician's Manual to be better organized and more concise. This will not happen all in one update.
 */
public final class GuideBooks{

	public static final BookBinder MAIN = new BookBinder(new ResourceLocation(Crossroads.MODID, "crossroadsmainguide"));
	public static final BookBinder INFO = new BookBinder(new ResourceLocation(Crossroads.MODID, "info_guide"));
	public static Book MAIN_BOOK;
	public static Book INFO_BOOK;

	@GuideBook
	public static class MainGuide implements IGuideBook{

		@Nullable
		@Override
		public Book buildBook(){

			// Technomancer = normal (§r§r), use the double §r to make createPages() work
			// Witch = underline (§r§n)
			// Alchemist = italic (§r§o)
			// Bobo = bold (§r§l)
			// The § symbol can be typed by holding alt and typing 0167 on the numpad, then releasing alt.

			LinkedHashMap<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
			ArrayList<IPage> pages = new ArrayList<IPage>();
			ArrayList<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();

//			// INTRO
//			entries.put(new ResourceLocation(Crossroads.MODID, "first_read"), new SmartEntry("READ ME FIRST", new ItemStack(Items.BOOK, 1), "lore.first_read"));
//			createPages(pages, "lore.intro.start", new ShapelessOreRecipe(null, Items.WRITTEN_BOOK, Items.BOOK, "ingotIron"), "lore.intro.end");
//			entries.put(new ResourceLocation(Crossroads.MODID, "intro"), new EntryItemStack(pages, "Introduction", new ItemStack(Items.PAPER, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.ores.start", new ResourceLocation(Crossroads.MODID, "textures/blocks/ore_native_copper.png"), "lore.ores.copper", new ResourceLocation(Crossroads.MODID, "textures/blocks/ore_copper.png"), "lore.ores.tin", new ResourceLocation(Crossroads.MODID, "textures/blocks/ore_tin.png"), "lore.ores.ruby", new ResourceLocation(Crossroads.MODID, "textures/blocks/ore_ruby.png"), "lore.ores.bronze", new ShapedOreRecipe(null, OreSetup.ingotBronze, "###", "#$#", "###", '#', "nuggetCopper", '$', "nuggetTin"), new ShapedOreRecipe(null, OreSetup.blockBronze, "###", "#$#", "###", '#', "ingotCopper", '$', "ingotTin"), new ShapedOreRecipe(null, new ItemStack(OreSetup.blockBronze, 9), "###", "#?#", "###", '#', "blockCopper", '?', "blockTin"), "lore.ores.salt", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.blockSalt, 1), "##", "##", '#', "dustSalt"), new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.dustSalt, 4), "#", '#', "blockSalt"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "ores"), new EntryItemStack(pages, "Ores", new ItemStack(OreSetup.ingotCopper, 1), true));
//			pages = new ArrayList<IPage>();
//
//			entries.put(new ResourceLocation(Crossroads.MODID, "energy"), new SmartEntry("lore.energy.name", new ItemStack(CrossroadsItems.omnimeter, 1), "lore.energy"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heat"), new SmartEntry("lore.heat.name", new ItemStack(CrossroadsBlocks.firebox, 1), "lore.heat.start", ((Supplier<Object>) () -> CrossroadsConfig.getConfigBool(CrossroadsConfig.heatEffects, true) ? "lore.heat.insulator" : "lore.heat.insulator_effect_disable"), "lore.heat.end", "lore.heat.bobo"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "steam"), new SmartEntry("lore.steam.name", new ItemStack(CrossroadsBlocks.fluidTube, 1), Pair.of("lore.steam", new Object[] {Math.round(EnergyConverters.degPerSteamBucket(true) * 1.1D), EnergyConverters.degPerSteamBucket(true)})));
//			//entries.put(new ResourceLocation(Crossroads.MODID, "rotary"), new SmartEntry("lore.rotary.name", new ItemStack(GearFactory.BASIC_GEARS[GearFactory.GearMaterial.BRONZE.ordinal()], 1), "lore.rotary"));
//			createPages(pages, "lore.copper", new ResourceLocation(Crossroads.MODID, "textures/book/copper_process.png"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "copper"), new EntryItemStack(pages, "Copper Processing", new ItemStack(OreSetup.ingotCopper, 1), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "intro_path"), new SmartEntry("lore.intro_path", new ItemStack(CrossroadsItems.watch), "lore.intro_path.start", (Supplier<Object>) () -> {return StoreNBTToClient.clientPlayerTag.getBoolean("multiplayer") ? CrossroadsConfig.getConfigBool(CrossroadsConfig.allowAllServer, true) ? "lore.intro_path.locked" : null : CrossroadsConfig.getConfigBool(CrossroadsConfig.allowAllSingle, true) ? "lore.intro_path.locked" : null;}, "lore.intro_path.continue", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.detailedCrafter, 1), "*^*", "^&^", "*^*", '*', "ingotIron", '^', "ingotTin", '&', Blocks.CRAFTING_TABLE), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.detailedCrafter, 1), "*&*", "&#&", "*&*", '*', "nuggetIron", '&', "nuggetTin", '#', Blocks.CRAFTING_TABLE), 0)));
//
//			categories.add(new CategoryItemStack(entries, "The Basics", new ItemStack(OreSetup.oreCopper, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			// HEAT
//			entries.put(new ResourceLocation(Crossroads.MODID, "fuel_heater"), new SmartEntry("lore.fuel_heater", new ItemStack(CrossroadsBlocks.firebox, 1), "lore.fuel_heater.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.firebox, 1), "#*#", "# #", "###", '#', "cobblestone", '*', "ingotCopper")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heating_chamber"), new SmartEntry("lore.heating_chamber", new ItemStack(CrossroadsBlocks.smelter, 1), "lore.heating_chamber.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.smelter, 1), "#*#", "# #", "###", '#', "ingotIron", '*', "ingotCopper")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heat_exchanger"), new SmartEntry("lore.heat_exchanger", new ItemStack(CrossroadsBlocks.heatSink, 1), "lore.heat_exchanger.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.heatSink, 1), "#$#", "$$$", "###", '#', Blocks.IRON_BARS, '$', "ingotCopper")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heating_crucible"), new SmartEntry("lore.heating_crucible", new ItemStack(CrossroadsBlocks.smelter, 1), "lore.heating_crucible.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.heatingCrucible, 1), "# #", "#?#", "###", '#', Blocks.HARDENED_CLAY, '?', Items.CAULDRON)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "fluid_cooling"), new SmartEntry("lore.fluid_cooling", new ItemStack(CrossroadsBlocks.fluidCoolingChamber, 1), "lore.fluid_cooling.text",  new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fluidCoolingChamber, 1), "###", "# #", "#%#", '#', "ingotIron", '%', "ingotCopper")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "redstone_cable"), new SmartEntry("lore.redstone_cable", new ItemStack(Items.REDSTONE, 1), "lore.redstone_cable.text", new ShapedOreRecipe(null, new ItemStack(HeatCableFactory.REDSTONE_HEAT_CABLES.get(HeatInsulators.WOOL), 1), "###", "#?#", "###", '#', "dustRedstone", '?', HeatCableFactory.HEAT_CABLES.get(HeatInsulators.WOOL))));
//			entries.put(new ResourceLocation(Crossroads.MODID, "salt_reactor"), new SmartEntry("lore.salt_reactor", new ItemStack(CrossroadsBlocks.saltReactor, 1), "lore.salt_reactor.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.saltReactor, 1), "#$#", "$%$", "#@#", '#', "ingotIron", '$', CrossroadsBlocks.fluidTube, '%', "blockSalt", '@', "ingotCopper")));
//
//			categories.add(new CategoryItemStack(entries, "Heat Machines", new ItemStack(CrossroadsBlocks.smelter, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			// ROTARY
//			createPages(pages, "lore.millstone.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.millstone, 1), "#$#", "#?#", "#$#", '#', "cobblestone", '?', "stickIron", '$', Blocks.PISTON), "lore.millstone.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "millstone"), new EntryItemStack(pages, "Millstone", new ItemStack(CrossroadsBlocks.millstone, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.drill.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.rotaryDrill, 2), " * ", "*#*", '*', "ingotIron", '#', "blockIron"), "lore.drill.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "drill"), new EntryItemStack(pages, "Rotary Drill", new ItemStack(CrossroadsBlocks.rotaryDrill, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.toggle_gear.pre_recipe", new ShapelessOreRecipe(null, new ItemStack(GearFactory.TOGGLE_GEARS[GearFactory.GearMaterial.GOLD.ordinal()], 1), "dustRedstone", "dustRedstone", "stickIron", GearFactory.BASIC_GEARS[GearFactory.GearMaterial.GOLD.ordinal()]), "lore.toggle_gear.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "toggle_gear"), new EntryItemStack(pages, "Toggle Gear", new ItemStack(Items.REDSTONE, 1), true));
//			pages = new ArrayList<IPage>();
//
//			categories.add(new CategoryItemStack(entries, "Rotary Machines", new ItemStack(GearFactory.BASIC_GEARS[GearFactory.GearMaterial.BRONZE.ordinal()], 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			// FLUIDS
//			createPages(pages, "lore.rotary_pump.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.rotaryPump, 1), "#$#", "#$#", "&$&", '#', "ingotBronze", '&', "blockGlass", '$', "stickIron"), "lore.rotary_pump.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "rotary_pump"), new EntryItemStack(pages, "Rotary Pump", new ItemStack(CrossroadsBlocks.rotaryPump, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.steam_turbine.pre_recipe", new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.steamTurbine, 1), CrossroadsBlocks.rotaryPump), new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.rotaryPump, 1), CrossroadsBlocks.steamTurbine), Pair.of("lore.steam_turbine.post_recipe", new Object[] {EnergyConverters.degPerSteamBucket(true) / EnergyConverters.degPerJoule(true)}));
//			entries.put(new ResourceLocation(Crossroads.MODID, "steam_turbine"), new EntryItemStack(pages, "Steam Turbine", new ItemStack(CrossroadsBlocks.steamTurbine, 1), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "radiator"), new SmartEntry("lore.radiator.name", new ItemStack(CrossroadsBlocks.radiator, 1), Pair.of("lore.radiator", new Object[] {EnergyConverters.degPerSteamBucket(true)}), new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.radiator, 1), "#$#", "#$#", "#$#", '#', CrossroadsBlocks.fluidTube, '$', "ingotIron"), ((Supplier<Object>) () -> {return CrossroadsConfig.getConfigBool(CrossroadsConfig.weatherControl, true) ? "lore.radiator.bobo_rain_idol" : null;})));
//			createPages(pages, "lore.liquid_fat.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fatCollector, 1), "***", "# #", "*&*", '*', "ingotBronze", '#', "netherrack", '&', "ingotCopper"), Pair.of("lore.liquid_fat.mid_recipe", new Object[] {EnergyConverters.FAT_PER_VALUE}), new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fatCongealer, 1), "*^*", "# #", "* *", '*', "ingotBronze", '#', "netherrack", '^', "stickIron"), "lore.liquid_fat.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "liquid_fat"), new EntryItemStack(pages, "Basics of Liquid Fat", new ItemStack(CrossroadsItems.edibleBlob, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.fat_feeder.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fatFeeder, 1), "*^*", "#&#", "*^*", '*', "ingotBronze", '#', "netherrack", '^', "stickIron", '&', "ingotTin"), Pair.of("lore.fat_feeder.post_recipe", new Object[]{EnergyConverters.FAT_PER_VALUE}));
//			entries.put(new ResourceLocation(Crossroads.MODID, "fat_feeder"), new EntryItemStack(pages, "Fat Feeder", new ItemStack(CrossroadsBlocks.fatFeeder, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.redstone_tube", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.redstoneFluidTube, 1), "***", "*&*", "***", '*', "dustRedstone", '&', CrossroadsBlocks.fluidTube));
//			entries.put(new ResourceLocation(Crossroads.MODID, "redstone_tube"), new EntryItemStack(pages, "Redstone Integration-Fluids", new ItemStack(Items.REDSTONE), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "fluid_splitter"), new SmartEntry("lore.fluid_splitter.name", new ItemStack(CrossroadsBlocks.fluidSplitter, 1), "lore.fluid_splitter", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.basicFluidSplitter, 1), "*^*", "&&&", "*^*", '*', "nuggetTin", '^', CrossroadsBlocks.fluidTube, '&', "ingotBronze"), new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.fluidSplitter, 1), CrossroadsBlocks.basicFluidSplitter, "dustRedstone", "dustRedstone", "dustRedstone")));
//			createPages(pages, "lore.water_centrifuge.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.waterCentrifuge, 1), "*&*", "^%^", "* *", '*', "ingotBronze", '&', "stickIron", '^', CrossroadsBlocks.fluidTube, '%', "ingotTin"), "lore.water_centrifuge.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "water_centrifuge"), new EntryItemStack(pages, "Water Centrifuge", new ItemStack(CrossroadsBlocks.waterCentrifuge), true));
//			pages = new ArrayList<IPage>();
//
//			categories.add(new CategoryItemStack(entries, "Fluid Machines", new ItemStack(CrossroadsBlocks.fluidTube, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			// MISC
//			entries.put(new ResourceLocation(Crossroads.MODID, "brazier"), new SmartEntry("lore.brazier.name", new ItemStack(EssentialsBlocks.brazier, 1), (Supplier<Object>) (() -> EssentialsConfig.getConfigInt(EssentialsConfig.brazierRange, true) == 0 ? "lore.brazier.no_witch" : Pair.of("lore.brazier.normal", new Object[] {EssentialsConfig.getConfigInt(EssentialsConfig.brazierRange, true)}))));
//			entries.put(new ResourceLocation(Crossroads.MODID, "item_sorting"), new SmartEntry("lore.item_sorting.name", new ItemStack(EssentialsBlocks.sortingHopper, 1), "lore.item_sorting"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "wrench"), new SmartEntry("lore.wrench.name", new ItemStack(EssentialsItems.wrench, 1), "lore.wrench"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "ob_cutting"), new SmartEntry("lore.ob_cutting.name", new ItemStack(EssentialsItems.obsidianKit, 1), "lore.ob_cutting"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "decorative"), new SmartEntry("lore.decorative.name", new ItemStack(EssentialsBlocks.candleLilyPad, 1), "lore.decorative", "lore.decorative.bobo"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "fertile_soil"), new SmartEntry("lore.fertile_soil.name", new ItemStack(EssentialsBlocks.fertileSoilWheat, 1), "lore.fertile_soil"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "item_chute"), new SmartEntry("lore.item_chute.name", new ItemStack(EssentialsBlocks.itemShifter, 1), "lore.item_chute.start", "lore.item_chute.default"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "multi_piston"), new SmartEntry("lore.multi_piston.name", new ItemStack(EssentialsBlocks.multiPiston, 1), "lore.multi_piston"));
//			createPages(pages, "lore.ratiator.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.ratiator, 1), " * ", "*#*", "^^^", '*', CrossroadsItems.luminescentQuartz, '#', CrossroadsItems.pureQuartz, '^', "stone"), "lore.ratiator.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "ratiator"), new EntryItemStack(pages, "Ratiator", new ItemStack(CrossroadsBlocks.ratiator, 1), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "port_extender"), new SmartEntry("lore.port_extender.name", new ItemStack(EssentialsBlocks.portExtender, 1), "lore.port_extender"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "redstone_keyboard"), new SmartEntry("lore.redstone_keyboard.name", new ItemStack(CrossroadsBlocks.redstoneKeyboard, 1), "lore.redstone_keyboard", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.redstoneKeyboard, 1), " & ", "&*&", " & ", '*', "ingotBronze", '&', "dustRedstone")));
//
//			categories.add(new CategoryItemStack(entries, "Miscellaneous", new ItemStack(EssentialsBlocks.brazier, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			//MAGIC
//			createPages(pages, "lore.basic_magic.pre_recipe", new ShapelessOreRecipe(null, new ItemStack(CrossroadsItems.pureQuartz, 1), "dustSalt", "dustSalt", "gemQuartz"), new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.blockPureQuartz, 1), "**", "**", '*', CrossroadsItems.pureQuartz), new ShapelessOreRecipe(null, new ItemStack(CrossroadsItems.pureQuartz, 4), CrossroadsBlocks.blockPureQuartz), "lore.basic_magic.mid_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.lensArray, 2), "*&*", "@ $", "***", '*', CrossroadsItems.pureQuartz, '&', "gemEmerald", '@', "gemRuby", '$', "gemDiamond"), "lore.basic_magic.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "basic_magic"), new EntryItemStack(pages, "Basics of Magic", new ItemStack(CrossroadsItems.pureQuartz, 1), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "elements"), new SmartEntry("lore.elements.name", new ItemStack(CrossroadsItems.lensArray, 1), "lore.elements.preamble", true, ((Supplier<Object>) () -> {NBTTagCompound elementTag = StoreNBTToClient.clientPlayerTag.getCompoundTag("elements"); Object[] out = new Object[elementTag.getKeySet().size() * 2]; int arrayIndex = 0; for(int i = EnumBeamAlignments.values().length - 1; i >= 0; i--){EnumBeamAlignments elem = EnumBeamAlignments.values()[i]; if(elementTag.hasKey(elem.name())){out[arrayIndex++] = "lore.elements." + elem.name().toLowerCase(); out[arrayIndex++] = true;}} return out;})));
//			createPages(pages, "lore.color_chart.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.colorChart, 1), "RGB", "^^^", "___", '_', "slabWood", '^', "paper", 'R', "dyeRed", 'G', "dyeLime", 'B', "dyeBlue"), "lore.color_chart.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "color_chart"), new EntryItemStack(pages, "Discovering Elements", new ItemStack(CrossroadsBlocks.colorChart, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.arcane_extractor.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.beamExtractor, 1), "***", "*# ", "***", '*', "stone", '#', CrossroadsItems.lensArray), "lore.arcane_extractor.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "arcane_extractor"), new EntryItemStack(pages, "Arcane Extractor", new ItemStack(CrossroadsBlocks.beamExtractor, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.quartz_stabilizer.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.quartzStabilizer, 1), " * ", "*&*", "***", '*', CrossroadsItems.pureQuartz, '&', CrossroadsItems.lensArray), "lore.quartz_stabilizer.mid_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.quartzStabilizer, 1), " & ", "***", '&', CrossroadsItems.luminescentQuartz, '*', CrossroadsItems.pureQuartz), "lore.quartz_stabilizer.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "quartz_stabilizer"), new EntryItemStack(pages, "Quartz Stabilizer", new ItemStack(CrossroadsBlocks.quartzStabilizer, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.lens_holder.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.lensFrame, 1), "***", "*&*", "***", '*', "stone", '&', CrossroadsItems.pureQuartz), "lore.lens_holder.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "lens_holder"), new EntryItemStack(pages, "Lens Holder", new ItemStack(CrossroadsBlocks.lensFrame, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.arcane_reflector", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.beamReflector, 1), "*^*", '*', "stone", '^', CrossroadsItems.pureQuartz));
//			entries.put(new ResourceLocation(Crossroads.MODID, "arcane_reflector"), new EntryItemStack(pages, "Arcane Reflector", new ItemStack(CrossroadsBlocks.beamReflector, 1), true));
//			pages = new ArrayList<IPage>();
////TODO			createPages(pages, "lore.beam_splitter", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.beamSplitterBasic, 1), "*^*", "*&*", "*^*", '*', CrossroadsItems.pureQuartz, '^', CrossroadsItems.luminescentQuartz, '&', CrossroadsItems.lensArray), new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.beamSiphon, 1), CrossroadsBlocks.beamSplitterBasic, "dustRedstone", "dustRedstone", "dustRedstone"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "beam_splitter"), new EntryItemStack(pages, "Beam Splitter", new ItemStack(CrossroadsBlocks.beamSiphon, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.crystalline_prism", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.crystallinePrism, 1), "*^*", "^&^", "*&*", '*', CrossroadsItems.pureQuartz, '^', CrossroadsItems.luminescentQuartz, '&', CrossroadsItems.lensArray));
//			entries.put(new ResourceLocation(Crossroads.MODID, "crystalline_prism"), new EntryItemStack(pages, "Crystalline Prism", new ItemStack(CrossroadsBlocks.crystallinePrism, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.crystal_master_axis.pre_recipe", new ShapedOreRecipe(null, CrossroadsBlocks.crystalMasterAxis, "*&*", "*#*", "***", '*', CrossroadsItems.pureQuartz, '#', CrossroadsBlocks.masterAxis, '&', CrossroadsItems.lensArray), "lore.crystal_master_axis.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "crystal_master_axis"), new EntryItemStack(pages, "Crystalline Master Axis", new ItemStack(CrossroadsBlocks.crystalMasterAxis, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "lore.void.pre_recipe", new ShapedOreRecipe(null, new ItemStack(OreSetup.voidCrystal, 1), "*#*", "###", "*#*", '*', Items.DRAGON_BREATH, '#', CrossroadsItems.pureQuartz), "lore.void.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "void"), new EntryItemStack(pages, "Void", new ItemStack(OreSetup.voidCrystal, 1), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "beacon_harness"), new SmartEntry("lore.beacon_harness.name", new ItemStack(CrossroadsBlocks.beaconHarness, 1), "lore.beacon_harness.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.beaconHarness, 1), "*&*", "&^&", "*&*", '*', CrossroadsItems.pureQuartz, '&', CrossroadsItems.lensArray, '^', CrossroadsItems.luminescentQuartz), "lore.beacon_harness.post_recipe", "lore.beacon_harness.bobo"));
//
//			categories.add(new CategoryItemStack(entries, "Magic", new ItemStack(CrossroadsItems.lensArray, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			//TECHNOMANCY
//			entries.put(new ResourceLocation(Crossroads.MODID, "copshowium_chamber"), new SmartEntry("lore.copshowium_chamber.name", new ItemStack(CrossroadsBlocks.copshowiumCreationChamber, 1), Pair.of("lore.copshowium_chamber", new Object[] {TextHelper.localize("fluid." + CrossroadsConfig.getConfigString(CrossroadsConfig.cccExpenLiquid, true)), TextHelper.localize("fluid." + CrossroadsConfig.getConfigString(CrossroadsConfig.cccEntropLiquid, true))}), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.copshowiumCreationChamber, 1), "*^*", "^&^", "*^*", '*', CrossroadsItems.pureQuartz, '^', CrossroadsItems.luminescentQuartz, '&', CrossroadsBlocks.fluidCoolingChamber), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "goggles"), new SmartEntry("lore.goggles.name", new ItemStack(CrossroadsItems.moduleGoggles, 1), "lore.goggles"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "clock_stab"), new SmartEntry("lore.clock_stab.name", new ItemStack(CrossroadsBlocks.clockworkStabilizer, 1), "lore.clock_stab"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "rotary_math"), new SmartEntry("lore.rotary_math_devices.name", new ItemStack(CrossroadsBlocks.redstoneAxis, 1), "lore.rotary_math_devices", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.masterAxis, 1), "***", "*#*", "*&*", '*', "nuggetIron", '#', "nuggetCopshowium", '&', "stickIron"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.redstoneAxis, 1), "*^*", "^&^", "*^*", '*', "dustRedstone", '^', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.multiplicationAxis, 1), "***", "%^&", "***", '*', "nuggetBronze", '%', "gearCopshowium", '^', "wool", '&', "stickIron"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.multiplicationAxis, 1), "***", "%^&", "***", '*', "nuggetBronze", '%', "gearCopshowium", '^', "leather", '&', "stickIron"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.additionAxis, 1), "***", "&^&", "***", '*', "nuggetBronze", '&', "stickIron", '^', "gearCopshowium"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.equalsAxis, 1), "***", " & ", "***", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.greaterThanAxis, 1), false, "** ", " &*", "** ", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.lessThanAxis, 1), false, " **", "*& ", " **", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.squareRootAxis, 1), " **", "*& ", " * ", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.sinAxis, 1), " **", " & ", "** ", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.cosAxis, 1), " * ", "*&*", "* *", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.arcsinAxis, 1), CrossroadsBlocks.sinAxis), 0), new PageDetailedRecipe(new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.arccosAxis, 1), CrossroadsBlocks.cosAxis), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "redstone_registry"), new SmartEntry("lore.redstone_registry.name", new ItemStack(CrossroadsBlocks.redstoneRegistry, 1), "lore.redstone_registry", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.redstoneRegistry, 1), "*&*", "&^&", "*&*", '*', "nuggetTin", '&', CrossroadsBlocks.redstoneKeyboard, '^', "ingotCopshowium"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "workspace_dim"), new SmartEntry("lore.workspace_dim.name", new ItemStack(CrossroadsBlocks.gatewayFrame, 1), "lore.workspace_dim", new ResourceLocation(Crossroads.MODID, "textures/book/gateway.png"), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.gatewayFrame, 3), "***", "^^^", "%^%", '*', Blocks.STONE, '^', "ingotCopshowium", '%', "obsidian"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "mech_arm"), new SmartEntry("lore.mech_arm.name", new ItemStack(CrossroadsBlocks.mechanicalArm, 1), "lore.mech_arm", new ResourceLocation(Crossroads.MODID, "textures/book/mech_arm.png"), "lore.mech_arm.post_image", new ResourceLocation(Crossroads.MODID, "textures/book/mech_arm_equat_1.png"), "lore.mech_arm.post_calc_1", new ResourceLocation(Crossroads.MODID, "textures/book/mech_arm_equat_2.png"), "lore.mech_arm.post_calc_2", new ResourceLocation(Crossroads.MODID, "textures/book/mech_arm_table.png"), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.mechanicalArm, 1), " * ", " ||", "***", '|', "stickIron", '*', "gearCopshowium"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "beam_cage_+_staff"), new SmartEntry("lore.beam_cage_+_staff.name", new ItemStack(CrossroadsItems.beamCage, 1), "lore.beam_cage_+_staff", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.beamCage, 1), "*&*", '*', CrossroadsBlocks.quartzStabilizer, '&', "ingotCopshowium"), 0), new PageDetailedRecipe(new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.cageCharger, 1), "ingotBronze", "ingotBronze", "ingotCopshowium", CrossroadsItems.pureQuartz), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.staffTechnomancy, 1), "*&*", " & ", " | ", '*', CrossroadsItems.lensArray, '&', "ingotCopshowium", '|', "stickIron"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "prototyping"), new SmartEntry("lore.prototyping.name", (EntityPlayer play) -> {return CrossroadsConfig.getConfigInt(CrossroadsConfig.allowPrototype, true) != -1;}, new ItemStack(CrossroadsBlocks.prototype, 1), ((Supplier<Object>) () -> {int setting = CrossroadsConfig.getConfigInt(CrossroadsConfig.allowPrototype, true); return setting == 0 ? "lore.prototyping.default" : setting == 1 ? "lore.prototyping.consume" : "lore.prototyping.device";}), "lore.prototyping.pistol", true, new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.prototypingTable, 1), "*&*", "&%&", "*&*", '*', "ingotBronze", '&', "ingotCopshowium", '%', CrossroadsBlocks.detailedCrafter), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.prototypePort, 1), "*&*", "& &", "*&*", '*', "ingotBronze", '&', "nuggetCopshowium"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.pistol, 1), "CBB", "CA ", 'C', "ingotCopshowium", 'B', "ingotBronze", 'A', CrossroadsItems.lensArray), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "fields"), new SmartEntry("lore.fields.name", new ItemStack(CrossroadsBlocks.chunkUnlocker, 1), "lore.fields", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.chunkUnlocker, 1), "*^*", "^&^", "*^*", '*', "ingotBronze", '^', "ingotCopshowium", '&', CrossroadsItems.lensArray), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fluxReaderAxis, 1), "***", "*&*", "***", '*', "nuggetCopshowium", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fluxManipulator, 2), "*^*", "^&^", "*^*", '*', "ingotBronze", '^', "ingotCopshowium", '&', "gemRuby"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.rateManipulator, 2), "*^*", "^&^", "*^*", '*', "ingotBronze", '^', "ingotCopshowium", '&', "gemEmerald"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "watch"), new SmartEntry("lore.watch.name", (EntityPlayer play) -> {return CrossroadsConfig.getConfigInt(CrossroadsConfig.allowPrototype, true) != -1;}, new ItemStack(CrossroadsItems.watch, 1), "lore.watch", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.watch, 1), " * ", "*&*", " * ", '*', "ingotBronze", '&', "ingotCopshowium"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "flying_machine"), new SmartEntry("lore.flying_machine.name", new ItemStack(CrossroadsItems.flyingMachine, 1), "lore.flying_machine"));
//
//
//			categories.add(new CategoryItemStack(entries, "lore.cat_technomancy.name", new ItemStack(CrossroadsBlocks.mechanicalArm, 1)){
//				@Override
//				public boolean canSee(EntityPlayer player, ItemStack bookStack){
//					return StoreNBTToClient.clientPlayerTag.getCompoundTag("path").getBoolean("technomancy");
//				}
//			});
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//
//			//ALCHEMY
//			entries.put(new ResourceLocation(Crossroads.MODID, "alc_intro"), new SmartEntry("lore.alc_intro.name", new ItemStack(CrossroadsItems.phial, 1), "lore.alc_intro"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "man_glass"), new SmartEntry("lore.man_glass.name", new ItemStack(CrossroadsItems.florenceFlask, 1), "lore.man_glass", Pair.of(new ResourceLocation(Crossroads.MODID, "textures/book/man_glass.png"), "lore.man_glass_pic")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "aut_glass"), new SmartEntry("lore.aut_glass.name", new ItemStack(CrossroadsBlocks.reactionChamber, 1), "lore.aut_glass", Pair.of(new ResourceLocation(Crossroads.MODID, "textures/book/aut_glass.png"), "lore.aut_glass_pic")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "electric"), new SmartEntry("lore.electric.name", new ItemStack(CrossroadsBlocks.teslaCoil, 1), "lore.electric"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "tesla_ray"), new SmartEntry("lore.tesla_ray.name", new ItemStack(CrossroadsItems.teslaRay, 1), "lore.tesla_ray"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heat_limiter"), new SmartEntry("lore.heat_limiter.name", new ItemStack(CrossroadsBlocks.heatLimiterRedstone, 1), "lore.heat_limiter"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "acids"), new SmartEntry("lore.acids.name", new ItemStack(CrossroadsItems.solidVitriol, 1), "lore.acids"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "chlorine"), new SmartEntry("lore.chlorine.name", new ItemStack(Items.FERMENTED_SPIDER_EYE, 1), "lore.chlorine"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "phil_stone"), new SmartEntry("lore.phil_stone.name", new ItemStack(CrossroadsItems.philosopherStone, 1), "lore.phil_stone"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "metal_trans"), new SmartEntry("lore.metal_trans.name", new ItemStack(OreSetup.nuggetCopper, 1), "lore.metal_trans"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "early_terra"), new SmartEntry("lore.early_terra.name", new ItemStack(Blocks.GRASS, 1), "lore.early_terra"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "densus"), new SmartEntry("lore.densus.name", new ItemStack(CrossroadsBlocks.densusPlate, 1), "lore.densus"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "prac_stone"), new SmartEntry("lore.prac_stone.name", new ItemStack(CrossroadsItems.practitionerStone, 1), "lore.prac_stone"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "voltus"), new SmartEntry("lore.voltus.name", new ItemStack(CrossroadsBlocks.atmosCharger, 1), Pair.of("lore.voltus", new Object[] {1000F / CrossroadsConfig.getConfigDouble(CrossroadsConfig.voltusUsage, true)})));
//			entries.put(new ResourceLocation(Crossroads.MODID, "gem_trans"), new SmartEntry("lore.gem_trans.name", new ItemStack(Items.DIAMOND, 1), "lore.gem_trans"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "late_terra"), new SmartEntry("lore.late_terra.name", new ItemStack(Blocks.NETHERRACK, 1), "lore.late_terra.main", (Supplier<String>)  () -> CrossroadsConfig.getConfigBool(CrossroadsConfig.allowHellfire, true) ? "lore.late_terra.infer" : ""));
//
//			categories.add(new CategoryItemStack(entries, "lore.cat_alchemy.name", new ItemStack(CrossroadsItems.philosopherStone, 1)){
//				@Override
//				public boolean canSee(EntityPlayer player, ItemStack bookStack){
//					return StoreNBTToClient.clientPlayerTag.getCompoundTag("path").getBoolean("alchemy");
//				}
//			});

			MAIN.setGuideTitle("Crossroads Menu");
			MAIN.setHeader("Welcome to Crossroads");
			MAIN.setItemName("mysterious_journal");
			MAIN.setColor(Color.GRAY);
			for(CategoryAbstract c : categories){
				MAIN.addCategory(c);
			}
			MAIN.setSpawnWithBook();
			MAIN_BOOK = MAIN.build();
			return MAIN_BOOK;
		}

		@Override
		public void handleModel(ItemStack bookStack){
			GuideAPI.setModel(MAIN_BOOK);

		}

		@Override
		public void handlePost(ItemStack bookStack){
			GameRegistry.addShapelessRecipe(new ResourceLocation("guideapi", "guide_journal"), null, bookStack, CraftingHelper.getIngredient(Items.BOOK), CraftingHelper.getIngredient("ingotIron"));
			GameRegistry.addShapelessRecipe(new ResourceLocation("guideapi", "guide_manual_to_journal"), null, bookStack, CraftingHelper.getIngredient(GuideAPI.getStackFromBook(INFO_BOOK)));
		}
	}

	@GuideBook
	public static class InfoGuide implements IGuideBook{

		@Override
		public Book buildBook(){
			LinkedHashMap<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
			ArrayList<IPage> pages = new ArrayList<IPage>();
			ArrayList<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();

//			// INTRO
//			createPages(pages, "info.first_read");
//			entries.put(new ResourceLocation(Crossroads.MODID, "first_read"), new EntryItemStack(pages, "READ ME FIRST", new ItemStack(Items.BOOK, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.intro.pre_recipe", new ShapelessOreRecipe(null, Items.WRITTEN_BOOK, Items.BOOK, "ingotIron"), "info.intro.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "intro"), new EntryItemStack(pages, "Introduction", new ItemStack(Items.PAPER, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.ores.native_copper", new ResourceLocation(Crossroads.MODID, "textures/blocks/ore_native_copper.png"), "info.ores.copper", new ResourceLocation(Crossroads.MODID, "textures/blocks/ore_copper.png"), "info.ores.tin", new ResourceLocation(Crossroads.MODID, "textures/blocks/ore_tin.png"), "info.ores.ruby", new ResourceLocation(Crossroads.MODID, "textures/blocks/ore_ruby.png"), "info.ores.bronze", new ShapedOreRecipe(null, OreSetup.ingotBronze, "###", "#$#", "###", '#', "nuggetCopper", '$', "nuggetTin"), new ShapedOreRecipe(null, OreSetup.blockBronze, "###", "#$#", "###", '#', "ingotCopper", '$', "ingotTin"), new ShapedOreRecipe(null, new ItemStack(OreSetup.blockBronze, 9), "###", "#?#", "###", '#', "blockCopper", '?', "blockTin"), "info.ores.salt", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.blockSalt, 1), "##", "##", '#', "dustSalt"), new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.dustSalt, 4), "#", '#', "blockSalt"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "ores"), new EntryItemStack(pages, "Ores", new ItemStack(OreSetup.ingotCopper, 1), true));
//			entries.put(new ResourceLocation(Crossroads.MODID, "energy"), new SmartEntry("info.energy.name", new ItemStack(CrossroadsItems.omnimeter, 1), "info.energy"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heat"), new SmartEntry("info.heat.name", new ItemStack(CrossroadsBlocks.firebox, 1), "info.heat.start", ((Supplier<Object>) () -> CrossroadsConfig.getConfigBool(CrossroadsConfig.heatEffects, true) ? "info.heat.insulator" : "info.heat.insulator_effect_disable"), "info.heat.end"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "steam"), new SmartEntry("info.steam.name", new ItemStack(CrossroadsBlocks.fluidTube, 1), Pair.of("info.steam", new Object[] {Math.round(EnergyConverters.degPerSteamBucket(true) * 1.1D), EnergyConverters.degPerSteamBucket(true)})));
//			entries.put(new ResourceLocation(Crossroads.MODID, "rotary"), new SmartEntry("info.rotary.name", new ItemStack(GearFactory.BASIC_GEARS[GearFactory.GearMaterial.BRONZE.ordinal()], 1), "info.rotary"));
//			createPages(pages, "info.copper", new ResourceLocation(Crossroads.MODID, "textures/book/copper_process.png"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "copper"), new EntryItemStack(pages, "Copper Processing", new ItemStack(OreSetup.ingotCopper, 1), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "intro_path"), new SmartEntry("info.intro_path", new ItemStack(CrossroadsItems.watch), "info.intro_path.start", (Supplier<Object>) () -> {return StoreNBTToClient.clientPlayerTag.getBoolean("multiplayer") ? CrossroadsConfig.getConfigBool(CrossroadsConfig.allowAllServer, true) ? "info.intro_path.locked" : null : CrossroadsConfig.getConfigBool(CrossroadsConfig.allowAllSingle, true) ? "info.intro_path.locked" : null;}, "info.intro_path.continue", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.detailedCrafter, 1), "*^*", "^&^", "*^*", '*', "ingotIron", '^', "ingotTin", '&', Blocks.CRAFTING_TABLE), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.detailedCrafter, 1), "*&*", "&#&", "*&*", '*', "nuggetIron", '&', "nuggetTin", '#', Blocks.CRAFTING_TABLE), 0)));
//
//			categories.add(new CategoryItemStack(entries, "The Basics", new ItemStack(OreSetup.oreCopper, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			// HEAT
//			entries.put(new ResourceLocation(Crossroads.MODID, "fuel_heater"), new SmartEntry("info.fuel_heater", new ItemStack(CrossroadsBlocks.firebox, 1), "info.fuel_heater.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.firebox, 1), "#*#", "# #", "###", '#', "cobblestone", '*', "ingotCopper")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heating_chamber"), new SmartEntry("info.heating_chamber", new ItemStack(CrossroadsBlocks.smelter, 1), "info.heating_chamber.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.smelter, 1), "#*#", "# #", "###", '#', "ingotIron", '*', "ingotCopper")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heat_exchanger"), new SmartEntry("info.heat_exchanger", new ItemStack(CrossroadsBlocks.heatSink, 1), "info.heat_exchanger.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.heatSink, 1), "#$#", "$$$", "###", '#', Blocks.IRON_BARS, '$', "ingotCopper")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heating_crucible"), new SmartEntry("info.heating_crucible", new ItemStack(CrossroadsBlocks.smelter, 1), "info.heating_crucible.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.heatingCrucible, 1), "# #", "#?#", "###", '#', Blocks.HARDENED_CLAY, '?', Items.CAULDRON)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "fluid_cooling"), new SmartEntry("info.fluid_cooling", new ItemStack(CrossroadsBlocks.fluidCoolingChamber, 1), "info.fluid_cooling.text",  new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fluidCoolingChamber, 1), "###", "# #", "#%#", '#', "ingotIron", '%', "ingotCopper")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "redstone_cable"), new SmartEntry("info.redstone_cable", new ItemStack(Items.REDSTONE, 1), "info.redstone_cable.text", new ShapedOreRecipe(null, new ItemStack(HeatCableFactory.REDSTONE_HEAT_CABLES.get(HeatInsulators.WOOL), 1), "###", "#?#", "###", '#', "dustRedstone", '?', HeatCableFactory.HEAT_CABLES.get(HeatInsulators.WOOL))));
//			entries.put(new ResourceLocation(Crossroads.MODID, "salt_reactor"), new SmartEntry("info.salt_reactor", new ItemStack(CrossroadsBlocks.saltReactor, 1), "info.salt_reactor.text", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.saltReactor, 1), "#$#", "$%$", "#@#", '#', "ingotIron", '$', CrossroadsBlocks.fluidTube, '%', "blockSalt", '@', "ingotCopper")));
//
//			categories.add(new CategoryItemStack(entries, "Heat Machines", new ItemStack(CrossroadsBlocks.smelter, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			// ROTARY
//			createPages(pages, "info.millstone.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.millstone, 1), "#$#", "#?#", "#$#", '#', "cobblestone", '?', "stickIron", '$', Blocks.PISTON), "info.millstone.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "millstone"), new EntryItemStack(pages, "Millstone", new ItemStack(CrossroadsBlocks.millstone, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.drill.pre_recipe", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.rotaryDrill, 2), " * ", "*#*", '*', "ingotIron", '#', "blockIron"), "info.drill.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "drill"), new EntryItemStack(pages, "Rotary Drill", new ItemStack(CrossroadsBlocks.rotaryDrill, 1), true));
//			pages = new ArrayList<IPage>();
//			//createPages(pages, "info.toggle_gear.pre_recipe", new ShapelessOreRecipe(null, new ItemStack(GearFactory.TOGGLE_GEARS[GearFactory.GearMaterial.GOLD.ordinal()], 1), "dustRedstone", "dustRedstone", "stickIron", GearFactory.BASIC_GEARS[GearFactory.GearMaterial.GOLD.ordinal()]), "info.toggle_gear.post_recipe");
//			entries.put(new ResourceLocation(Crossroads.MODID, "toggle_gear"), new EntryItemStack(pages, "Toggle Gear", new ItemStack(Items.REDSTONE, 1), true));
//			pages = new ArrayList<IPage>();
//
//			//categories.add(new CategoryItemStack(entries, "Rotary Machines", new ItemStack(GearFactory.BASIC_GEARS[GearFactory.GearMaterial.BRONZE.ordinal()], 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			// FLUIDS
//			createPages(pages,  "info.rotary_pump", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.rotaryPump, 1), "#$#", "#$#", "&$&", '#', "ingotBronze", '&', "blockGlass", '$', "stickIron"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "rotary_pump"), new EntryItemStack(pages, "Rotary Pump", new ItemStack(CrossroadsBlocks.rotaryPump, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, Pair.of("info.steam_turbine", new Object[] {EnergyConverters.degPerSteamBucket(true) / EnergyConverters.degPerJoule(true)}), new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.steamTurbine, 1), CrossroadsBlocks.rotaryPump), new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.rotaryPump, 1), CrossroadsBlocks.steamTurbine));
//			entries.put(new ResourceLocation(Crossroads.MODID, "steam_turbine"), new EntryItemStack(pages, "Steam Turbine", new ItemStack(CrossroadsBlocks.steamTurbine, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, Pair.of("info.radiator", new Object[] {EnergyConverters.degPerSteamBucket(true)}), new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.radiator, 1), "#$#", "#$#", "#$#", '#', CrossroadsBlocks.fluidTube, '$', "ingotIron"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "radiator"), new EntryItemStack(pages, "Radiator", new ItemStack(CrossroadsBlocks.radiator, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, Pair.of("info.liquid_fat", new Object[] {EnergyConverters.FAT_PER_VALUE}), new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fatCollector, 1), "***", "# #", "*&*", '*', "ingotTin", '#', "netherrack", '&', "ingotCopper"), new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fatCongealer, 1), "*^*", "# #", "* *", '*', "ingotTin", '#', "netherrack", '^', "stickIron"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "liquid_fat"), new EntryItemStack(pages, "Basics of Liquid Fat", new ItemStack(CrossroadsItems.edibleBlob, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, Pair.of("info.fat_feeder", new Object[] {EnergyConverters.FAT_PER_VALUE}), new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fatFeeder, 1), "*^*", "# #", "*^*", '*', "ingotTin", '#', "netherrack", '^', "stickIron"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "fat_feeder"), new EntryItemStack(pages, "Fat Feeder", new ItemStack(CrossroadsBlocks.fatFeeder, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.redstone_tube", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.redstoneFluidTube, 1), "***", "*&*", "***", '*', "dustRedstone", '&', CrossroadsBlocks.fluidTube));
//			entries.put(new ResourceLocation(Crossroads.MODID, "redstone_tube"), new EntryItemStack(pages, "Redstone Integration-Fluids", new ItemStack(Items.REDSTONE), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "fluid_splitter"), new SmartEntry("info.fluid_splitter.name", new ItemStack(CrossroadsBlocks.fluidSplitter, 1), "info.fluid_splitter", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.basicFluidSplitter, 1), "*^*", "&&&", "*^*", '*', "nuggetTin", '^', CrossroadsBlocks.fluidTube, '&', "ingotBronze"), new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.fluidSplitter, 1), CrossroadsBlocks.basicFluidSplitter, "dustRedstone", "dustRedstone", "dustRedstone")));
//			createPages(pages, "info.water_centrifuge",new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.waterCentrifuge, 1), "*&*", "^%^", "* *", '*', "ingotBronze", '&', "stickIron", '^', CrossroadsBlocks.fluidTube, '%', "ingotTin"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "water_centrifuge"), new EntryItemStack(pages, "Water Centrifuge", new ItemStack(CrossroadsBlocks.waterCentrifuge), true));
//			pages = new ArrayList<IPage>();
//
//			categories.add(new CategoryItemStack(entries, "Fluid Machines", new ItemStack(CrossroadsBlocks.fluidTube, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			// MISC
//			entries.put(new ResourceLocation(Crossroads.MODID, "brazier"), new SmartEntry("info.brazier.name", new ItemStack(EssentialsBlocks.brazier, 1), (Supplier<Object>) (() -> EssentialsConfig.getConfigInt(EssentialsConfig.brazierRange, true) == 0 ? "info.brazier.no_witch" : Pair.of("info.brazier.normal", new Object[] {EssentialsConfig.getConfigInt(EssentialsConfig.brazierRange, true)}))));
//			entries.put(new ResourceLocation(Crossroads.MODID, "item_sorting"), new SmartEntry("info.item_sorting.name", new ItemStack(EssentialsBlocks.sortingHopper, 1), "info.item_sorting"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "wrench"), new SmartEntry("info.wrench.name", new ItemStack(EssentialsItems.wrench, 1), "info.wrench"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "ob_cutting"), new SmartEntry("info.ob_cutting.name", new ItemStack(EssentialsItems.obsidianKit, 1), "info.ob_cutting"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "decorative"), new SmartEntry("info.decorative.name", new ItemStack(EssentialsBlocks.candleLilyPad, 1), "info.decorative"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "fertile_soil"), new SmartEntry("info.fertile_soil.name", new ItemStack(EssentialsBlocks.fertileSoilWheat, 1), "info.fertile_soil"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "item_chute"), new SmartEntry("info.item_chute.name", new ItemStack(EssentialsBlocks.itemShifter, 1), "info.item_chute.start", "info.item_chute.default"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "multi_piston"), new SmartEntry("info.multi_piston.name", new ItemStack(EssentialsBlocks.multiPiston, 1), "info.multi_piston"));
//			createPages(pages, "info.ratiator", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.ratiator, 1), " * ", "*#*", "^^^", '*', CrossroadsItems.luminescentQuartz, '#', CrossroadsItems.pureQuartz, '^', "stone"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "ratiator"), new EntryItemStack(pages, "Ratiator", new ItemStack(CrossroadsBlocks.ratiator, 1), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "port_extender"), new SmartEntry("info.port_extender.name", new ItemStack(EssentialsBlocks.portExtender, 1), "info.port_extender"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "redstone_keyboard"), new SmartEntry("info.redstone_keyboard.name", new ItemStack(CrossroadsBlocks.redstoneKeyboard, 1), "info.redstone_keyboard", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.redstoneKeyboard, 1), " & ", "&*&", " & ", '*', "ingotBronze", '&', "dustRedstone")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "crafttweaker"), new SmartEntry("info.crafttweaker.name", (EntityPlayer play) -> CrossroadsConfig.getConfigBool(CrossroadsConfig.documentCrafttweaker, true), new ItemStack(Blocks.CRAFTING_TABLE, 1), "info.crafttweaker"));
//
//			categories.add(new CategoryItemStack(entries, "Miscellaneous", new ItemStack(EssentialsBlocks.brazier, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			//MAGIC
//			createPages(pages, "info.basic_magic", new ShapelessOreRecipe(null, new ItemStack(CrossroadsItems.pureQuartz, 1), "dustSalt", "dustSalt", "gemQuartz"), new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.blockPureQuartz, 1), "**", "**", '*', CrossroadsItems.pureQuartz), new ShapelessOreRecipe(null, new ItemStack(CrossroadsItems.pureQuartz, 4), CrossroadsBlocks.blockPureQuartz), new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.lensArray, 2), "*&*", "@ $", "***", '*', CrossroadsItems.pureQuartz, '&', "gemEmerald", '@', "gemRuby", '$', "gemDiamond"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "basic_magic"), new EntryItemStack(pages, "Basics of Magic", new ItemStack(CrossroadsItems.pureQuartz, 1), true));
//			pages = new ArrayList<IPage>();
//			entries.put(new ResourceLocation(Crossroads.MODID, "elements"), new SmartEntry("info.elements.name", new ItemStack(CrossroadsItems.lensArray, 1), "info.elements.preamble", true, ((Supplier<Object>) () -> {NBTTagCompound elementTag = StoreNBTToClient.clientPlayerTag.getCompoundTag("elements"); Object[] out = new Object[elementTag.getKeySet().size() * 2]; int arrayIndex = 0; for(int i = EnumBeamAlignments.values().length - 1; i >= 0; i--){EnumBeamAlignments elem = EnumBeamAlignments.values()[i]; if(elementTag.hasKey(elem.name())){out[arrayIndex++] = "info.elements." + elem.name().toLowerCase(); out[arrayIndex++] = true;}} return out;})));
//			createPages(pages, "info.color_chart", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.colorChart, 1), "RGB", "^^^", "___", '_', "slabWood", '^', "paper", 'R', "dyeRed", 'G', "dyeLime", 'B', "dyeBlue"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "color_chart"), new EntryItemStack(pages, "Discovering Elements", new ItemStack(CrossroadsBlocks.colorChart, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.arcane_extractor", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.beamExtractor, 1), "***", "*# ", "***", '*', "stone", '#', CrossroadsItems.lensArray));
//			entries.put(new ResourceLocation(Crossroads.MODID, "arcane_extractor"), new EntryItemStack(pages, "Arcane Extractor", new ItemStack(CrossroadsBlocks.beamExtractor, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.quartz_stabilizer", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.quartzStabilizer, 1), " * ", "*&*", "***", '*', CrossroadsItems.pureQuartz, '&', CrossroadsItems.lensArray), new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.quartzStabilizer, 1), " & ", "***", '&', CrossroadsItems.luminescentQuartz, '*', CrossroadsItems.pureQuartz));
//			entries.put(new ResourceLocation(Crossroads.MODID, "quartz_stabilizer"), new EntryItemStack(pages, "Quartz Stabilizer", new ItemStack(CrossroadsBlocks.quartzStabilizer, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.lens_holder", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.lensFrame, 1), "***", "*&*", "***", '*', "stone", '&', CrossroadsItems.pureQuartz));
//			entries.put(new ResourceLocation(Crossroads.MODID, "lens_holder"), new EntryItemStack(pages, "Lens Holder", new ItemStack(CrossroadsBlocks.lensFrame, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.arcane_reflector", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.beamReflector, 1), "*^*", '*', "stone", '^', CrossroadsItems.pureQuartz));
//			entries.put(new ResourceLocation(Crossroads.MODID, "arcane_reflector"), new EntryItemStack(pages, "Arcane Reflector", new ItemStack(CrossroadsBlocks.beamReflector, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.beam_splitter");
//			entries.put(new ResourceLocation(Crossroads.MODID, "beam_splitter"), new EntryItemStack(pages, "Beam Splitter", new ItemStack(CrossroadsBlocks.beamSiphon, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.crystalline_prism", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.crystallinePrism, 1), "*^*", "^&^", "*&*", '*', CrossroadsItems.pureQuartz, '^', CrossroadsItems.luminescentQuartz, '&', CrossroadsItems.lensArray));
//			entries.put(new ResourceLocation(Crossroads.MODID, "crystalline_prism"), new EntryItemStack(pages, "Crystalline Prism", new ItemStack(CrossroadsBlocks.crystallinePrism, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.crystal_master_axis", new ShapedOreRecipe(null, CrossroadsBlocks.crystalMasterAxis, "*&*", "*#*", "***", '*', CrossroadsItems.pureQuartz, '#', CrossroadsBlocks.masterAxis, '&', CrossroadsItems.lensArray));
//			entries.put(new ResourceLocation(Crossroads.MODID, "crystal_master_axis"), new EntryItemStack(pages, "Crystalline Master Axis", new ItemStack(CrossroadsBlocks.crystalMasterAxis, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.void", new ShapedOreRecipe(null, new ItemStack(OreSetup.voidCrystal, 1), "*#*", "###", "*#*", '*', Items.DRAGON_BREATH, '#', CrossroadsItems.pureQuartz));
//			entries.put(new ResourceLocation(Crossroads.MODID, "void"), new EntryItemStack(pages, "Void", new ItemStack(OreSetup.voidCrystal, 1), true));
//			pages = new ArrayList<IPage>();
//			createPages(pages, "info.beacon_harness", new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.beaconHarness, 1), "*&*", "&^&", "*&*", '*', CrossroadsItems.pureQuartz, '&', CrossroadsItems.lensArray, '^', CrossroadsItems.luminescentQuartz));
//			entries.put(new ResourceLocation(Crossroads.MODID, "beacon_harness"), new EntryItemStack(pages, "Beacon Harness", new ItemStack(CrossroadsBlocks.beaconHarness, 1), true));
//			pages = new ArrayList<IPage>();
//
//			categories.add(new CategoryItemStack(entries, "Magic", new ItemStack(CrossroadsItems.lensArray, 1)));
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			//TECHNOMANCY
//			entries.put(new ResourceLocation(Crossroads.MODID, "copshowium_chamber"), new SmartEntry("info.copshowium_chamber.name", new ItemStack(CrossroadsBlocks.copshowiumCreationChamber, 1), Pair.of("info.copshowium_chamber", new Object[] {TextHelper.localize("fluid." + CrossroadsConfig.getConfigString(CrossroadsConfig.cccExpenLiquid, true)), TextHelper.localize("fluid." + CrossroadsConfig.getConfigString(CrossroadsConfig.cccEntropLiquid, true))}), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.copshowiumCreationChamber, 1), "*^*", "^&^", "*^*", '*', CrossroadsItems.pureQuartz, '^', CrossroadsItems.luminescentQuartz, '&', CrossroadsBlocks.fluidCoolingChamber), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "goggles"), new SmartEntry("info.goggles.name", new ItemStack(CrossroadsItems.moduleGoggles, 1), "info.goggles"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "clock_stab"), new SmartEntry("info.clock_stab.name", new ItemStack(CrossroadsBlocks.clockworkStabilizer, 1), "info.clock_stab"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "rotary_math"), new SmartEntry("info.rotary_math_devices.name", new ItemStack(CrossroadsBlocks.redstoneAxis, 1), "info.rotary_math_devices", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.masterAxis, 1), "***", "*#*", "*&*", '*', "nuggetIron", '#', "nuggetCopshowium", '&', "stickIron"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.redstoneAxis, 1), "*^*", "^&^", "*^*", '*', "dustRedstone", '^', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.multiplicationAxis, 1), "***", "%^&", "***", '*', "nuggetBronze", '%', "gearCopshowium", '^', "wool", '&', "stickIron"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.multiplicationAxis, 1), "***", "%^&", "***", '*', "nuggetBronze", '%', "gearCopshowium", '^', "leather", '&', "stickIron"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.additionAxis, 1), "***", "&^&", "***", '*', "nuggetBronze", '&', "stickIron", '^', "gearCopshowium"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.equalsAxis, 1), "***", " & ", "***", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.greaterThanAxis, 1), false, "** ", " &*", "** ", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.lessThanAxis, 1), false, " **", "*& ", " **", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.squareRootAxis, 1), " **", "*& ", " * ", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.sinAxis, 1), " **", " & ", "** ", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.cosAxis, 1), " * ", "*&*", "* *", '*', "nuggetBronze", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.arcsinAxis, 1), CrossroadsBlocks.sinAxis), 0), new PageDetailedRecipe(new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.arccosAxis, 1), CrossroadsBlocks.cosAxis), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "redstone_registry"), new SmartEntry("info.redstone_registry.name", new ItemStack(CrossroadsBlocks.redstoneRegistry, 1), "info.redstone_registry", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.redstoneRegistry, 1), "*&*", "&^&", "*&*", '*', "nuggetTin", '&', CrossroadsBlocks.redstoneKeyboard, '^', "ingotCopshowium"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "workspace_dim"), new SmartEntry("info.workspace_dim.name", new ItemStack(CrossroadsBlocks.gatewayFrame, 1), "info.workspace_dim", new ResourceLocation(Crossroads.MODID, "textures/book/gateway.png"), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.gatewayFrame, 3), "***", "^^^", "%^%", '*', Blocks.STONE, '^', "ingotCopshowium", '%', "obsidian"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "mech_arm"), new SmartEntry("info.mech_arm.name", new ItemStack(CrossroadsBlocks.mechanicalArm, 1), "info.mech_arm", new ResourceLocation(Crossroads.MODID, "textures/book/mech_arm.png"), "info.mech_arm.post_image", new ResourceLocation(Crossroads.MODID, "textures/book/mech_arm_equat_1.png"), "info.mech_arm.post_calc_1", new ResourceLocation(Crossroads.MODID, "textures/book/mech_arm_equat_2.png"), "info.mech_arm.post_calc_2", new ResourceLocation(Crossroads.MODID, "textures/book/mech_arm_table.png"), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.mechanicalArm, 1), " * ", " ||", "***", '|', "stickIron", '*', "gearCopshowium"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "beam_cage_+_staff"), new SmartEntry("info.beam_cage_+_staff.name", new ItemStack(CrossroadsItems.beamCage, 1), "info.beam_cage_+_staff", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.beamCage, 1), "*&*", '*', CrossroadsBlocks.quartzStabilizer, '&', "ingotCopshowium"), 0), new PageDetailedRecipe(new ShapelessOreRecipe(null, new ItemStack(CrossroadsBlocks.cageCharger, 1), "ingotBronze", "ingotBronze", "ingotCopshowium", CrossroadsItems.pureQuartz), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.staffTechnomancy, 1), "*&*", " & ", " | ", '*', CrossroadsItems.lensArray, '&', "ingotCopshowium", '|', "stickIron"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "prototyping"), new SmartEntry("info.prototyping.name", (EntityPlayer play) -> {return CrossroadsConfig.getConfigInt(CrossroadsConfig.allowPrototype, true) != -1;}, new ItemStack(CrossroadsBlocks.prototype, 1), ((Supplier<Object>) () -> {int setting = CrossroadsConfig.getConfigInt(CrossroadsConfig.allowPrototype, true); return setting == 0 ? "info.prototyping.default" : setting == 1 ? "info.prototyping.consume" : "info.prototyping.device";}), "info.prototyping.pistol", true, new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.prototypingTable, 1), "*&*", "&%&", "*&*", '*', "ingotBronze", '&', "ingotCopshowium", '%', CrossroadsBlocks.detailedCrafter), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.prototypePort, 1), "*&*", "& &", "*&*", '*', "ingotBronze", '&', "nuggetCopshowium"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.pistol, 1), "CBB", "CA ", 'C', "ingotCopshowium", 'B', "ingotBronze", 'A', CrossroadsItems.lensArray), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "fields"), new SmartEntry("info.fields.name", new ItemStack(CrossroadsBlocks.chunkUnlocker, 1), "info.fields", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.chunkUnlocker, 1), "*^*", "^&^", "*^*", '*', "ingotBronze", '^', "ingotCopshowium", '&', CrossroadsItems.lensArray), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fluxReaderAxis, 1), "***", "*&*", "***", '*', "nuggetCopshowium", '&', CrossroadsBlocks.masterAxis), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.fluxManipulator, 2), "*^*", "^&^", "*^*", '*', "ingotBronze", '^', "ingotCopshowium", '&', "gemRuby"), 0), new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsBlocks.rateManipulator, 2), "*^*", "^&^", "*^*", '*', "ingotBronze", '^', "ingotCopshowium", '&', "gemEmerald"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "watch"), new SmartEntry("info.watch.name", (EntityPlayer play) -> {return CrossroadsConfig.getConfigInt(CrossroadsConfig.allowPrototype, true) != -1;}, new ItemStack(CrossroadsItems.watch, 1), "info.watch", new PageDetailedRecipe(new ShapedOreRecipe(null, new ItemStack(CrossroadsItems.watch, 1), " * ", "*&*", " * ", '*', "ingotBronze", '&', "ingotCopshowium"), 0)));
//			entries.put(new ResourceLocation(Crossroads.MODID, "flying_machine"), new SmartEntry("info.flying_machine.name", new ItemStack(CrossroadsItems.flyingMachine, 1), "info.flying_machine"));
//
//			categories.add(new CategoryItemStack(entries, "info.cat_technomancy.name", new ItemStack(CrossroadsBlocks.mechanicalArm, 1)){
//				@Override
//				public boolean canSee(EntityPlayer player, ItemStack bookStack){
//					return StoreNBTToClient.clientPlayerTag.getCompoundTag("path").getBoolean("technomancy");
//				}
//			});
//			entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
//
//			//ALCHEMY
//			entries.put(new ResourceLocation(Crossroads.MODID, "alc_intro"), new SmartEntry("info.alc_intro.name", new ItemStack(CrossroadsItems.phial, 1), "info.alc_intro"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "man_glass"), new SmartEntry("info.man_glass.name", new ItemStack(CrossroadsItems.florenceFlask, 1), "info.man_glass", Pair.of(new ResourceLocation(Crossroads.MODID, "textures/book/man_glass.png"), "info.man_glass_pic")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "aut_glass"), new SmartEntry("info.aut_glass.name", new ItemStack(CrossroadsBlocks.reactionChamber, 1), "info.aut_glass", Pair.of(new ResourceLocation(Crossroads.MODID, "textures/book/aut_glass.png"), "info.aut_glass_pic")));
//			entries.put(new ResourceLocation(Crossroads.MODID, "electric"), new SmartEntry("info.electric.name", new ItemStack(CrossroadsBlocks.teslaCoil, 1), "info.electric"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "tesla_ray"), new SmartEntry("info.tesla_ray.name", new ItemStack(CrossroadsItems.teslaRay, 1), "info.tesla_ray"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "heat_limiter"), new SmartEntry("info.heat_limiter.name", new ItemStack(CrossroadsBlocks.heatLimiterRedstone, 1), "info.heat_limiter"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "acids"), new SmartEntry("info.acids.name", new ItemStack(CrossroadsItems.solidVitriol, 1), "info.acids"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "chlorine"), new SmartEntry("info.chlorine.name", new ItemStack(Items.FERMENTED_SPIDER_EYE, 1), "info.chlorine"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "phil_stone"), new SmartEntry("info.phil_stone.name", new ItemStack(CrossroadsItems.philosopherStone, 1), "info.phil_stone"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "metal_trans"), new SmartEntry("info.metal_trans.name", new ItemStack(OreSetup.nuggetCopper, 1), "info.metal_trans"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "early_terra"), new SmartEntry("info.early_terra.name", new ItemStack(Blocks.GRASS, 1), "info.early_terra"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "densus"), new SmartEntry("info.densus.name", new ItemStack(CrossroadsBlocks.densusPlate, 1), "info.densus"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "prac_stone"), new SmartEntry("info.prac_stone.name", new ItemStack(CrossroadsItems.practitionerStone, 1), "info.prac_stone"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "voltus"), new SmartEntry("info.voltus.name", new ItemStack(CrossroadsBlocks.atmosCharger, 1), Pair.of("info.voltus", new Object[] {1000F / CrossroadsConfig.getConfigDouble(CrossroadsConfig.voltusUsage, true)})));
//			entries.put(new ResourceLocation(Crossroads.MODID, "gem_trans"), new SmartEntry("info.gem_trans.name", new ItemStack(Items.DIAMOND, 1), "info.gem_trans"));
//			entries.put(new ResourceLocation(Crossroads.MODID, "late_terra"), new SmartEntry("info.late_terra.name", new ItemStack(Blocks.NETHERRACK, 1), "info.late_terra.main", (Supplier<String>)  () -> CrossroadsConfig.getConfigBool(CrossroadsConfig.allowHellfire, true) ? "info.late_terra.infer" : ""));
//
//			categories.add(new CategoryItemStack(entries, "info.cat_alchemy.name", new ItemStack(CrossroadsItems.philosopherStone, 1)){
//				@Override
//				public boolean canSee(EntityPlayer player, ItemStack bookStack){
//					return StoreNBTToClient.clientPlayerTag.getCompoundTag("path").getBoolean("alchemy");
//				}
//			});

			INFO.setGuideTitle("Crossroads Menu");
			INFO.setHeader("Welcome to Crossroads");
			INFO.setItemName("technician_manual");
			INFO.setColor(Color.CYAN);
			for(CategoryAbstract c : categories){
				INFO.addCategory(c);
			}
			INFO_BOOK = INFO.build();
			return INFO_BOOK;
		}

		@Override
		public void handleModel(ItemStack bookStack){
			GuideAPI.setModel(INFO_BOOK);
		}

		@Override
		public void handlePost(ItemStack bookStack){
			GameRegistry.addShapelessRecipe(new ResourceLocation("guideapi", "guide_journal_to_manual"), null, bookStack, CraftingHelper.getIngredient(GuideAPI.getStackFromBook(MAIN_BOOK)));
		}
	}

	/**
	 * Splits up a long string into pages. I can't use PageHelper for this
	 * because of the § symbol.
	 */
	private static void createTextPages(ArrayList<IPage> pages, String text){

		final int PERPAGE = 370;
		final char symbol = 167;
		String format = "";
		String formatTemp = "";

		int start = 0;
		double length = 0;
		for(int i = 0; i < text.length(); i++){
			if(text.charAt(i) == symbol){
				formatTemp = text.substring(i, i + 4);
				i += 4;
			}else if(i == text.length() - 1 || (length >= PERPAGE && text.charAt(i) == ' ')){
				//The .replace is to fix a bug where somehow (no clue how) some of the § symbols get turned to character 157. This turns them back.
				pages.add(new PageText((format + text.substring(start, i + 1)).replace((char) 157, symbol)));
				((Page) pages.get(pages.size() - 1)).setUnicodeFlag(true);
				format = formatTemp;
				length = 0;
				start = i + 1;
			}else{
				//Bold text is thicker than normal text.
				length += formatTemp.equals("§r§l") ? 1.34D : 1;
			}
		}
	}

	/**
	 * @deprecated Use a {@link SmartEntry} instead.
	 */
	@Deprecated
	private static void createPages(ArrayList<IPage> pages, Object... parts){
		for(Object obj : parts){
			if(obj instanceof String){
				//By default, pages localize by themselves. However, it is necessary to localize them on initialization for page splitting to work properly,
				//because the built in method doesn't support § and I have to make my own. This means reloading lang files in-game WILL NOT WORK for the guide
				//Also, the lang files need to be encoded in UTF-8 to support §.
				createTextPages(pages, TextHelper.localize((String) obj));
			}else if(obj instanceof Pair && ((Pair<?, ?>) obj).getLeft() instanceof String && ((Pair<?, ?>) obj).getRight() instanceof Object[]){
				@SuppressWarnings("unchecked")
				Pair<String, Object[]> pair = ((Pair<String, Object[]>) obj);
				createTextPages(pages, TextHelper.localize(pair.getLeft(), pair.getRight()));
			}else if(obj instanceof ItemStack){
				pages.add(new PageFurnaceRecipe((ItemStack) obj));
			}else if(obj instanceof ResourceLocation){
				pages.add(new PageImage((ResourceLocation) obj));
			}else if(obj instanceof IRecipe){
				pages.add(new PageIRecipe((IRecipe) obj));
			}else{
				throw new IllegalArgumentException("INVALID OBJECT FOR PAGE BUILDING!");
			}
		}
	}
}
