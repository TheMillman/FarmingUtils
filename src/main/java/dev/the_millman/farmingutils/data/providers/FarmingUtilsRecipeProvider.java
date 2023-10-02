package dev.the_millman.farmingutils.data.providers;

import java.util.function.Consumer;

import dev.the_millman.farmingutils.core.init.ItemInit;
import dev.the_millman.farmingutils.core.tags.ModItemTags;
import dev.the_millman.farmingutils.data.builder.ComposterRecipeBuilder;
import dev.the_millman.farmingutils.data.builder.InternalFarmerBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
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
		ComposterRecipeBuilder.composterRecipe(Ingredient.of(ModItemTags.COMPOSTER_ITEMS), ItemInit.COMPOST.get() ,1).build(consumer);
	}
}
