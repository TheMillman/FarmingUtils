package dev.the_millman.farmingutils.common.recipes;

import java.util.List;
import java.util.Objects;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.core.init.RecipeTypesInit;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

@JeiPlugin
public class JEIFarmingUtilsPlugin implements IModPlugin {
	
	public static RecipeType<InternalFarmerRecipe> INTERNAL_FARMER_TYPE = new RecipeType<>(InternalFarmerRecipeCategory.UID, InternalFarmerRecipe.class);
	public static RecipeType<ComposterRecipe> COMPOSTER_TYPE = new RecipeType<>(ComposterRecipeCategory.UID, ComposterRecipe.class);
	
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(FarmingUtils.MODID, "jei_plugin");
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new InternalFarmerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new ComposterRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}
	
	@SuppressWarnings("resource")
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager rm= Objects.requireNonNull(Minecraft.getInstance().level.getRecipeManager());
		List<InternalFarmerRecipe> internalFarmerRecipe = rm.getAllRecipesFor(RecipeTypesInit.INTERNAL_FARMER_TYPE.get());
		List<ComposterRecipe> composterRecipe = rm.getAllRecipesFor(RecipeTypesInit.COMPOSTER_TYPE.get());
		registration.addRecipes(INTERNAL_FARMER_TYPE, internalFarmerRecipe);
		registration.addRecipes(COMPOSTER_TYPE, composterRecipe);
	}

}
