package dev.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import dev.the_millman.farmingutils.common.blocks.BambooFarmerBlock;
import dev.the_millman.farmingutils.core.init.BlockEntityInit;
import dev.the_millman.farmingutils.core.tags.ModItemTags;
import dev.the_millman.farmingutils.core.util.FarmingConfig;
import dev.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import dev.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import dev.the_millman.themillmanlib.core.util.LibTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
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

public class BambooFarmerBE extends ItemEnergyBlockEntity {

	private final int UP_SLOT_MIN = 0;
	private final int UP_SLOT_MAX = 2;
	
	private int super_tick = FarmingConfig.BAMBOO_FARMER_TICK.get();
	private int tick;
	
	boolean initialized = false;
	boolean needRedstone = false;
	boolean pickupDrops = true;
	
	public BambooFarmerBE(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.BAMBOO_FARMER.get(), pWorldPosition, pBlockState);
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

		if (hasPowerToWork(energyStorage, FarmingConfig.FARMERS_NEEDS_ENERGY.get(), FarmingConfig.BAMBOO_FARMER_USEPERTICK.get())) {
			tick++;
			if (tick == super_tick) {
				tick = 0;
				this.needRedstone = getUpgrade(LibTags.Items.REDSTONE_UPGRADE);
				super_tick = speedUpgrade(FarmingConfig.BAMBOO_FARMER_TICK.get());
				if (canWork()) {
					upgradeSlot();
					BlockPos posToBreak = new BlockPos(this.x + this.pX, this.y, this.z + this.pZ);
					destroyUpperBlock(posToBreak);
					destroyBlock(posToBreak, false);
					if (getUpgrade(ModItemTags.SPEED_UPGRADE)) {
						consumeEnergy(energyStorage, FarmingConfig.BAMBOO_FARMER_USEPERTICK.get()*2);
					} else {
						consumeEnergy(energyStorage, FarmingConfig.BAMBOO_FARMER_USEPERTICK.get());
					}
					setChanged();

					posState();
				}
			}
		}
	}
	
	private boolean canWork() {
		if(this.needRedstone) {
			if(getBlockState().getValue(BambooFarmerBlock.POWERED)) {
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
		this.pickupDrops = !getUpgrade(LibTags.Items.DROP_UPGRADE);
	}
	
	private int speedUpgrade(int tick) {
		if (getUpgrade(ModItemTags.SPEED_UPGRADE)) {
			return tick / 2;
		} else {
			return tick;
		}
	}
	
	private void rangeUpgrade() {
		boolean ironUpgrade = getUpgrade(LibTags.Items.IRON_RANGE_UPGRADE);
		boolean goldUpgrade = getUpgrade(LibTags.Items.GOLD_RANGE_UPGRADE);
		boolean diamondUpgrade = getUpgrade(LibTags.Items.DIAMOND_RANGE_UPGRADE);
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
					return true;
				} else if(!this.pickupDrops) {
					level.destroyBlock(pos, true);
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
	private boolean getDestBlock(BlockState state) {
		if (state.getBlock() == Blocks.BAMBOO) {
			return true;
		} 
		return false;
	}
	
	private void destroyUpperBlock(BlockPos pos) {
		for(level.getBlockState(pos.above());;) {
			if(level.getBlockState(pos.above()).is(Blocks.BAMBOO)) {
				destroyBlock(pos.above(), false);
				pos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
				continue;
			}
			break;
		}
	}
	
	@Override
	public boolean isValidBlock(ItemStack stack) {
		return false;
	}
	
	public boolean getUpgrade(TagKey<Item> upgrade) {
		return getUpgrade(upgradeItemStorage, upgrade, UP_SLOT_MIN, UP_SLOT_MAX);
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
				return stack.is(Items.BAMBOO) ? true : false;
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
				if (isValidUpgrade(stack)) {
					return true;
				}
				return false;
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
		return new ModEnergyStorage(true, FarmingConfig.BAMBOO_FARMER_CAPACITY.get(), FarmingConfig.BAMBOO_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(energyStorage, FarmingConfig.BAMBOO_FARMER_USEPERTICK.get());
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
