package nico.kittylib.mixin.client.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import nico.kittylib.api.client.renderer.entity.boat.KittyLibBoatEntityRenderer;
import nico.kittylib.api.entity.boat.KittyLibBoat;
import nico.kittylib.api.entity.boat.KittyLibChestBoatEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Pair;

@Mixin(value = BoatEntityRenderer.class, priority = 500)
public abstract class BoatEntityRendererMixin {

    @Unique
    private BoatEntity kittylib$entity;
    @Unique
    private EntityRendererFactory.Context kittylib$context;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void kittylib$setInit(EntityRendererFactory.Context ctx, boolean chest, CallbackInfo ci) {
        this.kittylib$context = ctx;
    }

    @Inject(method = "render(Lnet/minecraft/entity/vehicle/BoatEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    public void kittylib$setEntity(BoatEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        this.kittylib$entity = boatEntity;
    }

    @ModifyExpressionValue(method = "render(Lnet/minecraft/entity/vehicle/BoatEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object kittylib$modifyBoatRenderPair(Object original) {
        BoatEntityRenderer renderer = (BoatEntityRenderer) (Object) this;

        if (renderer instanceof KittyLibBoatEntityRenderer kittylibRenderer && kittylib$entity instanceof KittyLibBoat kittylibBoat) {
            return new Pair<>(kittylibRenderer.getTexture(kittylib$entity), kittylibRenderer.createModel(kittylib$context, kittylibBoat, kittylib$entity instanceof KittyLibChestBoatEntity));
        }

        return original;
    }
}