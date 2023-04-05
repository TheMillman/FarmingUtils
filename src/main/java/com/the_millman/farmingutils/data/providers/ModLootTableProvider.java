package com.the_millman.farmingutils.data.providers;

import java.util.function.BiConsumer;

import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.init.BlockInit;
import com.the_millman.themillmanlib.core.data.LibLootTables;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable.Builder;

public class ModLootTableProvider implements LootTableSubProvider {

	@Override
	public void generate(BiConsumer<ResourceLocation, Builder> builder) {
		builder.accept(BlockInit.CROP_FARMER.getId(), LibLootTables.createItemEnergyTable("crop_farmer", BlockInit.CROP_FARMER.get(), BlockEntityInit.CROP_FARMER.get()));
		builder.accept(BlockInit.MELON_FARMER.getId(), LibLootTables.createItemEnergyTable("melon_farmer", BlockInit.MELON_FARMER.get(), BlockEntityInit.MELON_FARMER.get()));
		builder.accept(BlockInit.NETHER_WART_FARMER.getId(), LibLootTables.createItemEnergyTable("nether_wart_farmer", BlockInit.NETHER_WART_FARMER.get(), BlockEntityInit.NETHER_WART_FARMER.get()));
		builder.accept(BlockInit.COCOA_BEANS_FARMER.getId(), LibLootTables.createItemEnergyTable("cocoa_beans_farmer", BlockInit.COCOA_BEANS_FARMER.get(), BlockEntityInit.COCOA_BEANS_FARMER.get()));
		builder.accept(BlockInit.CACTUS_FARMER.getId(), LibLootTables.createItemEnergyTable("cactus_farmer", BlockInit.CACTUS_FARMER.get(), BlockEntityInit.CACTUS_FARMER.get()));
		builder.accept(BlockInit.SUGAR_CANES_FARMER.getId(), LibLootTables.createItemEnergyTable("sugar_canes_farmer", BlockInit.SUGAR_CANES_FARMER.get(), BlockEntityInit.SUGAR_CANES_FARMER.get()));
		builder.accept(BlockInit.BAMBOO_FARMER.getId(), LibLootTables.createItemEnergyTable("bamboo_farmer", BlockInit.BAMBOO_FARMER.get(), BlockEntityInit.BAMBOO_FARMER.get()));
		builder.accept(BlockInit.MUSHROOM_FARMER.getId(), LibLootTables.createItemEnergyTable("internal_farmer", BlockInit.MUSHROOM_FARMER.get(), BlockEntityInit.INTERNAL_FARMER.get()));
		builder.accept(BlockInit.COMPOSTER.getId(), LibLootTables.createItemEnergyTable("composter", BlockInit.COMPOSTER.get(), BlockEntityInit.COMPOSTER.get()));
	}

}
