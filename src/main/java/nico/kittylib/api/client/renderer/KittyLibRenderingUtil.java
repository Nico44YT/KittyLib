package nico.kittylib.api.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class KittyLibRenderingUtil {
    public static class Faces {
        public static final float[] FRONT = { // FRONT (z = 1)
                0, 0, 1,
                1, 0, 1,
                1, 1, 1,
                0, 1, 1
        };

        public static final float[] BACK = { // BACK (z = 0)
                1, 0, 0,
                0, 0, 0,
                0, 1, 0,
                1, 1, 0
        };

        public static final float[] LEFT = { // LEFT (x = 0)
                0, 0, 0,
                0, 0, 1,
                0, 1, 1,
                0, 1, 0
        };

        public static final float[] RIGHT = { // RIGHT (x = 1)
                1, 0, 1,
                1, 0, 0,
                1, 1, 0,
                1, 1, 1
        };

        public static final float[] TOP = { // TOP (y = 1)
                0, 1, 1,
                1, 1, 1,
                1, 1, 0,
                0, 1, 0,
        };

        public static final float[] BOTTOM = { // BOTTOM (y = 0)
                0, 0, 0,
                1, 0, 0,
                1, 0, 1,
                0, 0, 1
        };

    }

    public static void renderFaces(MatrixStack matrixStack, VertexConsumer buffer, int overlay, int light, float[]... faces) {
        int[] defaultColor = new int[]{
                255, 255, 255, 255
        };
        renderFaces(matrixStack, buffer, defaultColor, overlay, light, faces);
    }

    public static void renderFaces(MatrixStack matrixStack, VertexConsumer buffer, int overlay, int light, UVRegion uvRegion, float[]... faces) {
        int[] defaultColor = new int[]{
                255, 255, 255, 255
        };
        renderFaces(matrixStack, buffer, defaultColor, overlay, light, (float) uvRegion.minU(), (float) uvRegion.minV(), (float) uvRegion.maxU(), (float) uvRegion.maxV(), faces);
    }

    public static void renderFaces(MatrixStack matrixStack, VertexConsumer buffer, int[] rgba, int overlay, int light, float[]... faces) {
        renderFaces(matrixStack, buffer, rgba, overlay, light, 0, 0, 1, 1, faces);
    }

    public static void renderFaces(MatrixStack matrixStack, VertexConsumer buffer, int[] rgba, int overlay, int light, float minU, float minV, float maxU, float maxV, float[]... faces) {
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

    public static void renderQuad(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer buffer, float[] vertexes, float uMin, float vMin, float uMax, float vMax, int light, int overlay, int[] rgba) {
        // Front triangle
        assembleFace(
                new float[]{
                        vertexes[0], vertexes[1], vertexes[2], // Vertex 0
                        vertexes[3], vertexes[4], vertexes[5], // Vertex 1
                        vertexes[6], vertexes[7], vertexes[8], // Vertex 2
                },
                new float[]{
                        uMin, vMin,
                        uMax, vMin,
                        uMax, vMax
                }
        ).render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, KittyLibFace.NO_MIRROR);

        // Back triangle
        // new Vec3d[]{vertexes[0].multiply(1, 1, -1), vertexes[3].multiply(1, 1, -1), vertexes[2].multiply(1, 1, -1)},
        assembleFace(
                new float[]{
                        vertexes[0] * 1, vertexes[1] * 1, vertexes[2] * -1, // Vertex 0 Flipped
                        vertexes[9] * 1, vertexes[10] * 1, vertexes[11] * -1, // Vertex 3 Flipped
                        vertexes[6] * 1, vertexes[7] * 1, vertexes[8] * -1, // Vertex 2 Flipped

                },
                new float[]{
                        uMin, vMin,
                        uMin, vMax,
                        uMax, vMax
                }
        ).render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, KittyLibFace.NO_MIRROR);
    }

    public static KittyLibFace assembleFace(float[] vertexes, float[] texCoords) {
        KittyLibTriangleData[] triangles = new KittyLibTriangleData[(vertexes.length / 3) + 1];

        // Compute face normal using cross product
        float[] edge1 = new float[]{ // Subtract vertex 1 from 0
                vertexes[3] - vertexes[0], // X
                vertexes[4] - vertexes[1], // Y
                vertexes[5] - vertexes[2]  // Z
        };

        float[] edge2 = new float[]{ // Subtract vertex 2 from 0
                vertexes[6] - vertexes[0], // X
                vertexes[7] - vertexes[1], // Y
                vertexes[8] - vertexes[2]  // Z
        };

        float[] normal = normalize(crossProduct(edge1, edge2));

        for (int i = 0; i < triangles.length - 1; i++) {
            triangles[i] = new KittyLibTriangleData(
                    new float[]{
                            vertexes[3 * i],
                            vertexes[3 * i + 1],
                            vertexes[3 * i + 2]
                    },
                    normal,
                    new float[]{
                            texCoords[2 * i],
                            texCoords[2 * i + 1]
                    }
            );
        }

        triangles[triangles.length - 1] = triangles[0];
        return new KittyLibFace(triangles);
    }

    public static float[] multiplyMatrix(float[] matrix1, float[] matrix2) {
        return new float[]{
                matrix1[0] * matrix2[0],
                matrix1[1] * matrix2[1],
                matrix1[2] * matrix2[2]
        };
    }

    public static float[] crossProduct(float[] vector1, float[] vector2) {
        return new float[]{
                vector1[1] * vector2[2] - vector1[2] * vector2[1],
                vector1[2] * vector2[0] - vector1[0] * vector2[2],
                vector1[0] * vector2[1] - vector1[1] * vector2[0]
        };
    }

    public static float[] normalize(float[] vector) {
        float magnitude = (float)Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
        return new float[]{
                vector[0] / magnitude,
                vector[1] / magnitude,
                vector[2] / magnitude
        };
    }
}