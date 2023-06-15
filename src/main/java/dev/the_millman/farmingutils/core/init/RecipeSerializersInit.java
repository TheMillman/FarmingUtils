package dev.the_millman.farmingutils.core.init;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.common.recipes.InternalFarmerRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializersInit {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FarmingUtils.MODID);
	
	public static final RegistryObject<RecipeSerializer<InternalFarmerRecipe>> INTERNAL_FARMER_SERIALIZER = SERIALIZERS.register("internal_farmer", InternalFarmerRecipe.Serializer::new);
}
