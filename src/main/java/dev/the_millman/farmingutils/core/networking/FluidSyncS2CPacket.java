package dev.the_millman.farmingutils.core.networking;

import java.util.function.Supplier;

import dev.the_millman.farmingutils.common.blockentity.InternalFarmerBE;
import dev.the_millman.farmingutils.common.containers.InternalFarmerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

public class FluidSyncS2CPacket {
	private final FluidStack fluidStack;
    private final BlockPos pos;

    public FluidSyncS2CPacket(FluidStack fluidStack, BlockPos pos) {
        this.fluidStack = fluidStack;
        this.pos = pos;
    }

    public FluidSyncS2CPacket(FriendlyByteBuf buf) {
        this.fluidStack = buf.readFluidStack();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFluidStack(fluidStack);
        buf.writeBlockPos(pos);
    }

    @SuppressWarnings("resource")
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof InternalFarmerBE blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof InternalFarmerContainer menu &&
                    menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            }
        });
        return true;
    }
}
