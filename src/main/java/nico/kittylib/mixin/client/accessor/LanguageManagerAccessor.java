package nico.kittylib.mixin.client.accessor;

import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LanguageManager.class)
public interface LanguageManagerAccessor {
    @Accessor("ENGLISH_US")
    static LanguageDefinition kittylib$getDefaultLanguage() {
        return null;
    }
}
