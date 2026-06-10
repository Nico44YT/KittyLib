package nico.kittylib.mixin.client.tooltip;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.item.ItemStack;
import nico.kittylib.api.client.renderer.KittyLibDrawContext;
import nico.kittylib.api.client.renderer.item.KittyLibTooltipRenderer;
import nico.kittylib.client.KittyLibClientMixinFlags;
import nico.kittylib.internal.client.ImplementedTooltipRendererRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TooltipBackgroundRenderer.class})
public abstract class TooltipBackgroundRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private static void kittylib$render(DrawContext context, int x, int y, int width, int height, int z, CallbackInfo ci) {
        ItemStack stack = KittyLibClientMixinFlags.tooltipItemStack;
        if(stack == null || stack.isEmpty()) return;

        KittyLibTooltipRenderer renderer = ImplementedTooltipRendererRegistry.getTooltipRenderers().getOrDefault(stack.getItem(), null);
        if (renderer != null) {
            renderer.render(stack, new KittyLibDrawContext(context), x, y, width, height, z);
            ci.cancel();
        }
    }
}
