package nico.kittylib.api.nbt.record;

import net.minecraft.nbt.NbtCompound;

import java.util.Optional;

public class NbtRecord<T> {

    private final String key;
    private final NbtTypeHandler<T> type;

    @SuppressWarnings("unchecked")
    protected NbtRecord(String key, Class<T> valueClass) {
        this.key = key;
        this.type = (NbtTypeHandler<T>) NbtTypeHandler.HANDLERS.get(valueClass);

        if (type == null) throw new RuntimeException("Couldn't find NbtTypeHandler for " + valueClass.getName());
    }

    public static <T> NbtRecord<T> of(String key, Class<T> valueClass) {
        return new NbtRecord<>(key, valueClass);
    }

    //region // Get //
    public T get(NbtHolder nbtHolder) {
        return nbtHolder.kittylib$getNbt() != null ? get(nbtHolder.kittylib$getNbt()) : null;
    }

    public T getFromSub(NbtHolder holder, String subNbtKey) {
        return holder.kittylib$getSubNbt(subNbtKey) != null ? get(holder.kittylib$getSubNbt(subNbtKey)) : null;
    }

    public T get(NbtCompound nbtCompound) {
        return read(nbtCompound, this);
    }

    public Optional<T> maybeGet(NbtHolder holder) {
        return Optional.ofNullable(get(holder));
    }

    public Optional<T> maybeGetFromSub(NbtHolder holder, String subNbtKey) {
        NbtCompound sub = holder.kittylib$getSubNbt(subNbtKey);
        return sub != null ? maybeGet(sub) : Optional.empty();
    }

    public Optional<T> maybeGet(NbtCompound nbtCompound) {
        if (nbtCompound.contains(key)) return Optional.of(get(nbtCompound));
        return Optional.empty();
    }
    //endregion

    //region // Put //
    public void put(NbtHolder holder, T value) {
        NbtCompound nbt = holder.kittylib$getOrCreateNbt();
        put(nbt, value);
        holder.kittylib$setNbt(nbt);
    }

    public void putToSub(NbtHolder holder, String subNbtKey, T value) {
        NbtCompound root = holder.kittylib$getOrCreateNbt();
        NbtCompound sub = root.contains(subNbtKey) ? root.getCompound(subNbtKey) : new NbtCompound();

        put(sub, value);

        root.put(subNbtKey, sub);
        holder.kittylib$setNbt(root);
    }

    public void put(NbtCompound nbtCompound, T value) {
        write(nbtCompound, this, value);
    }

    public void putIfAbsent(NbtHolder holder, T value) {
        NbtCompound nbt = holder.kittylib$getOrCreateNbt();

        if (!nbt.contains(key)) {
            write(nbt, this, value);
            holder.kittylib$setNbt(nbt);
        }
    }

    public void putToSubIfAbsent(NbtHolder holder, String subNbtKey, T value) {
        putIfAbsent(holder.kittylib$getOrCreateSubNbt(subNbtKey), value);
    }

    public void putIfAbsent(NbtCompound nbtCompound, T value) {
        if (!nbtCompound.contains(key)) write(nbtCompound, this, value);
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
    protected static <T> void write(NbtCompound nbt, NbtRecord<T> record, T value) {
        record.type.write(nbt, record.key, value);
    }

    protected static <T> T read(NbtCompound nbt, NbtRecord<T> record) {
        return record.type.read(nbt, record.key);
    }
    //endregion
}
