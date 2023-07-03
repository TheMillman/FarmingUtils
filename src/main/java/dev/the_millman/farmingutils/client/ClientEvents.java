package dev.the_millman.farmingutils.client;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.client.screens.BambooFarmerScreen;
import dev.the_millman.farmingutils.client.screens.CactusFarmerScreen;
import dev.the_millman.farmingutils.client.screens.CocoaFarmerScreen;
import dev.the_millman.farmingutils.client.screens.ComposterScreen;
import dev.the_millman.farmingutils.client.screens.CropFarmerScreen;
import dev.the_millman.farmingutils.client.screens.GeneratorScreen;
import dev.the_millman.farmingutils.client.screens.InternalFarmerScreen;
import dev.the_millman.farmingutils.client.screens.MelonFarmerScreen;
import dev.the_millman.farmingutils.client.screens.NetherWartFarmerScreen;
import dev.the_millman.farmingutils.client.screens.SugarCanesFarmerScreen;
import dev.the_millman.farmingutils.common.blockentity.renderer.InternalFarmerBERenderer;
import dev.the_millman.farmingutils.core.init.BlockEntityInit;
import dev.the_millman.farmingutils.core.init.ContainerInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
			MenuScreens.register(ContainerInit.COMPOSTER_CONTAINER.get(), ComposterScreen::new);
			
		}
	}
}
