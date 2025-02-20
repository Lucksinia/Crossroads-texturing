package com.Da_Technomancer.crossroads.render.tesr;

import com.Da_Technomancer.crossroads.api.CRProperties;
import com.Da_Technomancer.crossroads.api.render.CRRenderUtil;
import com.Da_Technomancer.crossroads.blocks.CRBlocks;
import com.Da_Technomancer.crossroads.blocks.technomancy.TemporalAcceleratorTileEntity;
import com.Da_Technomancer.crossroads.api.CRMaterialLibrary;
import com.Da_Technomancer.crossroads.render.CRRenderTypes;
import com.Da_Technomancer.essentials.api.ConfigUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.BlockState;

public class TemporalAcceleratorRenderer extends EntropyRenderer<TemporalAcceleratorTileEntity>{


	protected TemporalAcceleratorRenderer(BlockEntityRendererProvider.Context dispatcher){
		super(dispatcher);
	}

	@Override
	public void render(TemporalAcceleratorTileEntity te, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int combinedOverlay){
		BlockState state = te.getBlockState();
		if(state.getBlock() != CRBlocks.temporalAccelerator){
			return;
		}
		super.render(te, partialTicks, matrix, buffer, combinedLight, combinedOverlay);

		Direction dir = state.getValue(CRProperties.FACING);

		matrix.translate(0.5D, 0.5D, 0.5D);
		matrix.mulPose(dir.getRotation());

		//Area of effect overlay when holding wrench
		if(ConfigUtil.isWrench(Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND)) || ConfigUtil.isWrench(Minecraft.getInstance().player.getItemInHand(InteractionHand.OFF_HAND))){
			float radius = TemporalAcceleratorTileEntity.SIZE / 2F + 0.01F;
			int[] overlayCol = {255, 100, 0, 60};
			VertexConsumer overlayBuilder = buffer.getBuffer(CRRenderTypes.AREA_OVERLAY_TYPE);

			matrix.pushPose();
			matrix.translate(0, radius + 0.5D - 0.001D, 0);

			overlayBuilder.vertex(matrix.last().pose(), -radius, -radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, -radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, -radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 8).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), -radius, -radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 8).endVertex();

			overlayBuilder.vertex(matrix.last().pose(), -radius, radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 8).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), -radius, radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 8).endVertex();

			overlayBuilder.vertex(matrix.last().pose(), radius, -radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 8).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, -radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 8).endVertex();

			overlayBuilder.vertex(matrix.last().pose(), -radius, -radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), -radius, radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), -radius, radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 8).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), -radius, -radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 8).endVertex();

			overlayBuilder.vertex(matrix.last().pose(), -radius, -radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, -radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 8).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), -radius, radius, -radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 8).endVertex();

			overlayBuilder.vertex(matrix.last().pose(), -radius, -radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, -radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 0).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), radius, radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(8, 8).endVertex();
			overlayBuilder.vertex(matrix.last().pose(), -radius, radius, radius).color(overlayCol[0], overlayCol[1], overlayCol[2], overlayCol[3]).uv(0, 8).endVertex();

			matrix.popPose();
		}

		//Render the two rotating octagons

		TextureAtlasSprite sprite = CRRenderUtil.getTextureSprite(CRRenderTypes.GEAR_8_TEXTURE);
		VertexConsumer builder = buffer.getBuffer(RenderType.solid());
		int[] col = CRRenderUtil.convertColor(CRMaterialLibrary.findMaterial("copshowium").getColor());
		float angle = CRRenderUtil.getRenderTime(partialTicks, te.getLevel());
		float lHalf = 7F / 16F;//Half the side length of the octagon
		int medLight = CRRenderUtil.calcMediumLighting(combinedLight);

		matrix.mulPose(Axis.YP.rotationDegrees(angle));
		matrix.translate(0, 5F / 16F, 0);
		matrix.scale(2F * lHalf, 1, 2F * lHalf);
		CRModels.draw8Core(builder, matrix, col, medLight, sprite);

		matrix.mulPose(Axis.YP.rotationDegrees(-2F * angle));
		matrix.translate(0, -4F / 16F, 0);
		matrix.scale(0.8F, 1, 0.8F);
		CRModels.draw8Core(builder, matrix, col, medLight, sprite);
	}
}
