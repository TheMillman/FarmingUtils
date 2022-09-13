package com.the_millman.farmingutils.core.data;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.core.data.providers.ModBlockStateModelProvider;
import com.the_millman.farmingutils.core.data.providers.ModBlockTagsProvider;
import com.the_millman.farmingutils.core.data.providers.ModItemModelProvider;
import com.the_millman.farmingutils.core.data.providers.ModItemTagsProvider;
import com.the_millman.farmingutils.core.data.providers.ModLootTableProvider;
import com.the_millman.farmingutils.core.data.providers.ModRecipeProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = FarmingUtils.MODID, bus = Bus.MOD)
public class DataGenerators {
	private DataGenerators() {}
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		ExistingFileHelper exFileHelper = event.getExistingFileHelper();
		
		gen.addProvider(event.includeServer(), new ModLootTableProvider(gen));
		gen.addProvider(event.includeServer(), new ModRecipeProvider(gen));
		ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, exFileHelper);
		gen.addProvider(event.includeServer(), blockTags);
		gen.addProvider(event.includeServer(), new ModItemTagsProvider(gen, blockTags, exFileHelper));
		
		gen.addProvider(event.includeClient(), new ModBlockStateModelProvider(gen, exFileHelper));
		gen.addProvider(event.includeClient(), new ModItemModelProvider(gen, exFileHelper));
	}
}
