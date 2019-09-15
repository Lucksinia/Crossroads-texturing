package com.Da_Technomancer.crossroads.entity;

import com.Da_Technomancer.crossroads.Crossroads;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderFlameCoreEntity extends EntityRenderer<EntityFlameCore>{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Crossroads.MODID, "textures/particles/flame.png");

	protected RenderFlameCoreEntity(EntityRendererManager renderManager){
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlameCore entity){
		return TEXTURE;
	}

	@Override
	public void doRender(EntityFlameCore entity, double x, double y, double z, float entityYaw, float partialTicks){
		if(entity.col == null){
			return;
		}

		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		float brightX = OpenGlHelper.lastBrightnessX;
		float brightY = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GlStateManager.color((float) entity.col.getRed() / 255F, (float) entity.col.getGreen() / 255F, (float) entity.col.getBlue() / 255F, (float) entity.col.getAlpha() / 255F);
		GlStateManager.translate(x, y, z);

		double scale = EntityFlameCore.FLAME_VEL * (float) entity.ticksExisted;
		GlStateManager.scale(scale, scale, scale);

		bindEntityTexture(entity);

		BufferBuilder buf = Tessellator.getInstance().getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		
		buf.pos(-1, -1, -1).tex(0, 0).endVertex();
		buf.pos(1, -1, -1).tex(1, 0).endVertex();
		buf.pos(1, 1, -1).tex(1, 1).endVertex();
		buf.pos(-1, 1, -1).tex(0, 1).endVertex();
		
		buf.pos(-1, -1, -1).tex(0, 0).endVertex();
		buf.pos(1, -1, -1).tex(1, 0).endVertex();
		buf.pos(1, -1, 1).tex(1, 1).endVertex();
		buf.pos(-1, -1, 1).tex(0, 1).endVertex();
		
		buf.pos(-1, -1, -1).tex(0, 0).endVertex();
		buf.pos(-1, -1, 1).tex(1, 0).endVertex();
		buf.pos(-1, 1, 1).tex(1, 1).endVertex();
		buf.pos(-1, 1, -1).tex(0, 1).endVertex();
		
		buf.pos(-1, -1, 1).tex(0, 0).endVertex();
		buf.pos(1, -1, 1).tex(1, 0).endVertex();
		buf.pos(1, 1, 1).tex(1, 1).endVertex();
		buf.pos(-1, 1, 1).tex(0, 1).endVertex();
		
		buf.pos(-1, 1, -1).tex(0, 0).endVertex();
		buf.pos(1, 1, -1).tex(1, 0).endVertex();
		buf.pos(1, 1, 1).tex(1, 1).endVertex();
		buf.pos(-1, 1, 1).tex(0, 1).endVertex();
		
		buf.pos(1, -1, -1).tex(0, 0).endVertex();
		buf.pos(1, -1, 1).tex(1, 0).endVertex();
		buf.pos(1, 1, 1).tex(1, 1).endVertex();
		buf.pos(1, 1, -1).tex(0, 1).endVertex();
		
		Tessellator.getInstance().draw();
		
		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}

	@Override
	public boolean shouldRender(EntityFlameCore livingEntity, ICamera camera, double camX, double camY, double camZ){
		return true;
	}
}
