package dev.the_millman.farmingutils.core.init;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.common.containers.BambooFarmerContainer;
import dev.the_millman.farmingutils.common.containers.CactusFarmerContainer;
import dev.the_millman.farmingutils.common.containers.CocoaFarmerContainer;
import dev.the_millman.farmingutils.common.containers.ComposterContainer;
import dev.the_millman.farmingutils.common.containers.CropFarmerContainer;
import dev.the_millman.farmingutils.common.containers.InternalFarmerContainer;
import dev.the_millman.farmingutils.common.containers.MelonFarmerContainer;
import dev.the_millman.farmingutils.common.containers.NetherWartFarmerContainer;
import dev.the_millman.farmingutils.common.containers.SugarCanesFarmerContainer;
import dev.the_millman.farmingutils.common.containers.TestGeneratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerInit {
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, FarmingUtils.MODID);
	
	public static final RegistryObject<MenuType<TestGeneratorContainer>> TEST_GENERATOR_CONTAINER = CONTAINERS.register("test_generator_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new TestGeneratorContainer(windowId, world, pos, inv, inv.player);
    }));
	
	public static final RegistryObject<MenuType<CropFarmerContainer>> CROP_FARMER_CONTAINER = CONTAINERS.register("crop_farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new CropFarmerContainer(windowId, world, pos, inv, inv.player);
    }));
	
	public static final RegistryObject<MenuType<MelonFarmerContainer>> MELON_FARMER_CONTAINER = CONTAINERS.register("melon_farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new MelonFarmerContainer(windowId, world, pos, inv, inv.player);
    }));
	
	public static final RegistryObject<MenuType<NetherWartFarmerContainer>> NETHER_WART_FARMER_CONTAINER = CONTAINERS.register("nether_wart_farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new NetherWartFarmerContainer(windowId, world, pos, inv, inv.player);
    }));
	
	public static final RegistryObject<MenuType<CocoaFarmerContainer>> COCOA_FARMER_CONTAINER = CONTAINERS.register("cocoa_farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new CocoaFarmerContainer(windowId, world, pos, inv, inv.player);
    }));
	
	public static final RegistryObject<MenuType<CactusFarmerContainer>> CACTUS_FARMER_CONTAINER = CONTAINERS.register("cactus_farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new CactusFarmerContainer(windowId, world, pos, inv, inv.player);
    }));
	
	public static final RegistryObject<MenuType<SugarCanesFarmerContainer>> SUGAR_CANES_FARMER_CONTAINER = CONTAINERS.register("sugar_canes_farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new SugarCanesFarmerContainer(windowId, world, pos, inv, inv.player);
    }));
	
	public static final RegistryObject<MenuType<BambooFarmerContainer>> BAMBOO_FARMER_CONTAINER = CONTAINERS.register("bamboo_farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BambooFarmerContainer(windowId, world, pos, inv, inv.player);
    }));
	
	public static final RegistryObject<MenuType<InternalFarmerContainer>> INTERNAL_FARMER_CONTAINER = CONTAINERS.register("internal_farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new InternalFarmerContainer(windowId, world, pos, inv, inv.player);
    }));
	
	public static final RegistryObject<MenuType<ComposterContainer>> COMPOSTER_CONTAINER = CONTAINERS.register("composter_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new ComposterContainer(windowId, world, pos, inv, inv.player);
    }));
}
