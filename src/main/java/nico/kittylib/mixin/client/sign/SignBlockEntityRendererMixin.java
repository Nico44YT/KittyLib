package nico.kittylib.mixin.client.sign;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import nico.kittylib.api.block.sign.KittyLibSign;
import nico.kittylib.api.block.sign.KittyLibSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(SignBlockEntityRenderer.class)
public abstract class SignBlockEntityRendererMixin {
    @Mutable
    @Shadow
    @Final
    private Map<WoodType, SignBlockEntityRenderer.SignModel> typeToModel;

    @Unique
    private SignBlockEntity kittylib$blockEntity;

    @WrapOperation(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/block/entity/SignBlockEntityRenderer;typeToModel:Ljava/util/Map;", opcode = Opcodes.PUTFIELD))
    public void kittylib$init(SignBlockEntityRenderer instance, Map<WoodType, SignBlockEntityRenderer.SignModel> value, Operation<Void> original) {
        typeToModel = new HashMap<>(value);
        original.call(instance, typeToModel);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void kittylib$init(BlockEntityRendererFactory.Context ctx, CallbackInfo ci) {
        Registries.BLOCK.stream().filter(block -> block instanceof KittyLibSignBlock).forEach(sign -> {
            SignBlockEntityRenderer.SignModel model = SignBlockEntityRenderer.createSignModel(ctx.getLayerRenderDispatcher(), WoodType.OAK);
            typeToModel.put(((KittyLibSignBlock)sign).getWoodType(), model);
        });
    }

    @Inject(method = "render(Lnet/minecraft/block/entity/SignBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At("HEAD"))
    public void kittylib$render(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        this.kittylib$blockEntity = signBlockEntity;
    }

    @Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
    public void kittylib$getTextureId(WoodType signType, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if(this.kittylib$blockEntity != null && this.kittylib$blockEntity.getCachedState().getBlock() instanceof KittyLibSign sign) {
            cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, sign.getTexture()));
        }
    }
}