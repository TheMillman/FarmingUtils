package dev.the_millman.farmingutils.core.init;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.common.blockentity.BambooFarmerBE;
import dev.the_millman.farmingutils.common.blockentity.CactusFarmerBE;
import dev.the_millman.farmingutils.common.blockentity.CocoaFarmerBE;
import dev.the_millman.farmingutils.common.blockentity.ComposterBE;
import dev.the_millman.farmingutils.common.blockentity.CropFarmerBE;
import dev.the_millman.farmingutils.common.blockentity.InternalFarmerBE;
import dev.the_millman.farmingutils.common.blockentity.MelonFarmerBE;
import dev.the_millman.farmingutils.common.blockentity.NetherWartFarmerBE;
import dev.the_millman.farmingutils.common.blockentity.SugarCanesFarmerBE;
import dev.the_millman.farmingutils.common.blockentity.TestEnergyGenerator;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FarmingUtils.MODID);
	
	public static final RegistryObject<BlockEntityType<TestEnergyGenerator>> TEST_ENERGY_GENERATOR = BLOCK_ENTITIES.register("test_energy_generator", () -> BlockEntityType.Builder.of(TestEnergyGenerator::new, BlockInit.TEST_ENERGY_GENERATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<CropFarmerBE>> CROP_FARMER = BLOCK_ENTITIES.register("crop_farmer", () -> BlockEntityType.Builder.of(CropFarmerBE::new, BlockInit.CROP_FARMER.get()).build(null));
	public static final RegistryObject<BlockEntityType<MelonFarmerBE>> MELON_FARMER = BLOCK_ENTITIES.register("melon_farmer", () -> BlockEntityType.Builder.of(MelonFarmerBE::new, BlockInit.MELON_FARMER.get()).build(null));
	public static final RegistryObject<BlockEntityType<NetherWartFarmerBE>> NETHER_WART_FARMER = BLOCK_ENTITIES.register("nether_wart_farmer", () -> BlockEntityType.Builder.of(NetherWartFarmerBE::new, BlockInit.NETHER_WART_FARMER.get()).build(null));
	public static final RegistryObject<BlockEntityType<CocoaFarmerBE>> COCOA_BEANS_FARMER = BLOCK_ENTITIES.register("cocoa_beans_farmer", () -> BlockEntityType.Builder.of(CocoaFarmerBE::new, BlockInit.COCOA_BEANS_FARMER.get()).build(null));
	public static final RegistryObject<BlockEntityType<CactusFarmerBE>> CACTUS_FARMER = BLOCK_ENTITIES.register("cactus_farmer", () -> BlockEntityType.Builder.of(CactusFarmerBE::new, BlockInit.CACTUS_FARMER.get()).build(null));
	public static final RegistryObject<BlockEntityType<SugarCanesFarmerBE>> SUGAR_CANES_FARMER = BLOCK_ENTITIES.register("sugar_canes_farmer", () -> BlockEntityType.Builder.of(SugarCanesFarmerBE::new, BlockInit.SUGAR_CANES_FARMER.get()).build(null));
	public static final RegistryObject<BlockEntityType<BambooFarmerBE>> BAMBOO_FARMER = BLOCK_ENTITIES.register("bamboo_farmer", () -> BlockEntityType.Builder.of(BambooFarmerBE::new, BlockInit.BAMBOO_FARMER.get()).build(null));
	public static final RegistryObject<BlockEntityType<InternalFarmerBE>> INTERNAL_FARMER = BLOCK_ENTITIES.register("internal_farmer", () -> BlockEntityType.Builder.of(InternalFarmerBE::new, BlockInit.MUSHROOM_FARMER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ComposterBE>> COMPOSTER = BLOCK_ENTITIES.register("composter", () -> BlockEntityType.Builder.of(ComposterBE::new, BlockInit.COMPOSTER.get()).build(null));
	
}
