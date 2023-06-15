package dev.the_millman.farmingutils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.the_millman.farmingutils.core.init.BlockEntityInit;
import dev.the_millman.farmingutils.core.init.BlockInit;
import dev.the_millman.farmingutils.core.init.ContainerInit;
import dev.the_millman.farmingutils.core.init.ItemInit;
import dev.the_millman.farmingutils.core.init.RecipeSerializersInit;
import dev.the_millman.farmingutils.core.init.RecipeTypesInit;
import dev.the_millman.farmingutils.core.networking.ModMessages;
import dev.the_millman.farmingutils.core.util.FarmingConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FarmingUtils.MODID)
public class FarmingUtils
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "farmingutils";
    
    public FarmingUtils() {
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	ItemInit.ITEMS.register(bus);
    	BlockInit.BLOCKS.register(bus);
    	BlockEntityInit.BLOCK_ENTITIES.register(bus);
    	ContainerInit.CONTAINERS.register(bus);
    	RecipeSerializersInit.SERIALIZERS.register(bus);
    	RecipeTypesInit.RECIPES_TYPE.register(bus);
    	
    	FarmingConfig.init();
    	
    	bus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

}
