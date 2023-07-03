package dev.the_millman.farmingutils.core.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.themillmanlib.core.init.LibItemInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FarmingUtils.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class CreativeTabInit {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FarmingUtils.MODID);

	public static final List<Supplier<? extends ItemLike>> FARMING_TAB_LIST = new ArrayList<>();
	public static final RegistryObject<CreativeModeTab> FARMING_UTILS_TAB = CREATIVE_TABS
			.register("farming_utils_tab", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.farming_utils_tab"))
					.icon(ItemInit.CROP_FARMER.get()::getDefaultInstance)
					.displayItems((displayParams, output) -> 
						FARMING_TAB_LIST.forEach(itemLike -> output.accept(itemLike.get())))
					.build());

	public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
		FARMING_TAB_LIST.add(itemLike);
		return itemLike;
	}
	
	@SubscribeEvent
	public static void customTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTab() == FARMING_UTILS_TAB.get()) {
			Stream<Item> hello = LibItemInit.ITEMS.getEntries().stream().map(RegistryObject::get);
			hello.forEach(item -> {
				if(item != LibItemInit.PLASTIC.get()) {
					event.accept(item);
				}
			});
		}
	}
}
