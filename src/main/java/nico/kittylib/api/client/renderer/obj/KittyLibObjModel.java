package nico.kittylib.api.client.renderer.obj;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import nico.kittylib.api.client.renderer.KittyLibFace;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.function.Supplier;

public class KittyLibObjModel {
    private final Identifier identifier;
    private final KittyLibFace[] faces;

    public static Supplier<KittyLibObjModel> create(Identifier id) {
        return () -> KittyLibObjResourceLoader.get().getMap().get(id);
    }

    public KittyLibObjModel(Identifier identifier, Resource resourceFile) {
        this(identifier, KittyLibObjDeserializer.objToFaceList(resourceFile).toArray(KittyLibFace[]::new));
    }

    public KittyLibObjModel(Identifier identifier, KittyLibFace[] faces) {
        this.identifier = identifier;
        this.faces = faces;
    }

    public Identifier getId() {
        return this.identifier;
    }

    public KittyLibFace[] bake() {
        return this.faces;
    }

    public void render(Identifier textureIdentifier, VertexConsumerProvider vertexConsumers, MatrixStack matrixStack, int light, int overlay) {
        this.render(vertexConsumers.getBuffer(RenderLayer.getEntityCutout(textureIdentifier)), matrixStack, light, overlay);
    }

    public void render(VertexConsumer vertexConsumer, MatrixStack matrices, int light, int overlay) {
        Matrix4f modelMatrix = matrices.peek().getPositionMatrix();
        Matrix3f normalMatrix = matrices.peek().getNormalMatrix();

        this.render(vertexConsumer, modelMatrix, normalMatrix, light, overlay, new int[]{255, 255, 255, 255}, new boolean[]{false, false, false});
    }

    public void render(VertexConsumer vertexConsumer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba, boolean[] mirror) {
        for (KittyLibFace face : this.faces) {
            face.render(vertexConsumer, modelMatrix, normalMatrix, light, overlay, rgba, mirror);
        }
    }

    public static Supplier<KittyLibObjModel> get(Identifier id) {
        return () -> KittyLibObjResourceLoader.get().getMap().get(id);
    }
}
