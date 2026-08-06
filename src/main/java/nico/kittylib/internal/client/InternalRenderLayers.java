package nico.kittylib.internal.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

import java.util.OptionalDouble;
import java.util.function.Function;

public class InternalRenderLayers {
    private static final Function<Double, RenderLayer.MultiPhase> DEBUG_LINES;

    public static RenderLayer getDebugLineStrip(double lineWidth) {
        return DEBUG_LINES.apply(lineWidth);
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
    }
}
