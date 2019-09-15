package com.Da_Technomancer.crossroads.gui;

import com.Da_Technomancer.crossroads.API.templates.FluidGuiObject;
import com.Da_Technomancer.crossroads.API.templates.IGuiObject;
import com.Da_Technomancer.crossroads.API.templates.InventoryTE;
import com.Da_Technomancer.crossroads.API.templates.MachineGUI;
import com.Da_Technomancer.crossroads.Crossroads;
import com.Da_Technomancer.crossroads.gui.container.CrucibleContainer;
import com.Da_Technomancer.crossroads.tileentities.heat.HeatingCrucibleTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class CrucibleGuiContainer extends MachineGUI{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Crossroads.MODID, "textures/gui/container/fat_collector_gui.png");

	public CrucibleGuiContainer(IInventory playerInv, InventoryTE te){
		super(new CrucibleContainer(playerInv, te));
	}

	@Override
	public void initGui(){
		super.initGui();
		guiObjects = new IGuiObject[1];
		guiObjects[0] = new FluidGuiObject(this, 0,1, 4_000, (width - xSize) / 2, (height - ySize) / 2, 70, 70);
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		mc.getTextureManager().bindTexture(TEXTURE);

		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		drawTexturedModalRect(i, j, 0, 0, xSize, ySize);

		drawTexturedModalRect(i + 42, j + 35, 176, 0, te.getField(te.getFieldCount() - 1) * 28 / HeatingCrucibleTileEntity.REQUIRED, 18);


		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
}
