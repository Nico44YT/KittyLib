package nico.kittylib.internal.client;

import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import nico.kittylib.KittyLibMain;

import java.util.OptionalDouble;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InternalRenderLayers {
    private static RenderPhase.ShaderProgram TEXTURED_NORMAL_MAP_PROGRAM;

    private static final Function<Double, RenderLayer.MultiPhase> DEBUG_LINES;
    private static final BiFunction<Identifier, Identifier, RenderLayer.MultiPhase> TEXTURED_NORMAL_MAP;

    public static RenderLayer getDebugLineStrip(double lineWidth) {
        return DEBUG_LINES.apply(lineWidth);
    }

    public static RenderLayer getTexturedNormal(Identifier texture, Identifier normalTexture) {
        return TEXTURED_NORMAL_MAP.apply(texture, normalTexture);
    }

    public static void init() {
        CoreShaderRegistrationCallback.EVENT.register(context -> {
            context.register(
                    KittyLibMain.id("textured_normal_map"),
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    glShader -> TEXTURED_NORMAL_MAP_PROGRAM = new RenderPhase.ShaderProgram(() -> glShader)
            );
        });
    }

    static {
        DEBUG_LINES = (lineWidth) -> {
            return RenderLayer.of("kittylib:debug_lines", VertexFormats.LINES, VertexFormat.DrawMode.DEBUG_LINES, 256,
                    RenderLayer.MultiPhaseParameters.builder()
                            .program(RenderLayer.LINES_PROGRAM)
                            .transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
                            .cull(RenderLayer.DISABLE_CULLING)
                            .writeMaskState(RenderLayer.ALL_MASK)
                            .layering(RenderLayer.POLYGON_OFFSET_LAYERING)
                            .target(RenderLayer.ITEM_ENTITY_TARGET)
                            .lineWidth(new RenderPhase.LineWidth(OptionalDouble.of(lineWidth)))
                            .build(false)
            );
        };

        TEXTURED_NORMAL_MAP = Util.memoize((texture, normalTexture) -> {
            RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                    .program(TEXTURED_NORMAL_MAP_PROGRAM)
                    .texture(RenderPhase.Textures.create()
                            .add(texture, false, true) // <- Albedo | Sampler 0
                            .add(texture, false, false) // <- Overlay | Sampler 1
                            .add(texture, false, false) // <- Lightmap | Sampler 2
                            .add(normalTexture, true, false) // Normal Map | Sampler 3
                            .build()
                    )
                    .transparency(RenderLayer.NO_TRANSPARENCY)
                    .lightmap(RenderLayer.ENABLE_LIGHTMAP)
                    .overlay(RenderLayer.ENABLE_OVERLAY_COLOR)
                    .build(true);
            return RenderLayer.of("kittylib:textured_normal_map", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, false, multiPhaseParameters);
        });
    }
}
