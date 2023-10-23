package dev.the_millman.farmingutils.common.blockentity;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import dev.the_millman.farmingutils.common.recipes.ComposterRecipe;
import dev.the_millman.farmingutils.core.init.BlockEntityInit;
import dev.the_millman.farmingutils.core.init.RecipeTypesInit;
import dev.the_millman.farmingutils.core.tags.ModItemTags;
import dev.the_millman.farmingutils.core.util.FarmingConfig;
import dev.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import dev.the_millman.themillmanlib.core.energy.ModEnergyStorage;
import dev.the_millman.themillmanlib.core.util.ModItemHandlerHelp;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class ComposterBE extends ItemEnergyBlockEntity {

	int tick;
	boolean initialized = false;
	public final ContainerData data;
	BlockPos pos;
	
	public ComposterBE(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.COMPOSTER.get(), pWorldPosition, pBlockState);
		this.pos = pWorldPosition;
		this.data = new ContainerData() {
			public int get(int index) {
				return ComposterBE.this.tick;
			}

			public void set(int index, int value) {
				ComposterBE.this.tick = value;
			}

			public int getCount() {
				return 1;
			}
		};
	}

	@Override
	protected void init() {
		this.initialized = true;
		this.tick = 0;
		
		pos = new BlockPos(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
	}
	
	@Override
	public void tickServer() {}
	
	// Upgrade is output slot
	public void tickServer(ComposterBE pEntity) {
		if(!initialized) 
			init();
		
		if (hasPowerToWork(energyStorage, FarmingConfig.FARMERS_NEEDS_ENERGY.get(), FarmingConfig.COMPOSTER_USEPERTICK.get())) {
			ItemStack stack = getStackInSlot(itemStorage, 0);
			if (stack.is(ModItemTags.COMPOSTER_ITEMS) && stack.getCount() >= 8  && canCraft()) {
				tick++;
				consumeEnergy(energyStorage, FarmingConfig.COMPOSTER_USEPERTICK.get());
				if (tick >= FarmingConfig.COMPOSTER_TICK.get()) {
					this.tick = 0;
					craftItem(pEntity);
				}
			} else if (getStackInSlot(itemStorage, 0).isEmpty() || stack.getCount() < 8) {
				if (tick > 0) {
					this.tick--;
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void craftItem(ComposterBE pEntity) {
		Level level = pEntity.getLevel();
        SimpleContainer inventory = setInventory();

        Optional<ComposterRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(RecipeTypesInit.COMPOSTER_TYPE.get(), inventory, level);
        
        if(recipe.isPresent() && canCraft()) {
        	if(!level.isClientSide()) {
        		ItemStack recipeOutput = new ItemStack(recipe.get().getResultItem().getItem(), recipe.get().getResultItem().getCount());
	            consumeStack(itemStorage, 0, 8);
	            ItemStack result = ModItemHandlerHelp.insertItemStacked(upgradeItemStorage, recipeOutput, 0, 1, false);
        	}
        }
	}
	
	private boolean canCraft() {
		ItemStack result = getStackInSlot(upgradeItemStorage, 0);
        
        if (result.getCount() >= 64) {
			return false;
		} else {
			return true;
		}
	}
	
	public int getProgress() {
		return this.tick;
	}
	
	public int getMaxProgress() {
		return FarmingConfig.COMPOSTER_TICK.get();	
	}
	
	@Override
	public boolean isValidBlock(ItemStack stack) {
		return false;
	}
	
	@Override
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return true;
			}
		};
	}

	@Override
	protected ItemStackHandler upgradeItemStorage() {
		return new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return true;
			}
		};
	}
	
	@Override
	protected IItemHandler createCombinedItemHandler() {
		return new CombinedInvWrapper(itemStorage, upgradeItemStorage) {
			@Override
			public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
				int index = getIndexForSlot(slot);
				if (getHandlerFromIndex(index) == upgradeItemStorage) {
					return stack;
				}
				return super.insertItem(slot, stack, simulate);
			}
		};
	}

	@Override
	protected ModEnergyStorage createEnergy() {
		return new ModEnergyStorage(true, FarmingConfig.COMPOSTER_CAPACITY.get(), FarmingConfig.COMPOSTER_USEPERTICK.get()*2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(energyStorage, FarmingConfig.COMPOSTER_USEPERTICK.get());
				if (newHasPower) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
				}
				setChanged();
			}
		};
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			if (side == null) {
				return combinedItemHandler.cast();
			} else if (side == Direction.DOWN) {
				return upgradeItemHandler.cast();
			} else {
				return itemStorageHandler.cast();
			}
		}
		return super.getCapability(cap, side);
	}

}
