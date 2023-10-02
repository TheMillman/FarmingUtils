package dev.the_millman.farmingutils.common.recipes;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dev.the_millman.farmingutils.core.init.RecipeSerializersInit;
import dev.the_millman.farmingutils.core.init.RecipeTypesInit;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class ComposterRecipe implements Recipe<SimpleContainer> {

	private final ResourceLocation id;
	private final ItemStack output;
	private final NonNullList<Ingredient> recipeItems;
	
	public ComposterRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
		this.id = id;
		this.output = output;
		this.recipeItems = recipeItems;
	}
	
	@Override
	public boolean matches(SimpleContainer pContainer, Level pLevel) {
		return recipeItems.get(0).test(pContainer.getItem(/* INDEX SLOT INPUT */0)) &&
				pContainer.getItem(0).getCount() >=8;
	}

	@Override
	public ItemStack assemble(SimpleContainer p_44001_, RegistryAccess p_267165_) {
		return output;
	}

	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		return getResultItem(access);
	}
	
	public ItemStack getResultItem() {
		return output.copy();
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return recipeItems;
	}
	
	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializersInit.COMPOSTER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypesInit.COMPOSTER_TYPE.get();
	}
	
	public static class Serializer implements RecipeSerializer<ComposterRecipe> {

		@Override
		public ComposterRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
			
			JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
			
			for(int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			}
			
			return new ComposterRecipe(pRecipeId, output, inputs);
		}

		@Override
		public @Nullable ComposterRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
			
			for(int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromNetwork(pBuffer));
			}
			
			ItemStack output = pBuffer.readItem();
			return new ComposterRecipe(pRecipeId, output, inputs);
		}

		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, ComposterRecipe pRecipe) {
			pBuffer.writeInt(pRecipe.getIngredients().size());
			
			for(Ingredient ing : pRecipe.getIngredients()) {
				ing.toNetwork(pBuffer);
			}
			
			pBuffer.writeItemStack(pRecipe.getResultItem(), false);
		}
		
	}

}
