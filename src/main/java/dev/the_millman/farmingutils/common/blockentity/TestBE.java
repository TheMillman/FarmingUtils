package dev.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import dev.the_millman.farmingutils.core.init.BlockEntityInit;
import dev.the_millman.farmingutils.core.util.FarmingConfig;
import dev.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class TestBE extends BlockEntity {

	public static final int INPUT_SLOTS = 18;
    public static final int UPGRADE_SLOTS = 3;
    
    @SuppressWarnings("unused")
	private int tick;
	
	boolean initialized = false;
	boolean needRedstone = false;
	boolean pickupDrops = true;
	
    private final ItemStackHandler inputItems = createInputItemHandler();
    private final LazyOptional<IItemHandler> inputItemHandler = LazyOptional.of(() -> inputItems);
    private final ItemStackHandler upgradeItems = createUpgradeItemHandler();
    private final LazyOptional<IItemHandler> upgradeItemHandler = LazyOptional.of(() -> upgradeItems);
    private final LazyOptional<IItemHandler> combinedItemHandler = LazyOptional.of(this::createCombinedItemHandler);

    private final ModEnergyStorage energy = createEnergyStorage();
    private final LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(() -> energy);
    
	public TestBE(BlockPos pPos, BlockState pBlockState) {
		super(BlockEntityInit.CROP_FARMER.get(), pPos, pBlockState);
	}

	@Nonnull
    private ItemStackHandler createInputItemHandler() {
        return new ItemStackHandler(INPUT_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @NotNull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }
        };
    }

    @Nonnull
    private ItemStackHandler createUpgradeItemHandler() {
        return new ItemStackHandler(UPGRADE_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Nonnull
    private IItemHandler createCombinedItemHandler() {
        return new CombinedInvWrapper(inputItems, upgradeItems) {
            @NotNull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @NotNull
            @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                return stack;
            }
        };
    }

    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(true, FarmingConfig.CROP_FARMER_CAPACITY.get(), FarmingConfig.CROP_FARMER_USEPERTICK.get()*2) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }	
    
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
        tag.put("Inventory", inputItems.serializeNBT());
        tag.put("Energy", energy.serializeNBT());
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inputItems.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("Energy")) {
            energy.deserializeNBT(tag.get("Energy"));
        }
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return combinedItemHandler.cast();
            } else if (side == Direction.DOWN) {
                return upgradeItemHandler.cast();
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
