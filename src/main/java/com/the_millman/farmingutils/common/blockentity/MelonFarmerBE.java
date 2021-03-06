package com.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nonnull;

import com.the_millman.farmingutils.common.blocks.MelonFarmerBlock;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.init.ItemInit;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MelonBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class MelonFarmerBE extends ItemEnergyBlockEntity {

	private int x, y, z, tick;
	int pX;
	int pZ;
	boolean initialized = false;
	int range = 5;
	boolean needRedstone = false;
	boolean pickupDrops = true;

	public MelonFarmerBE(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.MELON_FARMER.get(), pWorldPosition, pBlockState);
	}

	@Override
	protected void init() {
		initialized = true;
		this.x = getBlockPos().getX() - 2;
		this.y = getBlockPos().getY();
		this.z = getBlockPos().getZ() - 2;
		this.range = 5;
		tick = 0;

		this.pX = 0;
		this.pZ = 0;
		this.needRedstone = false;
		this.pickupDrops = true;
	}

	@Override
	public void tickServer() {
		if (!initialized) {
			init();
		}

		if (hasPowerToWork(FarmingConfig.MELON_FARMER_USEPERTICK.get())) {
			tick++;
			if (tick == FarmingConfig.MELON_FARMER_TICK.get()) {
				tick = 0;
				redstoneUpgrade();
				if (canWork()) {
					dropUpgrade();
					BlockPos posToBreak = new BlockPos(this.x + this.pX, this.y, this.z + this.pZ);
					destroyBlock(posToBreak, false);
					setChanged();
					pX++;
					if (pX >= this.range) {
						this.pX = 0;
						this.pZ++;
						if (this.pZ >= this.range) {
							this.pX = 0;
							this.pZ = 0;
						}
					}
				}
			}
		}
	}
	
	private boolean canWork() {
		if(this.needRedstone) {
			if(getBlockState().getValue(MelonFarmerBlock.POWERED)) {
				return true;
			}
			return false;
		} else if(!this.needRedstone) {
			return true;
		}
		return true;
	}
	
	private void redstoneUpgrade() {
		ItemStack upgradeSlot = itemStorage.getStackInSlot(9);
		if (upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = true;
		} else if (!upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = false;
		}
	}
	
	private void dropUpgrade() {
		ItemStack upgradeSlot = itemStorage.getStackInSlot(10);
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
					energyStorage.consumeEnergy(FarmingConfig.MELON_FARMER_USEPERTICK.get());
					return true;
				} else if (!this.pickupDrops) {
					level.destroyBlock(pos, true);
					energyStorage.consumeEnergy(FarmingConfig.MELON_FARMER_USEPERTICK.get());
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	private boolean getDestBlock(BlockState state) {
		if(state.getBlock() instanceof PumpkinBlock || state.getBlock() instanceof MelonBlock) {
			return true;
		}
		return false;
	}

	@Override
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(11) {
			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				if(slot > 8) {
					if(slot == 9) {
						if(stack.is(ItemInit.REDSTONE_UPGRADE.get())) {
							return true;
						}
						return false;
					} else if(slot == 10) {
						if(stack.is(ItemInit.DROP_UPGRADE.get())) {
							return true;
						}
						return false;
					}
				}
				if(slot <= 8) {
					if(isValidUpgrade(stack)) {
						return false;
					}
				}
				return true;
			}
		};
	}
	
	@Override
	protected ModEnergyStorage createEnergy() {
		return new ModEnergyStorage(true, FarmingConfig.MELON_FARMER_CAPACITY.get(), FarmingConfig.MELON_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(FarmingConfig.MELON_FARMER_USEPERTICK.get());
				if (newHasPower) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
				}
				setChanged();
			}
		};
	}
}
