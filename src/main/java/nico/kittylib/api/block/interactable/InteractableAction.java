package nico.kittylib.api.block.interactable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@FunctionalInterface
public interface InteractableAction {
    ActionResult apply(World world, PlayerEntity player, Hand hand, BlockState blockState, BlockPos blockPos, Vec3d hitPos);
}
