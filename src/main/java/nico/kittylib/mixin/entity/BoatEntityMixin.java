package nico.kittylib.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.vehicle.BoatEntity;
import nico.kittylib.api.entity.boat.KittyLibBoat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @Shadow
    @Final
    private static TrackedData<Integer> BOAT_TYPE;

    @WrapOperation(method = "initDataTracker", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker$Builder;add(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)Lnet/minecraft/entity/data/DataTracker$Builder;"))
    public <T> DataTracker.Builder kittylib$initDataTracker(DataTracker.Builder instance, TrackedData<T> key, T initialValue, Operation<DataTracker.Builder> original) {
        BoatEntity entity = (BoatEntity) (Object) this;

        if (entity instanceof KittyLibBoat && key.equals(BOAT_TYPE)) return instance;

        original.call(instance, key, initialValue);
        return instance;
    }
}