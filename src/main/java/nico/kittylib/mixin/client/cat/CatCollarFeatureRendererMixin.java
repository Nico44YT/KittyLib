package nico.kittylib.mixin.client.cat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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

@Mixin(CatCollarFeatureRenderer.class)
public abstract class CatCollarFeatureRendererMixin {

    @Unique
    private CatEntity kittylib$catEntity;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/CatEntity;FFFFFF)V", at = @At("HEAD"))
    private void kittylib$obtainEntity(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CatEntity catEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        this.kittylib$catEntity = catEntity;
    }


    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/CatEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DyeColor;getEntityColor()I"))
    private int kittylib$getColorComponents(DyeColor instance, Operation<Integer> original) {
        if(kittylib$catEntity.hasCustomName() && ("nico".equalsIgnoreCase(kittylib$catEntity.getName().getString()) || "rainbowkitty".equalsIgnoreCase(kittylib$catEntity.getName().getString()))) {
            int m = 25;
            int n = kittylib$catEntity.age / m + kittylib$catEntity.getId();
            int o = DyeColor.values().length;
            int p = n % o;
            int q = (n + 1) % o;
            float r = ((float)(kittylib$catEntity.age % m) + 0.1f) / (float)m;
            float[] fs = kittylib$unpackToArray(SheepEntity.getRgbColor(DyeColor.byId(p)));
            float[] gs = kittylib$unpackToArray(SheepEntity.getRgbColor(DyeColor.byId(q)));
            fs[0] = fs[0] * (1.0F - r) + gs[0] * r;
            fs[1] = fs[1] * (1.0F - r) + gs[1] * r;
            fs[2] = fs[2] * (1.0F - r) + gs[2] * r;

            return kittylib$packToInteger(fs);
        }
        return original.call(instance);
    }

    @Unique
    private static float[] kittylib$unpackToArray(int packedColor) {
        return new float[]{
                (float) (packedColor >> 16 & 255) / 255.0F,
                (float) (packedColor >> 8 & 255) / 255.0F,
                (float) (packedColor & 255) / 255.0F
        };
    }

    @Unique
    private static int kittylib$packToInteger(float[] array) {
        return ((int) Math.floor(array[0] * 255) << 16) |
                ((int) Math.floor(array[1] * 255) << 8) |
                ((int) Math.floor(array[2] * 255));
    }
}
