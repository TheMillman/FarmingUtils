package com.the_millman.farmingutils.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.common.containers.InternalFarmerContainer;
import com.the_millman.themillmanlib.client.screens.ItemEnergyScreen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.FluidStack;

public class InternalFarmerScreen extends ItemEnergyScreen<InternalFarmerContainer> {
	
	private ResourceLocation GUI = new ResourceLocation(FarmingUtils.MODID, "textures/gui/internal_farmer_gui.png");
	private ResourceLocation OVERLAY = new ResourceLocation(FarmingUtils.MODID, "textures/gui/offset_buttons.png");
	
	public InternalFarmerScreen(InternalFarmerContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}

	@Override
	public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(pPoseStack);
		super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.renderTooltip(pPoseStack, pMouseX, pMouseY);
		this.renderEnergyLevel(pPoseStack, pMouseX, pMouseY);
		this.renderFluidLevel(pPoseStack, pMouseX, pMouseY);
	}
	
	@Override
	protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
		RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        this.renderEnergyBar(pPoseStack, OVERLAY);
        this.renderFluidBar(pPoseStack, OVERLAY);
	}

	protected void renderFluidLevel(PoseStack matrixStack, int mouseX, int mouseY) {
		this.renderTooltip(matrixStack, "Water: ", this.menu.getFluidStack(), mouseX, mouseY, 108, 18, 120, 69);
	}
	
	protected void renderTooltip(PoseStack matrixStack, String energy, FluidStack fluidStack, int mouseX, int mouseY, int xLeft, int yTop, int xRight, int yBot) {
		if (mouseX >= this.getGuiLeft() + xLeft && mouseX <= this.getGuiLeft() + xRight && mouseY >= this.getGuiTop() + yTop && mouseY <= this.getGuiTop() + yBot) {
			this.renderTooltip(matrixStack, Component.translatable("Water: " + this.menu.getFluidStack().getAmount()), mouseX, mouseY);
		}
	}

	protected void renderFluidBar(PoseStack matrixStack, ResourceLocation overlay) {
		RenderSystem.setShaderTexture(0, overlay);
		int i = this.getGuiLeft();
		int j = this.getGuiTop();
		int l = this.menu.getFluidStack().getAmount();
		
		int e = l / 1000;

		// this.blit(matrixStack, i + 6, j + 21, 177, e * 5, 14, 50);
		//this.blit(matrixStack, i + 7, j + 20, 71, (e * 5) + 1, 15, 51);
		this.blit(matrixStack, i + 108, j + 18, 105, (e * 5), 13, 52);
	}
}
