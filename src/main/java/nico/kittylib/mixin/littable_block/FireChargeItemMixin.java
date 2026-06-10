package nico.kittylib.mixin.littable_block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.item.FireChargeItem;
import nico.kittylib.api.block.LittableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireChargeItem.class)
public abstract class FireChargeItemMixin {
    @WrapOperation(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/CandleBlock;canBeLit(Lnet/minecraft/block/BlockState;)Z"))
    public boolean kittylib$canBeLit(BlockState state, Operation<Boolean> original) {
        return original.call(state) || (state.getBlock() instanceof LittableBlock littableBlock && littableBlock.canBeLit(state));
    }
}
