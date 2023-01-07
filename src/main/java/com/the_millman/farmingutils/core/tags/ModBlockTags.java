package com.the_millman.farmingutils.core.tags;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.themillmanlib.core.util.LibTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags extends LibTags.Blocks {
	public static final TagKey<Block> MINEABLE_PICKAXE = modLoc(FarmingUtils.MODID, "mineable/pickaxe");
	public static final TagKey<Block> AGE_3_CROPS = modLoc(FarmingUtils.MODID, "age_3_crops");
	
}
