package dev.the_millman.farmingutils.common.containers;

import dev.the_millman.farmingutils.common.blockentity.InternalFarmerBE;
import dev.the_millman.farmingutils.core.init.BlockInit;
import dev.the_millman.farmingutils.core.init.ContainerInit;
import dev.the_millman.themillmanlib.common.containers.ItemEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
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
	private final ContainerData data;

	
	public InternalFarmerContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
		super(ContainerInit.INTERNAL_FARMER_CONTAINER.get(), windowId, world, pos, playerInventory, player);
		
		BlockEntity entity = world.getBlockEntity(pos);
		blockEntity = (InternalFarmerBE) entity;
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.fluidStack = blockEntity.getFluidStack();
        this.data= blockEntity.data;
        
		if (blockEntity != null) {
			blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
				addSlot(new SlotItemHandler(h, 0, 27, 18));
				addSlot(new SlotItemHandler(h, 1, 27, 54));
				addSlot(new SlotItemHandler(h, 2, 80, 36));
				addSlot(new SlotItemHandler(h, 3, 128, 36));
				layoutUpgradeSlots(h, 4, 152, 18, 3);
			});
		}
		
		layoutPlayerInventorySlots(this.playerInventory, 8, 84);
		addDataSlots(this.data);
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
			if (pIndex < 7) {
				if (!this.moveItemStackTo(itemstack1, 7, 43, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 7, false)) {
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
	
	public int getCraftingProgress() {
		return blockEntity.getCraftingProgress(blockEntity);
	}
	
	public int getMaxCraftingProgress() {
		return blockEntity.getMaxCraftingProgress();
	}
}
