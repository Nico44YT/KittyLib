package nico.kittylib.api.nbt.record;

import net.minecraft.nbt.NbtCompound;
import nico.kittylib.api.java.consumer.TriConsumer;

import java.util.function.BiFunction;

public class NbtTypeHandler<T> {

    private final TriConsumer<NbtCompound, String, T> put;
    private final BiFunction<NbtCompound, String, T> get;

    public NbtTypeHandler(TriConsumer<NbtCompound, String, T> put,
                          BiFunction<NbtCompound, String, T> get) {
        this.put = put;
        this.get = get;
    }

    public void write(NbtCompound nbt, String key, T value) {
        put.accept(nbt, key, value);
    }

    public T read(NbtCompound nbt, String key) {
        return get.apply(nbt, key);
    }
}