package dev.the_millman.farmingutils.data;

import java.util.concurrent.CompletableFuture;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.data.providers.ModBlockStateModelProvider;
import dev.the_millman.farmingutils.data.providers.ModBlockTagsProvider;
import dev.the_millman.farmingutils.data.providers.ModItemModelProvider;
import dev.the_millman.farmingutils.data.providers.ModItemTagsProvider;
import dev.the_millman.farmingutils.data.providers.ModRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
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
        
//		gen.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
//                List.of(new LootTableProvider.SubProviderEntry(ModLootTableProvider::new, LootContextParamSets.BLOCK))));
		
		gen.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
		ModBlockTagsProvider blockTags = new ModBlockTagsProvider(packOutput, lookupProvider, event.getExistingFileHelper());
		gen.addProvider(event.includeServer(), blockTags);
		gen.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), event.getExistingFileHelper()));
		
		gen.addProvider(event.includeClient(), new ModBlockStateModelProvider(packOutput, event.getExistingFileHelper()));
		gen.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, event.getExistingFileHelper()));
	}
}
