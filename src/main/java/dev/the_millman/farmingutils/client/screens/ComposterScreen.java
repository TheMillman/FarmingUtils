package dev.the_millman.farmingutils.client.screens;

import dev.the_millman.farmingutils.common.containers.ComposterContainer;
import dev.the_millman.farmingutils.core.util.FarmingResources;
import dev.the_millman.themillmanlib.client.screens.ItemEnergyScreen;
import dev.the_millman.themillmanlib.core.util.LibResources;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ComposterScreen extends ItemEnergyScreen<ComposterContainer> {

	private ResourceLocation GUI = FarmingResources.COMPOSTER_GUI;
	private ResourceLocation OVERLAY = LibResources.OFFSETS;
	
	public ComposterScreen(ComposterContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
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
        this.renderEnergyBar(guiGraphics, OVERLAY);
        renderProgress(guiGraphics);
	}
	
	private void renderProgress(GuiGraphics guiGraphics) {
		int gL = this.getGuiLeft();
		int gT = this.getGuiTop();
		int progress = (int)(23*menu.getProgress()/(float)menu.getMaxProgress());
		guiGraphics.blit(GUI, gL + 85, gT + 36, 176, 0, progress+1, 16);
	}
}
