package nico.kittylib.mixin.client.hanging_sign;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import nico.kittylib.api.block.hanging_sign.KittyLibHangingSign;
import nico.kittylib.api.block.hanging_sign.KittyLibHangingSignBlock;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(HangingSignBlockEntityRenderer.class)
public abstract class HangingSignBlockEntityRendererMixin {

    @Mutable
    @Shadow
    @Final
    private Map<WoodType, HangingSignBlockEntityRenderer.HangingSignModel> MODELS;
    @Unique
    private SignBlockEntity kittylib$blockEntity;

    @WrapOperation(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/block/entity/HangingSignBlockEntityRenderer;MODELS:Ljava/util/Map;", opcode = Opcodes.PUTFIELD))
    public void kittylib$init(HangingSignBlockEntityRenderer instance, Map<WoodType, HangingSignBlockEntityRenderer.HangingSignModel> value, Operation<Void> original) {
        MODELS = new HashMap<>(value);
        original.call(instance, MODELS);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void vanity$init(BlockEntityRendererFactory.Context context, CallbackInfo ci) {
        Registries.BLOCK.stream().filter(block -> block instanceof KittyLibHangingSignBlock).forEach(sign -> {
            WoodType type = ((KittyLibHangingSignBlock) sign).getWoodType();
            HangingSignBlockEntityRenderer.HangingSignModel model = new HangingSignBlockEntityRenderer.HangingSignModel(context.getLayerModelPart(EntityModelLayers.createHangingSign(WoodType.OAK)));
            MODELS.put(type, model);
        });
    }

    @Inject(method = "render(Lnet/minecraft/block/entity/SignBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At("HEAD"))
    public void vanity$render(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        this.kittylib$blockEntity = signBlockEntity;
    }

    @Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
    public void vanity$getTextureId(WoodType signType, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if (this.kittylib$blockEntity != null && this.kittylib$blockEntity.getCachedState().getBlock() instanceof KittyLibHangingSign sign) {
            cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, sign.getTexture()));
        }
    }
}