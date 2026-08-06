package nico.kittylib.api.client.renderer.obj;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import nico.kittylib.api.client.renderer.KittyLibRenderUtil;
import nico.kittylib.internal.client.obj.ObjResourceReloadListener;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.function.Supplier;

public record UnbakedObjModel(Identifier id, float[] vertexData) {

    public void render(VertexConsumer buffer, MatrixStack matrixStack, int argb, int light, int overlay) {
        var positionMatrix = matrixStack.peek().getPositionMatrix();
        var normalMatrix = matrixStack.peek().getNormalMatrix();

        this.render(buffer, positionMatrix, normalMatrix, argb, light, overlay);
    }

    public void render(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int argb, int light, int overlay) {
        KittyLibRenderUtil.renderVertexData(buffer, positionMatrix, normalMatrix, argb, light, overlay, this.vertexData);
    }

    public static Supplier<UnbakedObjModel> get(Identifier id) {
        return () -> ObjResourceReloadListener.UNBAKED_MODEL_MAP.get(id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{\n");

        builder.append("    id = ").append(this.id.toString()).append("\n");

        for (int i = 0; i < vertexData.length; i += 8) {
            builder.append(String.format("  %d, x=%f, y=%f, z=%f, nx=%f, ny=%f, nz=%f, u=%f, v=%f\n",
                    i,
                    vertexData[i], vertexData[i + 1], vertexData[i + 2],
                    vertexData[i + 3], vertexData[i + 4], vertexData[i + 5],
                    vertexData[i + 6], vertexData[i + 7])
            );
        }

        return builder.append("}").toString();
    }
}
