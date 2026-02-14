package nico.kittylib.mixin.client.accessor;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TooltipBackgroundRenderer.class)
public interface TooltipBackgroundRendererAccessor {
    @Invoker("renderBorder")
    static void kittylib$renderBorder(DrawContext context, int x, int y, int width, int height, int z, int startColor, int endColor) {

    }

    @Invoker("renderVerticalLine")
    static void kittylib$renderVerticalLine(DrawContext context, int x, int y, int height, int z, int color) {

    }

    @Invoker("renderVerticalLine")
    static void kittylib$renderVerticalLine(DrawContext context, int x, int y, int height, int z, int startColor, int endColor) {

    }

    @Invoker("renderHorizontalLine")
    static void kittylib$renderHorizontalLine(DrawContext context, int x, int y, int width, int z, int color) {

    }

    @Invoker("renderRectangle")
    static void kittylib$renderRectangle(DrawContext context, int x, int y, int width, int height, int z, int color) {

    }
}
