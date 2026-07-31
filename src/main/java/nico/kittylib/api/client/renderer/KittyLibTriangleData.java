package nico.kittylib.api.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public record KittyLibTriangleData(float[] vertex, float[] normal, float[] texturePos) {
    public void render(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba) {
        this.render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, false});
    }

    public void render(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba, boolean[] mirror) {
        buffer
                .vertex(positionMatrix, vertex[0] * (mirror[0]?-1:1), vertex[1] * (mirror[1]?-1:1), vertex[2] * (mirror[2]?-1:1))
                .color(rgba[0], rgba[1], rgba[2], rgba[3])
                .texture( texturePos[0], 1.0f - texturePos[1])
                .overlay(overlay)
                .light(light)
                .normal(normalMatrix, normal[0], normal[1], normal[2])
                .next();

    }
}