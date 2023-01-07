package com.the_millman.farmingutils.core.tags;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.themillmanlib.core.util.LibTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags extends LibTags.Items {
	public static final TagKey<Item> CROP_SEEDS= modLoc(FarmingUtils.MODID, "crop_seeds");
	public static final TagKey<Item> CROP_BLOCK_SEEDS= modLoc(FarmingUtils.MODID, "crop_block_seeds");
	public static final TagKey<Item> CROP_RESULTS= modLoc(FarmingUtils.MODID, "crop_results");
}
