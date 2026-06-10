package nico.kittylib.mixin.injections;

import net.minecraft.item.ItemStack;
import nico.kittylib.api.nbt.ItemStackInjection;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackInjection {

}
