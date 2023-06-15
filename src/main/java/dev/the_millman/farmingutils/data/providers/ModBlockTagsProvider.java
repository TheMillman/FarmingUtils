package dev.the_millman.farmingutils.data.providers;

import java.util.concurrent.CompletableFuture;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.core.init.BlockInit;
import dev.the_millman.farmingutils.core.tags.ModBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {

	public ModBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
		super(packOutput, lookupProvider, FarmingUtils.MODID, helper);
	}

	@Override
	protected void addTags(Provider pProvider) {
		tag(ModBlockTags.MINEABLE_PICKAXE)
			.add(BlockInit.CROP_FARMER.get())
			.add(BlockInit.MELON_FARMER.get())
			.add(BlockInit.NETHER_WART_FARMER.get())
			.add(BlockInit.COCOA_BEANS_FARMER.get())
			.add(BlockInit.CACTUS_FARMER.get())
			.add(BlockInit.SUGAR_CANES_FARMER.get())
			.add(BlockInit.BAMBOO_FARMER.get())
			.add(BlockInit.MUSHROOM_FARMER.get());
		
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
