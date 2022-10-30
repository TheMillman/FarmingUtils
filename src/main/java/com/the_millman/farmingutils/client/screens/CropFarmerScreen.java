package com.the_millman.farmingutils.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.the_millman.farmingutils.common.containers.CropFarmerContainer;
import com.the_millman.themillmanlib.client.screens.ItemEnergyScreen;
import com.the_millman.themillmanlib.core.util.LibResources;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CropFarmerScreen extends ItemEnergyScreen<CropFarmerContainer> {

	private ResourceLocation GUI = LibResources.THREE_FOR_SIX_GUI;
	private ResourceLocation OVERLAY = LibResources.OFFSETS;
	
	public CropFarmerScreen(CropFarmerContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}
	
	@Override
	public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(pPoseStack);
		super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.renderTooltip(pPoseStack, pMouseX, pMouseY);
		this.renderEnergyLevel(pPoseStack, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
		RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        this.renderEnergyBar(pPoseStack, OVERLAY);
	}
}
