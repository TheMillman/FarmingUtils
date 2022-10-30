package com.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.the_millman.farmingutils.common.blocks.InternalFarmerBlock;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.init.ItemInit;
import com.the_millman.farmingutils.core.networking.FluidSyncS2CPacket;
import com.the_millman.farmingutils.core.networking.ItemStackSyncS2CPacket2;
import com.the_millman.farmingutils.core.networking.ModMessages;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyFluidBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import com.the_millman.themillmanlib.core.util.BlockUtils;
import com.the_millman.themillmanlib.core.util.LibTags;
import com.the_millman.themillmanlib.core.util.ModItemHandlerHelp;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class InternalFarmerBE extends ItemEnergyFluidBlockEntity {

	private int tick;
	private int growthStage;
    private int maxGrowthStage = 5;
	boolean initialized = false;
	BlockPos pos;
	
	public InternalFarmerBE(BlockPos pPos, BlockState pBlockState) {
		super(BlockEntityInit.INTERNAL_FARMER.get(), pPos, pBlockState);
	}

	@Override
	protected void init() {
		this.initialized = true;
		tick = 0;
		resetGrowthStage();
		this.needRedstone = false;
		pos = new BlockPos(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
	}

	public void tickServer(InternalFarmerBE pEntity) {
		if (!initialized) {
			init();
		}

		ItemStack bucketStack = itemStorage.getStackInSlot(13);
		if(bucketStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
			transferItemFluidToFluidTank(pEntity, 13, 14);
			setChanged();
		}

		if (hasPowerToWork(FarmingConfig.INTERNAL_FARMER_USEPERTICK.get())) {
			if (getFluidAmount() >= FarmingConfig.INTERNAL_FARMER_FLUID_CONSUMED.get()) {
				tick++;
				if (tick == FarmingConfig.INTERNAL_FARMER_TICK.get()) {
					tick = 0;
					this.needRedstone = getUpgrade(LibTags.Items.REDSTONE_UPGRADE, 9, 11);
					if (canWork()) {
						updateGrowthStage();
						if (growthStage >= maxGrowthStage) {
							farm(pos);
							this.growthStage = 1;
							resetGrowthStage();
							setChanged();
						}
					}
				}
			}
		}
	}
	
	public float getScaledProgress() {
        float standardSize = 1f;
        int progess = this.growthStage;
        int maxProgress = this.maxGrowthStage;

        return maxProgress != 0 && progess != 0 ? progess * standardSize / maxProgress : 0;
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
	
	/**
	 * TODO Controllare quanta energia consuma.
	 * Destroy equivalent in another classes.
	 */
	private boolean farm(BlockPos pos) {
		ItemStack stack = getStackInSlot(12);
		if(isValidItem(stack)) {
			if(!level.isClientSide()) {
				ItemStack copy = ItemHandlerHelper.copyStackWithSize(stack, 2);
				if(!copy.isEmpty()) {
					if(copy.is(Items.KELP)) {
						drain(FarmingConfig.INTERNAL_FARMER_FLUID_CONSUMED.get() * 2, FluidAction.EXECUTE);
						consumeEnergy(FarmingConfig.INTERNAL_FARMER_USEPERTICK.get());
					} else {
						drain(FarmingConfig.INTERNAL_FARMER_FLUID_CONSUMED.get(), FluidAction.EXECUTE);
						consumeEnergy(FarmingConfig.INTERNAL_FARMER_USEPERTICK.get());
					}
					
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
			if (blockItem.getBlock() instanceof IPlantable || stack.is(Items.KELP)) {
				if (stack.is(ItemTags.SMALL_FLOWERS) || stack.is(ItemTags.TALL_FLOWERS) || stack.is(Items.BROWN_MUSHROOM) || stack.is(Items.RED_MUSHROOM) || stack.is(Items.KELP)) {
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
	public ItemStack getRenderStack() {
		return getStackInSlot(12);
	}
	
	public void setHandler(ItemStackHandler itemStackHandler) {
		for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemStorage.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
	}
	
	@Override
	protected boolean isValidBlock(ItemStack stack) {
		return false;
	}
	
	@Override
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(15) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if(!level.isClientSide()) {
					ModMessages.sendToClients(new ItemStackSyncS2CPacket2(this, worldPosition));
				}
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
						if (blockItem.getBlock() instanceof IPlantable || stack.is(Items.KELP)) {
							if (stack.is(ItemTags.SMALL_FLOWERS) || stack.is(ItemTags.TALL_FLOWERS) || stack.is(Items.BROWN_MUSHROOM) || stack.is(Items.RED_MUSHROOM) || stack.is(Items.KELP)) {
								return true;
							}
							return false;
						}
						return false;
					}
					return false;
				} else if(slot == 13) {
					if(stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
						return true;
					}
					return false;
				} else if(slot == 14) {
					if(stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
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
		return new FluidTank(FarmingConfig.INTERNAL_FARMER_FLUID_CAPACITY.get()) {
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
	
	private void resetGrowthStage() {
        this.growthStage = 1;
    }

    private void updateGrowthStage() {
        growthStage++;
    }
    
    public void setGrowthStage(int growthStage) {
        this.growthStage = growthStage;
    }
    
    @Override
    protected void saveAdditional(CompoundTag pTag) {
    	pTag.putInt("growth_stage", growthStage);
    	super.saveAdditional(pTag);
    }
    
    @Override
    public void load(CompoundTag pTag) {
    	super.load(pTag);
    	growthStage = pTag.getInt("growth_stage");
    }
    
    //Makes the InternalFarmerBERenderer rendering when the world is loaded
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        load(pkt.getTag());
    }
    
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }
}
