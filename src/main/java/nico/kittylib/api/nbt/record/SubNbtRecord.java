package nico.kittylib.api.nbt.record;

public class SubNbtRecord<T> extends NbtRecord<T> {

    private final String sub;

    protected SubNbtRecord(String key, Class<T> valueClass, String sub) {
        super(key, valueClass);

        this.sub = sub;
    }

    protected SubNbtRecord(String key, Class<T> valueClass, String sub, NbtTypeHandler<T> typeHandler) {
        super(key, valueClass, typeHandler);

        this.sub = sub;
    }

    public static <T> SubNbtRecord<T> of(String key, Class<T> valueClass, String sub) {
        return new SubNbtRecord<>(key, valueClass, sub);
    }

    public static <T> SubNbtRecord<T> of(String key, Class<T> valueClass, String sub, NbtTypeHandler<T> typeHandler) {
        return new SubNbtRecord<>(key, valueClass, sub, typeHandler);
    }

    @Override
    public T get(NbtHolder nbtHolder) {
        return getFromSub(nbtHolder, sub);
    }

    @Override
    public void put(NbtHolder holder, T value) {
        putToSub(holder, sub, value);
    }

    @Override
    public void putToSub(NbtHolder holder, String subNbtKey, T value) {
        super.putToSub(holder.kittylib$getOrCreateSubNbt(sub), subNbtKey, value);
    }
}
