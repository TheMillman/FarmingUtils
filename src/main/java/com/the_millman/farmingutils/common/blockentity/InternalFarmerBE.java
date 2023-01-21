package com.the_millman.farmingutils.common.blockentity;

import java.util.Optional;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.the_millman.farmingutils.common.blocks.InternalFarmerBlock;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.networking.FluidSyncS2CPacket;
import com.the_millman.farmingutils.core.networking.ItemStackSyncS2CPacket2;
import com.the_millman.farmingutils.core.networking.ModMessages;
import com.the_millman.farmingutils.core.recipes.InternalFarmerRecipe;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyFluidBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import com.the_millman.themillmanlib.core.util.BlockUtils;
import com.the_millman.themillmanlib.core.util.LibTags;
import com.the_millman.themillmanlib.core.util.ModItemHandlerHelp;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

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
		if(!initialized)
			init();
		
		if(getStackInSlot(itemStorage, 11).getCount() < 16) {
			transferItemFluidToFluidTank(itemStorage, fluidStorage, 10, 11);
			setChanged();
		}
		
		if(hasPowerToWork(energyStorage, FarmingConfig.INTERNAL_FARMER_USEPERTICK.get())) {
			if (getFluidAmount(fluidStorage) >= 100) {
				tick++;
				if(tick >= FarmingConfig.INTERNAL_FARMER_TICK.get()) {
					tick = 0;
					this.needRedstone = getUpgrade(upgradeItemStorage, LibTags.Items.REDSTONE_UPGRADE, 0, 2);
					if (canWork()) {
						updateGrowthStage();
						if (growthStage >= maxGrowthStage) {
							resetGrowthStage();
							craftItem(pEntity);
							setChanged();
						}
					}
				}
			}
		}
	}
	
	private void craftItem(InternalFarmerBE pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = setInventory();

        Optional<InternalFarmerRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(InternalFarmerRecipe.Type.INSTANCE, inventory, level);
        
        if(hasRecipe(pEntity)) {
        	if (!level.isClientSide()) {
	            ItemStack recipeOutput = new ItemStack(recipe.get().getResultItem().getItem(), recipe.get().getResultItem().getCount());
	            drain(fluidStorage, recipe.get().getFluidStack().getAmount(), FluidAction.EXECUTE);
	            consumeEnergy(energyStorage, FarmingConfig.INTERNAL_FARMER_USEPERTICK.get());
	            ItemStack result = ModItemHandlerHelp.insertItemStacked(pEntity.itemStorage, recipeOutput, 0, 9, false);
	            
	            if (!result.isEmpty()) {
					BlockUtils.spawnItemStack(result, level, pEntity.pos.above());
				}
        	}
        }
        pEntity.resetGrowthStage();
    }
	
	private boolean hasRecipe(InternalFarmerBE blockEntity) {
		SimpleContainer inventory = setInventory();
		
		Optional<InternalFarmerRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(InternalFarmerRecipe.Type.INSTANCE, inventory, level);
		
		return recipe.isPresent() && hasCorrectFluidInTank(blockEntity, recipe) && hasCorrectFluidAmountInTank(blockEntity, recipe);
	}
	
	private boolean hasCorrectFluidAmountInTank(InternalFarmerBE entity, Optional<InternalFarmerRecipe> recipe) {
        return getFluidAmount(fluidStorage) >= recipe.get().getFluidStack().getAmount();
    }

    private boolean hasCorrectFluidInTank(InternalFarmerBE entity, Optional<InternalFarmerRecipe> recipe) {
        return recipe.get().getFluidStack().equals(getFluidStack());
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
	
	public float getScaledProgress() {
        float standardSize = 1f;
        int progess = this.growthStage;
        int maxProgress = this.maxGrowthStage;

        return maxProgress != 0 && progess != 0 ? progess * standardSize / maxProgress : 0;
    }
	
	public ItemStack getRenderStack() {
		return getStackInSlot(itemStorage, 9);
	}
	
	public void setHandler(ItemStackHandler itemStackHandler) {
		for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemStorage.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
	}
	
	public void setFluid(FluidStack fluidStack) {
		setFluid(fluidStorage, fluidStack);
	}
	
	@Override
	public boolean isValidBlock(ItemStack stack) {
		return false;
	}
	
	@Override
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(12) {
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
					if(stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent() || stack.is(LibTags.Items.UPGRADES)) {
						return false;
					}
					return true;
				}  else if(slot == 9) {
					if(stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
						return false;
					}
					return true;
				} else if(slot == 10 || slot == 11) {
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
	protected ItemStackHandler upgradeItemStorage() {
		return new ItemStackHandler(3) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return stack.is(LibTags.Items.REDSTONE_UPGRADE) ? true : false;
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
		return new ModEnergyStorage(true, FarmingConfig.INTERNAL_FARMER_CAPACITY.get(), FarmingConfig.INTERNAL_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(energyStorage, FarmingConfig.INTERNAL_FARMER_USEPERTICK.get());
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
				return stack.isFluidEqual(getFluidStack()) || fluidStorage.isEmpty();
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
    
    @Override
	protected <T> LazyOptional<T> callCapability(Capability<T> cap, Direction side) {
    	return null;
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
