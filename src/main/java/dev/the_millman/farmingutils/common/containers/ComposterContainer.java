package dev.the_millman.farmingutils.common.containers;

import dev.the_millman.farmingutils.common.blockentity.ComposterBE;
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
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ComposterContainer extends ItemEnergyContainer {

	private ComposterBE blockEntity;
    private Player playerEntity;
	private IItemHandler playerInventory;
	
	public ComposterContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
		super(ContainerInit.COMPOSTER_CONTAINER.get(), windowId, world, pos, playerInventory, player);
		BlockEntity entity = world.getBlockEntity(pos);
		this.blockEntity = (ComposterBE) entity;
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

		if (blockEntity != null) {
			blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
				addSlot(new SlotItemHandler(h, 0, 62, 36));
				addSlot(new SlotItemHandler(h, 1, 116, 36));
			});
		}
		
		layoutPlayerInventorySlots(this.playerInventory, 8, 84);
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockInit.COMPOSTER.get());
	}
	
	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex < 2) {
				if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
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

	public int getProgress() {
		return blockEntity.getProgress();
	}
	
	public int getMaxProgress() {
		return blockEntity.getMaxProgress();
	}
}
