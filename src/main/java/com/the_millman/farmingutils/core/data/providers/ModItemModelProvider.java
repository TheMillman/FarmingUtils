package com.the_millman.farmingutils.core.data.providers;

import com.the_millman.farmingutils.FarmingUtils;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, FarmingUtils.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		cubeBottomTop("crop_farmer", modLoc("block/crop_farmer_side"), modLoc("block/crop_farmer_bottom"), modLoc("block/crop_farmer_top"));
		cubeBottomTop("melon_farmer", modLoc("block/melon_farmer_side"), modLoc("block/crop_farmer_bottom"), modLoc("block/crop_farmer_top"));
		cubeBottomTop("nether_wart_farmer", modLoc("block/nether_wart_farmer_side"), modLoc("block/nether_wart_farmer_bottom"), modLoc("block/nether_wart_farmer_top"));
		orientableWithBottom("cocoa_beans_farmer", modLoc("block/test_energy_generator"), modLoc("block/cocoa_beans_farmer"), modLoc("block/crop_farmer_bottom"), modLoc("block/crop_farmer_top"));
		cubeBottomTop("cactus_farmer", modLoc("block/cactus_farmer_side"), modLoc("block/cactus_farmer_bottom"), modLoc("block/cactus_farmer_top"));
		cubeBottomTop("sugar_canes_farmer", modLoc("block/sugar_canes_farmer_side"), modLoc("block/cactus_farmer_bottom"), modLoc("block/cactus_farmer_top"));
		cubeBottomTop("bamboo_farmer", modLoc("block/bamboo_farmer_side"), modLoc("block/crop_farmer_bottom"), modLoc("block/crop_farmer_top"));
		
		singleTexture("iron_upgrade", mcLoc("item/generated"), "layer0", modLoc("item/iron_upgrade"));
		singleTexture("gold_upgrade", mcLoc("item/generated"), "layer0", modLoc("item/gold_upgrade"));
		singleTexture("diamond_upgrade", mcLoc("item/generated"), "layer0", modLoc("item/diamond_upgrade"));
		singleTexture("redstone_upgrade", mcLoc("item/generated"), "layer0", modLoc("item/redstone_upgrade"));
		singleTexture("drop_upgrade", mcLoc("item/generated"), "layer0", modLoc("item/drop_upgrade"));
		
	}

}
