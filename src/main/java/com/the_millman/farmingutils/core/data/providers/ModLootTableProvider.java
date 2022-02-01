package com.the_millman.farmingutils.core.data.providers;

import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.init.BlockInit;
import com.the_millman.themillmanlib.core.data.BaseLootTableProvider;

import net.minecraft.data.DataGenerator;

public class ModLootTableProvider extends BaseLootTableProvider {

	public ModLootTableProvider(DataGenerator pGenerator) {
		super(pGenerator);
	}

	@Override
	protected void addTables() {
		lootTables.put(BlockInit.CROP_FARMER.get(), createStandardTable("crop_farmer", BlockInit.CROP_FARMER.get(), BlockEntityInit.CROP_FARMER.get()));
		lootTables.put(BlockInit.MELON_FARMER.get(), createStandardTable("melon_farmer", BlockInit.MELON_FARMER.get(), BlockEntityInit.MELON_FARMER.get()));
		lootTables.put(BlockInit.NETHER_WART_FARMER.get(), createStandardTable("nether_wart_farmer", BlockInit.NETHER_WART_FARMER.get(), BlockEntityInit.NETHER_WART_FARMER.get()));
		lootTables.put(BlockInit.COCOA_BEANS_FARMER.get(), createStandardTable("cocoa_beans_farmer", BlockInit.COCOA_BEANS_FARMER.get(), BlockEntityInit.COCOA_BEANS_FARMER.get()));
		lootTables.put(BlockInit.CACTUS_FARMER.get(), createStandardTable("cactus_farmer", BlockInit.CACTUS_FARMER.get(), BlockEntityInit.CACTUS_FARMER.get()));
		lootTables.put(BlockInit.SUGAR_CANES_FARMER.get(), createStandardTable("sugar_canes_farmer", BlockInit.SUGAR_CANES_FARMER.get(), BlockEntityInit.SUGAR_CANES_FARMER.get()));
		
	}

}
