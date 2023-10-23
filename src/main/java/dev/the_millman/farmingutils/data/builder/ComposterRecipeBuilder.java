package dev.the_millman.farmingutils.data.builder;

import java.util.function.Consumer;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.core.init.ItemInit;
import dev.the_millman.farmingutils.core.init.RecipeSerializersInit;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ComposterRecipeBuilder {
	
	private final ItemStack output;
	private final NonNullList<Ingredient> recipeItems;
	
	public ComposterRecipeBuilder(ItemStack output, NonNullList<Ingredient> recipeItems) {
		this.output = output;
		this.recipeItems = recipeItems;
	}
	
	public static ComposterRecipeBuilder composterRecipe(NonNullList<Ingredient> recipeItems, ItemStack output) {
		return new ComposterRecipeBuilder(output, recipeItems);
	}
	
	public static ComposterRecipeBuilder composterRecipe(Ingredient compostable, Item output, int count) {
		NonNullList<Ingredient> ingredients = NonNullList.withSize(1, Ingredient.EMPTY);
		ingredients.set(0, compostable);
		ItemStack stack = new ItemStack(output, count);
		return new ComposterRecipeBuilder(stack, ingredients);
	}
	
	public void build(Consumer<FinishedRecipe> consumerIn) {
		ResourceLocation location = ForgeRegistries.ITEMS.getKey(ItemInit.COMPOST.get());
		this.build(consumerIn, location.getPath());
	}

	public void build(Consumer<FinishedRecipe> consumerIn, String save) {
		ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(ItemInit.COMPOST.get());
		if ((new ResourceLocation(save)).equals(resourcelocation)) {
			throw new IllegalStateException("Composter Recipe " + save + " should remove its 'save' argument");
		} else {
			this.build(consumerIn, new ResourceLocation(FarmingUtils.MODID + ":composter/" + save));
		}
	}
	
	public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new ComposterRecipeBuilder.Result(id, this.recipeItems,  this.output));
	}
	
	public static class Result implements FinishedRecipe
	{
		private final ResourceLocation id;
		private final NonNullList<Ingredient> recipeItems;
		private final ItemStack output;
		
		public Result(ResourceLocation id, NonNullList<Ingredient> recipeItems, ItemStack output) {
			this.id =id;
			this.recipeItems = recipeItems;
			this.output = output;
		}
		
		@Override
		public void serializeRecipeData(JsonObject pJson) {
			JsonArray arrayIngredients = new JsonArray();
			for(int i = 0; i < recipeItems.size(); i++) {
				arrayIngredients.add(this.recipeItems.get(i).toJson());
			}
			pJson.add("ingredients", arrayIngredients);
			
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(output.getItem()).toString());
			jsonobject.addProperty("count", output.getCount());
			pJson.add("output", jsonobject);
		}

		@Override
		public ResourceLocation getId() {
			return this.id;		
		}

		@Override
		public RecipeSerializer<?> getType() {
			return RecipeSerializersInit.COMPOSTER_SERIALIZER.get();
		}

		@Override
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Override
		public ResourceLocation getAdvancementId() {
			return null;
		}
		
	}
}
