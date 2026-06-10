package nico.kittylib.api.util;

public interface KittyLibInjectedMethods<T> {
    default boolean kittylib$isOfAny(Class<?>... classes) {
        return false;
    }

    default boolean kittylib$isOfAny(T... types) {
        return false;
    }
}
