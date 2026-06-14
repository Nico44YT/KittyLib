package nico.kittylib.api.nbt.record;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import nico.kittylib.api.java.consumer.QuadConsumer;
import nico.kittylib.api.java.consumer.TriConsumer;
import nico.kittylib.api.java.function.TriFunction;

import java.util.function.BiFunction;

public class NbtTypeHandler<T> {

    private final QuadConsumer<RegistryWrapper.WrapperLookup, NbtCompound, String, T> put;
    private final TriFunction<RegistryWrapper.WrapperLookup, NbtCompound, String, T> get;

    public NbtTypeHandler(TriConsumer<NbtCompound, String, T> put,
                          BiFunction<NbtCompound, String, T> get) {
        this(
                (wrapper, nbt, string, t) -> put.accept(nbt, string, t),
                (wrapper, nbt, string) -> get.apply(nbt, string)
        );
    }

    public NbtTypeHandler(QuadConsumer<RegistryWrapper.WrapperLookup, NbtCompound, String, T> put,
                          TriFunction<RegistryWrapper.WrapperLookup, NbtCompound, String, T> get) {
        this.put = put;
        this.get = get;
    }

    public void write(NbtCompound nbtCompound, String name, T value) {
        this.write(null, nbtCompound, name, value);
    }

    public void write(RegistryWrapper.WrapperLookup wrapperLookup, NbtCompound nbt, String key, T value) {
        put.accept(wrapperLookup, nbt, key, value);
    }

    public T read(NbtCompound nbt, String key) {
        return this.read(null, nbt, key);
    }

    public T read(RegistryWrapper.WrapperLookup wrapperLookup, NbtCompound nbt, String key) {
        return get.apply(wrapperLookup, nbt, key);
    }
}