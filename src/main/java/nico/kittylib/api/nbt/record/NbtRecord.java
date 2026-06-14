package nico.kittylib.api.nbt.record;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryWrapper;

import java.util.Optional;

public class NbtRecord<T> {

    private final String key;
    private final NbtTypeHandler<T> typeHandler;

    @SuppressWarnings("unchecked")
    protected NbtRecord(String key, Class<T> valueClass) {
        this(key, valueClass, (NbtTypeHandler<T>) NbtTypeHandlers.getInstance().HANDLERS.get(valueClass));
    }

    protected NbtRecord(String key, Class<T> valueClass, NbtTypeHandler<T> typeHandler) {
        this.key = key;
        this.typeHandler = typeHandler;

        if (this.typeHandler == null)
            throw new RuntimeException("Couldn't find NbtTypeHandler for " + valueClass.getName());
    }

    public static <T> NbtRecord<T> of(String key, Class<T> valueClass) {
        return new NbtRecord<>(key, valueClass);
    }

    public static <T> NbtRecord<T> of(String key, Class<T> valueClass, NbtTypeHandler<T> typeHandler) {
        return new NbtRecord<>(key, valueClass, typeHandler);
    }

    //region // Get //
    public T get(RegistryWrapper.WrapperLookup lookup, NbtHolder nbtHolder) {
        return nbtHolder.kittylib$getNbt() != null ? get(lookup, nbtHolder.kittylib$getNbt()) : null;
    }

    public T getFromSub(RegistryWrapper.WrapperLookup lookup, NbtHolder holder, String subNbtKey) {
        return holder.kittylib$getSubNbt(subNbtKey) != null ? get(lookup, holder.kittylib$getSubNbt(subNbtKey)) : null;
    }

    public T get(RegistryWrapper.WrapperLookup lookup, NbtCompound nbtCompound) {
        return read(lookup, nbtCompound, this);
    }

    public Optional<T> maybeGet(RegistryWrapper.WrapperLookup lookup, NbtHolder holder) {
        return Optional.ofNullable(get(lookup, holder));
    }

    public Optional<T> maybeGetFromSub(RegistryWrapper.WrapperLookup lookup, NbtHolder holder, String subNbtKey) {
        NbtCompound sub = holder.kittylib$getSubNbt(subNbtKey);
        return sub != null ? maybeGet(lookup, sub) : Optional.empty();
    }

    public Optional<T> maybeGet(RegistryWrapper.WrapperLookup lookup, NbtCompound nbtCompound) {
        if (nbtCompound.contains(key)) return Optional.of(get(lookup, nbtCompound));
        return Optional.empty();
    }
    //endregion

    //region // Put //
    public void put(RegistryWrapper.WrapperLookup lookup, NbtHolder holder, T value) {
        NbtCompound nbt = holder.kittylib$getOrCreateNbt();
        put(lookup, nbt, value);
        holder.kittylib$setNbt(nbt);
    }

    public void putToSub(RegistryWrapper.WrapperLookup lookup, NbtHolder holder, String subNbtKey, T value) {
        NbtCompound root = holder.kittylib$getOrCreateNbt();
        NbtCompound sub = root.contains(subNbtKey) ? root.getCompound(subNbtKey) : new NbtCompound();

        put(lookup, sub, value);

        root.put(subNbtKey, sub);
        holder.kittylib$setNbt(root);
    }

    public void put(RegistryWrapper.WrapperLookup lookup, NbtCompound nbtCompound, T value) {
        write(lookup, nbtCompound, this, value);
    }

    public void putIfAbsent(RegistryWrapper.WrapperLookup lookup, NbtHolder holder, T value) {
        NbtCompound nbt = holder.kittylib$getOrCreateNbt();

        if (!nbt.contains(key)) {
            write(lookup, nbt, this, value);
            holder.kittylib$setNbt(nbt);
        }
    }

    public void putToSubIfAbsent(RegistryWrapper.WrapperLookup lookup, NbtHolder holder, String subNbtKey, T value) {
        putIfAbsent(lookup, holder.kittylib$getOrCreateSubNbt(subNbtKey), value);
    }

    public void putIfAbsent(RegistryWrapper.WrapperLookup lookup, NbtCompound nbtCompound, T value) {
        if (!nbtCompound.contains(key)) write(lookup, nbtCompound, this, value);
    }
    //endregion

    //region // IsEmpty //
    public boolean isEmpty(NbtHolder holder) {
        return isEmpty(holder.kittylib$getNbt());
    }

    public boolean isEmpty(NbtHolder holder, String subNbtKey) {
        return isEmpty(holder.kittylib$getSubNbt(subNbtKey));
    }

    public boolean isEmpty(NbtCompound nbtCompound) {
        return !(nbtCompound != null && nbtCompound.contains(key));
    }
    //endregion

    //region // Remove //
    public void remove(NbtHolder holder) {
        NbtCompound nbt = holder.kittylib$getNbt();

        if (nbt != null && nbt.contains(key)) {
            nbt.remove(key);
            holder.kittylib$setNbt(nbt);
        }
    }

    public void removeFromSub(NbtHolder holder, String subNbtKey) {
        NbtCompound root = holder.kittylib$getNbt();

        if (root != null && root.contains(subNbtKey)) {
            NbtCompound sub = root.getCompound(subNbtKey);

            if (sub.contains(key)) {
                sub.remove(key);
                root.put(subNbtKey, sub);
                holder.kittylib$setNbt(root);
            }
        }
    }

    public void remove(NbtCompound nbtCompound) {
        if (nbtCompound != null) nbtCompound.remove(key);
    }
    //endregion

    //region // Helpers //
    protected static <T> void write(RegistryWrapper.WrapperLookup wrapperLookup, NbtCompound nbt, NbtRecord<T> record, T value) {
        record.typeHandler.write(wrapperLookup, nbt, record.key, value);
    }
    protected static <T> T read(RegistryWrapper.WrapperLookup wrapperLookup, NbtCompound nbt, NbtRecord<T> record) {
        return record.typeHandler.read(wrapperLookup, nbt, record.key);
    }
    //endregion
}
