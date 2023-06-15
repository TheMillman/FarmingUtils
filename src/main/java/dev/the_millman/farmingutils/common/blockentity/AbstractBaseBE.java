package dev.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import dev.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import dev.the_millman.themillmanlib.common.blockentity.interfaces.IEnergyStorageHelper;
import dev.the_millman.themillmanlib.common.blockentity.interfaces.IItemStorageHelper;
import dev.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class AbstractBaseBE extends BlockEntity implements IEnergyStorageHelper, IItemStorageHelper {

	protected final ItemStackHandler itemStorage = createInputItemHandler();
	protected final LazyOptional<IItemHandler> inputItemHandler = LazyOptional.of(() -> itemStorage);
	protected final ItemStackHandler upgradeItems = createUpgradeItemHandler();
	protected final LazyOptional<IItemHandler> upgradeItemHandler = LazyOptional.of(() -> upgradeItems);
	protected final LazyOptional<IItemHandler> combinedItemHandler = LazyOptional.of(this::createCombinedItemHandler);

	protected final ModEnergyStorage energyStorage = createEnergyStorage();
	protected final LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(() -> energyStorage);
    
    protected int x, y, z;
	protected int pX, pZ;
	protected int range;
	
	public AbstractBaseBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}
	
	/**
	 * Method to set the parameters.
	 */
	protected abstract void init ();
	
	/**
	 * Method called from the Ticker method from the block.
	 */
	public abstract void tickServer();
	
	// Sets the position of the destroyed block.
	protected void posState() {
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
	
	/**
	 * @see ItemEnergyBlockEntity getSlot()
	 * @param stackInSlot
	 * @return boolean
	 */
	public abstract boolean isValidBlock(ItemStack stackInSlot);
	
	/**
	 * Method to call for debug, of the block. Always remember to set it to false.
	 * @param debug
	 */
	public void energyDebug(boolean debug) {
		if(debug == true) {
			ItemStack consumeItem = itemStorage.getStackInSlot(0);
			if (consumeItem.getItem() == Items.COAL) {
				energyStorage.addEnergy(1000);
				consumeItem.shrink(1);
			}
		}
	}

	protected abstract ModEnergyStorage createEnergyStorage();

	protected abstract ItemStackHandler createUpgradeItemHandler();

	protected abstract ItemStackHandler createInputItemHandler();

	protected abstract IItemHandler createCombinedItemHandler();
	
	@Override
    public void setRemoved() {
        super.setRemoved();
        inputItemHandler.invalidate();
        upgradeItemHandler.invalidate();
        combinedItemHandler.invalidate();
        energyHandler.invalidate();
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("InventorySlot", itemStorage.serializeNBT());
        tag.put("Energy", energyStorage.serializeNBT());
        super.saveAdditional(tag);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("InventorySlot")) {
        	itemStorage.deserializeNBT(tag.getCompound("InventorySlot"));
        }
        if (tag.contains("Energy")) {
        	energyStorage.deserializeNBT(tag.get("Energy"));
        }
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
            	upgradeItemHandler.cast();
                return combinedItemHandler.cast();
            } else {
                return inputItemHandler.cast();
            }
        } else if (cap == ForgeCapabilities.ENERGY) {
            return energyHandler.cast();
        } else {
            return super.getCapability(cap, side);
        }
    }
}
