package nico.kittylib.mixin.client.tooltip;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.TooltipData;
import nico.kittylib.api.client.tooltip.TooltipComponentFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin {
    @Inject(method = "of(Lnet/minecraft/item/tooltip/TooltipData;)Lnet/minecraft/client/gui/tooltip/TooltipComponent;", at = @At("HEAD"), cancellable = true)
    private static void home_smp$of(TooltipData data, CallbackInfoReturnable<TooltipComponent> cir) {
        if (data instanceof TooltipComponentFactory factory) cir.setReturnValue(factory.createTooltipComponent(data));
    }
}
