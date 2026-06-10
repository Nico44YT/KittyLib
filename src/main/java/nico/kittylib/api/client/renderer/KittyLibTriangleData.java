package nico.kittylib.api.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public record KittyLibTriangleData(double[] vertex, double[] normal, float[] texturePos) {
    public void render(VertexConsumer buffer, MatrixStack.Entry entry, int light, int overlay, int[] rgba) {
        this.render(buffer, entry, light, overlay, rgba, new boolean[]{false, false, false});
    }

    public void render(VertexConsumer buffer, MatrixStack.Entry entry, int light, int overlay, int[] rgba, boolean[] mirror) {
        buffer
                .vertex(entry.getPositionMatrix(), (float) vertex[0] * (mirror[0]?-1:1), (float) vertex[1] * (mirror[1]?-1:1), (float) vertex[2] * (mirror[2]?-1:1))
                .color(rgba[0], rgba[1], rgba[2], rgba[3])
                .texture( texturePos[0], 1.0f - texturePos[1])
                .overlay(overlay)
                .light(light)
                .normal(entry, (float) normal[0], (float) normal[1], (float) normal[2]);
                //.next();

    }
}