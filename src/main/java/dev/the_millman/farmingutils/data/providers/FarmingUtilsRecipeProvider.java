package dev.the_millman.farmingutils.data.providers;

import java.util.function.Consumer;

import dev.the_millman.farmingutils.core.init.ItemInit;
import dev.the_millman.farmingutils.data.builder.ComposterRecipeBuilder;
import dev.the_millman.farmingutils.data.builder.InternalFarmerBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

public class FarmingUtilsRecipeProvider {

	public static void register(Consumer<FinishedRecipe> consumer) {
		compostRecipes(consumer);
		flowerFarmer(consumer);
	}
	
	public static void flowerFarmer(Consumer<FinishedRecipe> consumer) {
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.AZURE_BLUET),
				new FluidStack(Fluids.WATER, 100), Items.AZURE_BLUET, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.BLUE_ORCHID),
				new FluidStack(Fluids.WATER, 100), Items.BLUE_ORCHID, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.BROWN_MUSHROOM),
				new FluidStack(Fluids.WATER, 100), Items.BROWN_MUSHROOM, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.CORNFLOWER),
				new FluidStack(Fluids.WATER, 100), Items.CORNFLOWER, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.CRIMSON_FUNGUS),
				new FluidStack(Fluids.LAVA, 200), Items.CRIMSON_FUNGUS, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.DANDELION),
				new FluidStack(Fluids.WATER, 100), Items.DANDELION, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.KELP),
				new FluidStack(Fluids.WATER, 500), Items.KELP, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.LILAC),
				new FluidStack(Fluids.WATER, 100), Items.LILAC, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.LILY_OF_THE_VALLEY),
				new FluidStack(Fluids.WATER, 100), Items.LILY_OF_THE_VALLEY, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.ORANGE_TULIP),
				new FluidStack(Fluids.WATER, 100), Items.ORANGE_TULIP, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.OXEYE_DAISY),
				new FluidStack(Fluids.WATER, 100), Items.OXEYE_DAISY, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.PEONY),
				new FluidStack(Fluids.WATER, 100), Items.PEONY, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.PINK_TULIP),
				new FluidStack(Fluids.WATER, 100), Items.PINK_TULIP, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.POPPY),
				new FluidStack(Fluids.WATER, 100), Items.POPPY, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.RED_MUSHROOM),
				new FluidStack(Fluids.WATER, 100), Items.RED_MUSHROOM, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.RED_TULIP),
				new FluidStack(Fluids.WATER, 100), Items.RED_TULIP, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.ROSE_BUSH),
				new FluidStack(Fluids.WATER, 100), Items.ROSE_BUSH, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.SUNFLOWER),
				new FluidStack(Fluids.WATER, 100), Items.SUNFLOWER, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.WARPED_FUNGUS),
				new FluidStack(Fluids.LAVA, 200), Items.WARPED_FUNGUS, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.WHITE_TULIP),
				new FluidStack(Fluids.WATER, 100), Items.WHITE_TULIP, 2).build(consumer);
		InternalFarmerBuilder.internalFarmer(Ingredient.of(ItemInit.COMPOST.get()), Ingredient.of(Items.WITHER_ROSE),
				new FluidStack(Fluids.WATER, 100), Items.WITHER_ROSE, 2).build(consumer);
	}
	
	public static void compostRecipes(Consumer<FinishedRecipe> consumer) {
//		ComposterRecipeBuilder.composterRecipe(Ingredient.of(ModItemTags.COMPOSTER_ITEMS), ItemInit.COMPOST.get() ,1).build(consumer);
		
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(ItemTags.LEAVES), ItemInit.COMPOST.get(), 1).build(consumer, "leaves");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(ItemTags.SAPLINGS), ItemInit.COMPOST.get(), 1).build(consumer, "saplings");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Tags.Items.CROPS), ItemInit.COMPOST.get(), 1).build(consumer, "crops");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Tags.Items.SEEDS), ItemInit.COMPOST.get(), 1).build(consumer, "seeds");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Tags.Items.MUSHROOMS), ItemInit.COMPOST.get(), 1).build(consumer, "mushrooms");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(ItemTags.FLOWERS), ItemInit.COMPOST.get(), 1).build(consumer, "flowers");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.DRIED_KELP), ItemInit.COMPOST.get(), 1).build(consumer, "dried_kelp");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.KELP), ItemInit.COMPOST.get(), 1).build(consumer, "kelp");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.SEAGRASS), ItemInit.COMPOST.get(), 1).build(consumer, "seagrass");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.GRASS), ItemInit.COMPOST.get(), 1).build(consumer, "grass");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.SWEET_BERRIES), ItemInit.COMPOST.get(), 1).build(consumer, "sweet_berries");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.GLOW_BERRIES), ItemInit.COMPOST.get(), 1).build(consumer, "glow_berries");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.MOSS_CARPET), ItemInit.COMPOST.get(), 1).build(consumer, "moss_carpet");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.SMALL_DRIPLEAF), ItemInit.COMPOST.get(), 1).build(consumer, "small_dripleaf");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.HANGING_ROOTS), ItemInit.COMPOST.get(), 1).build(consumer, "hanging_roots");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.MANGROVE_ROOTS), ItemInit.COMPOST.get(), 1).build(consumer, "mangrove_roots");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.DRIED_KELP_BLOCK), ItemInit.COMPOST.get(), 1).build(consumer, "dried_kelp_block");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.TALL_GRASS), ItemInit.COMPOST.get(), 1).build(consumer, "tall_grass");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.CACTUS), ItemInit.COMPOST.get(), 1).build(consumer, "cactus");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.SUGAR_CANE), ItemInit.COMPOST.get(), 1).build(consumer, "sugar_cane");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.VINE), ItemInit.COMPOST.get(), 1).build(consumer, "vine");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.NETHER_SPROUTS), ItemInit.COMPOST.get(), 1).build(consumer, "nether_sprouts");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.WEEPING_VINES), ItemInit.COMPOST.get(), 1).build(consumer, "weeping_vines");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.TWISTING_VINES), ItemInit.COMPOST.get(), 1).build(consumer, "twisting_vines");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.MELON_SLICE), ItemInit.COMPOST.get(), 1).build(consumer, "melon_slice");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.GLOW_LICHEN), ItemInit.COMPOST.get(), 1).build(consumer, "glow_lichen");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.SEA_PICKLE), ItemInit.COMPOST.get(), 1).build(consumer, "sea_pickle");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.LILY_PAD), ItemInit.COMPOST.get(), 1).build(consumer, "lily_pad");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.CARVED_PUMPKIN), ItemInit.COMPOST.get(), 1).build(consumer, "carved_pumpkin");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.CRIMSON_ROOTS), ItemInit.COMPOST.get(), 1).build(consumer, "crimson_roots");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.WARPED_ROOTS), ItemInit.COMPOST.get(), 1).build(consumer, "warped_roots");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.SHROOMLIGHT), ItemInit.COMPOST.get(), 1).build(consumer, "shroomlight");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.MOSS_BLOCK), ItemInit.COMPOST.get(), 1).build(consumer, "moss_block");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.BIG_DRIPLEAF), ItemInit.COMPOST.get(), 1).build(consumer, "big_dripleaf");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.HAY_BLOCK), ItemInit.COMPOST.get(), 1).build(consumer, "hay_block");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.BROWN_MUSHROOM_BLOCK), ItemInit.COMPOST.get(), 1).build(consumer, "brown_mushroom_block");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.RED_MUSHROOM_BLOCK), ItemInit.COMPOST.get(), 1).build(consumer, "red_mushroom_block");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.NETHER_WART_BLOCK), ItemInit.COMPOST.get(), 1).build(consumer, "nether_wart_block");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.WARPED_WART_BLOCK), ItemInit.COMPOST.get(), 1).build(consumer, "warped_wart_block");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.BREAD), ItemInit.COMPOST.get(), 1).build(consumer, "bread");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.BAKED_POTATO), ItemInit.COMPOST.get(), 1).build(consumer, "baked_potato");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.COOKIE), ItemInit.COMPOST.get(), 1).build(consumer, "cookie");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.CAKE), ItemInit.COMPOST.get(), 1).build(consumer, "cake");
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(Items.PUMPKIN_PIE), ItemInit.COMPOST.get(), 1).build(consumer, "pumpkin_pie");
		
	}
}
