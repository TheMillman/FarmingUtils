package dev.the_millman.farmingutils.core.init;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.common.recipes.ComposterRecipe;
import dev.the_millman.farmingutils.common.recipes.InternalFarmerRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypesInit {
	public static final DeferredRegister<RecipeType<?>> RECIPES_TYPE = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, FarmingUtils.MODID);
	
	public static final RegistryObject<RecipeType<InternalFarmerRecipe>> INTERNAL_FARMER_TYPE = RECIPES_TYPE.register("internal_farmer", () -> RecipeType.simple(new ResourceLocation(FarmingUtils.MODID, "internal_farmer")));
	public static final RegistryObject<RecipeType<ComposterRecipe>> COMPOSTER_TYPE = RECIPES_TYPE.register("composter", () -> RecipeType.simple(new ResourceLocation(FarmingUtils.MODID, "composter")));
	
}
