package nico.kittylib.api.client.renderer.obj;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.function.Supplier;

public class KittyLibObjModel {
    public static final int[] WHITE = new int[]{255, 255, 255, 255};

    private final Identifier identifier;
    private final float[] vertexData;

    public static Supplier<KittyLibObjModel> create(Identifier id) {
        return () -> KittyLibObjResourceLoader.get().getMap().get(id);
    }

    public KittyLibObjModel(Identifier identifier, Resource resourceFile) {
        this(identifier, KittyLibObjDeserializer.objToFaceList(resourceFile));
    }

    public KittyLibObjModel(Identifier identifier, float[] vertexData) {
        this.identifier = identifier;
        this.vertexData = vertexData;
    }

    public Identifier getId() {
        return this.identifier;
    }

    public void render(Identifier textureIdentifier, VertexConsumerProvider vertexConsumers, MatrixStack matrixStack, int light, int overlay) {
        this.render(vertexConsumers.getBuffer(RenderLayer.getEntityCutout(textureIdentifier)), matrixStack, light, overlay);
    }

    public void render(VertexConsumer vertexConsumer, MatrixStack matrices, int light, int overlay) {
        Matrix4f modelMatrix = matrices.peek().getPositionMatrix();
        Matrix3f normalMatrix = matrices.peek().getNormalMatrix();

        this.render(vertexConsumer, modelMatrix, normalMatrix, light, overlay, WHITE);
    }

    public void render(VertexConsumer buffer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, int overlay, int[] argb) {
        for (int i = 0; i < vertexData.length; i += 8) {
            buffer
                    .vertex(modelMatrix, vertexData[i], vertexData[i + 1], vertexData[i + 2])
                    .color(argb[1], argb[2], argb[3], argb[0]) // .color(r, g, b, a)
                    .texture(vertexData[i + 6], vertexData[i + 7])
                    .overlay(overlay)
                    .light(light)
                    .normal(normalMatrix, vertexData[i + 3], vertexData[i + 4], vertexData[i + 5])
                    .next();
        }
    }

    public static Supplier<KittyLibObjModel> get(Identifier id) {
        return () -> KittyLibObjResourceLoader.get().getMap().get(id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{\n");

        builder.append("    id = ").append(this.identifier.toString()).append("\n");

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
