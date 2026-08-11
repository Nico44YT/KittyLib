package nico.kittylib.mixin.client.cat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CatCollarFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CatCollarFeatureRenderer.class)
public abstract class CatCollarFeatureRendererMixin {

    private static final List<String> NAMES = List.of(
            "nico",
            "rainbowkitty",
            "pride",
            "rainbow"
    );

    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/CatEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DyeColor;getColorComponents()[F"))
    private float[] kittylib$getColorComponents(DyeColor instance, Operation<float[]> original, @Local(argsOnly = true) CatEntity catEntity) {
        if (catEntity.hasCustomName() && NAMES.contains(catEntity.getName().getString().toLowerCase())) {
            int m = 25;
            int n = catEntity.age / m + catEntity.getId();
            int o = DyeColor.values().length;
            int p = n % o;
            int q = (n + 1) % o;
            float r = ((float) (catEntity.age % m) + 0.1f) / (float) m;
            float[] fs = SheepEntity.getRgbColor(DyeColor.byId(p));
            float[] gs = SheepEntity.getRgbColor(DyeColor.byId(q));
            fs[0] = fs[0] * (1.0F - r) + gs[0] * r;
            fs[1] = fs[1] * (1.0F - r) + gs[1] * r;
            fs[2] = fs[2] * (1.0F - r) + gs[2] * r;

            return fs;
        }
        return original.call(instance);
    }
}
