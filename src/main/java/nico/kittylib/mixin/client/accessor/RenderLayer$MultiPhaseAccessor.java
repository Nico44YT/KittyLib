package nico.kittylib.mixin.client.accessor;

import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderLayer.MultiPhase.class)
public interface RenderLayer$MultiPhaseAccessor {
    @Accessor("phases")
    RenderLayer.MultiPhaseParameters kittylib$getPhases();
}
