package com.the_millman.farmingutils.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.common.containers.TestGeneratorContainer;
import com.the_millman.themillmanlib.client.screens.ItemEnergyScreen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GeneratorScreen extends ItemEnergyScreen<TestGeneratorContainer> {

    private ResourceLocation GUI = new ResourceLocation(FarmingUtils.MODID, "textures/gui/generator_gui.png");
    private ResourceLocation OVERLAY = new ResourceLocation(FarmingUtils.MODID, "textures/gui/offset_buttons.png");
	
    public GeneratorScreen(TestGeneratorContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
        this.renderEnergyLevel(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        this.renderEnergyBar(matrixStack, OVERLAY);
    }
    
    @Override
    protected void renderEnergyBar(PoseStack matrixStack, ResourceLocation overlay) {
    	RenderSystem.setShaderTexture(0, overlay);
		int i = this.getGuiLeft();
		int j = this.getGuiTop();
		int l = this.menu.getEnergy();
		int e = l / 10000;
		
		if(e >= 10) {
			e = 10;
		}

		this.blit(matrixStack, i + 6, j + 21, 177, e * 5, 14, 50);
    }
}