package nico.kittylib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import nico.kittylib.api.entity.boat.KittyLibBoat;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @Shadow
    @Final
    private static TrackedData<Integer> BOAT_TYPE;

    @WrapOperation(method = "initDataTracker", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;startTracking(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"))
    public <T> void kittylib$initDataTracker(DataTracker instance, TrackedData<T> key, T initialValue, Operation<Void> original) {
        BoatEntity entity = (BoatEntity)(Object)this;

        if(entity instanceof KittyLibBoat && key.equals(BOAT_TYPE)) return;

        original.call(instance, key, initialValue);
    }
}