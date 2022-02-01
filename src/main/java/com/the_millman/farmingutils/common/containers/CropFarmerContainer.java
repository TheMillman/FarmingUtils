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
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CropFarmerContainer extends ItemEnergyContainer {

	private BlockEntity blockEntity;
    private Player playerEntity;
	private IItemHandler playerInventory;
	
	public CropFarmerContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) 
	{
		super(ContainerInit.CROP_FARMER_CONTAINER.get(), windowId, world, pos, playerInventory, player);
        blockEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

		if (blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				//addSlot(new SlotItemHandler(h, 18, 147, 18));
				layoutInventorySlots(h, 26, 18, 3, 6);
				layoutUpgradeSlots(h, 18, 147, 18, 3);
				//outputInventorySlots(h, 80, 18);
			});
		}
		
		layoutPlayerInventorySlots(this.playerInventory, 8, 84);
	}

	@Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockInit.CROP_FARMER.get());
    }
    
//    private void outputInventorySlots(IItemHandler handler, int x, int y) {
//    	//Questo è un 3x3 input slots
//    	for(int row = 0; row < /*6*/ 3; ++row)
//		{
//			for(int column = 0; column < 3; ++column)
//			{
//				this.addSlot(new SlotItemHandler(handler, (row * 3) + column, x + row * 18, y + column * 18));
//			}
//		}
//    }
	
//	protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
//		// Player inventory
//		addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
//
//		// Hotbar
//		topRow += 58;
//		addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
//	}
	
	//TODO Sistemare slots, sistemare ordine    x + row * 18
	@Override
	protected void layoutInventorySlots(IItemHandler handler, int x, int y, int slotRow, int slotColumn) {
		for (int row = 0; row < slotRow; ++row) {
			for (int column = 0; column < slotColumn; ++column) {
				this.addSlot(new SlotItemHandler(handler, (column * 3) + row, x + column * 18, y + row * 18));
			}
		}
	}
    
	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex < 21) {
				if (!this.moveItemStackTo(itemstack1, 21, 57, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 21, false)) {
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
