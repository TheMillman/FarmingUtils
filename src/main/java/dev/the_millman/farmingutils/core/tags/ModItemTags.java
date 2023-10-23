package dev.the_millman.farmingutils.core.tags;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.themillmanlib.core.util.LibTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags extends LibTags.Items {
	
	public static final TagKey<Item> SPEED_UPGRADE= libLoc("speed_upgrade");
	
	public static final TagKey<Item> CROP_SEEDS= modLoc(FarmingUtils.MODID, "crop_seeds");
	public static final TagKey<Item> CROP_BLOCK_SEEDS= modLoc(FarmingUtils.MODID, "crop_block_seeds");
	public static final TagKey<Item> CROP_RESULTS= modLoc(FarmingUtils.MODID, "crop_results");
	public static final TagKey<Item> COMPOSTER_ITEMS = modLoc(FarmingUtils.MODID, "composter_items");
	public static final TagKey<Item> COMPOST = modLoc("forge", "compost");
}
