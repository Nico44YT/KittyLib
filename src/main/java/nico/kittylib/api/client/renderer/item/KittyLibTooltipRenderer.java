package nico.kittylib.api.client.renderer.item;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.ItemStack;
import nico.kittylib.api.client.renderer.KittyLibDrawContext;
import nico.kittylib.mixin.client.accessor.TooltipBackgroundRendererAccessor;
import org.joml.Matrix4f;

public interface KittyLibTooltipRenderer {
    void render(ItemStack var1, KittyLibDrawContext var2, int var3, int var4, int var5, int var6, int var7);

    default void renderText(TooltipComponent tooltip, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        tooltip.drawText(textRenderer, x, y, matrix, vertexConsumers);
    }

    default void renderHorizontalLine(ItemStack stack, KittyLibDrawContext context, int x, int y, int width, int height, int z, int startColor, int endColor) {
        TooltipBackgroundRendererAccessor.kittylib$renderBorder(context, x, y, width, height, z, startColor, endColor);
    }

    default void renderVerticalLine(ItemStack stack, KittyLibDrawContext context, int x, int y, int height, int z, int color) {
        TooltipBackgroundRendererAccessor.kittylib$renderVerticalLine(context, x, y, height, z, color);
    }

    default void renderVerticalLine(ItemStack stack, KittyLibDrawContext context, int x, int y, int height, int z, int startColor, int endColor) {
        TooltipBackgroundRendererAccessor.kittylib$renderVerticalLine(context, x, y, height, z, startColor, endColor);
    }

    default void renderHorizontalLine(ItemStack stack, KittyLibDrawContext context, int x, int y, int width, int z, int color) {
        TooltipBackgroundRendererAccessor.kittylib$renderHorizontalLine(context, x, y, width, z, color);
    }

    default void renderHorizontalLine(ItemStack stack, KittyLibDrawContext context, int x, int y, int width, int z, int startColor, int endColor) {
        context.fillGradient(x, y, x + width, y + 1, z, startColor, endColor);
    }

    default void renderRectangle(ItemStack stack, KittyLibDrawContext context, int x, int y, int width, int height, int z, int color) {
        TooltipBackgroundRendererAccessor.kittylib$renderRectangle(context, x, y, width, height, z, color);
    }

    default void renderBorder(ItemStack stack, KittyLibDrawContext context, int x, int y, int width, int height, int z, int startColor, int endColor) {
        TooltipBackgroundRendererAccessor.kittylib$renderBorder(context, x, y, width, height, z, startColor, endColor);
    }

    @FunctionalInterface
    public interface Factory {
        KittyLibTooltipRenderer apply();
    }
}
