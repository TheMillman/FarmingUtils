package com.the_millman.farmingutils.core.data.providers;

import java.util.function.Consumer;

import com.the_millman.farmingutils.core.init.BlockInit;
import com.the_millman.farmingutils.core.tags.ModItemTags;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(PackOutput output) {
		super(output);
	}
	
	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.CROP_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', ModItemTags.CROP_SEEDS)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.STONE)
        .unlockedBy("hoe", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_HOE))
        .save(pWriter);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.MELON_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', ModItemTags.CROP_BLOCK_SEEDS)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.STONE)
        .unlockedBy("seeds", InventoryChangeTrigger.TriggerInstance.hasItems(Items.MELON_SEEDS))
        .save(pWriter);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.NETHER_WART_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.NETHER_WART)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.RED_NETHER_BRICKS)
        .unlockedBy("nether_wart", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHER_WART))
        .save(pWriter);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.COCOA_BEANS_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.COCOA_BEANS)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.STONE)
        .unlockedBy("cocoa_beans", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COCOA_BEANS))
        .save(pWriter);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.CACTUS_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.CACTUS)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.SANDSTONE)
        .unlockedBy("cactus", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CACTUS))
        .save(pWriter);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.SUGAR_CANES_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.SUGAR_CANE)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.SANDSTONE)
        .unlockedBy("sugar_cane", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SUGAR_CANE))
        .save(pWriter);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.BAMBOO_FARMER.get())
        .pattern("iii")
        .pattern("ipi")
        .pattern("srs")
        .define('p', Items.BAMBOO)
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .define('s', Items.STONE)
        .unlockedBy("bamboo", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BAMBOO))
        .save(pWriter);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.MUSHROOM_FARMER.get())
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
        .save(pWriter);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.COMPOSTER.get())
        .pattern("i i")
        .pattern("iri")
        .pattern("iii")
        .define('i', Tags.Items.INGOTS_IRON)
        .define('r', Items.REDSTONE)
        .unlockedBy("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
        .save(pWriter);
	}
}
