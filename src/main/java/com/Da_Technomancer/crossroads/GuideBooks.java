package com.Da_Technomancer.crossroads;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.Da_Technomancer.crossroads.API.EnergyConverters;
import com.Da_Technomancer.crossroads.API.enums.GearTypes;
import com.Da_Technomancer.crossroads.API.enums.HeatConductors;
import com.Da_Technomancer.crossroads.API.enums.HeatInsulators;
import com.Da_Technomancer.crossroads.blocks.ModBlocks;
import com.Da_Technomancer.crossroads.items.ModItems;
import com.Da_Technomancer.crossroads.items.itemSets.GearFactory;
import com.Da_Technomancer.crossroads.items.itemSets.HeatCableFactory;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageImage;
import amerifrance.guideapi.page.PageText;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;


public class GuideBooks{

	public static Book main = new Book();

	public static void mainGuide(FMLPreInitializationEvent e) {


		//Techno = normal (§r§r) use the double §r to make createPages() work
		//Witch = underline (§r§n)
		//Alchem = italic (§r§o)
		//Bobo = normal (§r§l)
		//The § symbol can be typed by holding alt and typing 0167 on the numpad, then release alt.

		LinkedHashMap<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		ArrayList<IPage> pages = new ArrayList<IPage>();
		ArrayList<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
		
		//INTRO
		createPages(pages, "§r§oAre you sure this thing is working? §r§rYes I'm sure, I just turned it on and everything we're saying now is being recorded. §r§nThen stop talking you idiots and do the introduction! §r§rFine, then be quiet! Hello reader, I'm sure you are wondering what this book you have just found is. Let me explain. We are the three writers of this journal. §r§nWell obviously. §r§rBe Quiet. Anyways, at the time you are reading this we will be long gone. We each know the secrets of a different craft, and we are working together to record our knowledge for those in the future who would be capable of continuing our crafts. This journal will teach you the basics of magic and science so that you can make your decision as to which path to go down. I have worked with the other 2 writers to create a contraption that records what we say in this room into this journal, and will give a copy of this journal to capable people of the future, by making minor changes to reality. If you should ever need another copy of this book, just follow this recipe:", new ShapelessOreRecipe(Items.WRITTEN_BOOK, Items.BOOK, Items.COMPASS), "§r§rI know the secrets of Technomancy, manipulation of Time. Everything we say will be recorded in a different text style depending on the person. It was going to be different colors, but The Alchemist was worried the reader would be color blind. §r§oWahte, wat yf fhe reedre can't dyfernetiate dyffurent fonts? §r§nThat's called being blind. §r§rJust introduce yourself. §r§oFyne, I am yn Alcymist. Alkemy ys fhe manypulatine of relytie. §r§nThat leaves me. I am a witch. Witchcraft is the manipulation of void. Do not confuse true witchcraft with the silly villagers in pointy hats, they do no true witchcraft. §r§rWe won't tell you our names, for good reason. Basically, the three of us will teach you a bunch of mundane science and magic, and then we will have you choose: Witchcraft, Alchemy, or Technomancy. At that point you will recieve a new journal specifically about that art. You can only pick one, and you should wait to learn more from us before you make the decision. §r§nThat should wrap up this section then, so I just flip this switch to stop the machine, right? §r§rYep. §r§lhEloe mE es bObo! Me is no-er of the derp! Will teahc U durp like me! firSt lehson es pootato U muhst get dem 4 stuf! Beye now! §r§rWe just came back from lunch to find some idiot somehow got in and filled up the intro with badly spelled nonsense, with horrible grammer. §r§nYou never complain about The Alchemist's spelling. §r§rA law of the universe is all alchemists have bad spelling, so there's no point complaining about that. §r§oWate, mie spileng ys badd? §r§nForget It.§r§r Anyway, DO NOT TRUST anything this 'Bobo' person says, they seem like an idiot. Due to the nature of the machine, we can't remove this idiot's contribution. Just ignore it if he writes in any more stuff. §r§nHow do you know it's a he? §r§rI don't, people just automatically use he. §r§nFair enough. Why does The Alchemist's bad spelling get written in when he talks?§r§r Just. Don't. Ask.");
		entries.put(new ResourceLocation(Main.MODID, "intro"), new Entry(pages, "Introduction", true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§rBefore you can start making machines to progress, you'll need some ores you might not be used to using. There are 4 ores of interest. §r§nNot counting the normal stuff, like gold and redstone. §r§oHouw ys redftoen nurmel? Wat evin ys redftoen? §r§nDon't know, don't care. §r§rAnyways, the things to look out for are tin, copper, native copper, and rubies. Native Copper is very common, but is only found between y=50 & y=70. When broken, it gives 3 copper nuggets.", new ResourceLocation(Main.MODID + ":textures/blocks/oreNativeCopper.png"), "§r§rNormal Copper ore is also very common, and is only found below y=30. However, you'll need infrastructure to make it into ingots.", new ResourceLocation(Main.MODID + ":textures/blocks/oreCopper.png"), "§r§rTin ore is about as rare as gold, and is only found below y=30. It can be smelted into a tin ingot. Mine any that you find.", new ResourceLocation(Main.MODID + ":textures/blocks/oreTin.png"), "§r§rRuby ore is very rare, and is only found mixed in with quartz ore in the nether. It's only useful if you go into magic (NYI)", new ResourceLocation(Main.MODID + ":textures/blocks/oreRuby.png"), "§r§rCopper and tin can be made into blocks and nuggets with the normal recipe, and rubies can be made into blocks with the normal recipe. Also, copper and tin can be combined to make bronze, a useful alloy. Also, I should note how to make an axle.", new ShapedOreRecipe(Item.getByNameOrId(Main.MODID + ":ingotBronze"), "###", "#$#", "###", '#', "nuggetCopper", '$', "nuggetTin"), new ShapedOreRecipe(Item.getByNameOrId(Main.MODID + ":blockBronze"), "###", "#$#", "###", '#', "ingotCopper", '$', "ingotTin"), new ShapedOreRecipe(new ItemStack(ModItems.axle, 1), "#", "?", "#", '#', Blocks.STONE_BUTTON, '?', "ingotIron"));
		entries.put(new ResourceLocation(Main.MODID, "ores"), new Entry(pages, "Ores", true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§rBefore you can start using technology, you need to understand the power system. There are three main ways to transmit energy: Heat, Rotary, and Steam. Energy can be converted between the three forms in certain machines. This diagram shows the basic conversions:", new ResourceLocation(Main.MODID + ":textures/book/energyDiag.png"), "§r§rAs you can see from the diagram, once enegy is converted to rotary form, you cannot change it back to heat. How exactly the energy is converted will be covered in later entries. A quick overview of each form of energy is that heat is simple, lossy, slow, and expensive to transfer, and is the main form of energy that you will generate. Steam is cheap, simple, lossless, and quick to transfer (making it ideal for transfering energy), and rotary is expensive, very fast, and complicated to transfer, and depending on how well you design your system could be very lossy or nearly lossless. Rotary is also the main type of energy you will be using for machines.");
		entries.put(new ResourceLocation(Main.MODID, "energy"), new Entry(pages, "Basics of Energy", true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§oThee moest bacik fourm ofe enyrgie ys heate. Heete ys tramnspherrde wyth heyte kabiles. Heate cabyles avirage thyre tempurchire wifth that ofe thyngs araond thym. Thiere ayre two pairts tooo eche kabyl, thee ynsulaightor ynd thee konduktur. The conduictir detyrmenes the tranzpher rayte, wythe a raite of one avergyng phully efvery 10 tikcs, ynd .5 afvereging halvway efvery 10 tikcs, ynd soo ohne. Thee conductorrs aure cuprum (.33), diymind (1), ferrous (.1), ynd kwaurtz (.05). Ferrous ynd Quertz yre beste fore limetyng the rayte of heate transpher. Thye ensuletor detyrmines 3 thengs: thee rate of heete loss, thee melteng tempirtchure ofe thye cabel, end wat thee cabul dus whene et melts. A losse ratyng ofe 1 menes thee temperchure avereges wythe the biome efery  10 tickes, wile a ratyng of .5 haf avereges evury 10 tickes. Thee ynsulyters aer woole, which berns at 300*, end has a losse of .01, slyme, whiche re-anymetes at 500*, ynd hase ae losse ofe .005, Yce meltes at 0*, and ys at -10* whyne furst pleced, but hase thye smallist los off .00005. Finaly, tere es obsidean, wich meltes at 2000*, ynd has a losse of .0001. Fhe cabyles yre made in thee same patern as this exampel:", new ShapedOreRecipe(HeatCableFactory.cableMap.get(HeatConductors.COPPER).get(HeatInsulators.WOOL), "###", "$$$", "###", '#', Blocks.WOOL, '$', "ingotCopper"), "§r§oAe uesful tule es fhe thermometer, wich measires thee tempurachure ofe ae heate cabel and es maed as suche:", new ShapedOreRecipe(new ItemStack(ModItems.thermometer, 1), "#", "$", "?", '#', "dyeRed", '$', ModItems.axle, '?', "blockGlass"), "§r§lHELLLOO it's-a-me, bobo! me kNow trik 4 heat! dirt Is much g0od as ins-ool-8-or! hEat relEse power of d1rt! heAt 1et dErP frE!");
		entries.put(new ResourceLocation(Main.MODID, "heat"), new Entry(pages, "Basics of Heat", true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§nSteam is best explained by covering fluids in general. Most machines that need fluid can't have it bucketed in directly, and need you to pipe it in. Fluids obviously flow through pipes, made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.fluidTube, 8), "###", "   ", "###", '#', "ingotBronze"), "§r§nThese tubes can handle any fluid, hot or cold, and average out the pressure between themselves and stuff around them. Pressure is the amount of liquid divided by the amount it can store. Most machines can only accept or output fluids though, so pipes don't average with those, they just insert/extract. There are also tanks, which store 20 buckets, which can be bucketed in and out of them directly. They average their pressure with tubes. They're made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.fluidTank, 1), " # ", "#$#", " # ", '#', "ingotGold", '$', "blockBronze"), "§r§r What is the gold for? Surely bronze is enough? §r§nI thought I was supposed to be doing the bit on steam. And the gold is for reinforcment. §r§rGold? Reinforcment? You do know gold is softer than bronze, right? §r§nBe Quiet! You're breaking the fourth wall! §r§rMy bad, I'll just leave and let you get back to spreading misinformation then. §r§nGood ridance. Anyways, steam is just another fluid, and it's only special because it's great for moving energy. Heat can be converted to steam with boilers (more on that later), and steam can be converted back to heat or rotary energy. Also, it flows pretty quickly with no loss, and the tubes don't melt or anything like that. A boiler is made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.steamBoiler, 1), "#$#", "$?$", "#&#", '#', "ingotBronze", '?', "blockBronze", '$', ModBlocks.fluidTube, '&', "ingotCopper"), "§r§nA boiler is a bit more complicated than it sounds. Heat goes in the bottom, steam comes out the top, and water goes in the side. The boiler turns water into steam once it hits 110*C. One bucket of steam uses " + Math.round(EnergyConverters.DEG_PER_BUCKET_STEAM * 1.1D) + "*C to create and 1 bucket of water, and 10 pieces of salt are created. Salt can be pulled out the bottom, and if the boiler gets full of salt it stops working. Because salt needs to be hoppered out the bottom and heat goes in the bottom, you can't automate the boiler YET, if you care about that sort of thing for some reason. When you use steam to make rotary energy or heat, you get distilled water back instead of normal water. Distilled water can also be used to make steam, and if you use distilled water no salt is created, making the boiler automateable. Also, the boiler only has to be heated to 100*C, and only uses " + EnergyConverters.DEG_PER_BUCKET_STEAM + "*C per bucket, which is how much heat you can get out of a bucket of steam. Distilled water is just better for making steam for some reason. I don't really know why it takes less heat to boil, maybe the Alchemist could tell you. One last thing in this long ramble, to measure the fluid pressure in something make a fluid guage, made like so:", new ShapedOreRecipe(new ItemStack(ModItems.fluidGauge, 1), "#", "$", '#', Items.COMPASS, '$', ModBlocks.fluidTube));
		entries.put(new ResourceLocation(Main.MODID, "steam"), new Entry(pages, "Basics of Steam", true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§rThe most complicated energy system is rotary. Before I get into specifics, I should explain the physics. Because they are so complicated, I will explain the physics twice: first in a complete way that includes the calculations, and then again in a dumbed down way for people who hate math. Knowing the actual calculations can help optimize rotary systems greatly though. Just skip ahead through this part if you hate math. Gear physics should first be explained with a single gear. Imagine this 1 gear is floating in space, with no friction, AKA no loss. The gear has a mass, and a moment of inertia. A moment of inertia (I) is like mass, but also takes into account the shape of the gear. It is such a pain to calculate that each gear will have the value of I in the tooltip. For a perfect cylinder, I is easy to calculate though: I = R*R*M. Or in plain english, I equals the radius squared times mass. Just assume gears are perfect cylinders when calculating I. The calculations for I outside of perfect shapes involves calculus, but with gears just say they are cylinders. The speed the gear is spinning (in radians per second) is represented by a lowercase omega, w. §r§nThere's no such thing as a lowercase omega. You just wrote a normal W. §r§rYes there is, and they look almost the same. Now leave. Anyways, a radian is a measure of angles, like degrees. There are 2 Pi radians in a circle. The energy in a gear is equal to I*w*w, or I times speed squared. Power is the change in energy every second. Most machines that use rotary energy use it from an attached gear. Most machines will use energy from the gear at a rate determined by the speed. A typical machine (like the grindstone, to be covered in a different section) will have an amount of energy need per operation, a minimum speed, and a maximum speed. They will still work fine above the maximum speed. They will draw all the energy from the gear every tick at or above the maximum speed, will not work at all below the minimum speed, and halfway between the maximum and minimum speed will draw half the energy from the gear every tick. At all speeds it will draw the same amount of energy total, but it runs faster at higher speeds. Now comes gears transmitting energy between each other. Every tick, all gears in a network redistribute energy. A network has a value called V which equals the radius of any gear times the speed of that gear. All gears in a network have the same V. Each gear will have a fraction of the network's energy equal to the fraction of the network's mass that the gear has. So a network of light gears with one heavy gear will result in a higher concentration of energy in the heavier gear. Therefore, putting the heavy gear attached to the machine you want to power will give the machine a bigger bank of energy to draw every tick. Now comes loss: Loss is complicated, and I'll only give you the basics. (Note from the mod author: I made up the loss mechanics for balance reasons.) Every tick, each gear loses a percentage of its energy. The % lost changes based on 2 factors, gears with a higher value of I have a lower loss, and higher speeds increases loss. Therefore, gears with a higher value of I are doubly more efficient: higher I value directly decreases loss, and for the same amount of energy a higher value of I decreases speeds. Now for the dumbed down version of gears: Gears spin and more energy means faster spin, but heavier gears means slower spin. Faster spin means faster machines but more energy loss. Big gears have less loss. Ok, now for something important whether you hate or love math: Every gear network needs at least one Master Axis with a gear attached to the front. Additional master axises have no effect. Without a Master Axis, nothing will work. They are made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.masterAxis, 1), "###", "#?#", "#$#", '#', "ingotIron", '?', Blocks.DROPPER, '$', "stickIron"), "§r§rNow for how to make the gears themselves. There are several gear materials, and in order of most to least dense they are: Gold, Copper, Bronze, Iron, & Tin, with gold being more than twice as dense as copper. The two mains types of gears are small gears and large gears. Small gears have a radius of .5 blocks, and 6 can fit in one blockspace. They need to be attached to a solid block. They are made like this, with this particuler recipe making gold ones:", new ShapedOreRecipe(new ItemStack(GearFactory.basicGears.get(GearTypes.GOLD), 8), "***", "*&*", "***", '*', "ingotGold", '&', "blockGold"), "§r§rLarge gears have a radius of 1.5 blocks, and do not need to be supported on something. This recipe makes a gold large gear, but once again they are available in all varieties:", new ShapedOreRecipe(new ItemStack(GearFactory.largeGears.get(GearTypes.GOLD), 1), "***", "*&*", "***", '*', GearFactory.basicGears.get(GearTypes.GOLD), '&', "blockGold"), "§r§rGears can connect around corners, do inside corners, can connect on the same plane, and basically connect in any way that looks logical. One last thing, there are a few tools that help with gears. The hand crank, made like so:", new ShapedOreRecipe(new ItemStack(ModItems.handCrank, 1), " ?", "##", "$ ", '?', Blocks.LEVER, '#', "stickWood", '$', "cobblestone"), "§r§rallows you to manually add energy to gears by cranking them. Right click to spin them one way, shift right click to crank them the other way. Holding down the button is best. Also, a speedometer can measure the speed and energy of a gear, and is made like so:", new ShapedOreRecipe(new ItemStack(ModItems.speedometer, 1), " #", "#$", '#', "string", '$', "ingotIron"), "§r§rOne last trick, if you combine the thermometer, the speedometer, and the fluid guage, you can create an omnimeter which combines all of their functions into one device, which can also give additional information. I highly recommend making one like so:", new ShapedOreRecipe(new ItemStack(ModItems.omnimeter, 1), " # ", "&$%", " ? ", '#', ModItems.fluidGauge, '&', ModItems.thermometer, '$', "gemEmerald", '%', ModItems.speedometer, '?', Items.CLOCK) );
		entries.put(new ResourceLocation(Main.MODID, "rotary"), new Entry(pages, "Basics of Rotary", true));
		pages = new ArrayList<IPage>();
		
		categories.add(new CategoryItemStack(entries, "The Basics", new ItemStack(Item.getByNameOrId("crossroads:oreCopper"), 1)));
		entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		
		//HEAT
		createPages(pages, "§r§nThere are a few ways to make heat, but the easiest is to use a coal heater. §r§rI wouldn't call it easiest, cheapest early on maybe, but its not exactly as easily automatable as a heat exchanger. §r§oIye prepher ufther mefhodes purcunally. §r§nYou two and your automation. Not everything has to be automated. §r§rBut at least some things should be. Your idea of enough automation is using the torch trick to collect sand faster. §r§nLook, everything has a place, unless that something is automation. Doing things the hard way is more proper. §r§rBut the hard way is, by definition, harder. §r§nAnd worth it. Now let me talk. A coal heater is made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.coalHeater, 1), "#*#", "#$#", "###", '#', "cobblestone", '$', Blocks.FURNACE, '*', "ingotCopper"), "§r§nA Coal Heater can burn coal or charcoal to produce 1600*C per piece. The heat cables can only be connected to the top. §r§oAse yoew kann sae bie fhe cuprum patche oen fhe topp. §r§rRemember to be careful with the coal heater before you set up regulatory systems, you don't want to melt the heat cables. Also, the coal heater works as a copper heat cable in that it can move heat between itself and things above it without the aid of a cable.");
		entries.put(new ResourceLocation(Main.MODID, "coalHeater"), new EntryItemStack(pages, "Coal Heater", new ItemStack(ModBlocks.coalHeater, 1), true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§rThe heating chamber is the most obvious and simplest application for heat. It basically acts as a heat powered furnace, though it is faster than a normal furnace. It is made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.heatingChamber, 1), "#*#", "#$#", "###", '#', "ingotIron", '$', Blocks.FURNACE, '*', "ingotCopper"), "§r§rThe Heating Chamber only works above 200*C, and consumes 100*C to cook one item. Now obviously it is difficult to compare the efficieny of the heating chamber with that of the furnace due to that numerous ways to generate heat, and the fact that the only burnables that can be burned for heat are coal and charcoal, but assuming no loss and that the heating chamber is already pre-heated, 1 coal in a coal heater will produce enough heat to cook 16 items, as opposed to 8 in a furnace. Heat cables can only be connected to the top.");
		entries.put(new ResourceLocation(Main.MODID, "heatingChamber"), new EntryItemStack(pages, "Heating Chamber", new ItemStack(ModBlocks.heatingChamber, 1), true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§rIf you have already begun working with heat at this point, then you have most likely run into trouble with heat cables melting at high tempertures. The heat exchanger is the solution to that. Heat cables can connect to any side of it other than the bottom. It never melts, and like a heat cable it has loss. Specifically a loss of 0.1. Basically, use it as a heat sink in your heat systems. I would suggest making your main heat system with good conductors, and then using bad conductors like iron to connect to a Heat Exchanger to prevent overheating. Heat exchangers are made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.heatExchanger, 1), "#$#", "$$$", "###", '#', Blocks.IRON_BARS, '$', "ingotCopper"), "§r§rHeat exchangers have one extra ability that when certain things are placed below them they can exchange heat with the block below them and change the block into something else. For example, when the exchanger is below 3000*C, it turns lava below it to cobblestone, and adds 1000* to the heat exchanger. Fire caps at 2000* and adds 300*C, magma blocks turn to netherack, adding 500*C and capping at 2000*C. Snow decreases the temperture by 50*C, with a limit of -20*C, and turns to water. Ice and Packed ice also melt, with ice removing 70*C with a limit of -50*C, and packed ice removing 140*C with a limit of -100*C. There is also another varient of heat exchanger, the insulated heat exchanger which is made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.insulHeatExchanger, 1), "###", "#$#", "###", '#', "obsidian", '$', ModBlocks.heatExchanger), "§r§rAn insulated heat exchanger does not lose heat to its surroundings. §r§nWhat's the point of a heat exchanger that doesn't exchange heat? §r§rThe point is that it can still move heat with blocks below them. Therefore, if you wanted to use it for heat generation it is more efficient. A little tip if that is your intention, if you use lava it doesn't have to be a lava source.");
		entries.put(new ResourceLocation(Main.MODID, "heatExchanger"), new EntryItemStack(pages, "Heat Exchangers", new ItemStack(ModBlocks.heatExchanger, 1), true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§oShuolde youe neede too heate fomefhyng too a hiyre tempurchure theyn ae phurnas canne reeche, ae heatyng crucible wille satifsy. Ite canne yther heate coblestowne ento magmae oer grounde cuprum ynto moltene cuprim whenne heeted abuve 1000*C. Ynserte ityms throwe a hopere oer other atomahted mefhod. Pippe fhe fluide oute fhe sidee. Heate yt throwe fhe botome. Mayke oene licke soe:", new ShapedOreRecipe(new ItemStack(ModBlocks.heatingCrucible, 1), "# #", "#?#", "###", '#', Blocks.HARDENED_CLAY, '?', Items.CAULDRON), "§r§oThise crucible ys kruciale toe prossecyng cuprum oere. Ite ohperates fahster ate hiher tempurtchures, reechyng peke eficsencie aet 1500*C. Efverie opuratchion kreates 200mB ofe fluide.");
		entries.put(new ResourceLocation(Main.MODID, "heatingCrucible"), new EntryItemStack(pages, "Heating Crucible", new ItemStack(ModBlocks.heatingCrucible, 1), true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§nThe fluid cooling chamber is a machine with an annoyingly long name. §r§rNonetheless, it's still important. §r§nMaybe. It's the counterpart to the heating crucible. It can cool liquids to produce items. Extract items out the bottom, put fluid in the side, and connect heat cables to the top. When the fluid cooling chamber cools a liquid it creates heat, and if the machine gets above a certain temperture it stops working. It can turn a bucket of lava into obsidian when below 1000*, adding 500* per craft, it can turn 1/5 of a bucket of molten copper into a copper ingot when below 1000*C, adding 100*C per craft, it can turn water into ice when below -10*, adding 1* per craft, and it can turn distilled water into packed ice when below -20*C, adding 2* per craft. It is made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.fluidCoolingChamber, 1), "###", "# #", "%%%", '#', "ingotTin", '%', "ingotIron"));
		entries.put(new ResourceLocation(Main.MODID, "fluidCooling"), new EntryItemStack(pages, "Fluid Cooling Chamber", new ItemStack(ModBlocks.fluidCoolingChamber, 1), true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§rIf you need to cool a machine below the ambient biome temperture, such as to create packed ice in a fluid cooling chamber, §r§nI still say it's a stupidly long name. §r§rAnd I don't care. As I Was Saying, should you need to cool something below ambiemt tempertures, I would recommend a salt reactor. Make one like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.saltReactor, 1), "#$#", "$%$", "#@#", '#', "ingotTin", '$', ModBlocks.fluidTube, '%', "blockSalt", '@', "ingotCopper"), "§r§rWith a piece of salt and 200mB of distilled water, the salt reactor decreases its temperture by 1* and creates 200mB of normal water. §r§nThat's it? §r§rWhat do you mean that's it? §r§nWhen you said we needed to do the bit on something called a salt reactor, I was expecting something involving radiation that would glow green. Also, 1* isn't very much. §r§rYou do know that the whole concept of radiation glowing green is a misconception, right? And if you use well insulated heat cables, such as ice, 1* is quite a bit. §r§nReally though? Salt to cold. Pretty boring. How does that even work, anyway? §r§oAye canne ansere thahte. Disohlvyng natrium chloride en watre ys endothermic. Yte ueses distilede watre toe bee abel to disohlv moer.");
		entries.put(new ResourceLocation(Main.MODID, "saltReactor"), new EntryItemStack(pages, "Salt Reactor", new ItemStack(ModBlocks.saltReactor, 1), true));
		pages = new ArrayList<IPage>();
		
		categories.add(new CategoryItemStack(entries, "Heat Machines", new ItemStack(ModBlocks.heatingChamber, 1)));
		entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		
		//ROTARY
		createPages(pages, "§r§rThe Grindstone is the first rotary powered machine you should create. Make one like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.grindstone, 1), "#$#", "#?#", "#$#", '#', "cobblestone", '?', "stickIron", '$', Blocks.PISTON), "§r§rThe grindstone has a minimum speed of 0, and a maximum speed of 10. It uses 100 energy total to grind up an item. The grindstone is interesting in that it can be optimized more than usual. You can put a gear on the top AND bottom of the grindstone. When calculating the speed of the attached gears, the grindstone adds together the speeds of the top and bottom gear. If the two gears are spinning in opposite directions, this is beneficial. If they spin in the same direction, this is detrimental. Only one gear is actually neccesary, but due to loss increasing at higher speeds, this allows higher efficiencies with low speeds, reducing energy loss. Of course, items can only be extracted out the bottom, so automation requires the use of only one gear. Items can be inserted into any side other than the bottom. Early on, a hand crank is sufficient to run a grindstone, albeit slowly. The grindstone is the first stage in proccesing copper ore, and the proccesing chain eventually results in two ingots per copper ore. The recipes that the grindstone can do are as follows: Copper Ore -> 2 Copper Dust + 1 Sand, Gravel -> 1 Flint, Bone -> 5 Bonemeal, Coal Block -> 1 Gunpowder, Nether Wart Block -> 9 Netherwart, Wheat -> 3 Seeds, Pumpkin -> 8 Pumpkin Seeds, Melon (slice) -> 3 Melon Seeds. §r§lhOI mE i$ BOBO! musHi mAsh1 p0Tato! iT RAw- me no eAt!");//TODO recipe rendering
		entries.put(new ResourceLocation(Main.MODID, "grindstone"), new EntryItemStack(pages, "Grindstone", new ItemStack(ModBlocks.grindstone, 1), true));
		pages = new ArrayList<IPage>();
		createPages(pages, "§r§rTranferring items vertically is always a challenge, with hoppers being incapable of the job, dropper chains requiring a timer, and slime blocks often losing items or getting stuck mid launch. However, at the cost of some rotary power, item chutes provide the solution. There are two parts to a chute: the chute itself and the ports. They are made like so:", new ShapedOreRecipe(new ItemStack(ModBlocks.itemChute, 4), "#$#", "#$#", "#$#", '#', "ingotIron", '$', "stickIron"), new ShapelessOreRecipe(new ItemStack(ModBlocks.itemChutePort, 1), ModBlocks.itemChute, Blocks.IRON_TRAPDOOR), "§r§rTo use item chutes, place down a chute port where you want to insert items into the chute automatically. Insert items into the large hole in the port. Put a column of normal Item Chutes from the bottom port up to one block below where you want to get the items out. Put a second chute port on top of the pillar of chutes. Items will be dropped out of the large hole in the top port. Provide the bottom port energy with a gear attached to the small axle hole in the side. Only the bottom port needs power. The chute only operatoes above speeds of .1, and consumes .5 energy per item. A demonstration image is included.", new ResourceLocation(Main.MODID, ":textures/book/chute.png"));
		entries.put(new ResourceLocation(Main.MODID, "itemChute"), new EntryItemStack(pages, "Item Chutes", new ItemStack(ModBlocks.itemChutePort, 1), true));
		pages = new ArrayList<IPage>();
		
		categories.add(new CategoryItemStack(entries, "Rotary Machines", new ItemStack(GearFactory.basicGears.get(GearTypes.BRONZE), 1)));
		entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		
		//TODO FLUIDS
		
		categories.add(new CategoryItemStack(entries, "Fluid Machines", new ItemStack(ModBlocks.fluidTube, 1)));
		entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		
		//TODO MISC
		
		categories.add(new CategoryItemStack(entries, "Miscellanous", new ItemStack(ModBlocks.brazier, 1)));
		entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		
		main.setTitle("Main Menu");
		main.setWelcomeMessage("Welcome to Crossroads");
		main.setDisplayName("mysteriousJournal");
		main.setColor(Color.GRAY);
		main.setCategoryList(categories);
		main.setRegistryName("crossroadsMainGuide");
		main.setSpawnWithBook(true);
		
		GuideAPI.BOOKS.register(main);
	}

	
	/** Splits up a long string into pages. I can't use PageHelper for this because of the § symbol.
	 * 
	 */
	private static void createTextPages(ArrayList<IPage> pages, String text){

		final int PERPAGE = 400;

		char symbol = 167;
		String format = "";
		String snip = "";

		final double ILIMIT = Math.ceil((double) (text.replaceAll("§r§r", "").replaceAll("§r§n", "").replaceAll("§r§o", "").replaceAll("§r§l", "").length()) / PERPAGE);
		for(int i = 0; i < ILIMIT; ++i){
			String chunk = text.substring(i * PERPAGE, (i == ILIMIT - 1) ? (text.length() % PERPAGE) + i * PERPAGE : (i + 1) * PERPAGE);

			pages.add(new PageText(format + snip + (i == ILIMIT - 1 ? chunk : chunk.substring(0, chunk.lastIndexOf(" ")))));
			((Page) pages.get(pages.size() - 1)).setUnicodeFlag(true);
			
			snip = chunk.substring(chunk.lastIndexOf(" ") + 1);

			int marker = (text.substring(0, i == ILIMIT - 1 ? (text.length() % PERPAGE)  + i * PERPAGE: (i + 1) * PERPAGE)).lastIndexOf(symbol);
			format = text.substring(marker - 2, marker + 2);

		}
	}
	
	private static void createPages(ArrayList<IPage> pages, Object... parts){
		for(Object obj : parts){
			if(obj instanceof String){
				createTextPages(pages, (String) obj);
			}else if(obj instanceof ItemStack){
				pages.add(new PageFurnaceRecipe((ItemStack) obj));
			}else if(obj instanceof ResourceLocation){
				pages.add(new PageImage((ResourceLocation) obj));
			}else if(obj instanceof IRecipe){
				pages.add(new PageIRecipe((IRecipe) obj));
			}else{
				System.err.println("CROSSROADS: ERROR WRITING TO THE JOURNAL, REPORT TO MOD AUTHOR");
			}
		}
	}

}
