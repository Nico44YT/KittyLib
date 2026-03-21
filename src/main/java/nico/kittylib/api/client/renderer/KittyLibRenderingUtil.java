package nico.kittylib.api.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

public class KittyLibRenderingUtil {
    public static class Faces {
        public static final double[] FRONT = { // FRONT (z = 1)
                0, 0, 1,
                1, 0, 1,
                1, 1, 1,
                0, 1, 1
        };

        public static final double[] BACK = { // BACK (z = 0)
                1, 0, 0,
                0, 0, 0,
                0, 1, 0,
                1, 1, 0
        };

        public static final double[] LEFT = { // LEFT (x = 0)
                0, 0, 0,
                0, 0, 1,
                0, 1, 1,
                0, 1, 0
        };

        public static final double[] RIGHT = { // RIGHT (x = 1)
                1, 0, 1,
                1, 0, 0,
                1, 1, 0,
                1, 1, 1
        };

        public static final double[] TOP = { // TOP (y = 1)
                0, 1, 1,
                1, 1, 1,
                1, 1, 0,
                0, 1, 0,
        };

        public static final double[] BOTTOM = {
                0, 0, 0,
                1, 0, 0,
                1, 0, 1,
                0, 0, 1
        };

    }

    public static void renderFaces(MatrixStack matrixStack, VertexConsumer buffer, int[] rgba, int overlay, int light, double[]... faces) {
        renderFaces(matrixStack, buffer, rgba, overlay, light, 0, 0, 1, 1, faces);
    }

    public static void renderFaces(MatrixStack matrixStack, VertexConsumer buffer, int[] rgba, int overlay, int light, float minU, float minV, float maxU, float maxV, double[]... faces) {
        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
        Matrix3f normalMatrix = matrixStack.peek().getNormalMatrix();

        for (int i = 0; i < faces.length; i++) {
            renderQuad(positionMatrix, normalMatrix, buffer,
                    faces[i],
                    minU, minV,
                    maxU, maxV,
                    light, overlay,
                    rgba
            );
        }
    }

    /***
     * Renders a cube. If the cube only renders black because of the uv cords try calling renderCube with flipV
     * @see #renderCube(MatrixStack, VertexConsumer, int[], int, int, float, float, float, float, boolean)
     * @param matrixStack
     * @param buffer
     * @param rgba
     * @param overlay
     * @param light
     * @param minU
     * @param minV
     * @param maxU
     * @param maxV
     */
    public static void renderCube(MatrixStack matrixStack, VertexConsumer buffer, int[] rgba, int overlay, int light, float minU, float minV, float maxU, float maxV) {
        renderCube(matrixStack, buffer, rgba, overlay, light, minU, minV, maxU, maxV, false);
    }

    public static void renderCube(MatrixStack matrixStack, VertexConsumer buffer, int[] rgba, int overlay, int light, float minU, float minV, float maxU, float maxV, boolean flipV) {
        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
        Matrix3f normalMatrix = matrixStack.peek().getNormalMatrix();

        minV *= flipV ? -1 : 1;
        maxV *= flipV ? -1 : 1;

        List<Vec3d> vertexes = List.of(
                // FRONT (z = 1)
                new Vec3d(0, 0, 1),
                new Vec3d(1, 0, 1),
                new Vec3d(1, 1, 1),
                new Vec3d(0, 1, 1),
                // BACK (z = 0)
                new Vec3d(1, 0, 0),
                new Vec3d(0, 0, 0),
                new Vec3d(0, 1, 0),
                new Vec3d(1, 1, 0),
                // LEFT (x = 0)
                new Vec3d(0, 0, 0),
                new Vec3d(0, 0, 1),
                new Vec3d(0, 1, 1),
                new Vec3d(0, 1, 0),
                // RIGHT (x = 1)
                new Vec3d(1, 0, 1),
                new Vec3d(1, 0, 0),
                new Vec3d(1, 1, 0),
                new Vec3d(1, 1, 1),
                // TOP (y = 1)
                new Vec3d(0, 1, 1),
                new Vec3d(1, 1, 1),
                new Vec3d(1, 1, 0),
                new Vec3d(0, 1, 0),
                // BOTTOM (y = 0)
                new Vec3d(0, 0, 0),
                new Vec3d(1, 0, 0),
                new Vec3d(1, 0, 1),
                new Vec3d(0, 0, 1)
        );

        for (int i = 0; i < vertexes.size(); i+=4) {
            renderQuad(positionMatrix, normalMatrix, buffer,
                    new Vec3d[]{
                            vertexes.get(i),
                            vertexes.get(i + 1),
                            vertexes.get(i + 2),
                            vertexes.get(i + 3)

                    },
                    minU, minV,
                    maxU, maxV,
                    light, overlay,
                    rgba
            );
        }
    }

