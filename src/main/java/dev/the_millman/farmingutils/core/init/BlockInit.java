package dev.the_millman.farmingutils.core.init;

import dev.the_millman.farmingutils.FarmingUtils;
import dev.the_millman.farmingutils.common.blocks.AdvancedComposterBlock;
import dev.the_millman.farmingutils.common.blocks.BambooFarmerBlock;
import dev.the_millman.farmingutils.common.blocks.CactusFarmerBlock;
import dev.the_millman.farmingutils.common.blocks.CocoaFarmerBlock;
import dev.the_millman.farmingutils.common.blocks.CropFarmerBlock;
import dev.the_millman.farmingutils.common.blocks.InternalFarmerBlock;
import dev.the_millman.farmingutils.common.blocks.MelonFarmerBlock;
import dev.the_millman.farmingutils.common.blocks.NetherWartFarmerBlock;
import dev.the_millman.farmingutils.common.blocks.SugarCanesFarmerBlock;
import dev.the_millman.farmingutils.common.blocks.TestEnergyGeneratorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FarmingUtils.MODID);
	
	public static final RegistryObject<Block> CROP_FARMER = BLOCKS.register("crop_farmer", () -> new CropFarmerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final RegistryObject<Block> MELON_FARMER = BLOCKS.register("melon_farmer", () -> new MelonFarmerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final RegistryObject<Block> NETHER_WART_FARMER = BLOCKS.register("nether_wart_farmer", () -> new NetherWartFarmerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final RegistryObject<Block> COCOA_BEANS_FARMER = BLOCKS.register("cocoa_beans_farmer", () -> new CocoaFarmerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final RegistryObject<Block> CACTUS_FARMER = BLOCKS.register("cactus_farmer", () -> new CactusFarmerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final RegistryObject<Block> SUGAR_CANES_FARMER = BLOCKS.register("sugar_canes_farmer", () -> new SugarCanesFarmerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final RegistryObject<Block> BAMBOO_FARMER = BLOCKS.register("bamboo_farmer", () -> new BambooFarmerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final RegistryObject<Block> MUSHROOM_FARMER = BLOCKS.register("internal_farmer", () -> new InternalFarmerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final RegistryObject<Block> COMPOSTER = BLOCKS.register("composter", () -> new AdvancedComposterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	
	
	public static final RegistryObject<Block> TEST_ENERGY_GENERATOR = BLOCKS.register("test_energy_generator", () -> new TestEnergyGeneratorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.METAL)));
	
}
