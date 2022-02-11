package com.the_millman.farmingutils.core.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class FarmingConfig {
	public static ForgeConfigSpec CLIENT_CONFIG;
	public static ForgeConfigSpec SERVER_CONFIG;
    
    public static ForgeConfigSpec.IntValue CROP_FARMER_CAPACITY;
    public static ForgeConfigSpec.IntValue CROP_FARMER_USEPERTICK;
    public static ForgeConfigSpec.IntValue CROP_FARMER_TICK;
    
    public static ForgeConfigSpec.IntValue MELON_FARMER_CAPACITY;
    public static ForgeConfigSpec.IntValue MELON_FARMER_USEPERTICK;
    public static ForgeConfigSpec.IntValue MELON_FARMER_TICK;
    
    public static ForgeConfigSpec.IntValue NETHER_WART_FARMER_CAPACITY;
    public static ForgeConfigSpec.IntValue NETHER_WART_FARMER_USEPERTICK;
    public static ForgeConfigSpec.IntValue NETHER_WART_FARMER_TICK;
    
    public static ForgeConfigSpec.IntValue COCOA_BEANS_FARMER_CAPACITY;
    public static ForgeConfigSpec.IntValue COCOA_BEANS_FARMER_USEPERTICK;
    public static ForgeConfigSpec.IntValue COCOA_BEANS_FARMER_TICK;
    
    public static ForgeConfigSpec.IntValue CACTUS_FARMER_CAPACITY;
    public static ForgeConfigSpec.IntValue CACTUS_FARMER_USEPERTICK;
    public static ForgeConfigSpec.IntValue CACTUS_FARMER_TICK;
    
    public static ForgeConfigSpec.IntValue SUGAR_CANES_FARMER_CAPACITY;
    public static ForgeConfigSpec.IntValue SUGAR_CANES_FARMER_USEPERTICK;
    public static ForgeConfigSpec.IntValue SUGAR_CANES_FARMER_TICK;
    
    public static ForgeConfigSpec.IntValue BAMBOO_FARMER_CAPACITY;
    public static ForgeConfigSpec.IntValue BAMBOO_FARMER_USEPERTICK;
    public static ForgeConfigSpec.IntValue BAMBOO_FARMER_TICK;
    
    
    public static void init() {
    	initServer();
    	initClient();
    	
    	ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
    	ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }
    
    private static void initServer() {
    	ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        builder.comment("Crop Farmer settings").push("crop_farmer");
        CROP_FARMER_CAPACITY = builder
                .comment("How much FE the crop farmer can store")
                .defineInRange("crop_farmer_store", 10000, 0, Integer.MAX_VALUE);
        
        CROP_FARMER_USEPERTICK = builder
                .comment("How much FE the crop farmer can use per tick")
                .defineInRange("crop_farmer_use_per_tick", 20, 0, Integer.MAX_VALUE);
        
        CROP_FARMER_TICK = builder
                .comment("How many ticks must pass before the crop farmer works")
                .defineInRange("crop_farmer_tick", 40, 20, 200);
        builder.pop();
        
        builder.comment("Melon and Pumpkin Farmer settings").push("melon_farmer");
        MELON_FARMER_CAPACITY = builder
                .comment("How much FE the melon and pumpkin farmer can store")
                .defineInRange("melon_farmer_store", 10000, 0, Integer.MAX_VALUE);
        
        MELON_FARMER_USEPERTICK = builder
                .comment("How much FE the melon and pumpkin farmer can use per tick")
                .defineInRange("melon_farmer_use_per_tick", 20, 0, Integer.MAX_VALUE);
        
        MELON_FARMER_TICK = builder
                .comment("How many ticks must pass before the melon and pumpkin farmer works")
                .defineInRange("melon_farmer_tick", 40, 20, 200);
        builder.pop();
        
        builder.comment("Nether Wart Farmer settings").push("nether_wart_farmer");
        NETHER_WART_FARMER_CAPACITY = builder
                .comment("How much FE the nether wart farmer can store")
                .defineInRange("nether_wart_farmer_store", 10000, 0, Integer.MAX_VALUE);
        
        NETHER_WART_FARMER_USEPERTICK = builder
                .comment("How much FE the nether wart farmer can use per tick")
                .defineInRange("nether_wart_farmer_use_per_tick", 20, 0, Integer.MAX_VALUE);
        
        NETHER_WART_FARMER_TICK = builder
                .comment("How many ticks must pass before the nether wart farmer works")
                .defineInRange("nether_wart_farmer_tick", 40, 20, 200);
        builder.pop();
        
        builder.comment("Cocoa Beans Farmer settings").push("cocoa_beans_farmer");
        COCOA_BEANS_FARMER_CAPACITY = builder
                .comment("How much FE the cocoa beans farmer can store")
                .defineInRange("cocoa_beans_farmer_store", 10000, 0, Integer.MAX_VALUE);
        
        COCOA_BEANS_FARMER_USEPERTICK = builder
                .comment("How much FE the cocoa beans farmer can use per tick")
                .defineInRange("cocoa_beans_farmer_use_per_tick", 20, 0, Integer.MAX_VALUE);
        
        COCOA_BEANS_FARMER_TICK = builder
                .comment("How many ticks must pass before the cocoa beans farmer works")
                .defineInRange("cocoa_beans_farmer_tick", 40, 20, 200);
        builder.pop();
        
		builder.comment("Cactus Farmer settings").push("cactus_farmer");
		CACTUS_FARMER_CAPACITY = builder
				.comment("How much FE the cactus farmer can store")
				.defineInRange("cactus_farmer_store", 10000, 0, Integer.MAX_VALUE);
		
		CACTUS_FARMER_USEPERTICK = builder
                .comment("How much FE the cactus farmer can use per tick")
                .defineInRange("cactus_farmer_use_per_tick", 20, 0, Integer.MAX_VALUE);
        
        CACTUS_FARMER_TICK = builder
                .comment("How many ticks must pass before the cactus farmer works")
                .defineInRange("cactus_farmer_tick", 40, 20, 200);
        builder.pop();
        
        builder.comment("Sugar Canes Farmer settings").push("sugar_canes_farmer");
		SUGAR_CANES_FARMER_CAPACITY = builder
				.comment("How much FE the sugar canes farmer can store")
				.defineInRange("sugar_canes_farmer_store", 10000, 0, Integer.MAX_VALUE);
		
		SUGAR_CANES_FARMER_USEPERTICK = builder
                .comment("How much FE the sugar canes farmer can use per tick")
                .defineInRange("sugar_canes_farmer_use_per_tick", 20, 0, Integer.MAX_VALUE);
        
		SUGAR_CANES_FARMER_TICK = builder
                .comment("How many ticks must pass before the sugar canes farmer works")
                .defineInRange("sugar_canes_farmer_tick", 40, 20, 200);
        builder.pop();
        
        builder.comment("Bamboo Farmer settings").push("bamboo_farmer");
		BAMBOO_FARMER_CAPACITY = builder
				.comment("How much FE the bamboo farmer can store")
				.defineInRange("bamboo_farmer_store", 10000, 0, Integer.MAX_VALUE);
		
		BAMBOO_FARMER_USEPERTICK = builder
                .comment("How much FE the bamboo farmer can use per tick")
                .defineInRange("bamboo_farmer_use_per_tick", 20, 0, Integer.MAX_VALUE);
        
		BAMBOO_FARMER_TICK = builder
                .comment("How many ticks must pass before the bamboo farmer works")
                .defineInRange("bamboo_farmer_tick", 40, 20, 200);
        builder.pop();
        
    	SERVER_CONFIG = builder.build();
    }
    
    private static void initClient() {
    	ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    	
    	CLIENT_CONFIG = builder.build();
    }
    
    
}
