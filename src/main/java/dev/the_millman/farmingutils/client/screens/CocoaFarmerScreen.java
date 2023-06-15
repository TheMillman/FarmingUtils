package dev.the_millman.farmingutils.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import dev.the_millman.farmingutils.common.containers.CocoaFarmerContainer;
import dev.the_millman.themillmanlib.client.screens.ItemEnergyScreen;
import dev.the_millman.themillmanlib.core.util.LibResources;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CocoaFarmerScreen extends ItemEnergyScreen<CocoaFarmerContainer> {

	private ResourceLocation GUI = LibResources.THREE_FOR_THREE_GUI;
	private ResourceLocation OVERLAY = LibResources.OFFSETS;
	
	public CocoaFarmerScreen(CocoaFarmerContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
		this.renderEnergyLevel(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
		RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        blit(pPoseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        renderEnergyBar(pPoseStack, OVERLAY);
	}

}