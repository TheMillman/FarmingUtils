package com.the_millman.farmingutils.core.tags;

import com.the_millman.farmingutils.FarmingUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
	public static final Tag.Named<Block> MINEABLE_PICKAXE = mod("mineable/pickaxe");
	
	private static Tag.Named<Block> mod(String path) {
		return BlockTags.bind(new ResourceLocation(FarmingUtils.MODID, path).toString());
	}
}
