package com.the_millman.farmingutils.data.providers;

import java.util.concurrent.CompletableFuture;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.core.tags.ModItemTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
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
		
		tag(ModItemTags.COMPOSTER_ITEMS)
			.addTag(ItemTags.LEAVES).addTag(ItemTags.SAPLINGS).addTag(Tags.Items.CROPS).addTag(Tags.Items.SEEDS)
			.addTag(Tags.Items.MUSHROOMS).addTag(ItemTags.FLOWERS).add(Items.DRIED_KELP).add(Items.KELP)
			.add(Items.SEAGRASS).add(Items.GRASS).add(Items.SWEET_BERRIES).add(Items.GLOW_BERRIES)
			.add(Items.MOSS_CARPET).add(Items.SMALL_DRIPLEAF).add(Items.HANGING_ROOTS).add(Items.MANGROVE_ROOTS)
			.add(Items.DRIED_KELP_BLOCK).add(Items.TALL_GRASS).add(Items.CACTUS).add(Items.SUGAR_CANE)
			.add(Items.VINE).add(Items.NETHER_SPROUTS).add(Items.WEEPING_VINES).add(Items.TWISTING_VINES)
			.add(Items.MELON_SLICE).add(Items.GLOW_LICHEN).add(Items.SEA_PICKLE).add(Items.LILY_PAD)
			.add(Items.CARVED_PUMPKIN).add(Items.CRIMSON_ROOTS).add(Items.WARPED_ROOTS).add(Items.SHROOMLIGHT)
			.add(Items.MOSS_BLOCK).add(Items.BIG_DRIPLEAF).add(Items.HAY_BLOCK).add(Items.BROWN_MUSHROOM_BLOCK)
			.add(Items.RED_MUSHROOM_BLOCK).add(Items.NETHER_WART_BLOCK).add(Items.WARPED_WART_BLOCK)
			.add(Items.BREAD).add(Items.BAKED_POTATO).add(Items.COOKIE).add(Items.CAKE).add(Items.PUMPKIN_PIE);
	}
	
	@Override
	public String getName() {
		return "FarmingUtils Item Tags";
	}
}
