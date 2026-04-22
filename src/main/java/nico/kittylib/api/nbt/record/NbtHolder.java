package nico.kittylib.api.nbt.record;

import net.minecraft.nbt.NbtCompound;

public interface NbtHolder {
    default NbtCompound kittylib$getNbt() {
        throw new RuntimeException();
    }

    default void kittylib$writeNbt(NbtCompound nbtCompound) {
        throw new RuntimeException();
    }
}
