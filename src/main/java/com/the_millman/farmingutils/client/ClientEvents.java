package com.the_millman.farmingutils.client;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.common.blockentity.renderer.InternalFarmerBERenderer;
import com.the_millman.farmingutils.core.init.BlockEntityInit;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class ClientEvents {
	
	@Mod.EventBusSubscriber(modid = FarmingUtils.MODID, bus = Bus.MOD, value = Dist.CLIENT)
	public static class ClientModBusEvents {
		
		@SubscribeEvent
		public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
			event.registerBlockEntityRenderer(BlockEntityInit.INTERNAL_FARMER.get(), InternalFarmerBERenderer::new);
		}
	}
}
