package com.the_millman.farmingutils.common.containers;

import com.the_millman.farmingutils.core.init.BlockInit;
import com.the_millman.farmingutils.core.init.ContainerInit;
import com.the_millman.themillmanlib.common.containers.ItemEnergyContainer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MelonFarmerContainer extends ItemEnergyContainer {

	private BlockEntity blockEntity;
	private Player playerEntity;
	private IItemHandler playerInventory;

	public MelonFarmerContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
		super(ContainerInit.MELON_FARMER_CONTAINER.get(), windowId, world, pos, playerInventory, player);
		blockEntity = world.getBlockEntity(pos);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(playerInventory);

		if (blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				layoutInventorySlots(h, 62, 18, 3, 3);
				layoutUpgradeSlots(h, 9, 147, 18, 2);
			});
		}
		
		layoutPlayerInventorySlots(this.playerInventory, 8, 84);
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockInit.MELON_FARMER.get());
	}
	
	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex < 11) {
				if (!this.moveItemStackTo(itemstack1, 11, 47, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 11, false)) {
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
