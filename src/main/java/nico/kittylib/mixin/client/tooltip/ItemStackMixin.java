package nico.kittylib.mixin.client.tooltip;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import nico.kittylib.client.KittyLibClientMixinFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ItemStack.class)
@Environment(EnvType.CLIENT)
public abstract class ItemStackMixin {

    @Inject(method = "getTooltipData", at = @At("HEAD"))
    public void kittylib$getTooltipData(CallbackInfoReturnable<Optional<TooltipData>> cir) {
        KittyLibClientMixinFlags.tooltipItemStack = (ItemStack) (Object) this;
    }
}
