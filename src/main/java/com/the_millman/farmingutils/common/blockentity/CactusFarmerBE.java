package com.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import com.the_millman.farmingutils.common.blocks.CactusFarmerBlock;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import com.the_millman.themillmanlib.core.util.LibTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class CactusFarmerBE extends ItemEnergyBlockEntity {
	
	private final int UP_SLOT_MIN = 0;
	private final int UP_SLOT_MAX = 2;
	
	private int tick;

	boolean initialized = false;
	boolean needRedstone = false;
	boolean pickupDrops = true;
	
	public CactusFarmerBE(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.CACTUS_FARMER.get(), pWorldPosition, pBlockState);
	}

	@Override
	protected void init() {
		initialized = true;
		this.x = getBlockPos().getX() - 1;
		this.y = getBlockPos().getY() + 1;
		this.z = getBlockPos().getZ() - 1;
		tick = 0;

		this.pX = 0;
		this.pZ = 0;
		this.range = 3;
		this.needRedstone = false;
		this.pickupDrops = true;
	}

	@Override
	public void tickServer() {
		if (!initialized) {
			init();
		}

		if (hasPowerToWork(energyStorage, FarmingConfig.FARMERS_NEEDS_ENERGY.get(), FarmingConfig.CACTUS_FARMER_USEPERTICK.get())) {
			tick++;
			if (tick == FarmingConfig.CACTUS_FARMER_TICK.get()) {
				tick = 0;
				this.needRedstone = this.getUpgrade(upgradeItemStorage, LibTags.Items.REDSTONE_UPGRADE, UP_SLOT_MIN, UP_SLOT_MAX);
				if (canWork()) {
					upgradeSlot();
					BlockPos posToBreak = new BlockPos(this.x + this.pX, this.y, this.z + this.pZ);
					destroyUpperBlock(posToBreak);
					destroyBlock(posToBreak, false);
					setChanged();

					posState();
				}
			}
		}
	}
	
	private boolean canWork() {
		if(this.needRedstone) {
			if(getBlockState().getValue(CactusFarmerBlock.POWERED)) {
				return true;
			}
			return false;
		} else if(!this.needRedstone) {
			return true;
		}
		return true;
	}
	
	private void upgradeSlot() {
		rangeUpgrade();
		this.pickupDrops = !getUpgrade(upgradeItemStorage, LibTags.Items.DROP_UPGRADE, UP_SLOT_MIN, UP_SLOT_MAX);
	}
	
	private void rangeUpgrade() {
		boolean ironUpgrade = getUpgrade(upgradeItemStorage, LibTags.Items.IRON_RANGE_UPGRADE, UP_SLOT_MIN, UP_SLOT_MAX);
		boolean goldUpgrade = getUpgrade(upgradeItemStorage, LibTags.Items.GOLD_RANGE_UPGRADE, UP_SLOT_MIN, UP_SLOT_MAX);
		boolean diamondUpgrade = getUpgrade(upgradeItemStorage, LibTags.Items.DIAMOND_RANGE_UPGRADE, UP_SLOT_MIN, UP_SLOT_MAX);
		if (ironUpgrade) {
			this.x = getBlockPos().getX() - 2;
			this.z = getBlockPos().getZ() - 2;
			this.range = 5;
		} else if (goldUpgrade) {
			this.x = getBlockPos().getX() - 3;
			this.z = getBlockPos().getZ() - 3;
			this.range = 7;
		} else if (diamondUpgrade) {
			this.x = getBlockPos().getX() - 4;
			this.z = getBlockPos().getZ() - 4;
			this.range = 9;
		} else {
			this.x = getBlockPos().getX() - 1;
			this.z = getBlockPos().getZ() - 1;
			this.range = 3;
		}
	}
	
	private boolean destroyBlock(BlockPos pos, boolean dropBlock) {
		BlockState state = level.getBlockState(pos);
		if (state.isAir()) {
			return false;
		} else if (getDestBlock(state)) {
			if (!level.isClientSide) {
				if (this.pickupDrops) {
					collectDrops(level, itemStorage, pos, 0, 9);
					level.destroyBlock(pos, dropBlock);
					consumeEnergy(energyStorage, FarmingConfig.CACTUS_FARMER_USEPERTICK.get());
					return true;
				} else if(!this.pickupDrops) {
					level.destroyBlock(pos, true);
					consumeEnergy(energyStorage, FarmingConfig.CACTUS_FARMER_USEPERTICK.get());
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
	private void destroyUpperBlock(BlockPos pos) {
		for(level.getBlockState(pos.above());;) {
			if(level.getBlockState(pos.above()).is(Blocks.CACTUS)) {
				destroyBlock(pos.above(), false);
				pos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
				continue;
			}
			break;
		}
	}
	
	private boolean getDestBlock(BlockState state) {
		if (state.getBlock() == Blocks.CACTUS) {
			return true;
		} 
		return false;
	}
	
	@Override
	public boolean isValidBlock(ItemStack stack) {
		return false;
	}
	
	@Override
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(9) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return stack.is(Items.CACTUS) ? true : false;
			}
		};
	}
	
	@Override
	protected ItemStackHandler upgradeItemStorage() {
		return new ItemStackHandler(3) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return isValidUpgrade(stack) ? true : false;
			}
		};
	}
	
	@Override
	protected IItemHandler createCombinedItemHandler() {
		return new CombinedInvWrapper(itemStorage, upgradeItemStorage) {
			
		};
	}

	@Override
	protected ModEnergyStorage createEnergy() {
		return new ModEnergyStorage(true, FarmingConfig.CACTUS_FARMER_CAPACITY.get(), FarmingConfig.CACTUS_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(energyStorage, FarmingConfig.CACTUS_FARMER_USEPERTICK.get());
				if (newHasPower) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
				}
				setChanged();
			}
		};
	}
	
	@Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    	if(cap == ForgeCapabilities.ITEM_HANDLER) {
			if (side == null) {
            	upgradeItemHandler.cast();
                return combinedItemHandler.cast();
            } else {
                return itemStorageHandler.cast();
            }
        }
    	return super.getCapability(cap, side);
    }
}
