package nico.kittylib.api.math;

import net.minecraft.util.math.MathHelper;
import org.joml.Vector3d;

public class KittyLibMathHelper extends MathHelper {
    public float fixedSin(float value) {
        return sin(value*PI);
    }

    public float fixedCos(float value) {
        return cos(value*PI);
    }

    public static Vector3d rotateAroundOrigin(Vector3d point, Vector3d origin, double rotX, double rotY, double rotZ) {

        // Translate point to origin
        double x = point.x - origin.x;
        double y = point.y - origin.y;
        double z = point.z - origin.z;

        // Rotate around X axis
        if (rotX != 0) {
            double cos = Math.cos(rotX);
            double sin = Math.sin(rotX);
            double ny = y * cos - z * sin;
            double nz = y * sin + z * cos;
            y = ny;
            z = nz;
        }

        // Rotate around Y axis
        if (rotY != 0) {
            double cos = Math.cos(rotY);
            double sin = Math.sin(rotY);
            double nx = x * cos + z * sin;
            double nz = -x * sin + z * cos;
            x = nx;
            z = nz;
        }

        // Rotate around Z axis
        if (rotZ != 0) {
            double cos = Math.cos(rotZ);
            double sin = Math.sin(rotZ);
            double nx = x * cos - y * sin;
            double ny = x * sin + y * cos;
            x = nx;
            y = ny;
        }

        // Translate back
        return new Vector3d(
                x + origin.x,
                y + origin.y,
                z + origin.z
        );
    }
}
