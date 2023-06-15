package dev.the_millman.farmingutils.common.recipes;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dev.the_millman.farmingutils.core.init.RecipeSerializersInit;
import dev.the_millman.farmingutils.core.init.RecipeTypesInit;
import dev.the_millman.themillmanlib.core.util.FluidJsonUtil;
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
import net.minecraftforge.fluids.FluidStack;

public class InternalFarmerRecipe implements Recipe<SimpleContainer> {

	private final ResourceLocation id;
	private final ItemStack output;
	private final NonNullList<Ingredient> recipeItems;
	private final FluidStack fluidStack;
	
	public InternalFarmerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, FluidStack fluidStack) {
		this.id = id;
		this.output = output;
		this.recipeItems = recipeItems;
		this.fluidStack = fluidStack;
	}
	
	@Override
	public boolean matches(SimpleContainer pContainer, Level pLevel) {
//		if(pLevel.isClientSide()) {
//			return false;
//		}
		
		return recipeItems.get(0).test(pContainer.getItem(/* INDEX SLOT INPUT */0))
				&& recipeItems.get(1).test(pContainer.getItem(/* INDEX SLOT INPUT */1));
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return recipeItems;
	}
	
	public FluidStack getFluidStack() {
		return fluidStack;
	}

	@Override
	public ItemStack assemble(SimpleContainer pContainer, RegistryAccess access) {
		return output;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		return getResultItem();
	}
	
	public ItemStack getResultItem() {
		return output.copy();
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}
	
	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializersInit.INTERNAL_FARMER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypesInit.INTERNAL_FARMER_TYPE.get();
	}
	
	public static class Serializer implements RecipeSerializer<InternalFarmerRecipe> {
		
		@Override
		public InternalFarmerRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
			
			JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
			FluidStack fluidStack = FluidJsonUtil.readFluidStack(pSerializedRecipe.get("fluid").getAsJsonObject());
			
			for(int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			}
			
			return new InternalFarmerRecipe(pRecipeId, output, inputs, fluidStack);
		}
		
		@Override
		public @Nullable InternalFarmerRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
			FluidStack fluidStack = pBuffer.readFluidStack();
			
			for(int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromNetwork(pBuffer));
			}
			
			ItemStack output = pBuffer.readItem();
			return new InternalFarmerRecipe(pRecipeId, output, inputs, fluidStack);
		}
		
		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, InternalFarmerRecipe pRecipe) {
			pBuffer.writeInt(pRecipe.getIngredients().size());
			pBuffer.writeFluidStack(pRecipe.fluidStack);
			
			for(Ingredient ing : pRecipe.getIngredients()) {
				ing.toNetwork(pBuffer);
			}
			
			pBuffer.writeItemStack(pRecipe.getResultItem(), false);
		}
	}
}
