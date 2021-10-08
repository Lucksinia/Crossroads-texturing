package com.Da_Technomancer.crossroads.gui.screen;

import com.Da_Technomancer.crossroads.API.MiscUtil;
import com.Da_Technomancer.crossroads.API.templates.MachineGUI;
import com.Da_Technomancer.crossroads.Crossroads;
import com.Da_Technomancer.crossroads.gui.container.BloodCentrifugeContainer;
import com.Da_Technomancer.crossroads.tileentities.witchcraft.BloodCentrifugeTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class BloodCentrifugeScreen extends MachineGUI<BloodCentrifugeContainer, BloodCentrifugeTileEntity>{

	private static final ResourceLocation BACKGROUND = new ResourceLocation(Crossroads.MODID, "textures/gui/container/blood_centrifuge_gui.png");

	public BloodCentrifugeScreen(BloodCentrifugeContainer cont, Inventory playerInv, Component text){
		super(cont, playerInv, text);
	}

	@Override
	protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
		RenderSystem.color4f(1, 1, 1, 1);
		Minecraft.getInstance().getTextureManager().bind(BACKGROUND);
		blit(matrix, leftPos, topPos, 0, 0, imageWidth, imageHeight);

		blit(matrix, leftPos + 50, topPos + 48, 176, 0, Math.min(66, menu.progRef.get() * 66 / BloodCentrifugeTileEntity.REQUIRED), 9);
	}

	@Override
	protected void renderLabels(PoseStack matrix, int mouseX, int mouseY){
		super.renderLabels(matrix, mouseX, mouseY);

		String s = MiscUtil.localize("container.crossroads.blood_centrifuge.target", MiscUtil.preciseRound(BloodCentrifugeTileEntity.getTargetSpeed(menu.progRef.get()), 1));
		font.draw(matrix, s, imageWidth - 8 - font.width(s), 18, 0x404040);
	}
}
