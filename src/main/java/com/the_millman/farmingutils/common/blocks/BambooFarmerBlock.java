package com.the_millman.farmingutils.common.blocks;

import com.the_millman.farmingutils.common.blockentity.BambooFarmerBE;
import com.the_millman.farmingutils.common.containers.BambooFarmerContainer;
import com.the_millman.themillmanlib.common.blocks.PoweredBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class BambooFarmerBlock extends PoweredBlock implements EntityBlock {

	public BambooFarmerBlock(Properties properties) {
		super(properties);
	}
	
	/**
	 * TODO controllare open screen
	 */
	@Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BambooFarmerBE tile) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("screen.farmingutils.bamboo_farmer");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new BambooFarmerContainer(windowId, level, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openScreen((ServerPlayer) player, containerProvider, blockEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BambooFarmerBE(pPos, pState);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return (level1, pos, state1, tile) -> {
			if (tile instanceof BambooFarmerBE blockEntity) {
				blockEntity.tickServer();
			}
		};
	}
}
