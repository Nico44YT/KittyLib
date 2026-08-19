package nico.kittylib.api.client.renderer;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import nico.kittylib.KittyLibMain;
import nico.kittylib.api.client.renderer.obj.UnbakedObjModel;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.function.Supplier;

public class KittyLibRenderUtil {
    public static Supplier<UnbakedObjModel> PLANE = UnbakedObjModel.get(KittyLibMain.id("plane"));
    public static Supplier<UnbakedObjModel> CUBE = UnbakedObjModel.get(KittyLibMain.id("cube"));

    private static final float[] DEBUG_AXIS_LINES = new float[]{
            1, 0, 0,        // x, y, z
            0xFF_FF_00_00,  // color
            0, 0, 0,
            0xFF_FF_00_00,

            0, 1, 0,
            0xFF_00_FF_00,
            0, 0, 0,
            0xFF_00_FF_00,

            0, 0, 1,
            0xFF_00_00_FF,
            0, 0, 0,
            0xFF_00_00_FF
    };

    /**
     *
     * @param vertexConsumerProvider
     * @param matrixStack
     */
    public static void renderDebugAxis(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack) {
        renderDebugAxis(vertexConsumerProvider, matrixStack, 1);
    }

    /**
     *
     * @param vertexConsumerProvider
     * @param matrixStack
     * @param scale
     */
    public static void renderDebugAxis(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, float scale) {
        final VertexConsumer debugLines = vertexConsumerProvider.getBuffer(RenderLayer.getDebugLineStrip(1.0));
        final Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();

        matrixStack.scale(scale, scale, scale);

        for (int i = 0; i < DEBUG_AXIS_LINES.length; i += 4) {
            float x = DEBUG_AXIS_LINES[i];
            float y = DEBUG_AXIS_LINES[i + 1];
            float z = DEBUG_AXIS_LINES[i + 2];

            float color = DEBUG_AXIS_LINES[i + 3];

            debugLines
                    .vertex(positionMatrix, x, y, z)
                    .color((int) color)
                    .next();
        }

        matrixStack.scale(1 / scale, 1 / scale, 1 / scale);
    }

    /**
     *
     * @param x0
     * @param y0
     * @param z0
     * @param x1
     * @param y1
     * @param z1
     * @param buffer
     * @param matrixStack
     * @param argb
     * @param light
     * @param overlay
     */
    public static void renderCube(float x0, float y0, float z0, float x1, float y1, float z1, VertexConsumer buffer, MatrixStack matrixStack, int argb, int light, int overlay) {
        renderVertexData(x0, y0, z0, x1, y1, z1, buffer, matrixStack, argb, light, overlay, CUBE.get().vertexData());
    }

    /**
     *
     * @param buffer
     * @param matrixStack
     * @param argb
     * @param light
     * @param overlay
     */
    public static void renderCube(VertexConsumer buffer, MatrixStack matrixStack, int argb, int light, int overlay) {
        renderVertexData(buffer, matrixStack.peek().getPositionMatrix(), matrixStack.peek().getNormalMatrix(), argb, light, overlay, CUBE.get().vertexData());
    }

    /**
     *
     * @param x0
     * @param z0
     * @param x1
     * @param z1
     * @param buffer
     * @param matrixStack
     * @param argb
     * @param light
     * @param overlay
     * @param u
     * @param v
     */
    public static void renderPlane(float x0, float z0, float x1, float z1, VertexConsumer buffer, MatrixStack matrixStack, int argb, int light, int overlay, float u, float v) {
        renderVertexDataWithFixedUV(x0, 0, z0, x1, 1, z1, buffer, matrixStack, argb, light, overlay, PLANE.get().vertexData(), u, v);
    }

    /**
     *
     * @param x0
     * @param z0
     * @param x1
     * @param z1
     * @param buffer
     * @param matrixStack
     * @param argb
     * @param light
     * @param overlay
     */
    public static void renderPlane(float x0, float z0, float x1, float z1, VertexConsumer buffer, MatrixStack matrixStack, int argb, int light, int overlay) {
        renderVertexData(x0, 0, z0, x1, 1, z1, buffer, matrixStack, argb, light, overlay, PLANE.get().vertexData());
    }

    /**
     *
     * @param buffer
     * @param matrixStack
     */
    public static void renderPlane(VertexConsumer buffer, MatrixStack matrixStack) {
        renderPlane(buffer, matrixStack, 0xFF_FF_FF_FF, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV);
    }

    /**
     *
     * @param buffer
     * @param matrixStack
     * @param argb
     * @param light
     * @param overlay
     */
    public static void renderPlane(VertexConsumer buffer, MatrixStack matrixStack, int argb, int light, int overlay) {
        renderPlane(buffer, matrixStack.peek().getPositionMatrix(), matrixStack.peek().getNormalMatrix(), argb, light, overlay);
    }

    /**
     *
     * @param buffer
     * @param positionMatrix
     * @param normalMatrix
     * @param argb
     * @param light
     * @param overlay
     */
    public static void renderPlane(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int argb, int light, int overlay) {
        PLANE.get().render(buffer, positionMatrix, normalMatrix, argb, light, overlay);
    }

