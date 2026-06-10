package nico.kittylib.mixin.injections;

import nico.kittylib.api.util.KittyLibIdentifierResolvable;
import nico.kittylib.api.util.KittyLibInjectedMethods;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
public abstract class ItemMixin implements KittyLibInjectedMethods<Item>, KittyLibIdentifierResolvable {
    @Shadow @Deprecated public abstract RegistryEntry.Reference<Item> getRegistryEntry();

    @Override
    public boolean kittylib$isOfAny(Class<?>... classes) {
        Item thisItem = (Item)(Object)this;

        for (Class<?> aClass : classes) {
            if(aClass.isInstance(thisItem)) return true;
        }
        return false;
    }

    @Override
    public boolean kittylib$isOfAny(Item... types) {
        Item thisItem = (Item)(Object)this;
        for (Item anItem : types) {
            if(anItem == thisItem) return true;
        }
        return false;
    }

    @Override
    public Identifier kittylib$getId() {
        return this.getRegistryEntry().registryKey().getValue();
    }
}
