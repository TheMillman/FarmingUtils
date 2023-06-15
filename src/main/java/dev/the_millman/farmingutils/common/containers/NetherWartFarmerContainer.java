package dev.the_millman.farmingutils.common.containers;

import dev.the_millman.farmingutils.core.init.BlockInit;
import dev.the_millman.farmingutils.core.init.ContainerInit;
import dev.the_millman.themillmanlib.common.containers.ItemEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class NetherWartFarmerContainer extends ItemEnergyContainer {

	private BlockEntity blockEntity;
    private Player playerEntity;
	private IItemHandler playerInventory;
	
	public NetherWartFarmerContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
		super(ContainerInit.NETHER_WART_FARMER_CONTAINER.get(), windowId, world, pos, playerInventory, player);
		blockEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

		if (blockEntity != null) {
			blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
				layoutInventorySlots(h, 62, 18, 3, 3);
				layoutUpgradeSlots(h, 9, 147, 18, 3);
			});
		}
		
		layoutPlayerInventorySlots(this.playerInventory, 8, 84);
	}

	@Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockInit.NETHER_WART_FARMER.get());
    }
	
	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex < 12) {
				if (!this.moveItemStackTo(itemstack1, 12, 48, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 12, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(pPlayer, itemstack1);
		}

		return itemstack;
	}
}
