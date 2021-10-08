package com.Da_Technomancer.crossroads;

import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.client.extensions.IForgeKeybinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keys{

	//Stored as IForgeKeybinding instead of KeyBinding as KeyBinding is a client-side-only class.
	public static IForgeKeybinding controlEnergy;
	public static IForgeKeybinding controlPotential;
	public static IForgeKeybinding controlStability;
	public static IForgeKeybinding controlVoid;
	public static IForgeKeybinding boost;

	public static boolean keysInitialized = false;

	protected static void init(){
		try{
			controlEnergy = new KeyMapping("key.control_energy", InputConstants.UNKNOWN.getValue(), Crossroads.MODID);
			controlPotential = new KeyMapping("key.control_potential", InputConstants.UNKNOWN.getValue(), Crossroads.MODID);
			controlStability = new KeyMapping("key.control_stability", InputConstants.UNKNOWN.getValue(), Crossroads.MODID);
			controlVoid = new KeyMapping("key.control_void", InputConstants.UNKNOWN.getValue(), Crossroads.MODID);
			boost = new KeyMapping("key.prop_pack_boost", 341, Crossroads.MODID);//341 is Control
			keysInitialized = true;
		}catch(RuntimeException e){
			Crossroads.logger.error("Keys loaded on server side; Report to mod author", e);
		}
		ClientRegistry.registerKeyBinding((KeyMapping) controlEnergy);
		ClientRegistry.registerKeyBinding((KeyMapping) controlPotential);
		ClientRegistry.registerKeyBinding((KeyMapping) controlStability);
		ClientRegistry.registerKeyBinding((KeyMapping) controlVoid);
		ClientRegistry.registerKeyBinding((KeyMapping) boost);
	}
}
