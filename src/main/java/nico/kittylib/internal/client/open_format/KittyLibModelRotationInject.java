package nico.kittylib.internal.client.open_format;

public interface KittyLibModelRotationInject {
    default KittyLibFreeFormRotation kittyLib$getFreeFormRotation() {
        return null;
    }

    default void kittyLib$setFreeFormRotation(KittyLibFreeFormRotation kittyLibFreeFormRotation) {

    }

    default boolean kittyLib$isKittyLibFreeFormSet() {
        return false;
    }
}