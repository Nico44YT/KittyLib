package nico.kittylib.mixin.injections;

import nico.kittylib.api.util.KittyLibIdentifierResolvable;
import nico.kittylib.api.util.KittyLibInjectedMethods;
import net.minecraft.block.Block;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public abstract class BlockMixin implements KittyLibInjectedMethods<Block>, KittyLibIdentifierResolvable {
    @Shadow @Deprecated public abstract RegistryEntry.Reference<Block> getRegistryEntry();

    @Override
    public boolean kittylib$isOfAny(Class<?>... classes) {
        Block thisBlock = (Block)(Object)this;

        for (Class<?> aClass : classes) {
            if(aClass.isInstance(thisBlock)) return true;
        }
        return false;
    }

    @Override
    public boolean kittylib$isOfAny(Block... types) {
        Block thisBlock = (Block)(Object)this;

        for (Block block : types) {
            if(block == thisBlock) return true;
        }

        return false;
    }

    @Override
    public Identifier kittylib$getId() {
        return this.getRegistryEntry().registryKey().getValue();
    }
}
