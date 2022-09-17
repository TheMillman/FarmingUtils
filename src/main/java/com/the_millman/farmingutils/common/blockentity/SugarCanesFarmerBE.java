package com.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nonnull;

import com.the_millman.farmingutils.common.blocks.SugarCanesFarmerBlock;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.init.ItemInit;
import com.the_millman.farmingutils.core.tags.ModItemTags;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class SugarCanesFarmerBE extends ItemEnergyBlockEntity {

	private int tick;
	
	boolean initialized = false;
	boolean needRedstone = false;
	boolean pickupDrops = true;
	
	public SugarCanesFarmerBE(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.SUGAR_CANES_FARMER.get(), pWorldPosition, pBlockState);
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

		if (hasPowerToWork(FarmingConfig.SUGAR_CANES_FARMER_USEPERTICK.get())) {
			tick++;
			if (tick == FarmingConfig.SUGAR_CANES_FARMER_TICK.get()) {
				tick = 0;
				redstoneUpgrade();
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
			if(getBlockState().getValue(SugarCanesFarmerBlock.POWERED)) {
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
		dropUpgrade();
	}
	
	private void redstoneUpgrade() {
		ItemStack upgradeSlot = getStackInSlot(10);
		if (upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = true;
		} else if (!upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = false;
		}
	}
	
	private void dropUpgrade() {
		ItemStack upgradeSlot = getStackInSlot(11);
		if (upgradeSlot.is(ItemInit.DROP_UPGRADE.get())) {
			this.pickupDrops = false;
		} else if (!upgradeSlot.is(ItemInit.DROP_UPGRADE.get())) {
			this.pickupDrops = true;
		}
	}
	
	private void rangeUpgrade() {
		ItemStack upgradeSlot = getStackInSlot(9);
		if (upgradeSlot.is(ItemInit.IRON_UPGRADE.get())) {
			this.x = getBlockPos().getX() - 2;
			this.z = getBlockPos().getZ() - 2;
			this.range = 5;
		} else if (upgradeSlot.is(ItemInit.GOLD_UPGRADE.get())) {
			this.x = getBlockPos().getX() - 3;
			this.z = getBlockPos().getZ() - 3;
			this.range = 7;
		} else if (upgradeSlot.is(ItemInit.DIAMOND_UPGRADE.get())) {
			this.x = getBlockPos().getX() - 4;
			this.z = getBlockPos().getZ() - 4;
			this.range = 9;
		} else if (upgradeSlot.isEmpty()) {
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
					collectDrops(pos, 0, 9);
					level.destroyBlock(pos, dropBlock);
					consumeEnergy(FarmingConfig.SUGAR_CANES_FARMER_USEPERTICK.get());
					return true;
				} else if(!this.pickupDrops) {
					level.destroyBlock(pos, true);
					consumeEnergy(FarmingConfig.SUGAR_CANES_FARMER_USEPERTICK.get());
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
			if(level.getBlockState(pos.above()).is(Blocks.SUGAR_CANE)) {
				destroyBlock(pos.above(), false);
				pos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
				continue;
			}
			break;
		}
	}
	
	private boolean getDestBlock(BlockState state) {
		if (state.getBlock() == Blocks.SUGAR_CANE) {
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
				if (slot <= 8) {
					if (stack.getItem() == Items.SUGAR_CANE) {
						return true;
					}
				} else if (slot > 8) {
					if (isValidUpgrade(stack)) {
						if (slot == 9) {
							if (stack.is(ModItemTags.RANGE_UPGRADES)) {
								return true;
							}
							return false;
						} else if (slot == 10) {
							if (stack.is(ItemInit.REDSTONE_UPGRADE.get())) {
								return true;
							}
							return false;
						} else if (slot == 11) {
							if (stack.is(ItemInit.DROP_UPGRADE.get())) {
								return true;
							}
							return false;
						}
					}
				}
				return false;
			}
		};
	}

	@Override
	protected ModEnergyStorage createEnergy() {
		return new ModEnergyStorage(true, FarmingConfig.SUGAR_CANES_FARMER_CAPACITY.get(), FarmingConfig.SUGAR_CANES_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(FarmingConfig.SUGAR_CANES_FARMER_USEPERTICK.get());
				if (newHasPower) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
				}
				setChanged();
			}
		};
	}

}
