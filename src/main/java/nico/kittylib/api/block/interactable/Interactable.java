package nico.kittylib.api.block.interactable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Interactable {
    HashMap<Interactable, List<InteractableEntry>> actions = new HashMap<>();

    default void addAction(Vec3d hitPos, double maxDistance, InteractableAction action) {
        actions.computeIfAbsent(this, $ -> new ArrayList<>()).add(new InteractableRadiusEntry(hitPos, maxDistance, action));
    }

    default void addPixelAction(Vec3d hitPos, double maxDistance, InteractableAction action) {
        addAction(hitPos.multiply(1 / 16d), maxDistance, action);
    }

    default void addAction(Vec3d corner1, Vec3d corner2, InteractableAction action) {
        actions.computeIfAbsent(this, $ -> new ArrayList<>()).add(new InteractableZoneEntry(corner1, corner2, action));
    }

    default void addPixelAction(Vec3d corner1, Vec3d corner2, InteractableAction action) {
        addAction(corner1.multiply(1/16d), corner2.multiply(1/16d), action);
    }

    default ActionResult checkAndExecuteActions(Vec3d hitPos, World world, PlayerEntity player, Hand hand, BlockState blockState, BlockPos blockPos) {
        final Vec3d rotatedHitPos = rotateHitLocal(hitPos.subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ()), blockState.getOrEmpty(Properties.HORIZONTAL_FACING).orElse(Direction.NORTH));

        for (InteractableEntry entry : actions.computeIfAbsent(this, $ -> new ArrayList<>())) {
            if (entry.canInteract(rotatedHitPos)) {
                return entry.getAction().apply(world, player, hand, blockState, blockPos, rotatedHitPos);
            }
        }

        return null;
    }

    default Vec3d rotateHitLocal(Vec3d hit, Direction facing) {
        return switch (facing) {
            case NORTH -> hit; // no rotation needed
            case EAST -> new Vec3d(1 - hit.z, hit.y, hit.x); // 90° clockwise
            case SOUTH -> new Vec3d(1 - hit.x, hit.y, 1 - hit.z); // 180°
            case WEST -> new Vec3d(hit.z, hit.y, 1 - hit.x); // 270° clockwise
            default -> hit;
        };
    }
}
