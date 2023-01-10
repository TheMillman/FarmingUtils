package com.the_millman.farmingutils.core.init;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.core.recipes.InternalFarmerRecipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeInit {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FarmingUtils.MODID);
	
	public static final RegistryObject<RecipeSerializer<InternalFarmerRecipe>> INTERNAL_FARMER_SERIALIZER = SERIALIZERS.register("internal_farmer", () -> InternalFarmerRecipe.Serializer.INSTANCE);
}
