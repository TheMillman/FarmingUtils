package com.the_millman.farmingutils.core.tags;

import com.the_millman.farmingutils.FarmingUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
	public static final TagKey<Block> MINEABLE_PICKAXE = mod("mineable/pickaxe");
	public static final TagKey<Block> AGE_3_CROPS = mod("age_3_crops");
	
	private static TagKey<Block> mod(String path) {
		return BlockTags.create(new ResourceLocation(FarmingUtils.MODID, path));
	}
}
