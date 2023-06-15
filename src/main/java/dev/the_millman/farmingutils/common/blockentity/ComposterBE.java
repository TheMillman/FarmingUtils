package dev.the_millman.farmingutils.common.blockentity;

import org.jetbrains.annotations.NotNull;

import dev.the_millman.farmingutils.core.init.BlockEntityInit;
import dev.the_millman.farmingutils.core.init.ItemInit;
import dev.the_millman.farmingutils.core.tags.ModItemTags;
import dev.the_millman.farmingutils.core.util.FarmingConfig;
import dev.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import dev.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import dev.the_millman.themillmanlib.core.util.ModItemHandlerHelp;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class ComposterBE extends ItemEnergyBlockEntity {

	int tick;
	boolean initialized = false;
	
	public ComposterBE(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.COMPOSTER.get(), pWorldPosition, pBlockState);
	}

	@Override
	protected void init() {
		this.initialized = true;
		this.tick = 0;
	}
	
	// Upgrade is output slot
	@Override
	public void tickServer() {
		if(!initialized) 
			init();
		
		if (hasPowerToWork(energyStorage, FarmingConfig.FARMERS_NEEDS_ENERGY.get(), FarmingConfig.COMPOSTER_USEPERTICK.get())) {
			ItemStack stack = getStackInSlot(itemStorage, 0);
			if (stack.is(ModItemTags.COMPOSTER_ITEMS) && stack.getCount() >= 4) {
				if (getStackInSlot(upgradeItemStorage, 0).getCount() < 64) {
					tick++;
					if (tick >= FarmingConfig.COMPOSTER_TICK.get()) {
						this.tick = 0;
						work();
					}
				} 
			} else if(getStackInSlot(itemStorage, 0).isEmpty()) {
				if(tick > 0) {
					this.tick--;
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void work() {
		if (!level.isClientSide()) {
			ItemStack bonemeal = ModItemHandlerHelp.insertItemStacked(upgradeItemStorage, new ItemStack(ItemInit.COMPOST.get(), 1), 0, 1, false);
			consumeStack(itemStorage, 0, 4);
			consumeEnergy(energyStorage, FarmingConfig.COMPOSTER_USEPERTICK.get());
		}
	}
	
	public int getProgress() {
		return this.tick;
	}
	
	public int getMaxProgress() {
		return FarmingConfig.COMPOSTER_TICK.get();	
	}
	
	@Override
	public boolean isValidBlock(ItemStack stack) {
		return false;
	}
	
	@Override
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return true;
			}
		};
	}

	@Override
	protected ItemStackHandler upgradeItemStorage() {
		return new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return true;
			}
		};
	}
	
	@Override
	protected IItemHandler createCombinedItemHandler() {
		return new CombinedInvWrapper(itemStorage, upgradeItemStorage) {
			@Override
			public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
				int index = getIndexForSlot(slot);
				if (getHandlerFromIndex(index) == upgradeItemStorage) {
					return stack;
				}
				return super.insertItem(slot, stack, simulate);
			}
		};
	}

	@Override
	protected ModEnergyStorage createEnergy() {
		return new ModEnergyStorage(true, FarmingConfig.COMPOSTER_CAPACITY.get(), FarmingConfig.COMPOSTER_USEPERTICK.get()*2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(energyStorage, FarmingConfig.COMPOSTER_USEPERTICK.get());
				if (newHasPower) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
				}
				setChanged();
			}
		};
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			if (side == null) {
				return combinedItemHandler.cast();
			} else if (side == Direction.DOWN) {
				return upgradeItemHandler.cast();
			} else {
				return itemStorageHandler.cast();
			}
		}
		return super.getCapability(cap, side);
	}
}
