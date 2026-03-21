package nico.kittylib.mixin.injections;

import nico.kittylib.api.util.KittyLibIdentifierResolvable;
import nico.kittylib.api.util.KittyLibInjectedMethods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements KittyLibInjectedMethods<Entity>, KittyLibIdentifierResolvable {
    @Shadow public abstract EntityType<?> getType();

    @Override
    public boolean kittylib$isOfAny(Class<?>... classes) {
        Entity thisEntity = (Entity)(Object)this;

        for (Class<?> aClass : classes) {
            if(aClass.isInstance(thisEntity)) return true;
        }
        return false;
    }

    @Override
    public boolean kittylib$isOfAny(Entity... types) {
        Entity thisEntity = (Entity)(Object)this;

        for(Entity entity : types) {
            if(entity == thisEntity) return true;
        }

        return false;
    }

    @Override
    public Identifier kittylib$getId() {
        return this.getType().getRegistryEntry().registryKey().getValue();
    }
}
