package nico.kittylib.api.client.renderer.obj;

import com.mojang.blaze3d.systems.RenderSystem;
import jdk.jfr.Experimental;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Experimental
public record BakedObjModel(Identifier identifier, VertexBuffer vertexBuffer) implements AutoCloseable {
    public static Map<Identifier, BakedObjModel> BAKED_MODELS = new HashMap<>();

    public Identifier getId() {
        return this.identifier;
    }

    public void render(RenderLayer renderLayer, MatrixStack matrices, int argb) {
        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();

        RenderSystem.getModelViewStack().push();
        RenderSystem.getModelViewStack().multiplyPositionMatrix(positionMatrix);
        RenderSystem.applyModelViewMatrix();

        renderLayer.startDrawing();

        ShaderProgram shader = RenderSystem.getShader();

        final float[] prevColors = new float[]{
                RenderSystem.getShaderColor()[0],
                RenderSystem.getShaderColor()[1],
                RenderSystem.getShaderColor()[2],
                RenderSystem.getShaderColor()[3],
        };

        float alpha = ((argb >> 24) & 0xFF) / 255f;
        float red = ((argb >> 16) & 0xFF) / 255f;
        float green = ((argb >> 8) & 0xFF) / 255f;
        float blue = (argb & 0xFF) / 255f;

        RenderSystem.setShaderColor(red, green, blue, alpha);

        vertexBuffer.bind();
        vertexBuffer.draw(
                RenderSystem.getModelViewMatrix(),
                RenderSystem.getProjectionMatrix(),
                shader
        );
        VertexBuffer.unbind();

        renderLayer.endDrawing();

        RenderSystem.getModelViewStack().pop();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setShaderColor(prevColors[0], prevColors[1], prevColors[2], prevColors[3]);
    }

    @Override
    public void close() {
        vertexBuffer.close();
    }

    public static Supplier<BakedObjModel> get(Identifier identifier) {
        return () -> BAKED_MODELS.get(identifier);
    }
}
