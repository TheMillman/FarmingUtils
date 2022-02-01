package com.the_millman.farmingutils.client;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.client.screens.CactusFarmerScreen;
import com.the_millman.farmingutils.client.screens.CocoaFarmerScreen;
import com.the_millman.farmingutils.client.screens.CropFarmerScreen;
import com.the_millman.farmingutils.client.screens.GeneratorScreen;
import com.the_millman.farmingutils.client.screens.MelonFarmerScreen;
import com.the_millman.farmingutils.client.screens.NetherWartFarmerScreen;
import com.the_millman.farmingutils.client.screens.SugarCanesFarmerScreen;
import com.the_millman.farmingutils.core.init.ContainerInit;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FarmingUtils.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
	
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		MenuScreens.register(ContainerInit.TEST_GENERATOR_CONTAINER.get(), GeneratorScreen::new);
		MenuScreens.register(ContainerInit.CROP_FARMER_CONTAINER.get(), CropFarmerScreen::new);
		MenuScreens.register(ContainerInit.MELON_FARMER_CONTAINER.get(), MelonFarmerScreen::new);
		MenuScreens.register(ContainerInit.NETHER_WART_FARMER_CONTAINER.get(), NetherWartFarmerScreen::new);
		MenuScreens.register(ContainerInit.COCOA_FARMER_CONTAINER.get(), CocoaFarmerScreen::new);
		MenuScreens.register(ContainerInit.CACTUS_FARMER_CONTAINER.get(), CactusFarmerScreen::new);
		MenuScreens.register(ContainerInit.SUGAR_CANES_FARMER_CONTAINER.get(), SugarCanesFarmerScreen::new);
	}
}