    /**
     *
     * @param x0
     * @param y0
     * @param z0
     * @param x1
     * @param y1
     * @param z1
     * @param buffer
     * @param matrixStack
     * @param argb
     * @param light
     * @param overlay
     * @param vertexData
     * @param u
     * @param v
     */
    public static void renderVertexDataWithFixedUV(float x0, float y0, float z0, float x1, float y1, float z1, VertexConsumer buffer, MatrixStack matrixStack, int argb, int light, int overlay, float[] vertexData, float u, float v) {
        float scaleX = x1 - x0;
        float scaleY = y1 - y0;
        float scaleZ = z1 - z0;

        matrixStack.push();

        matrixStack.translate(0.5, 0, 0.5);

        matrixStack.translate(-x0, y0, -z0);
        matrixStack.scale(scaleX, scaleY, scaleZ);

        matrixStack.translate(-0.5, 0, -0.5);

        renderVertexDataWithFixedUV(buffer, matrixStack.peek().getPositionMatrix(), matrixStack.peek().getNormalMatrix(), argb, light, overlay, vertexData, u, v);
        matrixStack.pop();
    }

    /**
     *
     * @param x0
     * @param y0
     * @param z0
     * @param x1
     * @param y1
     * @param z1
     * @param buffer
     * @param matrixStack
     * @param argb
     * @param light
     * @param overlay
     * @param vertexData
     */
    public static void renderVertexData(float x0, float y0, float z0, float x1, float y1, float z1, VertexConsumer buffer, MatrixStack matrixStack, int argb, int light, int overlay, float[] vertexData) {
        float scaleX = x1 - x0;
        float scaleY = y1 - y0;
        float scaleZ = z1 - z0;

        matrixStack.push();

        matrixStack.translate(0.5, 0, 0.5);

        matrixStack.translate(-x0, y0, -z0);
        matrixStack.scale(scaleX, scaleY, scaleZ);

        matrixStack.translate(-0.5, 0, -0.5);

        renderVertexData(buffer, matrixStack.peek().getPositionMatrix(), matrixStack.peek().getNormalMatrix(), argb, light, overlay, vertexData);
        matrixStack.pop();
    }

    /**
     *
     * @param buffer         the vertex consumer
     * @param positionMatrix
     * @param normalMatrix
     * @param argb           0xAA_RR_GG_BB
     * @param overlay
     * @param light
     * @param vertexData     a float array containing multiple vertieces,
     *                       0 = x;
     *                       1 = y;
     *                       2 = z;
     *                       3 = nx;
     *                       4 = ny;
     *                       5 = nz;
     *                       6 = u;
     *                       7 = v;
     */
    public static void renderVertexData(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int argb, int light, int overlay, float[] vertexData) {
        for (int i = 0; i < vertexData.length; i += 8) {
            float x = vertexData[i];
            float y = vertexData[i + 1];
            float z = vertexData[i + 2];

            float nx = vertexData[i + 3];
            float ny = vertexData[i + 4];
            float nz = vertexData[i + 5];

            float u = vertexData[i + 6];
            float v = vertexData[i + 7];

            renderVertex(buffer, positionMatrix, normalMatrix, argb, light, overlay, x, y, z, nx, ny, nz, u, v);
        }
    }

    /**
     *
     * @param buffer
     * @param positionMatrix
     * @param normalMatrix
     * @param argb
     * @param light
     * @param overlay
     * @param vertexData
     * @param u
     * @param v
     */
    public static void renderVertexDataWithFixedUV(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int argb, int light, int overlay, float[] vertexData, float u, float v) {
        for (int i = 0; i < vertexData.length; i += 8) {
            float x = vertexData[i];
            float y = vertexData[i + 1];
            float z = vertexData[i + 2];

            float nx = vertexData[i + 3];
            float ny = vertexData[i + 4];
            float nz = vertexData[i + 5];

            renderVertex(buffer, positionMatrix, normalMatrix, argb, light, overlay, x, y, z, nx, ny, nz, u, v);
        }
    }

    /**
     *
     * @param buffer
     * @param positionMatrix
     * @param normalMatrix
     * @param argb
     * @param light
     * @param overlay
     * @param x
     * @param y
     * @param z
     * @param nx
     * @param ny
     * @param nz
     * @param u
     * @param v
     */
    public static void renderVertex(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int argb, int light, int overlay, float x, float y, float z, float nx, float ny, float nz, float u, float v) {
        buffer
                .vertex(positionMatrix, x, y, z)
                .color(argb)
                .texture(u, v)
                .overlay(overlay)
                .light(light)
                .normal(normalMatrix, nx, ny, nz)
                .next();
    }

    /**
     *
     * @param buffer
     * @param positionMatrix
     * @param normalMatrix
     * @param argb
     * @param light
     * @param overlay
     * @param uvRegion
     */
    public static void renderPlane(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int argb, int light, int overlay, UVRegion uvRegion) {
        renderVertexData(buffer, positionMatrix, normalMatrix, argb, light, overlay, uvRegion, PLANE.get().vertexData());
    }

    /**
     *
     * @param buffer
     * @param positionMatrix
     * @param normalMatrix
     * @param argb
     * @param light
     * @param overlay
     * @param uvRegion
     * @param vertexData
     */
    @ApiStatus.Internal
    public static void renderVertexData(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int argb, int light, int overlay, UVRegion uvRegion, float[] vertexData) {
        for (int i = 0; i < vertexData.length; i += 8) {
            float x = vertexData[i];
            float y = vertexData[i + 1];
            float z = vertexData[i + 2];

            float nx = vertexData[i + 3];
            float ny = vertexData[i + 4];
            float nz = vertexData[i + 5];

            float u = vertexData[i + 6];
            float v = vertexData[i + 7];

            if (u == 0) u = uvRegion.minU();
            else u = uvRegion.maxU();

            if (v == 0) v = uvRegion.minV();
            else v = uvRegion.maxV();

            renderVertex(buffer, positionMatrix, normalMatrix, argb, light, overlay, x, y, z, nx, ny, nz, u, v);
        }
    }
}
