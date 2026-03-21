package nico.kittylib.api.math;

import org.joml.Vector3d;

public class KittyLibShapeHelper {
    public static Vector3d circle(Vector3d startingPosition, int point, int maxPoints, double radiusX, double radiusY, double radiusZ) {
        double angle = 2d * Math.PI * point / maxPoints;
        double x = startingPosition.x + radiusX * Math.cos(angle);
        double y = startingPosition.y + radiusY * Math.sin(angle);
        double z = startingPosition.z + radiusZ * Math.sin(angle);

        return new Vector3d(x, y, z);
    }

    public static Vector3d cylinder(Vector3d startingPos, int point, int maxPoints, double radiusX, double radiusZ, double height) {
        double angle = 2d * Math.PI * point / maxPoints;
        double x = startingPos.x + radiusX * Math.cos(angle);
        double y = startingPos.y + height;
        double z = startingPos.z + radiusZ * Math.sin(angle);

        return new Vector3d(x, y, z);
    }

    public static Vector3d line(Vector3d start, Vector3d end, int point, int maxPoints) {
        double dx = (end.x - start.x) / maxPoints;
        double dy = (end.y - start.y) / maxPoints;
        double dz = (end.z - start.z) / maxPoints;

        return new Vector3d(start.x + point * dx, start.y + point * dy, start.z + point * dz);
    }
}
