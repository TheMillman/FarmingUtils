package com.the_millman.farmingutils.core.data.providers;

import java.util.concurrent.CompletableFuture;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.core.tags.ModItemTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {

	public ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTags, ExistingFileHelper helper) {
		super(packOutput, lookupProvider, blockTags, FarmingUtils.MODID, helper);
	}

	@Override
	protected void addTags(Provider pProvider) {
		tag(ModItemTags.CROP_BLOCK_SEEDS)
			.add(Items.MELON_SEEDS)
			.add(Items.PUMPKIN_SEEDS);
		
		tag(ModItemTags.CROP_RESULTS)
			.add(Items.WHEAT)
			.add(Items.POTATO)
			.add(Items.CARROT)
			.add(Items.BEETROOT);
		
		tag(ModItemTags.CROP_SEEDS)
			.add(Items.WHEAT_SEEDS)
			.add(Items.POTATO)
			.add(Items.CARROT)
			.add(Items.BEETROOT_SEEDS);
	}
	
	@Override
	public String getName() {
		return "FarmingUtils Item Tags";
	}
}
