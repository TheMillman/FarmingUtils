package com.the_millman.farmingutils.core.tags;

import com.the_millman.farmingutils.FarmingUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ModItemTags {
	public static final Tag.Named<Item> CROP_SEEDS= mod("crop_seeds");
	public static final Tag.Named<Item> CROP_BLOCK_SEEDS= mod("crop_block_seeds");
	public static final Tag.Named<Item> CROP_RESULTS= mod("crop_results");
	public static final Tag.Named<Item> RANGE_UPGRADES= mod("range_upgrades");
	
	
	private static Tag.Named<Item> mod(String path) {
		return ItemTags.bind(new ResourceLocation(FarmingUtils.MODID, path).toString());
	}
}
