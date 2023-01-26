package com.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nonnull;

import com.the_millman.farmingutils.common.blocks.CropFarmerBlock;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.tags.ModBlockTags;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import com.the_millman.themillmanlib.core.util.LibTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class CropFarmerBE extends ItemEnergyBlockEntity {
	
	private final int UP_SLOT_MIN = 0;
	private final int UP_SLOT_MAX = 2;
	
	private int tick;
	
	boolean initialized = false;
	boolean needRedstone = false;
	boolean pickupDrops = true;
			
	public CropFarmerBE(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.CROP_FARMER.get(), pWorldPosition, pBlockState);
	}

	@Override
	protected void init() {
		initialized = true;
		this.x = getBlockPos().getX() - 1;
		this.y = getBlockPos().getY();
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

		if (hasPowerToWork(energyStorage, FarmingConfig.FARMERS_NEEDS_ENERGY.get(), FarmingConfig.CROP_FARMER_USEPERTICK.get())) {
			tick++;
			if (tick == FarmingConfig.CROP_FARMER_TICK.get()) {
				tick = 0;
				this.needRedstone = getUpgrade(upgradeItemStorage, LibTags.Items.REDSTONE_UPGRADE, UP_SLOT_MIN, UP_SLOT_MAX);
				if (canWork()) {
					upgradeSlot();
					BlockPos posToBreak = new BlockPos(this.x + this.pX, this.y, this.z + this.pZ);
					destroyBlock(posToBreak, false);
					placeBlock(posToBreak);
					setChanged();

					posState();
				}
			}
		}
	}
	
	private boolean canWork() {
		if(this.needRedstone) {
			if(getBlockState().getValue(CropFarmerBlock.POWERED)) {
				return true;
			}
			return false;
		} else if(!this.needRedstone) {
			return true;
		}
		return true;
	}

	private void upgradeSlot() {
		rangeSlot();
		this.pickupDrops = !getUpgrade(upgradeItemStorage, LibTags.Items.DROP_UPGRADE, UP_SLOT_MIN, UP_SLOT_MAX);
	}
	
	private void rangeSlot() {
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
			//Sostituito getDestBlock(state)
		} else if (testBlock(state)) {
			if (!level.isClientSide) {
				if (this.pickupDrops) {
					collectDrops(level, itemStorage, pos, 0, 18);
					level.destroyBlock(pos, dropBlock);
					consumeEnergy(energyStorage, FarmingConfig.CROP_FARMER_USEPERTICK.get());
					return true;
				} else if(!this.pickupDrops) {
					// collectDrops(pos, dropBlock);
					level.destroyBlock(pos, true);
					consumeEnergy(energyStorage, FarmingConfig.CROP_FARMER_USEPERTICK.get());
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	private boolean placeBlock(BlockPos pos) {
		int slot = getSlot(itemStorage, 17);

		BlockPos posY = pos.below();
		BlockState state = level.getBlockState(pos);
		BlockState yState = level.getBlockState(posY);
		if (state.getBlock() instanceof CropBlock) {
			return false;
		} else if (yState.getBlock() instanceof FarmBlock) {
			if (isValidBlock(getStackInSlot(itemStorage, slot))) {
				if (getStackInSlot(itemStorage, slot).getItem() instanceof BlockItem blockItem) {
					if (!level.isClientSide) {
						Block block = blockItem.getBlock();
						level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL);
						level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1F, 1F);
						consumeStack(itemStorage, slot, 1);
						consumeEnergy(energyStorage, FarmingConfig.CROP_FARMER_USEPERTICK.get());
					}
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private boolean getDestBlock(BlockState state) {
		if (state.is(ModBlockTags.AGE_3_CROPS)
				&& state.getValue(BlockStateProperties.AGE_3) == BeetrootBlock.MAX_AGE) {
			return true;
		} else if (!state.is(ModBlockTags.AGE_3_CROPS) && state.getBlock() instanceof IPlantable
				&& state.getBlock() instanceof CropBlock) {
			if (state.getValue(CropBlock.AGE) == CropBlock.MAX_AGE) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * TODO BOY, WE MUST BE BETTER!.
	 * @param state
	 * @return
	 */
	private boolean testBlock(BlockState state) {
		if (state.getBlock() instanceof IPlantable) {
			IntegerProperty AGE_3 = BlockStateProperties.AGE_3;
			if (state.hasProperty(AGE_3)) {
				if (state.getValue(AGE_3) == BeetrootBlock.MAX_AGE) {
					return true;
				}
			}

			IntegerProperty AGE_7 = BlockStateProperties.AGE_7;
			if (state.hasProperty(AGE_7)) {
				if (state.getValue(AGE_7) == CropBlock.MAX_AGE) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isValidBlock(ItemStack stack) {
		if (stack.getItem()instanceof ItemNameBlockItem blockItem) {
			if (blockItem.getBlock() instanceof CropBlock) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	@Override
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(18) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return !isValidUpgrade(stack) ? true : false;
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
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
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
		return new ModEnergyStorage(true, FarmingConfig.CROP_FARMER_CAPACITY.get(), FarmingConfig.CROP_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(energyStorage, FarmingConfig.CROP_FARMER_USEPERTICK.get());
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
