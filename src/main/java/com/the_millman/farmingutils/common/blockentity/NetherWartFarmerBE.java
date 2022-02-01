package com.the_millman.farmingutils.common.blockentity;

import javax.annotation.Nonnull;

import com.the_millman.farmingutils.common.blocks.NetherWartFarmerBlock;
import com.the_millman.farmingutils.core.init.BlockEntityInit;
import com.the_millman.farmingutils.core.init.ItemInit;
import com.the_millman.farmingutils.core.tags.ModItemTags;
import com.the_millman.farmingutils.core.util.FarmingConfig;
import com.the_millman.themillmanlib.common.blockentity.ItemEnergyBlockEntity;
import com.the_millman.themillmanlib.core.energy.ModEnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.ItemStackHandler;

public class NetherWartFarmerBE extends ItemEnergyBlockEntity {

	private int x, y, z, tick;
	int pX;
	int pZ;
	boolean initialized = false;
	int range;
	boolean needRedstone = false;
	boolean pickupDrops = true;
	
	public NetherWartFarmerBE(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.NETHER_WART_FARMER.get(), pWorldPosition, pBlockState);
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

		if (hasPowerToWork(FarmingConfig.NETHER_WART_FARMER_USEPERTICK.get())) {
			tick++;
			if (tick == FarmingConfig.NETHER_WART_FARMER_TICK.get()) {
				tick = 0;
				redstoneUpgrade();
				if (canWork()) {
					upgradeSlot();
					BlockPos posToBreak = new BlockPos(this.x + this.pX, this.y, this.z + this.pZ);
					destroyBlock(posToBreak, false);
					placeBlock(posToBreak);
					setChanged();

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
			}
		}
	}
	
	private boolean canWork() {
		if(this.needRedstone) {
			if(getBlockState().getValue(NetherWartFarmerBlock.POWERED)) {
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
		ItemStack upgradeSlot = itemStorage.getStackInSlot(10);
		if (upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = true;
		} else if (!upgradeSlot.is(ItemInit.REDSTONE_UPGRADE.get())) {
			this.needRedstone = false;
		}
	}
	
	private void dropUpgrade() {
		ItemStack upgradeSlot = itemStorage.getStackInSlot(11);
		if (upgradeSlot.is(ItemInit.DROP_UPGRADE.get())) {
			this.pickupDrops = false;
		} else if (!upgradeSlot.is(ItemInit.DROP_UPGRADE.get())) {
			this.pickupDrops = true;
		}
	}
	
	/**
	 * Metodo per riconoscere l'upgrade inserito nel suo slot, a seconda
	 * dell'upgrade il raggio della Block Entity aumenta.
	 */
	private void rangeUpgrade() {
		ItemStack upgradeSlot = itemStorage.getStackInSlot(9);
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
	
	/**
	 * Metodo per distruggere i blocchi, racoglie i drop e trasforma il blocco in
	 * aria.
	 * 
	 * @param pos
	 * @param dropBlock
	 * @return boolean, vero se il blocco è stato distrutto.
	 */
	private boolean destroyBlock(BlockPos pos, boolean dropBlock) {
		BlockState state = level.getBlockState(pos);
		if (state.isAir()) {
			return false;
		} else if (getDestBlock(state)) {
			if (!level.isClientSide) {
				if (this.pickupDrops) {
					collectDrops(pos, 0, 9);
					level.destroyBlock(pos, dropBlock);
					energyStorage.consumeEnergy(FarmingConfig.NETHER_WART_FARMER_USEPERTICK.get());
					return true;
				} else if(!this.pickupDrops) {
					level.destroyBlock(pos, true);
					energyStorage.consumeEnergy(FarmingConfig.NETHER_WART_FARMER_USEPERTICK.get());
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	/**
	 * Metodo per piazzare i blocchi, controlla il blocco sotto e l'item negli slot,
	 * se va bene piazza il blocco
	 * 
	 * @param pos
	 * @return boolean, vero se il blocco è stato piazzato.
	 */
	private boolean placeBlock(BlockPos pos) {
		int slot = 0;
		for (int i = 0; i < 9; i++) {
			if (itemStorage.getStackInSlot(i).isEmpty())
				continue;

			if (!itemStorage.getStackInSlot(i).isEmpty()) {
				slot = i;
				break;
			}
		}

		BlockPos posY = pos.below();
		BlockState state = level.getBlockState(pos);
		BlockState yState = level.getBlockState(posY);
		if (state.getBlock() == Blocks.NETHER_WART) {
			return false;
		} else if (yState.is(Blocks.SOUL_SAND)) {
			if (itemStorage.getStackInSlot(slot).getItem()instanceof BlockItem blockItem) {
				if (!level.isClientSide) {
					if (blockItem.asItem() == Items.NETHER_WART) {
						Block block = blockItem.getBlock();
						level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL);
						level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1F, 1F);
						ItemStack stack = itemStorage.getStackInSlot(slot);
						stack.shrink(1);
						energyStorage.consumeEnergy(FarmingConfig.NETHER_WART_FARMER_USEPERTICK.get());
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * getDestroiedBlock, metodo per distruggere i blocchi. Prende il BlockState,
	 * controlla se il blocco va bene e ritorna true se si può distroggere. Nessun
	 * problema con zucche e meloni perchè non sono dei cropblock
	 * 
	 * @param state
	 * @return boolean, vero se il blocco si può distruggere.
	 */
	private boolean getDestBlock(BlockState state) {
		if (state.getBlock() == Blocks.NETHER_WART && state.getValue(BlockStateProperties.AGE_3) == NetherWartBlock.MAX_AGE) {
			return true;
		} 
		return false;
	}
	
	@Override
	protected ItemStackHandler itemStorage() {
		return new ItemStackHandler(12) {
			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				if (slot <= 8) {
					if (stack.getItem() == Items.NETHER_WART) {
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
		return new ModEnergyStorage(true, FarmingConfig.NETHER_WART_FARMER_CAPACITY.get(), FarmingConfig.NETHER_WART_FARMER_USEPERTICK.get() * 2) {
			@Override
			protected void onEnergyChanged() {
				boolean newHasPower = hasPowerToWork(FarmingConfig.NETHER_WART_FARMER_USEPERTICK.get());
				if (newHasPower) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
				}
				setChanged();
			}
		};
	}
}
