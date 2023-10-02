package dev.the_millman.farmingutils.common.recipes;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.core.init.BlockInit;
import dev.the_millman.farmingutils.core.util.FarmingResources;
import mezz.jei.api.constants.VanillaTypes;
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

public class ComposterRecipeCategory implements IRecipeCategory<ComposterRecipe> {

	public static final ResourceLocation UID = new ResourceLocation(FarmingUtils.MODID, "composter");
	public static final ResourceLocation TEXTURE = FarmingResources.COMPOSTER_GUI;
	
	private final IDrawable background;
	private final IDrawable icon;
	
	public ComposterRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(TEXTURE, 3, 6, 170, 77);
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockInit.COMPOSTER.get()));
	}
	
	@Override
	public RecipeType<ComposterRecipe> getRecipeType() {
		return JEIFarmingUtilsPlugin.COMPOSTER_TYPE;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("screen.farmingutils.composter");
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
	public void setRecipe(IRecipeLayoutBuilder builder, ComposterRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 62-3, 36-6).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 116-3, 36-6).addItemStack(recipe.getResultItem());
	}

}
