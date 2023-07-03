package dev.the_millman.farmingutils.client.screens;

import dev.the_millman.farmingutils.common.containers.CocoaFarmerContainer;
import dev.the_millman.themillmanlib.client.screens.ItemEnergyScreen;
import dev.the_millman.themillmanlib.core.util.LibResources;
import net.minecraft.client.gui.GuiGraphics;
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
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		this.renderEnergyLevel(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        renderEnergyBar(guiGraphics, OVERLAY);
	}

}
