package com.the_millman.farmingutils.core.data;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.core.data.providers.ModBlockStateModelProvider;
import com.the_millman.farmingutils.core.data.providers.ModBlockTagsProvider;
import com.the_millman.farmingutils.core.data.providers.ModItemModelProvider;
import com.the_millman.farmingutils.core.data.providers.ModItemTagsProvider;
import com.the_millman.farmingutils.core.data.providers.ModLootTableProvider;
import com.the_millman.farmingutils.core.data.providers.ModRecipeProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
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
		PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        
		gen.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModLootTableProvider::new, LootContextParamSets.BLOCK))));
		
		gen.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
		ModBlockTagsProvider blockTags = new ModBlockTagsProvider(packOutput, lookupProvider, event.getExistingFileHelper());
		gen.addProvider(event.includeServer(), blockTags);
		gen.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, blockTags, event.getExistingFileHelper()));
		
		gen.addProvider(event.includeClient(), new ModBlockStateModelProvider(packOutput, event.getExistingFileHelper()));
		gen.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, event.getExistingFileHelper()));
	}
}
