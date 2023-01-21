package com.the_millman.farmingutils.common.containers;

import com.the_millman.farmingutils.common.blockentity.InternalFarmerBE;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class InternalFarmerContainer extends ItemEnergyContainer
{	
	protected final InternalFarmerBE blockEntity;
	protected Player playerEntity;
	protected IItemHandler playerInventory;
	protected FluidStack fluidStack;
	
	public InternalFarmerContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
		super(ContainerInit.INTERNAL_FARMER_CONTAINER.get(), windowId, world, pos, playerInventory, player);
		
		BlockEntity entity = world.getBlockEntity(pos);
		blockEntity = (InternalFarmerBE) entity;
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.fluidStack = blockEntity.getFluidStack();
        
		if (blockEntity != null) {
			blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
				layoutInventorySlots(h, 49, 18, 3, 3);
				layoutUpgradeSlots(h, 12, 152, 18, 3);
				addSlot(new SlotItemHandler(h, 9, 27, 36));
				addSlot(new SlotItemHandler(h, 10, 128, 18));
				addSlot(new SlotItemHandler(h, 11, 128, 54));
			});
		}
		
		layoutPlayerInventorySlots(this.playerInventory, 8, 84);
	}

	@Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockInit.MUSHROOM_FARMER.get());
    }
    
	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex < 15) {
				if (!this.moveItemStackTo(itemstack1, 15, 51, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 15, false)) {
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

	public BlockEntity getBlockEntity() {
		return this.blockEntity;
	}

	public void setFluid(FluidStack fluidStack) {
		this.fluidStack = fluidStack;
	}
	
	public FluidStack getFluidStack() {
		return this.fluidStack;
	}
}
