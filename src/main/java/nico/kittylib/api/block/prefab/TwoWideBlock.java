package nico.kittylib.api.block.prefab;

import io.netty.util.internal.UnstableApi;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nico.kittylib.api.voxelshape.VoxelShapeUtil;

@UnstableApi
public class TwoWideBlock extends DefaultedHorizontalFacingBlock {

    public static final BooleanProperty PARENT = BooleanProperty.of("parent");

    public TwoWideBlock(Settings settings) {
        super(settings);

        this.setDefaultState(this.getDefaultState().with(PARENT, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(PARENT));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Direction facing = ctx.getHorizontalPlayerFacing();

        // Second half goes to the right
        BlockPos otherPos = pos.offset(facing.rotateYClockwise());

        if (!world.getBlockState(otherPos).canReplace(ctx)) {
            return null;
        }

        return getDefaultState()
                .with(FACING, facing)
                .with(PARENT, true);
    }

    @Override
    public void onPlaced(
            World world,
            BlockPos pos,
            BlockState state,
            LivingEntity placer,
            ItemStack itemStack
    ) {
        if (!world.isClient) {
            Direction facing = state.get(FACING);
            BlockPos otherPos = pos.offset(facing.rotateYClockwise());

            world.setBlockState(
                    otherPos,
                    state.with(PARENT, false),
                    Block.NOTIFY_ALL
            );
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        breakOtherPart(state, pos, world, player);
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return state.get(PARENT)
                ? BlockRenderType.MODEL
                : BlockRenderType.INVISIBLE;
    }

    public VoxelShape getShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        var shape = getShape(state, world, pos, context);
        Direction facing = state.get(FACING).getOpposite();
        if (state.get(PARENT)) return VoxelShapeUtil.rotate(shape, facing);
        Direction rotatedFacing = facing.rotateYCounterclockwise();
        return VoxelShapeUtil.rotate(shape, facing).offset(-rotatedFacing.getOffsetX(), 0, -rotatedFacing.getOffsetZ());
    }

    @Override
    public void onStateReplaced(BlockState oldState, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(oldState, world, pos, newState, moved);
        if (oldState.isOf(newState.getBlock())) {
            breakOtherPart(oldState, pos, world, null);
        }
    }

    private void breakOtherPart(BlockState state, BlockPos pos, World world, PlayerEntity player) {
        Direction facing = state.get(FACING);

        BlockPos otherPos = state.get(PARENT)
                ? pos.offset(facing.rotateYClockwise())
                : pos.offset(facing.rotateYCounterclockwise());

        if (world.getBlockState(otherPos).isOf(this)) {
            world.breakBlock(otherPos, false);
            world.syncWorldEvent(player, 2001, pos, Block.getRawIdFromState(state));
        }
    }

}
