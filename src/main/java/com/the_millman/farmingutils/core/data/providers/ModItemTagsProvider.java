package com.the_millman.farmingutils.core.data.providers;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.core.init.ItemInit;
import com.the_millman.farmingutils.core.tags.ModItemTags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {

	public ModItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(pGenerator, pBlockTagsProvider, FarmingUtils.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
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
		
		tag(ModItemTags.RANGE_UPGRADES)
			.add(ItemInit.IRON_UPGRADE.get())
			.add(ItemInit.GOLD_UPGRADE.get())
			.add(ItemInit.DIAMOND_UPGRADE.get());
	}
	
	@Override
	public String getName() {
		return "FarmingUtils Item Tags";
	}
}
