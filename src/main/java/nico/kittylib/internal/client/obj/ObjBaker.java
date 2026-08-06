package nico.kittylib.internal.client.obj;

import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import nico.kittylib.api.client.renderer.obj.BakedObjModel;
import nico.kittylib.api.client.renderer.obj.UnbakedObjModel;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Map;

public class ObjBaker {
    protected static final Matrix4f POSITION_IDENTITY = new Matrix4f().identity();
    protected static final Matrix3f NORMAL_IDENTITY = new Matrix3f().identity();

    protected static VertexBuffer bake(float[] vertexData) {
        BufferBuilder buffer = new BufferBuilder(vertexData.length * 4);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);

        for (int i = 0; i < vertexData.length; i += 8) {
            buffer
                    .vertex(POSITION_IDENTITY, vertexData[i], vertexData[i + 1], vertexData[i + 2])
                    .color(0xFF_FF_FF_FF)
                    .texture(vertexData[i + 6], vertexData[i + 7])
                    .overlay(OverlayTexture.DEFAULT_UV)
                    .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                    .normal(NORMAL_IDENTITY, vertexData[i + 3], vertexData[i + 4], vertexData[i + 5])
                    .next();
        }

        BufferBuilder.BuiltBuffer builtBuffer = buffer.end();

        VertexBuffer vertexBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        vertexBuffer.bind();
        vertexBuffer.upload(builtBuffer);
        VertexBuffer.unbind();

        return vertexBuffer;
    }

    public static void bake(UnbakedObjModel unbaked, Map<Identifier, BakedObjModel> bakedModels) {
        VertexBuffer vertexBuffer = bake(unbaked.vertexData());
        bakedModels.put(unbaked.id(), new BakedObjModel(unbaked.id(), vertexBuffer));
    }
}
