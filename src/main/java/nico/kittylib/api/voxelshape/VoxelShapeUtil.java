package nico.kittylib.api.voxelshape;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for VoxelShapes
 */
public class VoxelShapeUtil {
    private static final Map<CacheKey, VoxelShape> cachedVoxelShapes = new HashMap<>();

    /**
     * Rotates a VoxelShape by the given rotation (90-degree increments).
     *
     * @param shape     The VoxelShape to rotate.
     * @param direction The direction the shape should get rotated to.
     * @return The rotated VoxelShape.
     */
    public static VoxelShape rotate(VoxelShape shape, Direction direction) {
        return rotate(shape, direction.getHorizontal() * 90 % 360);
    }

    /**
     * Rotates a VoxelShape by the given rotation (90-degree increments).
     *
     * @param shape    The VoxelShape to rotate.
     * @param rotation The amount of rotation (0, 90, 180, 270).
     * @return The rotated VoxelShape.
     */
    public static VoxelShape rotate(VoxelShape shape, int rotation) {
        if (rotation == 0) return shape; // No rotation needed

        if (rotation < 0) {
            //rotation = rotation * -1 / 90 * 270 % 360;
            rotation = -3 * rotation % 360;
        }
        rotation %= 360;
        if (rotation % 90 != 0) {
            throw new IllegalArgumentException("Rotation \"" + rotation + "\" must be a multiple of 90");
        }


        CacheKey cacheKey = new CacheKey(rotation, shape);
        if (cachedVoxelShapes.containsKey(cacheKey)) {
            return cachedVoxelShapes.get(cacheKey);
        }

        VoxelShape rotatedShape = VoxelShapes.empty();

        // Rotate each bounding box in the VoxelShape
        for (Box box : shape.getBoundingBoxes()) {
            rotatedShape = VoxelShapes.union(rotatedShape, rotateBox(box, rotation));
        }

        cachedVoxelShapes.put(cacheKey, rotatedShape);
        return rotatedShape;
    }

    /**
     * Rotates a Box by the given rotation (90-degree increments).
     *
     * @param box      The box to rotate.
     * @param rotation The amount of rotation (0, 90, 180, 270).
     * @return The rotated Box.
     */
    private static VoxelShape rotateBox(Box box, int rotation) {
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;


        return switch (rotation % 360) {
            case 0 -> VoxelShapes.cuboid(minX, minY, minZ, maxX, maxY, maxZ);
            case 90 -> VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX);
            case 180 -> VoxelShapes.cuboid(1 - maxX, minY, 1 - maxZ, 1 - minX, maxY, 1 - minZ);
            case 270 -> VoxelShapes.cuboid(minZ, minY, 1 - maxX, maxZ, maxY, 1 - minX);
            default -> throw new IllegalArgumentException("Rotation must be 0, 90, 180, or 270 degrees");
        };
    }

    /**
     * Moves a VoxelShape by the given amount of blocks
     *
     * @param shape    The VoxelShape that gets moved.
     * @param position A Vec3i or BlockPos (1 unit = 1 block)
     * @return The moved VoxelShape
     */
    public static VoxelShape moveBlock(VoxelShape shape, Vec3i position) {
        return shape.offset(position.getX(), position.getY(), position.getZ());
    }

    /**
     * Moves a VoxelShape by the given amount of pixels, (1/16 of a block)
     *
     * @param shape    The VoxelShape that gets moved.
     * @param position A Vec3d (1 unit = 1/16 block)
     * @return The moved VoxelShape
     */
    public static VoxelShape move(VoxelShape shape, Vec3d position) {
        return shape.offset(position.getX() / 16d, position.getY() / 16d, position.getZ() / 16d);
    }

    record CacheKey(int rotation, VoxelShape shape) {
    }
}
