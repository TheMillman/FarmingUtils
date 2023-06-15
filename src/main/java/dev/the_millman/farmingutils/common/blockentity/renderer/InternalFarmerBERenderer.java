package dev.the_millman.farmingutils.common.blockentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import dev.the_millman.farmingutils.common.blockentity.InternalFarmerBE;
import dev.the_millman.farmingutils.common.blocks.InternalFarmerBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
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
		pPoseStack.translate(0.5f, 0.2f+pBlockEntity.getScaledProgress(pBlockEntity)/2, 0.5f);
		pPoseStack.scale(pBlockEntity.getScaledProgress(pBlockEntity), pBlockEntity.getScaledProgress(pBlockEntity), pBlockEntity.getScaledProgress(pBlockEntity));
		
		Direction direction = pBlockEntity.getBlockState().getValue(InternalFarmerBlock.FACING);
		
		if (direction == Direction.NORTH) {
			pPoseStack.mulPose(Axis.XP.rotationDegrees(0));
		} else if (direction == Direction.SOUTH) {
			pPoseStack.mulPose(Axis.XP.rotationDegrees(0));
		} else if (direction == Direction.WEST) {
			pPoseStack.mulPose(Axis.XP.rotationDegrees(0));
			pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
		} else {
			pPoseStack.mulPose(Axis.XP.rotationDegrees(0));
			pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
		}
		
		itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED,
				getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,
				pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
		pPoseStack.popPose();
	}

	private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
	
	@Override
	public boolean shouldRenderOffScreen(InternalFarmerBE pBlockEntity) {
		return true;
	}
}
