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
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.items.ItemStackHandler;

public class CropFarmerBE extends ItemEnergyBlockEntity {
	
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

		if (hasPowerToWork(FarmingConfig.FARMERS_NEEDS_ENERGY.get(), FarmingConfig.CROP_FARMER_USEPERTICK.get())) {
			tick++;
			if (tick == FarmingConfig.CROP_FARMER_TICK.get()) {
				tick = 0;
				this.needRedstone = getUpgrade(LibTags.Items.REDSTONE_UPGRADE, 18, 20);
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
		this.pickupDrops = !getUpgrade(LibTags.Items.DROP_UPGRADE, 18, 20);
	}
	
	private void rangeSlot() {
		boolean ironUpgrade = getUpgrade(LibTags.Items.IRON_RANGE_UPGRADE, 18, 20);
		boolean goldUpgrade = getUpgrade(LibTags.Items.GOLD_RANGE_UPGRADE, 18, 20);
		boolean diamondUpgrade = getUpgrade(LibTags.Items.DIAMOND_RANGE_UPGRADE, 18, 20);
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
		} else if (getDestBlock(state)) {
			if (!level.isClientSide) {
				if (this.pickupDrops) {
					collectDrops(pos, 0, 18);
					level.destroyBlock(pos, dropBlock);
					consumeEnergy(FarmingConfig.CROP_FARMER_USEPERTICK.get());
					return true;
				} else if(!this.pickupDrops) {
					// collectDrops(pos, dropBlock);
					level.destroyBlock(pos, true);
					consumeEnergy(FarmingConfig.CROP_FARMER_USEPERTICK.get());
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	private boolean placeBlock(BlockPos pos) {
		int slot = getSlot(18);

		BlockPos posY = pos.below();
		BlockState state = level.getBlockState(pos);
		BlockState yState = level.getBlockState(posY);
		if (state.getBlock() instanceof CropBlock) {
			return false;
		} else if (yState.getBlock() instanceof FarmBlock) {
			if (isValidBlock(getStackInSlot(slot))) {
				if (getStackInSlot(slot).getItem() instanceof BlockItem blockItem) {
					if (!level.isClientSide) {
						Block block = blockItem.getBlock();
						level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL);
						level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1F, 1F);
						consumeStack(slot, 1);
						consumeEnergy(FarmingConfig.CROP_FARMER_USEPERTICK.get());
					}
				}
			}
		}
		return false;
	}
	
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
	
	@Override
	protected boolean isValidBlock(ItemStack stack) {
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
		return new ItemStackHandler(21) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				if (slot < 18) {
					if (isValidUpgrade(stack)) {
						return false;
					}
					return true;
				} else if (slot >= 18 && isValidUpgrade(stack)) {
					return true;
				}
				return false;
			}
		};
	}

	protected ModEnergyStorage createEnergy() {
		return new ModEnergyStorage(true, FarmingConfig.CROP_FARMER_CAPACITY.get(), FarmingConfig.CROP_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(FarmingConfig.CROP_FARMER_USEPERTICK.get());
				if (newHasPower) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
				}
				setChanged();
			}
		};
	}
}
