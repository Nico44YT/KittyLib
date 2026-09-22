package nico.kittylib.mixin.client.injections;

import net.minecraft.client.render.model.json.Transformation;
import nico.kittylib.internal.client.injections.JsonModelTransformationInjections;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Transformation.class)
public abstract class JsonModelTransformationMixin implements JsonModelTransformationInjections {
    @Shadow
    @Final
    public Vector3f rotation;

    @Shadow
    @Final
    public Vector3f translation;

    @Shadow
    @Final
    public Vector3f scale;

    @Override
    public Transformation kittylib$inverse() {
        return new Transformation(
            rotation.mul(-1),
                translation.mul(-1),
                new Vector3f(1/scale.x, 1/scale.y, 1/scale.z)
        );
    }
}
