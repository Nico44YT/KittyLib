package nico.kittylib.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Mixin(TranslationStorage.class)
public abstract class TranslationStorageMixin {
    @Shadow
    @Final
    private static Logger LOGGER;

    @Unique
    private static ResourceManager kittylib$resourceManager;
    @Unique
    private static Identifier kittylib$id;

    @Inject(method = "load(Lnet/minecraft/resource/ResourceManager;Ljava/util/List;Z)Lnet/minecraft/client/resource/language/TranslationStorage;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resource/language/TranslationStorage;load(Ljava/lang/String;Ljava/util/List;Ljava/util/Map;)V"))
    private static void kittylib$load(ResourceManager resourceManager, List<String> definitions, boolean rightToLeft, CallbackInfoReturnable<TranslationStorage> cir, @Local Map<String, String> map, @Local Identifier identifier, @Local(ordinal = 0) String definition) {
        kittylib$resourceManager = resourceManager;
        kittylib$id = Identifier.of(identifier.getNamespace(), String.format(Locale.ROOT, "lang/%s.kittylib.json", definition));
    }

    @Inject(method = "load(Ljava/lang/String;Ljava/util/List;Ljava/util/Map;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Language;load(Ljava/io/InputStream;Ljava/util/function/BiConsumer;)V", shift = At.Shift.BEFORE))
    private static void kittylib$load(String langCode, List<Resource> resourceRefs, Map<String, String> translations, CallbackInfo ci, @Local Resource resource) {
        List<Resource> myResources = kittylib$resourceManager.getAllResources(kittylib$id);

        myResources.forEach(myResource -> {
            try (InputStream inputStream = myResource.getInputStream()) {
                Language.load(inputStream, translations::put);
            } catch (IOException iOException) {
                LOGGER.warn("[KittyLib] Failed to load translations for {} from {}/{}", langCode, resource.getResourcePackName(), kittylib$id, iOException);
            }
        });
    }
}
