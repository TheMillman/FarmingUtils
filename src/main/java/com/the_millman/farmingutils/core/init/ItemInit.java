package com.the_millman.farmingutils.core.init;

import com.the_millman.farmingutils.FarmingUtils;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FarmingUtils.MODID);
//	private static final CreativeModeTab TAB = FarmingUtilsTab.FARMING_UTILS;
	
	public static final RegistryObject<Item> CROP_FARMER = ITEMS.register("crop_farmer", () -> new BlockItem(BlockInit.CROP_FARMER.get(), new Item.Properties())); //.tab(TAB)));
	public static final RegistryObject<Item> MELON_FARMER = ITEMS.register("melon_farmer", () -> new BlockItem(BlockInit.MELON_FARMER.get(), new Item.Properties())); //.tab(TAB)));
	public static final RegistryObject<Item> NETHER_WART_FARMER = ITEMS.register("nether_wart_farmer", () -> new BlockItem(BlockInit.NETHER_WART_FARMER.get(), new Item.Properties())); //.tab(TAB)));
	public static final RegistryObject<Item> COCOA_BEANS_FARMER = ITEMS.register("cocoa_beans_farmer", () -> new BlockItem(BlockInit.COCOA_BEANS_FARMER.get(), new Item.Properties())); //.tab(TAB)));
	public static final RegistryObject<Item> CACTUS_FARMER = ITEMS.register("cactus_farmer", () -> new BlockItem(BlockInit.CACTUS_FARMER.get(), new Item.Properties())); //.tab(TAB)));
	public static final RegistryObject<Item> SUGAR_CANES_FARMER = ITEMS.register("sugar_canes_farmer", () -> new BlockItem(BlockInit.SUGAR_CANES_FARMER.get(), new Item.Properties())); //.tab(TAB)));
	public static final RegistryObject<Item> BAMBOO_FARMER = ITEMS.register("bamboo_farmer", () -> new BlockItem(BlockInit.BAMBOO_FARMER.get(), new Item.Properties())); //.tab(TAB)));
	public static final RegistryObject<Item> MUSHROOM_FARMER = ITEMS.register("internal_farmer", () -> new BlockItem(BlockInit.MUSHROOM_FARMER.get(), new Item.Properties())); //.tab(TAB)));
	public static final RegistryObject<Item> COMPOSTER = ITEMS.register("composter", () -> new BlockItem(BlockInit.COMPOSTER.get(), new Item.Properties())); //.tab(TAB)));
	
	//public static final RegistryObject<Item> TEST_ENERGY_GENERATOR = ITEMS.register("test_energy_generator", () -> new BlockItem(BlockInit.TEST_ENERGY_GENERATOR.get(), new Item.Properties().tab(TAB)));
}
