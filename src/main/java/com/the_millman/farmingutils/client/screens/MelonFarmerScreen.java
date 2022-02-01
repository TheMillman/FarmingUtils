package com.the_millman.farmingutils.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.common.containers.MelonFarmerContainer;
import com.the_millman.themillmanlib.client.screens.ItemEnergyScreen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MelonFarmerScreen extends ItemEnergyScreen<MelonFarmerContainer> {

	private ResourceLocation GUI = new ResourceLocation(FarmingUtils.MODID, "textures/gui/melon_farmer_gui.png");
	private ResourceLocation OVERLAY = new ResourceLocation(FarmingUtils.MODID, "textures/gui/offset_buttons.png");
	
	public MelonFarmerScreen(MelonFarmerContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        this.blit(pPoseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        this.renderEnergyBar(pPoseStack, OVERLAY);
	}

}
