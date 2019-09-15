package com.Da_Technomancer.crossroads.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class EntityRenderEmpty extends EntityRenderer<Entity>{

	protected EntityRenderEmpty(EntityRendererManager renderManager){
		super(renderManager);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(Entity entity){
		return null;
	}
}
