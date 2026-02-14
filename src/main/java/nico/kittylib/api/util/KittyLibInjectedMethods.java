package nico.kittylib.api.util;

public interface KittyLibInjectedMethods {
    default boolean kittylib$isOfAny(Class<?>... classes) {
        return false;
    }
}
