package nico.kittylib.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.ItemStack;
import nico.kittylib.api.client.renderer.item.KittyLibTooltipRenderer;
import nico.kittylib.client.KittyLibClientMixinFlags;
import nico.kittylib.internal.client.ImplementedTooltipRendererRegistry;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({DrawContext.class})
public abstract class DrawContextMixin {
    @WrapOperation(method = {"drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V"},
        at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/client/gui/tooltip/TooltipComponent;drawText(Lnet/minecraft/client/font/TextRenderer;IILorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;)V"
        )
    )
    public void kittylib$drawText(TooltipComponent instance, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers, Operation<Void> original) {
        ItemStack stack = KittyLibClientMixinFlags.tooltipItemStack;
        if (stack != null && !stack.isEmpty()) {
            KittyLibTooltipRenderer renderer = ImplementedTooltipRendererRegistry.getTooltipRenderers().getOrDefault(stack.getItem(), null);
            if (renderer != null) {
                renderer.renderText(instance, textRenderer, x, y, matrix, vertexConsumers);
                return;
            }

            KittyLibClientMixinFlags.tooltipItemStack = null;
        }

        original.call(instance, textRenderer, x, y, matrix, vertexConsumers);
    }
}
