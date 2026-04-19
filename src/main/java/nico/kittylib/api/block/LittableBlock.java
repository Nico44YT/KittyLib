package nico.kittylib.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public interface LittableBlock {
    boolean canBeLit(BlockState state);

    default ItemStack dispenseSilently(World world, BlockPointer pointer, ItemStack stack, BlockState blockState, BlockPos blockPos) {
        world.setBlockState(blockPos, blockState.with(Properties.LIT, true));
        world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);

        return stack;
    }
}
