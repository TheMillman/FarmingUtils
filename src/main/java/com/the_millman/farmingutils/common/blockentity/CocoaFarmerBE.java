package com.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nonnull;

import com.the_millman.farmingutils.common.blocks.CocoaFarmerBlock;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.init.ItemInit;
import com.the_millman.farmingutils.core.tags.ModItemTags;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.ItemStackHandler;

public class CocoaFarmerBE extends ItemEnergyBlockEntity {

	private int x, y, z, tick;
	int pX, pY, pZ;
	boolean initialized = false;
	int range;
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

		if (hasPowerToWork(FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get())) {
			tick++;
			if (tick == FarmingConfig.COCOA_BEANS_FARMER_TICK.get()) {
				tick = 0;
				redstoneUpgrade();
				if (canWork()) {
					upgradeSlot();
					BlockPos posToBreak = new BlockPos(this.x + pX, this.y + pY, this.z + pZ);
					destroyBlock(posToBreak, false);
					placeBlock(posToBreak);
					setChanged();

					stadioState();
				}
			}
		}
	}
	
	private void upgradeSlot() {
		rangeUpgrade();
		dropUpgrade();
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
		ItemStack upgradeSlot = itemStorage.getStackInSlot(9);
		Direction facing = getBlockState().getValue(CocoaFarmerBlock.FACING);
		if (upgradeSlot.is(ItemInit.IRON_UPGRADE.get())) {
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
		} else if (upgradeSlot.is(ItemInit.GOLD_UPGRADE.get())) {
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
		} else if (upgradeSlot.is(ItemInit.DIAMOND_UPGRADE.get())) {
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
		} else if (upgradeSlot.isEmpty()) {
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
	
	private void redstoneUpgrade() {
		ItemStack upgradeSlot = itemStorage.getStackInSlot(10);
		if (upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = true;
		} else if (!upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = false;
		}
	}
	
	private void dropUpgrade() {
		ItemStack upgradeSlot = itemStorage.getStackInSlot(11);
		if (upgradeSlot.is(ItemInit.DROP_UPGRADE.get())) {
			this.pickupDrops = false;
		} else if (!upgradeSlot.is(ItemInit.DROP_UPGRADE.get())) {
			this.pickupDrops = true;
		}
	}
	
	private boolean destroyBlock(BlockPos pos, boolean dropBlock) {
		BlockState state = level.getBlockState(pos);
		if (state.isAir()) {
			return false;
		} else if (getDestBlock(state)) {
			if (!level.isClientSide) {
				if (this.pickupDrops) {
					collectDrops(pos, 0, 9);
					level.destroyBlock(pos, dropBlock);
					energyStorage.consumeEnergy(FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get());
					return true;
				} else if (!this.pickupDrops) {
					level.destroyBlock(pos, true);
					energyStorage.consumeEnergy(FarmingConfig.CROP_FARMER_USEPERTICK.get());
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	private boolean placeBlock(BlockPos pos) { 
		int slot = 0;
		for (int i = 0; i < 9; i++) {
			if (itemStorage.getStackInSlot(i).isEmpty())
				continue;

			if (!itemStorage.getStackInSlot(i).isEmpty()) {
				slot = i;
				break;
			}
		}

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
			if (itemStorage.getStackInSlot(slot).getItem()instanceof BlockItem blockItem) {
				if (!level.isClientSide) {
					if (blockItem.asItem() == Items.COCOA_BEANS) {
						Block block = blockItem.getBlock();
						level.setBlock(pos, block.defaultBlockState().setValue(CocoaBlock.FACING, getBlockState().getValue(CocoaFarmerBlock.FACING)), Block.UPDATE_ALL);
						level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1F, 1F);
						ItemStack stack = itemStorage.getStackInSlot(slot);
						stack.shrink(1);
						energyStorage.consumeEnergy(FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get());
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
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(12) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				if (slot < 9) {
					if(stack.getItem() == Items.COCOA_BEANS) {
						return true;
					}
				} else if (slot >= 9) {
					if(isValidUpgrade(stack)) {
						if(slot == 9) {
							if(stack.is(ModItemTags.RANGE_UPGRADES)) {
								return true;
							}
							return false;
						} else if(slot == 10) {
							if(stack.is(ItemInit.REDSTONE_UPGRADE.get())) {
								return true;
							}
							return false;
						} else if(stack.is(ItemInit.DROP_UPGRADE.get())) {
							return true;
						}
						return false;
					}
				} 
				return false;
			}
		};
	}
	
	@Override
	protected ModEnergyStorage createEnergy() {
		return new ModEnergyStorage(true, FarmingConfig.COCOA_BEANS_FARMER_CAPACITY.get(), FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(FarmingConfig.COCOA_BEANS_FARMER_USEPERTICK.get());
				if (newHasPower) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
				}
				setChanged();
			}
		};
	}

}
