package nico.kittylib.mixin.injections;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.util.Identifier;
import nico.kittylib.api.util.KittyLibIdentifierResolvable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BannerPattern.class)
public abstract class BannerPatternMixin implements KittyLibIdentifierResolvable {
    @Shadow
    @Final
    String id;

    @Override
    public Identifier kittylib$getId() {
        return Identifier.tryParse(id);
    }
}
