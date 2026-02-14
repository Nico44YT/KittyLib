package nico.kittylib.mixin.client;

import nico.kittylib.api.block.hanging_sign.KittyLibHangingSign;
import nico.kittylib.api.block.sign.KittyLibSign;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(TexturedRenderLayers.class)
public abstract class TexturedRenderLayersMixin {
    @Shadow
    @Final
    public static Identifier SIGNS_ATLAS_TEXTURE;

    @Inject(method = "addDefaultTextures", at = @At("TAIL"))
    private static void vanity$addSignTextures(Consumer<SpriteIdentifier> adder, CallbackInfo ci) {
        Registries.BLOCK.forEach(block -> {
            if(block instanceof KittyLibSign sign) {
                adder.accept(new SpriteIdentifier(SIGNS_ATLAS_TEXTURE, sign.getTexture()));
            }

            if(block instanceof KittyLibHangingSign sign) {
                adder.accept(new SpriteIdentifier(SIGNS_ATLAS_TEXTURE, sign.getTexture()));
            }
        });
    }
}