package dev.the_millman.farmingutils.core.init;

import dev.the_millman.farmingutils.FarmingUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FarmingUtils.MODID);
	
	public static final RegistryObject<Item> CROP_FARMER = register(ITEMS.register("crop_farmer", () -> new BlockItem(BlockInit.CROP_FARMER.get(), new Item.Properties())));
	public static final RegistryObject<Item> MELON_FARMER = register(ITEMS.register("melon_farmer", () -> new BlockItem(BlockInit.MELON_FARMER.get(), new Item.Properties())));
	public static final RegistryObject<Item> NETHER_WART_FARMER = register(ITEMS.register("nether_wart_farmer", () -> new BlockItem(BlockInit.NETHER_WART_FARMER.get(), new Item.Properties())));
	public static final RegistryObject<Item> COCOA_BEANS_FARMER = register(ITEMS.register("cocoa_beans_farmer", () -> new BlockItem(BlockInit.COCOA_BEANS_FARMER.get(), new Item.Properties())));
	public static final RegistryObject<Item> CACTUS_FARMER = register(ITEMS.register("cactus_farmer", () -> new BlockItem(BlockInit.CACTUS_FARMER.get(), new Item.Properties())));
	public static final RegistryObject<Item> SUGAR_CANES_FARMER = register(ITEMS.register("sugar_canes_farmer", () -> new BlockItem(BlockInit.SUGAR_CANES_FARMER.get(), new Item.Properties())));
	public static final RegistryObject<Item> BAMBOO_FARMER = register(ITEMS.register("bamboo_farmer", () -> new BlockItem(BlockInit.BAMBOO_FARMER.get(), new Item.Properties())));
	public static final RegistryObject<Item> MUSHROOM_FARMER = register(ITEMS.register("internal_farmer", () -> new BlockItem(BlockInit.MUSHROOM_FARMER.get(), new Item.Properties())));
	public static final RegistryObject<Item> COMPOSTER = register(ITEMS.register("composter", () -> new BlockItem(BlockInit.COMPOSTER.get(), new Item.Properties())));
	
	public static final RegistryObject<Item> COMPOST = register(ITEMS.register("compost", () -> new BoneMealItem(new Item.Properties())));
	
	public static <T extends Item> RegistryObject<T> register(RegistryObject<T> itemLike) {
		return CreativeTabInit.addToTab(itemLike);
	}
}
