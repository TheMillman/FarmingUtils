package com.the_millman.farmingutils.common.blockentity;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemStackHandler;

public class TestEnergyGenerator extends ItemEnergyBlockEntity {

    private int counter;

    public TestEnergyGenerator(BlockPos pWorldPosition, BlockState pBlockState) 
	{
		super(BlockEntityInit.TEST_ENERGY_GENERATOR.get(), pWorldPosition, pBlockState);
	}

    @Override
    public void tickServer() {
        if (counter > 0) {
            counter--;
            energyStorage.addEnergy(FarmingConfig.GENERATOR_PERTICK.get());
            setChanged();
        }

		if (counter <= 0) {
			ItemStack stack = itemStorage.getStackInSlot(0);
			int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
			if (burnTime > 0) {
				if (energyStorage.getEnergyStored() < FarmingConfig.GENERATOR_CAPACITY.get()) {
					itemStorage.extractItem(0, 1, false);
					counter = burnTime/4;
					setChanged();
				} 
			}
        }

        BlockState blockState = level.getBlockState(worldPosition);
        if (blockState.getValue(BlockStateProperties.POWERED) != counter > 0) {
            level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, counter > 0), 3 + 2);
        }

        sendOutPower();
    }

    private void sendOutPower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() > 0) {
            for (Direction direction : Direction.values()) {
                BlockEntity te = level.getBlockEntity(worldPosition.relative(direction));
                if (te != null) {
                    boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                                if (handler.canReceive()) {
                                    int received = handler.receiveEnergy(Math.min(capacity.get(), FarmingConfig.GENERATOR_SENDPERTICK.get()), false);
                                    capacity.addAndGet(-received);
                                    energyStorage.consumeEnergy(received);
                                    setChanged();
                                    return capacity.get() > 0;
                                } else {
                                    return true;
                                }
                            }
                    ).orElse(true);
                    if (!doContinue) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected ItemStackHandler itemStorage() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    protected ModEnergyStorage createEnergy() {
        return new ModEnergyStorage(false, FarmingConfig.GENERATOR_CAPACITY.get(), 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
            
            @Override
            public boolean canExtract() {
            	return false;
            }
        };
    }

	@Override
	protected void init() {
		
	}
}
