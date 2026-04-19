package nico.kittylib.api.nbt;

import net.minecraft.item.Item;
import net.minecraft.nbt.NbtElement;

import java.util.Optional;
import java.util.function.Supplier;

public interface ItemStackInjection {
    default NbtElement kittylib$getElement(String key) {
        return null;
    }

    default NbtElement kittylib$getElementOrElse(String key, Supplier<NbtElement> elseSupplier) {
        return null;
    }

    default Optional<NbtElement> kittylib$maybeGetElement(String key) {
        return Optional.empty();
    }

    default void kittylib$putElement(String key, NbtElement nbtElement) {

    }
}
