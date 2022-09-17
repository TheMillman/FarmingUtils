package com.the_millman.farmingutils.core.networking;

import java.util.function.Supplier;

import com.the_millman.farmingutils.common.blockentity.InternalFarmerBE;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class GrowthSyncS2CPacket2 {
    private final int progress;
    private final BlockPos pos;

    public GrowthSyncS2CPacket2(int progress, BlockPos pos) {
        this.progress = progress;
        this.pos = pos;
    }

    public GrowthSyncS2CPacket2(FriendlyByteBuf buf) {
        this.progress = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(progress);
        buf.writeBlockPos(pos);
    }

    @SuppressWarnings("resource")
	public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof InternalFarmerBE blockEntity) {
                blockEntity.setGrowthStage(progress);
            }
        });
        return true;
    }
}
