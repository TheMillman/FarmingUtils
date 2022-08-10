package com.the_millman.farmingutils.core.tags;

import com.the_millman.farmingutils.FarmingUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
	public static final TagKey<Item> CROP_SEEDS= mod("crop_seeds");
	public static final TagKey<Item> CROP_BLOCK_SEEDS= mod("crop_block_seeds");
	public static final TagKey<Item> CROP_RESULTS= mod("crop_results");
	public static final TagKey<Item> RANGE_UPGRADES= mod("range_upgrades");
	
	
	private static TagKey<Item> mod(String path) {
		return ItemTags.create(new ResourceLocation(FarmingUtils.MODID, path));
	}
}
