package com.the_millman.farmingutils.core.data.providers;

import com.the_millman.farmingutils.FarmingUtils;
import com.the_millman.farmingutils.core.init.BlockInit;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateModelProvider extends BlockStateProvider {

	public ModBlockStateModelProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, FarmingUtils.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(BlockInit.TEST_ENERGY_GENERATOR.get(), models().cubeBottomTop("test_energy_generator", modLoc("block/test_energy_generator"), modLoc("block/crop_farmer_bottom"), modLoc("block/crop_farmer_top")));
		simpleBlock(BlockInit.CROP_FARMER.get(), models().cubeBottomTop("crop_farmer", modLoc("block/crop_farmer_side"), modLoc("block/crop_farmer_bottom"), modLoc("block/crop_farmer_top")));
		simpleBlock(BlockInit.MELON_FARMER.get(), models().cubeBottomTop("melon_farmer", modLoc("block/melon_farmer_side"), modLoc("block/crop_farmer_bottom"), modLoc("block/crop_farmer_top")));
		simpleBlock(BlockInit.NETHER_WART_FARMER.get(), models().cubeBottomTop("nether_wart_farmer", modLoc("block/nether_wart_farmer_side"), modLoc("block/nether_wart_farmer_bottom"), modLoc("block/nether_wart_farmer_top")));
		simpleBlock(BlockInit.CACTUS_FARMER.get(), models().cubeBottomTop("cactus_farmer", modLoc("block/cactus_farmer_side"), modLoc("block/cactus_farmer_bottom"), modLoc("block/cactus_farmer_top")));
		simpleBlock(BlockInit.SUGAR_CANES_FARMER.get(), models().cubeBottomTop("sugar_canes_farmer", modLoc("block/sugar_canes_farmer_side"), modLoc("block/cactus_farmer_bottom"), modLoc("block/cactus_farmer_top")));
		simpleBlock(BlockInit.BAMBOO_FARMER.get(), models().cubeBottomTop("bamboo_farmer", modLoc("block/bamboo_farmer_side"), modLoc("block/crop_farmer_bottom"), modLoc("block/crop_farmer_top")));
		horizontalBlock(BlockInit.COCOA_BEANS_FARMER.get(), models().orientableWithBottom("cocoa_beans_farmer", modLoc("block/test_energy_generator"), modLoc("block/cocoa_beans_farmer"), modLoc("block/crop_farmer_bottom"), modLoc("block/crop_farmer_top")));
		
	}

}
