package com.the_millman.farmingutils.core.data.providers;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.core.init.BlockInit;
import com.the_millman.farmingutils.core.tags.ModBlockTags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {

	public ModBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, FarmingUtils.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(ModBlockTags.MINEABLE_PICKAXE)
			.add(BlockInit.CROP_FARMER.get())
			.add(BlockInit.MELON_FARMER.get())
			.add(BlockInit.NETHER_WART_FARMER.get())
			.add(BlockInit.COCOA_BEANS_FARMER.get())
			.add(BlockInit.CACTUS_FARMER.get())
			.add(BlockInit.SUGAR_CANES_FARMER.get())
			.add(BlockInit.BAMBOO_FARMER.get());
		
		tag(ModBlockTags.AGE_3_CROPS)
			.add(Blocks.BEETROOTS);
		
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.addTag(ModBlockTags.MINEABLE_PICKAXE);
		
		tag(BlockTags.NEEDS_STONE_TOOL)
			.addTag(ModBlockTags.MINEABLE_PICKAXE);
	}
	
	@Override
	public String getName() {
		return "FarmingUtils Block Tags";
	}
}
