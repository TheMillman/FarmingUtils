package com.the_millman.farmingutils.common.recipes;

import java.util.List;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.core.init.BlockInit;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.farmingutils.core.util.FarmingResources;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class InternalFarmerRecipeCategory implements IRecipeCategory<InternalFarmerRecipe>{

	public static final ResourceLocation UID = new ResourceLocation(FarmingUtils.MODID, "internal_farmer");
	public static final ResourceLocation TEXTURE = FarmingResources.INTERNAL_FARMER_GUI;
	
	private final IDrawable background;
	private final IDrawable icon;
	
	public InternalFarmerRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(TEXTURE, 3, 6, 170, 77);
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockInit.MUSHROOM_FARMER.get()));
	}

	@Override
	public RecipeType<InternalFarmerRecipe> getRecipeType() {
		return JEIFarmingUtilsPlugin.INTERNAL_FARMER_TYPE;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("screen.farmingutils.internal_farmer");
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, InternalFarmerRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 27-3, 36-6).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.INPUT, 108-3, 18-6)
				.addIngredients(ForgeTypes.FLUID_STACK, List.of(recipe.getFluidStack()))
				.setFluidRenderer(FarmingConfig.INTERNAL_FARMER_FLUID_CAPACITY.get(), false, 13, 52);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 48-3, 18-6).addItemStack(recipe.getResultItem());
	}

}
