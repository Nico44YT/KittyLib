package nico.kittylib.api.nbt.record;

import net.minecraft.nbt.NbtCompound;

public interface NbtHolder {
    default NbtCompound kittylib$getNbt() {
        throw new RuntimeException();
    }

    default NbtCompound kittylib$getOrCreateNbt() {
        throw new RuntimeException();
    }

    default NbtCompound kittylib$getSubNbt(String key) {
        throw new RuntimeException();
    }

    default NbtCompound kittylib$getOrCreateSubNbt(String key) {
        throw new RuntimeException();
    }

    default void kittylib$setNbt(NbtCompound nbtCompound) {
        throw new RuntimeException();
    }

    default void kittylib$setSubNbt(String key, NbtCompound nbtCompound) {
        throw new RuntimeException();
    }
}
