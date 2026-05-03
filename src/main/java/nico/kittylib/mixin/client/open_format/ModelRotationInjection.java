package nico.kittylib.mixin.client.open_format;

import net.minecraft.client.render.model.json.ModelRotation;
import nico.kittylib.internal.client.open_format.KittyLibFreeFormRotation;
import nico.kittylib.internal.client.open_format.KittyLibModelRotationInject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ModelRotation.class)
public abstract class ModelRotationInjection implements KittyLibModelRotationInject {

    @Unique
    private boolean isSet = false;
    @Unique
    private KittyLibFreeFormRotation kittylib$freeFormRotation;

    @Override
    public KittyLibFreeFormRotation kittyLib$getFreeFormRotation() {
        return kittylib$freeFormRotation;
    }

    @Override
    public void kittyLib$setFreeFormRotation(KittyLibFreeFormRotation rotation) {
        this.isSet = true;
        this.kittylib$freeFormRotation = rotation;
    }

    @Override
    public boolean kittyLib$isKittyLibFreeFormSet() {
        return this.isSet;
    }
}