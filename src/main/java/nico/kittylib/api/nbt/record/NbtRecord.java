package nico.kittylib.api.nbt.record;

import net.minecraft.item.ItemStack;
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
    public T get(ItemStack stack) {
        return stack.getNbt() != null ? get(stack.getNbt()) : null;
    }

    public T getFromSub(ItemStack stack, String subNbtKey) {
        return stack.getSubNbt(subNbtKey) != null ? get(stack.getSubNbt(subNbtKey)) : null;
    }

    public T get(NbtCompound nbtCompound) {
        return read(nbtCompound, this);
    }

    public Optional<T> maybeGet(ItemStack stack) {
        return Optional.ofNullable(get(stack));
    }

    public Optional<T> maybeGetFromSub(ItemStack stack, String subNbtKey) {
        return maybeGet(stack.getOrCreateSubNbt(subNbtKey));
    }

    public Optional<T> maybeGet(NbtCompound nbtCompound) {
        if (nbtCompound.contains(key)) return Optional.of(get(nbtCompound));
        return Optional.empty();
    }
    //endregion

    //region // Put //
    public void put(ItemStack stack, T value) {
        put(stack.getOrCreateNbt(), value);
    }

    public void putToSub(ItemStack stack, String subNbtKey, T value) {
        put(stack.getOrCreateSubNbt(subNbtKey), value);
    }

    public void put(NbtCompound nbtCompound, T value) {
        write(nbtCompound, this, value);
    }

    public void putIfAbsent(ItemStack stack, T value) {
        putIfAbsent(stack.getOrCreateNbt(), value);
    }

    public void putToSubIfAbsent(ItemStack stack, String subNbtKey, T value) {
        putIfAbsent(stack.getOrCreateSubNbt(subNbtKey), value);
    }

    public void putIfAbsent(NbtCompound nbtCompound, T value) {
        if (!nbtCompound.contains(key)) write(nbtCompound, this, value);
    }
    //endregion

    //region // IsEmpty //
    public boolean isEmpty(ItemStack stack) {
        return isEmpty(stack.getNbt());
    }

    public boolean isEmpty(ItemStack stack, String subNbtKey) {
        return isEmpty(stack.getSubNbt(subNbtKey));
    }

    public boolean isEmpty(NbtCompound nbtCompound) {
        return !(nbtCompound != null && nbtCompound.contains(key));
    }
    //endregion

    //region // Remove //
    public void remove(ItemStack stack) {
        remove(stack.getNbt());
    }

    public void removeFromSub(ItemStack stack, String subNbtKey) {
        remove(stack.getSubNbt(subNbtKey));
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
