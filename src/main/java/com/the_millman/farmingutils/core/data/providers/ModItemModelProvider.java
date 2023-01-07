package com.the_millman.farmingutils.core.data.providers;

import com.the_millman.farmingutils.FarmingUtils;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, FarmingUtils.MODID, existingFileHelper);
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
	}

}
