package nico.kittylib.mixin.client;

import it.unimi.dsi.fastutil.ints.IntComparators;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import nico.kittylib.api.client.renderer.ModelRegistrationEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.List;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    private void kittylib$addModels(CallbackInfo ci) {
        List<Identifier> ids = new LinkedList<>();

        ModelRegistrationEvent.entries.stream()
                .sorted((a, b) -> IntComparators.NATURAL_COMPARATOR.compare(a.priority(), b.priority()))
                .forEach(entry -> entry.modelList().accept(ids));

        ids.forEach(id -> {
            if (id instanceof ModelIdentifier modelIdentifier) this.addModel(modelIdentifier);
            else this.addModel(new ModelIdentifier(id, "inventory"));
        });
    }
}
