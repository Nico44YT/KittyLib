package nico.kittylib.api.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public record KittyLibFace(KittyLibTriangleData... data) {
    public void render(VertexConsumer buffer, MatrixStack matrixStack, int light, int overlay, int[] rgba) {
        this.render(buffer, matrixStack.peek(), light, overlay, rgba);
    }

    public void render(VertexConsumer buffer, MatrixStack.Entry entry, int light, int overlay, int[] rgba) {
        this.render(buffer, entry, light, overlay, rgba, new boolean[]{false, false, false});
    }

    public void render(VertexConsumer buffer, MatrixStack.Entry entry, int light, int overlay, int[] rgba, boolean[] mirror) {
        if(mirror[0] || mirror[1] || mirror[2]) {
            for (int i = data.length - 1; i >= 0; i--) {
                data[i].render(buffer, entry, light, overlay, rgba, mirror);
            }
        } else {
            for (int i = 0; i < data.length; i++) {
                data[i].render(buffer, entry, light, overlay, rgba, mirror);
            }
        }
    }
}