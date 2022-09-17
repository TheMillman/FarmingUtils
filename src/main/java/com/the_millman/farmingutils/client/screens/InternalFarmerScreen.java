package com.the_millman.farmingutils.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.common.containers.InternalFarmerContainer;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.client.screens.ItemEnergyFluidScreen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class InternalFarmerScreen extends ItemEnergyFluidScreen<InternalFarmerContainer> {
	
	private ResourceLocation GUI = new ResourceLocation(FarmingUtils.MODID, "textures/gui/internal_farmer_gui.png");
	private ResourceLocation OVERLAY = new ResourceLocation(FarmingUtils.MODID, "textures/gui/offset_buttons.png");
	
	public InternalFarmerScreen(InternalFarmerContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}
	
	@Override
	protected void init() {
		super.init();
		initFluidRenderer(FarmingConfig.INTERNAL_FARMER_FLUID_CAPACITY.get(), true, 13, 52);
	}
	
	@Override
	protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
		int xPos = (width - imageWidth) / 2;
        int yPos = (height - imageHeight) / 2;
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
        renderFluidTooltip(pPoseStack, pMouseX, pMouseY, 108, 120, 18, 69, menu.getFluidStack(), xPos, yPos, "tooltip.farmingutils.fluid_amount_capacity", "tooltip.farmingutils.fluid_amount");
        
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
        renderer.render(pPoseStack, relX + 108, relY + 18, menu.getFluidStack());
        this.renderEnergyBar(pPoseStack, OVERLAY);
	}
}
