package dev.the_millman.farmingutils.common.blocks;

import dev.the_millman.farmingutils.common.blockentity.InternalFarmerBE;
import dev.the_millman.farmingutils.common.containers.InternalFarmerContainer;
import dev.the_millman.farmingutils.core.networking.FluidSyncS2CPacket;
import dev.the_millman.farmingutils.core.networking.ModMessages;
import dev.the_millman.themillmanlib.common.blocks.DirectionalPoweredBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class InternalFarmerBlock extends DirectionalPoweredBlock implements EntityBlock {
	
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	
	public InternalFarmerBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return SHAPE;
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof InternalFarmerBE tile) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("screen.farmingutils.internal_farmer");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                    	if(!pLevel.isClientSide()) {
        	                ModMessages.sendToClients(new FluidSyncS2CPacket(tile.getFluidStack(), tile.getBlockPos()));
        	            }
                    	
                        return new InternalFarmerContainer(windowId, pLevel, pPos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openScreen((ServerPlayer) pPlayer, containerProvider, blockEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new InternalFarmerBE(pPos, pState);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return (level1, pos, state1, tile) -> {
			if (tile instanceof InternalFarmerBE blockEntity) {
				blockEntity.tickServer(blockEntity);
			}
		};
	}
}
