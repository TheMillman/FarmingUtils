package com.the_millman.farmingutils.common.blockentity.renderer;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.the_millman.farmingutils.common.blockentity.InternalFarmerBE;
import com.the_millman.farmingutils.common.blocks.InternalFarmerBlock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class InternalFarmerBERenderer implements BlockEntityRenderer<InternalFarmerBE> {

	public InternalFarmerBERenderer(BlockEntityRendererProvider.Context context) {
		
	}
	
	@Override
	public void render(InternalFarmerBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
		
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		ItemStack itemStack = pBlockEntity.getRenderStack();
		pPoseStack.pushPose();
		pPoseStack.translate(0.5f, 0.2f+pBlockEntity.getScaledProgress()/2, 0.5f);
		pPoseStack.scale(pBlockEntity.getScaledProgress(), pBlockEntity.getScaledProgress(), pBlockEntity.getScaledProgress());
		
		Direction direction = pBlockEntity.getBlockState().getValue(InternalFarmerBlock.FACING);
		
//		Vector3f XP = new Vector3f(1.0F, 0.0F, 0.0F);
//		Vector3f YP = new Vector3f(0.0F, 1.0F, 0.0F);
		Quaternionf XP = new Quaternionf(1.0F, 0.0F, 0.0F, 1.0F);
		Quaternionf YP = new Quaternionf(0.0F, 1.0F, 0.0F, 1.0F);
		
		if (direction == Direction.NORTH) {
//			pPoseStack.mulPose(Vector3f.XP.rotationDegrees(0));
			pPoseStack.mulPose(XP.rotationX(0));
		} else if (direction == Direction.SOUTH) {
			pPoseStack.mulPose(XP.rotationX(0));
		} else if (direction == Direction.WEST) {
			pPoseStack.mulPose(XP.rotationX(0));
			pPoseStack.mulPose(YP.rotationY(90));
		} else {
			pPoseStack.mulPose(XP.rotationX(0));
			pPoseStack.mulPose(YP.rotationY(90));
		}
		
		itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI,
				getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,
				pPoseStack, pBufferSource, 1);
		pPoseStack.popPose();
	}

	private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
