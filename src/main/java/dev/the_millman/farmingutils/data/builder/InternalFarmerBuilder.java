package dev.the_millman.farmingutils.data.builder;

import java.util.function.Consumer;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.core.init.RecipeSerializersInit;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class InternalFarmerBuilder {
	
	private final ItemStack output;
	private final NonNullList<Ingredient> recipeItems;
	private final FluidStack fluidStack;
	
	public InternalFarmerBuilder(ItemStack output, NonNullList<Ingredient> recipeItems, FluidStack fluidStack) {
		this.output = output;
		this.recipeItems = recipeItems;
		this.fluidStack = fluidStack;
	}

	public static InternalFarmerBuilder internalFarmer(NonNullList<Ingredient> recipeItems, FluidStack fluidStack, ItemStack output) {
		return new InternalFarmerBuilder(output, recipeItems, fluidStack);
	}
	
	public static InternalFarmerBuilder internalFarmer(Ingredient compost, Ingredient flower, FluidStack fluidStack, Item output, int count) {
		NonNullList<Ingredient> ingredients = NonNullList.withSize(2, Ingredient.EMPTY);
		ingredients.set(0, compost);
		ingredients.set(1, flower);
		ItemStack stack = new ItemStack(output, count);
		return new InternalFarmerBuilder(stack, ingredients, fluidStack);
	}
	
	public void build(Consumer<FinishedRecipe> consumerIn) {
		ResourceLocation location = ForgeRegistries.ITEMS.getKey(this.recipeItems.get(1).getItems()[0].getItem());
		this.build(consumerIn, FarmingUtils.MODID + ":internal_farmer/" + location.getPath());
	}

	public void build(Consumer<FinishedRecipe> consumerIn, String save) {
		ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.recipeItems.get(1).getItems()[0].getItem());
		if ((new ResourceLocation(save)).equals(resourcelocation)) {
			throw new IllegalStateException("Internal Farmer Recipe " + save + " should remove its 'save' argument");
		} else {
			this.build(consumerIn, new ResourceLocation(save));
		}
	}
	
	public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new InternalFarmerBuilder.Result(id, this.recipeItems,  this.fluidStack,  this.output));
	}
	
	public static class Result implements FinishedRecipe
	{
		private final ResourceLocation id;
		private final NonNullList<Ingredient> recipeItems;
		private final FluidStack fluidStack;
		private final ItemStack output;
		
		public Result(ResourceLocation id, NonNullList<Ingredient> recipeItems, FluidStack fluidStack, ItemStack output) {
			this.id =id;
			this.recipeItems = recipeItems;
			this.fluidStack = fluidStack;
			this.output = output;
		}
		
		@Override
		public void serializeRecipeData(JsonObject pJson) {
			JsonArray arrayIngredients = new JsonArray();
			for(int i = 0; i < recipeItems.size(); i++) {
				arrayIngredients.add(this.recipeItems.get(i).toJson());
			}
			pJson.add("ingredients", arrayIngredients);
			
			JsonObject jsonofluid = new JsonObject();
			jsonofluid.addProperty("FluidName", ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
			jsonofluid.addProperty("Amount", fluidStack.getAmount());
			pJson.add("fluid", jsonofluid);
			
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(output.getItem()).toString());
			jsonobject.addProperty("count", output.getCount());
			pJson.add("output", jsonobject);
		}

		@Override
		public ResourceLocation getId() {
			return this.id;		}

		@Override
		public RecipeSerializer<?> getType() {
			return RecipeSerializersInit.INTERNAL_FARMER_SERIALIZER.get();
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
