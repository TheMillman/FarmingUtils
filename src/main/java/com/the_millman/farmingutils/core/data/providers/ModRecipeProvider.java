package com.the_millman.farmingutils.core.data.providers;

import java.util.function.Consumer;

import com.the_millman.farmingutils.core.init.BlockInit;
import com.the_millman.farmingutils.core.init.ItemInit;
import com.the_millman.farmingutils.core.tags.ModItemTags;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(DataGenerator pGenerator) {
		super(pGenerator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
		ShapedRecipeBuilder.shaped(BlockInit.CROP_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', ModItemTags.CROP_SEEDS)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.STONE)
        .unlockedBy("hoe", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_HOE))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(BlockInit.MELON_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', ModItemTags.CROP_BLOCK_SEEDS)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.STONE)
        .unlockedBy("seeds", InventoryChangeTrigger.TriggerInstance.hasItems(Items.MELON_SEEDS))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(BlockInit.NETHER_WART_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.NETHER_WART)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.RED_NETHER_BRICKS)
        .unlockedBy("nether_wart", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHER_WART))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(BlockInit.COCOA_BEANS_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.COCOA_BEANS)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.STONE)
        .unlockedBy("cocoa_beans", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COCOA_BEANS))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(BlockInit.CACTUS_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.CACTUS)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.SANDSTONE)
        .unlockedBy("cactus", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CACTUS))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(BlockInit.SUGAR_CANES_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.SUGAR_CANE)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.SANDSTONE)
        .unlockedBy("sugar_cane", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SUGAR_CANE))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(BlockInit.BAMBOO_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.BAMBOO)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.STONE)
        .unlockedBy("bamboo", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BAMBOO))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(BlockInit.MUSHROOM_FARMER.get())
        .pattern("iri")
        .pattern("ipg")
        .pattern("sds")
        .define('p', ItemTags.SMALL_FLOWERS)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.STONE)
        .define('g', Items.TINTED_GLASS)
        .define('d', Items.DIRT)
        .unlockedBy("small_flower", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DANDELION, Items.POPPY))
        .save(pFinishedRecipeConsumer);
		
		
		ShapedRecipeBuilder.shaped(ItemInit.IRON_UPGRADE.get())
        .pattern("iii")
        .pattern("iri")
        .pattern("iii")
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .group("farmingutils_upgrade")
        .unlockedBy("farmers", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.CROP_FARMER.get()))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(ItemInit.GOLD_UPGRADE.get())
        .pattern("iii")
        .pattern("iri")
        .pattern("iii")
        .define('i', Tags.Items.INGOTS_GOLD)
        .define('r', ItemInit.IRON_UPGRADE.get())
        .group("farmingutils_upgrade")
        .unlockedBy("upgrade", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.IRON_UPGRADE.get()))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(ItemInit.DIAMOND_UPGRADE.get())
        .pattern("iii")
        .pattern("iri")
        .pattern("iii")
        .define('i', Tags.Items.GEMS_DIAMOND)
        .define('r', ItemInit.GOLD_UPGRADE.get())
        .group("farmingutils_upgrade")
        .unlockedBy("upgrade", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.GOLD_UPGRADE.get()))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(ItemInit.REDSTONE_UPGRADE.get())
        .pattern("iii")
        .pattern("iri")
        .pattern("iii")
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE_BLOCK)
        .group("farmingutils_upgrade")
        .unlockedBy("farmers", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.CROP_FARMER.get()))
        .save(pFinishedRecipeConsumer);
		
		ShapedRecipeBuilder.shaped(ItemInit.DROP_UPGRADE.get())
        .pattern("iii")
        .pattern("iri")
        .pattern("iii")
        .define('i', Tags.Items.INGOTS_COPPER)
        .define('r', ModItemTags.CROP_RESULTS)
        .group("farmingutils_upgrade")
        .unlockedBy("farmers", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.CROP_FARMER.get()))
        .save(pFinishedRecipeConsumer);
	}
}
