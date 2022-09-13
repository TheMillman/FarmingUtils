package com.the_millman.farmingutils.common.blockentity;

import org.jetbrains.annotations.NotNull;

import com.the_millman.farmingutils.common.blocks.InternalFarmerBlock;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.init.ItemInit;
import com.the_millman.farmingutils.core.networking.FluidSyncS2CPacket;
import com.the_millman.farmingutils.core.networking.ModMessages;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyFluidBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import com.the_millman.themillmanlib.core.util.BlockUtils;
import com.the_millman.themillmanlib.core.util.ModItemHandlerHelp;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class InternalFarmerBE extends ItemEnergyFluidBlockEntity {

	private int tick;
	public int growthStage;
	boolean initialized = false;
	boolean needRedstone = false;
	BlockPos pos;
	
	public InternalFarmerBE(BlockPos pPos, BlockState pBlockState) {
		super(BlockEntityInit.INTERNAL_FARMER.get(), pPos, pBlockState);
	}

	@Override
	protected void init() {
		this.initialized = true;
		tick = 0;
		growthStage = 0;
		this.needRedstone = false;
		pos = new BlockPos(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
	}

	/**
	 * TODO Sostituire 100 con config
	 */
	public void tickServer(InternalFarmerBE pEntity) {
		if (!initialized) {
			init();
		}

		if (itemStorage.getStackInSlot(13).getItem() == Items.WATER_BUCKET) {
			transferItemFluidToFluidTank(pEntity);
			setChanged();
		}

		if (hasPowerToWork(FarmingConfig.INTERNAL_FARMER_USEPERTICK.get())) {
			if (fluidStorage.getFluidAmount() >= 100) {
				tick++;
				if (tick == FarmingConfig.INTERNAL_FARMER_TICK.get()) {
					tick = 0;
					if (canWork()) {
						growthStage++;
						if (growthStage >= 3) {
							growthStage = 0;
							redstoneUpgrade();
							farm(pos);
							setChanged();
						}
					}
				}
			}
		}
	}
	
	private static void transferItemFluidToFluidTank(InternalFarmerBE pEntity) {
        pEntity.itemStorage.getStackInSlot(13).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler -> {
            int drainAmount = Math.min(pEntity.fluidStorage.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(pEntity.fluidStorage.isFluidValid(stack)) {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(pEntity, stack, handler.getContainer());
            }
        });
    }

	private static void fillTankWithFluid(InternalFarmerBE pEntity, FluidStack stack, ItemStack container) {
        pEntity.fluidStorage.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemStorage.extractItem(13, 1, false);
        pEntity.itemStorage.insertItem(14, container, false);
    }
	
	private boolean canWork() {
		if(this.needRedstone) {
			if(getBlockState().getValue(InternalFarmerBlock.POWERED)) {
				return true;
			}
			return false;
		} else if(!this.needRedstone) {
			return true;
		}
		return true;
	}
	
	private void redstoneUpgrade() {
		ItemStack upgradeSlot = itemStorage.getStackInSlot(10);
		if (upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = true;
		} else if (!upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = false;
		}
	}
	
	/**
	 * Destroy equivalent in another classes.
	 */
	private boolean farm(BlockPos pos) {
		ItemStack stack = itemStorage.getStackInSlot(12);
		if(isValidItem(stack)) {
			fluidStorage.drain(100, FluidAction.EXECUTE);
			if(!level.isClientSide) {
				ItemStack copy = ItemHandlerHelper.copyStackWithSize(stack, 2);
				if(!copy.isEmpty()) {
					ItemStack result = ModItemHandlerHelp.insertItemStacked(itemStorage, copy, 0, 9, false);
					if(!result.isEmpty()) {
						BlockUtils.spawnItemStack(copy, this.level, pos);
					}
					setChanged();
				}
				setChanged();
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidItem(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof BlockItem blockItem) {
			if (blockItem.getBlock() instanceof IPlantable) {
				if (stack.is(ItemTags.SMALL_FLOWERS) || stack.is(ItemTags.TALL_FLOWERS) || stack.is(Items.BROWN_MUSHROOM) || stack.is(Items.RED_MUSHROOM)) {
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
	@Override
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(15) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				if(slot >=0 && slot <= 8) {
					return true;
				} else if(slot >= 9 && slot <= 11) {
					if(stack.is(ItemInit.REDSTONE_UPGRADE.get())) {
						return true;
					}
					return false;
				} else if(slot == 12) {
					Item item = stack.getItem();
					if (item instanceof BlockItem blockItem) {
						if (blockItem.getBlock() instanceof IPlantable) {
							if (stack.is(ItemTags.SMALL_FLOWERS) || stack.is(ItemTags.TALL_FLOWERS) || stack.is(Items.BROWN_MUSHROOM) || stack.is(Items.RED_MUSHROOM)) {
								return true;
							}
							return false;
						}
						return false;
					}
					return false;
				} else if(slot == 13) {
					if(stack.is(Items.WATER_BUCKET)) {
						return true;
					}
					return false;
				} else if(slot == 14) {
					if(stack.is(Items.BUCKET)) {
						return true;
					}
					return false;
				}
				return false;
			}
		};
	}
	
	@Override
	protected ModEnergyStorage createEnergy() {
		return new ModEnergyStorage(true, FarmingConfig.INTERNAL_FARMER_CAPACITY.get(), FarmingConfig.INTERNAL_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(FarmingConfig.INTERNAL_FARMER_USEPERTICK.get());
				if (newHasPower) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
				}
				setChanged();
			}
		};
	}
	
	@Override
	protected FluidTank fluidStorage() {
		return new FluidTank(10000) {
			@Override
			protected void onContentsChanged() {
				setChanged();
				if(!level.isClientSide()) {
	                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
	            }
			}
			
			@Override
			public boolean isFluidValid(FluidStack stack) {
				return stack.getFluid() == Fluids.WATER;
			}
		};
	}
}
