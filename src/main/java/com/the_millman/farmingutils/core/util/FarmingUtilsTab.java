package com.the_millman.farmingutils.core.util;

import com.the_millman.farmingutils.core.init.ItemInit;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FarmingUtilsTab extends CreativeModeTab {

	public static final FarmingUtilsTab FARMING_UTILS = new FarmingUtilsTab(CreativeModeTab.TABS.length, "farming_utils_tab");
	
	public FarmingUtilsTab(int pId, String pLangId) {
		super(pId, pLangId);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemInit.CROP_FARMER.get());
	}
}
