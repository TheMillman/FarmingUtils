package com.the_millman.farmingutils.client;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.client.screens.BambooFarmerScreen;
import com.the_millman.farmingutils.client.screens.CactusFarmerScreen;
import com.the_millman.farmingutils.client.screens.CocoaFarmerScreen;
import com.the_millman.farmingutils.client.screens.CropFarmerScreen;
import com.the_millman.farmingutils.client.screens.GeneratorScreen;
import com.the_millman.farmingutils.client.screens.InternalFarmerScreen;
import com.the_millman.farmingutils.client.screens.MelonFarmerScreen;
import com.the_millman.farmingutils.client.screens.NetherWartFarmerScreen;
import com.the_millman.farmingutils.client.screens.SugarCanesFarmerScreen;
import com.the_millman.farmingutils.common.blockentity.renderer.InternalFarmerBERenderer;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.init.ContainerInit;
import com.the_millman.farmingutils.core.init.ItemInit;
import com.the_millman.themillmanlib.core.init.LibItemInit;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
	
	@Mod.EventBusSubscriber(modid = FarmingUtils.MODID, bus = Bus.MOD, value = Dist.CLIENT)
	public static class ClientModBusEvents {
		
		@SubscribeEvent
		public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
			event.registerBlockEntityRenderer(BlockEntityInit.INTERNAL_FARMER.get(), InternalFarmerBERenderer::new);
		}
		
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			MenuScreens.register(ContainerInit.TEST_GENERATOR_CONTAINER.get(), GeneratorScreen::new);
			MenuScreens.register(ContainerInit.CROP_FARMER_CONTAINER.get(), CropFarmerScreen::new);
			MenuScreens.register(ContainerInit.MELON_FARMER_CONTAINER.get(), MelonFarmerScreen::new);
			MenuScreens.register(ContainerInit.NETHER_WART_FARMER_CONTAINER.get(), NetherWartFarmerScreen::new);
			MenuScreens.register(ContainerInit.COCOA_FARMER_CONTAINER.get(), CocoaFarmerScreen::new);
			MenuScreens.register(ContainerInit.CACTUS_FARMER_CONTAINER.get(), CactusFarmerScreen::new);
			MenuScreens.register(ContainerInit.SUGAR_CANES_FARMER_CONTAINER.get(), SugarCanesFarmerScreen::new);
			MenuScreens.register(ContainerInit.BAMBOO_FARMER_CONTAINER.get(), BambooFarmerScreen::new);
			MenuScreens.register(ContainerInit.INTERNAL_FARMER_CONTAINER.get(), InternalFarmerScreen::new);
		}
	}
	
	@Mod.EventBusSubscriber(modid = FarmingUtils.MODID, bus = Bus.MOD)
	public static class CommonDistEvent {
		public static final String TAB_NAME = "farming_utils_tab";
		
		@SubscribeEvent
		public static void customTab(CreativeModeTabEvent.Register event) {
			event.registerCreativeModeTab(new ResourceLocation(FarmingUtils.MODID, "farming_utils_tab"), builder -> {
	            builder.title(Component.translatable("itemGroup." + TAB_NAME))
	                    .icon(() -> new ItemStack(ItemInit.CROP_FARMER.get()))
	                    .displayItems((enabledFeatures, output, tab) -> {
	                    	// Blocks
	                    	output.accept(ItemInit.CROP_FARMER.get());
	                    	output.accept(ItemInit.MELON_FARMER.get());
	                    	output.accept(ItemInit.NETHER_WART_FARMER.get());
	                    	output.accept(ItemInit.COCOA_BEANS_FARMER.get());
	                    	output.accept(ItemInit.CACTUS_FARMER.get());
	                    	output.accept(ItemInit.SUGAR_CANES_FARMER.get());
	                    	output.accept(ItemInit.BAMBOO_FARMER.get());
	                    	output.accept(ItemInit.MUSHROOM_FARMER.get());
	                    	
	                    	//Items
	                    	output.accept(LibItemInit.IRON_UPGRADE.get());
	                    	output.accept(LibItemInit.GOLD_UPGRADE.get());
	                    	output.accept(LibItemInit.DIAMOND_UPGRADE.get());
	                    	output.accept(LibItemInit.REDSTONE_UPGRADE.get());
	                    	output.accept(LibItemInit.DROP_UPGRADE.get());
	                    });
			});
		}
	}
}
