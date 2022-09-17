package com.the_millman.farmingutils.core.networking;

import com.the_millman.farmingutils.FarmingUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

	public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(FarmingUtils.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(FluidSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        	.decoder(FluidSyncS2CPacket::new)
        	.encoder(FluidSyncS2CPacket::toBytes)
        	.consumerMainThread(FluidSyncS2CPacket::handle)
        	.add();
        
        net.messageBuilder(ItemStackSyncS2CPacket2.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        	.decoder(ItemStackSyncS2CPacket2::new)
        	.encoder(ItemStackSyncS2CPacket2::toBytes)
        	.consumerMainThread(ItemStackSyncS2CPacket2::handle)
        	.add();
        
        net.messageBuilder(GrowthSyncS2CPacket2.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        	.decoder(GrowthSyncS2CPacket2::new)
        	.encoder(GrowthSyncS2CPacket2::toBytes)
        	.consumerMainThread(GrowthSyncS2CPacket2::handle)
        .add();
    }

    public static <MSG> void sendToServer(MSG message) {
         INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
