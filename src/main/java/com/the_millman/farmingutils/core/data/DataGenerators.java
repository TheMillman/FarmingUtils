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
	
	/**
	 * TODO
	 * Controllare i false
	 * @param event
	 */
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		ExistingFileHelper exFileHelper = event.getExistingFileHelper();
		
		if(event.includeServer()) {
			gen.addProvider(false, new ModLootTableProvider(gen));
			gen.addProvider(false, new ModRecipeProvider(gen));
			ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, exFileHelper);
			gen.addProvider(false, blockTags);
			gen.addProvider(false, new ModItemTagsProvider(gen, blockTags, exFileHelper));
		}
		
		if(event.includeClient()) {
			gen.addProvider(false, new ModBlockStateModelProvider(gen, exFileHelper));
			gen.addProvider(false, new ModItemModelProvider(gen, exFileHelper));
		}
		
		//ModBlockTagsProvider tagProvider = new ModBlockTagsProvider(gen, exFileHelper);
		//gen.addProvider(tagProvider);
		//gen.addProvider(new ModItemTagsProvider(gen, tagProvider, exFileHelper));
	}
}