    public static void renderQuad(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer buffer, double[] vertexes, float uMin, float vMin, float uMax, float vMax, int light, int overlay, int[] rgba) {
        // Front triangle
        assembleFace(
                new double[]{
                        vertexes[0], vertexes[1], vertexes[2], // Vertex 0
                        vertexes[3], vertexes[4], vertexes[5], // Vertex 1
                        vertexes[6], vertexes[7], vertexes[8], // Vertex 2
                },
                new float[]{
                        uMin, vMin,
                        uMax, vMin,
                        uMax, vMax
                }
        ).render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, false});

        // Back triangle
        // new Vec3d[]{vertexes[0].multiply(1, 1, -1), vertexes[3].multiply(1, 1, -1), vertexes[2].multiply(1, 1, -1)},
        assembleFace(
                new double[]{
                        vertexes[0] * 1, vertexes[1] * 1, vertexes[2] * -1, // Vertex 0 Flipped
                        vertexes[9] * 1, vertexes[10] * 1, vertexes[11] * -1, // Vertex 3 Flipped
                        vertexes[6] * 1, vertexes[7] * 1, vertexes[8] * -1, // Vertex 2 Flipped

                },
                new float[]{
                        uMin, vMin,
                        uMin, vMax,
                        uMax, vMax
                }
        ).render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, true});
    }

    public static void renderQuad(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer buffer, Vec3d[] vertexes, float uMin, float vMin, float uMax, float vMax, int light, int overlay, int[] rgba) {
        // Front triangle
        assembleFace(
                new Vec3d[]{vertexes[0], vertexes[1], vertexes[2]},
                new Vec2f[]{
                        new Vec2f(uMin, vMin),
                        new Vec2f(uMax, vMin),
                        new Vec2f(uMax, vMax)
                }
        ).render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, false});

        // Back triangle
        assembleFace(
                new Vec3d[]{vertexes[0].multiply(1, 1, -1), vertexes[3].multiply(1, 1, -1), vertexes[2].multiply(1, 1, -1)},
                new Vec2f[]{
                        new Vec2f(uMin, vMin),
                        new Vec2f(uMin, vMax),
                        new Vec2f(uMax, vMax)
                }
        ).render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, true});
    }

    public static KittyLibFace assembleFace(double[] vertexes, float[] texCoords) {
        Vec3d[] vertices = new Vec3d[4];
        Vec2f[] texes = new Vec2f[2];

        for (int i = 0; i < vertexes.length; i+=4) {
            vertices[i/4] = new Vec3d(vertexes[i], vertexes[i+1], vertexes[i+2]);
        }

        for (int i = 0; i < texCoords.length; i+=2) {
            texes[i/2] = new Vec2f(texCoords[i], texCoords[i+1]);
        }

        return assembleFace(vertices, texes);
    }

    public static KittyLibFace assembleFace(Vec3d[] vertexes, Vec2f[] texCoords) {
        KittyLibTriangleData[] triangles = new KittyLibTriangleData[vertexes.length + 1];

        // Compute face normal using cross product
        Vec3d edge1 = vertexes[1].subtract(vertexes[0]);
        Vec3d edge2 = vertexes[2].subtract(vertexes[0]);
        Vec3d normal = edge1.crossProduct(edge2).normalize();

        for (int i = 0; i < vertexes.length; i++) {
            triangles[i] = new KittyLibTriangleData(vertexes[i], normal, texCoords[i]);
        }

        triangles[vertexes.length] = triangles[0]; // close loop

        return new KittyLibFace(triangles);
    }
}