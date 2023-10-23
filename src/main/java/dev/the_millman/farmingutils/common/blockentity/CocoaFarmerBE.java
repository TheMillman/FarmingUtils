package dev.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import dev.the_millman.farmingutils.common.blocks.CocoaFarmerBlock;
import dev.the_millman.farmingutils.core.init.BlockEntityInit;
import dev.the_millman.farmingutils.core.tags.ModItemTags;
import dev.the_millman.farmingutils.core.util.FarmingConfig;
import dev.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import dev.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import dev.the_millman.themillmanlib.core.util.LibTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class CocoaFarmerBE extends ItemEnergyBlockEntity {

	private final int UP_SLOT_MIN = 0;
	private final int UP_SLOT_MAX = 2;
	
	private int super_tick = FarmingConfig.COCOA_BEANS_FARMER_TICK.get();
	private int tick;
	int pY;
	
	boolean initialized = false;
	BlockPos facingPos = null;
	boolean needRedstone = false;
	boolean pickupDrops = true;
	
	public CocoaFarmerBE(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.COCOA_BEANS_FARMER.get(), pWorldPosition, pBlockState);
	}

	@Override
	protected void init() {
		initialized = true;
		
		Direction facing = getBlockState().getValue(CocoaFarmerBlock.FACING);	
		if(facing == Direction.NORTH) {
			this.x = getBlockPos().getX();
			this.z = getBlockPos().getZ() - 2;
		} else if(facing == Direction.SOUTH) {
			this.x = getBlockPos().getX();
			this.z = getBlockPos().getZ() + 2;
		} else if(facing == Direction.WEST) {
			this.x = getBlockPos().getX() - 2;
			this.z = getBlockPos().getZ();
		} else if(facing == Direction.EAST) {
			this.x = getBlockPos().getX() + 2;
			this.z = getBlockPos().getZ();
		}
		
		this.y = getBlockPos().getY();
		tick = 0;

		this.pX = 0;
		this.pY = 0;
		this.pZ = 0;
		
		this.range = 1;
		facingPos = getBlockPos();
		
		this.needRedstone = false;
		this.pickupDrops = true;
	}
	
	/**
	 * TODO Upgrade
	 */
	@Override
	public void tickServer() {
		if (!initialized) {
			init();
		}

		if (hasPowerToWork(energyStorage, FarmingConfig.FARMERS_NEEDS_ENERGY.get(), FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get())) {
			tick++;
			if (tick == super_tick) {
				tick = 0;
				this.needRedstone = getUpgrade(LibTags.Items.REDSTONE_UPGRADE);
				super_tick = speedUpgrade(FarmingConfig.COCOA_BEANS_FARMER_TICK.get());
				if (canWork()) {
					upgradeSlot();
					BlockPos posToBreak = new BlockPos(this.x + pX, this.y + pY, this.z + pZ);
					destroyBlock(posToBreak, false);
					placeBlock(posToBreak);
					if (getUpgrade(ModItemTags.SPEED_UPGRADE)) {
						consumeEnergy(energyStorage, FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get()*2);
					} else {
						consumeEnergy(energyStorage, FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get());
					}
					setChanged();

					stadioState();
				}
			}
		}
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

	private void stadioState() {
		Direction facing = getBlockState().getValue(CocoaFarmerBlock.FACING);
		if(facing == Direction.NORTH) {
			pY++;
			if(pY == 5) {
				this.pY = 0;
				pX++;
				if(pX >= this.range) {
					this.pX = 0;
					this.pY = 0;
				}
			}
		} else if(facing == Direction.SOUTH) {
			pY++;
			if(pY == 5) {
				this.pY = 0;
				pX--;
				if(pX <= -this.range) {
					this.pX = 0;
					this.pY = 0;
				}
			}
		} else if(facing == Direction.WEST) {
			pY++;
			if(pY == 5) {
				this.pY = 0;
				pZ--;
				if(pZ <= -this.range) {
					this.pZ = 0;
					this.pY = 0;
				}
			}
		} else if(facing == Direction.EAST) {
			pY++;
			if(pY == 5) {
				this.pY = 0;
				pZ++;
				if(pZ >= this.range) {
					this.pZ = 0;
					this.pY = 0;
				}
			}
		}
	}
	
	private boolean canWork() {
		if(this.needRedstone) {
			if(getBlockState().getValue(CocoaFarmerBlock.POWERED)) {
				return true;
			}
			return false;
		} else if(!this.needRedstone) {
			return true;
		}
		return true;
	}
	
	private void rangeUpgrade() {
		Direction facing = getBlockState().getValue(CocoaFarmerBlock.FACING);
		boolean ironUpgrade = getUpgrade(LibTags.Items.IRON_RANGE_UPGRADE);
		boolean goldUpgrade = getUpgrade(LibTags.Items.GOLD_RANGE_UPGRADE);
		boolean diamondUpgrade = getUpgrade(LibTags.Items.DIAMOND_RANGE_UPGRADE);
		if (ironUpgrade) {
			if (facing == Direction.NORTH) {
				// posizione da cui inizia
				this.x = getBlockPos().getX() - 1;
				// distanza dal farmer
				this.z = getBlockPos().getZ() - 2;
				this.range = 3;
			} else if (facing == Direction.SOUTH) {
				// posizione da cui inizia
				this.x = getBlockPos().getX() + 1;
				// distanza dal farmer
				this.z = getBlockPos().getZ() + 2;
				this.range = 3;
			} else if (facing == Direction.WEST) {
				// distanza dal farmer
				this.x = getBlockPos().getX() - 2;
				// posizione da cui inizia
				this.z = getBlockPos().getZ() + 1;
				this.range = 3;
			} else if (facing == Direction.EAST) {
				// distanza dal farmer
				this.x = getBlockPos().getX() + 2;
				// posizione da cui inizia
				this.z = getBlockPos().getZ() - 1;
				this.range = 3;
			}
		} else if (goldUpgrade) {
			if (facing == Direction.NORTH) {
				// posizione da cui inizia
				this.x = getBlockPos().getX() - 2;
				// distanza dal farmer
				this.z = getBlockPos().getZ() - 2;
				this.range = 5;
			} else if (facing == Direction.SOUTH) {
				// posizione da cui inizia
				this.x = getBlockPos().getX() + 2;
				// distanza dal farmer
				this.z = getBlockPos().getZ() + 2;
				this.range = 5;
			} else if (facing == Direction.WEST) {
				// distanza dal farmer
				this.x = getBlockPos().getX() - 2;
				// posizione da cui inizia
				this.z = getBlockPos().getZ() + 2;
				this.range = 5;
			} else if (facing == Direction.EAST) {
				// distanza dal farmer
				this.x = getBlockPos().getX() + 2;
				// posizione da cui inizia
				this.z = getBlockPos().getZ() - 2;
				this.range = 5;
			}
		} else if (diamondUpgrade) {
			if (facing == Direction.NORTH) {
				// posizione da cui inizia
				this.x = getBlockPos().getX() - 3;
				// distanza dal farmer
				this.z = getBlockPos().getZ() - 2;
				this.range = 7;
			} else if (facing == Direction.SOUTH) {
				// posizione da cui inizia
				this.x = getBlockPos().getX() + 3;
				// distanza dal farmer
				this.z = getBlockPos().getZ() + 2;
				this.range = 7;
			} else if (facing == Direction.WEST) {
				// distanza dal farmer
				this.x = getBlockPos().getX() - 2;
				// posizione da cui inizia
				this.z = getBlockPos().getZ() + 3;
				this.range = 7;
			} else if (facing == Direction.EAST) {
				// distanza dal farmer
				this.x = getBlockPos().getX() + 2;
				// posizione da cui inizia
				this.z = getBlockPos().getZ() - 3;
				this.range = 7;
			}
		} else {
			if (facing == Direction.NORTH) {
				this.x = getBlockPos().getX();
				this.z = getBlockPos().getZ() - 2;
				this.range = 1;
			} else if (facing == Direction.SOUTH) {
				this.x = getBlockPos().getX();
				this.z = getBlockPos().getZ() + 2;
				this.range = 1;
			} else if (facing == Direction.WEST) {
				this.x = getBlockPos().getX() - 2;
				this.z = getBlockPos().getZ();
				this.range = 1;
			} else if (facing == Direction.EAST) {
				this.x = getBlockPos().getX() + 2;
				this.z = getBlockPos().getZ();
				this.range = 1;
			}
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
				} else if (!this.pickupDrops) {
					level.destroyBlock(pos, true);
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	private boolean placeBlock(BlockPos pos) { 
		int slot = getSlot(itemStorage, 8);

		Direction facing = getBlockState().getValue(CocoaFarmerBlock.FACING);
		
		if(facing == Direction.NORTH) {
			facingPos = pos.relative(Direction.NORTH, 1);
		} else if(facing == Direction.SOUTH) {
			facingPos = pos.relative(Direction.SOUTH, 1);
		} else if(facing == Direction.WEST) {
			facingPos = pos.relative(Direction.WEST, 1);
		} else if(facing == Direction.EAST) {
			facingPos = pos.relative(Direction.EAST, 1);
		}
		
		BlockState state = level.getBlockState(pos);
		BlockState facingState = level.getBlockState(facingPos);
		if (state.getBlock() == Blocks.COCOA) {
			return false;
		} else if (facingState.is(Blocks.JUNGLE_LOG)) {
			if (getStackInSlot(itemStorage, slot).getItem()instanceof BlockItem blockItem) {
				if (!level.isClientSide) {
					if (blockItem.asItem() == Items.COCOA_BEANS) {
						Block block = blockItem.getBlock();
						level.setBlock(pos, block.defaultBlockState().setValue(CocoaBlock.FACING, getBlockState().getValue(CocoaFarmerBlock.FACING)), Block.UPDATE_ALL);
						level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1F, 1F);
						consumeStack(itemStorage, slot, 1);
						consumeEnergy(energyStorage, FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get());
					}
				}
			}
		}
		return false;
	}
	
	private boolean getDestBlock(BlockState state) {
		if (state.getBlock() == Blocks.COCOA && state.getValue(BlockStateProperties.AGE_2) == CocoaBlock.MAX_AGE) {
			return true;
		} 
		return false;
	}
	
	@Override
	public boolean isValidBlock(ItemStack stack) {
		return stack.is(Items.COCOA_BEANS) ? true : false;
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
				return isValidBlock(stack);
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
		return new ModEnergyStorage(true, FarmingConfig.COCOA_BEANS_FARMER_CAPACITY.get(), FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(energyStorage, FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get());
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
