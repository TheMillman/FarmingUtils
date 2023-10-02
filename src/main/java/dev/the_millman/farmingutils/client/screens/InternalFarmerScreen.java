package dev.the_millman.farmingutils.client.screens;

import dev.the_millman.farmingutils.common.containers.InternalFarmerContainer;
import dev.the_millman.farmingutils.core.util.FarmingConfig;
import dev.the_millman.farmingutils.core.util.FarmingResources;
import dev.the_millman.themillmanlib.client.screens.ItemEnergyFluidScreen;
import dev.the_millman.themillmanlib.core.util.LibResources;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class InternalFarmerScreen extends ItemEnergyFluidScreen<InternalFarmerContainer> {
	
	private ResourceLocation GUI = FarmingResources.INTERNAL_FARMER_GUI;
	private ResourceLocation OVERLAY = LibResources.OFFSETS;
	
	public InternalFarmerScreen(InternalFarmerContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}
	
	@Override
	protected void init() {
		super.init();
		initFluidRenderer(FarmingConfig.INTERNAL_FARMER_FLUID_CAPACITY.get(), true, 13, 52);
	}
	
	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
		int xPos = (width - imageWidth) / 2;
        int yPos = (height - imageHeight) / 2;
        super.renderLabels(guiGraphics, pMouseX, pMouseY);
        renderFluidTooltip(guiGraphics, pMouseX, pMouseY, 108, 120, 18, 69, menu.getFluidStack(), xPos, yPos, "tooltip.farmingutils.fluid_amount_capacity", "tooltip.farmingutils.fluid_amount");
	}

	@Override
	public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
		this.renderTooltip(guiGraphics, pMouseX, pMouseY);
		this.renderEnergyLevel(guiGraphics, pMouseX, pMouseY);
	}
	
	@Override
	protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        renderer.render(guiGraphics, relX + 108, relY + 18, menu.getFluidStack());
        this.renderEnergyBar(guiGraphics, OVERLAY);
        renderProgress(guiGraphics);
	}
	
	protected void renderProgress(GuiGraphics guiGraphics) {
		int gL = this.getGuiLeft();
		int gT = this.getGuiTop();
		int progress = (int)(32*menu.getCraftingProgress()/(float)menu.getMaxCraftingProgress());
		guiGraphics.blit(GUI, gL + 44, gT + 24, 176, 0, progress, 40);
	}
}
