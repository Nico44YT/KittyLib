package nico.kittylib.mixin.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import nico.kittylib.api.item.ItemHurtListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityHurtCaller {
    @Unique
    private PlayerEntity kittylib$lastPlayerDamage;

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void kittylib$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;

        if (source.getAttacker() instanceof PlayerEntity player) {
            kittylib$lastPlayerDamage = player;
            if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ItemHurtListener itemMethods)
                itemMethods.onEntityDamage(thisEntity, source, amount, Hand.MAIN_HAND, cir);
            if (player.getStackInHand(Hand.OFF_HAND).getItem() instanceof ItemHurtListener itemMethods)
                itemMethods.onEntityDamage(thisEntity, source, amount, Hand.OFF_HAND, cir);
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"), cancellable = true)
    private void kittylib$deathDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;

        if (kittylib$lastPlayerDamage == null) return;

        if (kittylib$lastPlayerDamage.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ItemHurtListener itemMethods)
            itemMethods.onEntityDeathDamage(thisEntity, source, amount, Hand.MAIN_HAND, cir);
        if (kittylib$lastPlayerDamage.getStackInHand(Hand.OFF_HAND).getItem() instanceof ItemHurtListener itemMethods)
            itemMethods.onEntityDeathDamage(thisEntity, source, amount, Hand.OFF_HAND, cir);
    }
}
