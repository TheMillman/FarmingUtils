package com.the_millman.farmingutils.common.blockentity;

import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Deprecated
public abstract class TestD extends BlockEntity 
{
	protected final ItemStackHandler itemStorage = itemStorage();
	private LazyOptional<IItemHandler> inputSlotHandler = LazyOptional.of(() -> itemStorage);
	
	protected final ModEnergyStorage energyStorage = createEnergy();
	private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
	
	protected final FluidTank fluidStorage = fluidStorage();
	private LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> fluidStorage);
	
	public TestD(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}
	
	/**
	 * Return true if the energy stored is greater than the energy usage
	 * @param usePerTick
	 * @return
	 */
	protected boolean hasPowerToWork(int usePerTick) {
		return energyStorage.getEnergyStored() >= usePerTick;
	}
	
	/**
	 * Method to set the parameters.
	 */
	protected abstract void init ();
	
	/**
	 * Metho to set the FluidStack to a specific Fluid Stack.
	 * @param fluidStack
	 */
	public void setFluid(FluidStack fluidStack) {
		fluidStorage.setFluid(fluidStack);
	}

	/**
	 * Method to get the FluidStack
	 * @return FluidStack
	 */
	public FluidStack getFluidStack() {
		return fluidStorage.getFluid();
	}
	
	@Override
	public void onLoad() {
		fluidHandler = LazyOptional.of(() -> fluidStorage);
		super.onLoad();
	}
	
	@Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inputSlotHandler.invalidate();
        energy.invalidate();
        fluidHandler.invalidate();
    }
	
	@Override
	public void setRemoved() {
		inputSlotHandler.invalidate();
		fluidHandler.invalidate();
		energy.invalidate();
		super.setRemoved();
	}
	
	@Override
	public void load(CompoundTag pTag) {
		if (pTag.contains("InventorySlot")) {
			itemStorage.deserializeNBT(pTag.getCompound("InventorySlot"));
		}

		if (pTag.contains("Energy")) {
			energyStorage.deserializeNBT(pTag.get("Energy"));
		}
		
		fluidStorage.readFromNBT(pTag);

		super.load(pTag);
	}
	
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		pTag.put("InventorySlot", itemStorage.serializeNBT());
		pTag.put("Energy", energyStorage.serializeNBT());
		pTag = fluidStorage.writeToNBT(pTag);
		
		CompoundTag infoTag = new CompoundTag();
		pTag.put("Info", infoTag);
		super.saveAdditional(pTag);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return inputSlotHandler.cast();
		}

		if (cap == CapabilityEnergy.ENERGY) {
			return energy.cast();
		}
		
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return fluidHandler.cast();
		}
		
		return super.getCapability(cap, side);
	}
	
	protected abstract ItemStackHandler itemStorage();
	
	protected abstract ModEnergyStorage createEnergy();

	protected abstract FluidTank fluidStorage();
}
