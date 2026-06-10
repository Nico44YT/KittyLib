package nico.kittylib.mixin.injections;

import net.minecraft.nbt.NbtCompound;
import nico.kittylib.api.nbt.record.NbtHolder;
import nico.kittylib.api.util.KittyLibIdentifierResolvable;
import nico.kittylib.api.util.KittyLibInjectedMethods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements KittyLibInjectedMethods<Entity>, NbtHolder, KittyLibIdentifierResolvable {
    @Shadow public abstract EntityType<?> getType();

    @Shadow
    protected abstract void readCustomDataFromNbt(NbtCompound nbt);

    @Shadow
    protected abstract void writeCustomDataToNbt(NbtCompound nbt);

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

    @Override
    public NbtCompound kittylib$getNbt() {
        NbtCompound nbt = new NbtCompound();
        writeCustomDataToNbt(nbt);
        return nbt;
    }

    @Override
    public NbtCompound kittylib$getOrCreateNbt() {
        // Entities always serialize into a fresh compound,
        // so "getOrCreate" is effectively identical
        return kittylib$getNbt();
    }

    @Override
    public void kittylib$setNbt(NbtCompound nbtCompound) {
        readCustomDataFromNbt(nbtCompound);
    }

    @Override
    public NbtCompound kittylib$getSubNbt(String key) {
        NbtCompound nbt = kittylib$getNbt();
        return nbt.contains(key) ? nbt.getCompound(key) : null;
    }

    @Override
    public NbtCompound kittylib$getOrCreateSubNbt(String key) {
        NbtCompound nbt = kittylib$getNbt();

        if (!nbt.contains(key)) {
            NbtCompound newTag = new NbtCompound();
            nbt.put(key, newTag);
            kittylib$setNbt(nbt);
            return newTag;
        }

        return nbt.getCompound(key);
    }

    @Override
    public void kittylib$setSubNbt(String key, NbtCompound nbtCompound) {
        NbtCompound nbt = kittylib$getNbt();
        nbt.put(key, nbtCompound);
        kittylib$setNbt(nbt);
    }
}
