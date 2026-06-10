package nico.kittylib.api.nbt;

import net.minecraft.nbt.NbtCompound;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


public interface NbtConvertible {

    default void kittylib$writeNbt(NbtCompound nbt) {
        for (Field field : getAllFields(this.getClass())) {
            if (!field.isAnnotationPresent(NbtStorable.class)) continue;
            if (Modifier.isStatic(field.getModifiers())) continue;

            field.setAccessible(true);

            try {
                Object value = field.get(this);
                if (value == null) continue;

                writeValue(nbt, field.getName(), value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to write NBT for field " + field.getName(), e);
            }
        }
    }

    default void kittylib$readNbt(NbtCompound nbt) {
        for (Field field : getAllFields(this.getClass())) {
            if (!field.isAnnotationPresent(NbtStorable.class)) continue;
            if (Modifier.isStatic(field.getModifiers())) continue;

            field.setAccessible(true);

            if (!nbt.contains(field.getName())) continue;

            try {
                Object value = readValue(nbt, field.getName(), field.getType());
                if (value != null) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to read NBT for field " + field.getName(), e);
            }
        }
    }

    /* ---------------- helper methods ---------------- */

    private static Field[] getAllFields(Class<?> clazz) {
        // Includes superclasses
        if (clazz == null || clazz == Object.class) return new Field[0];

        Field[] current = clazz.getDeclaredFields();
        Field[] parent = getAllFields(clazz.getSuperclass());

        Field[] result = new Field[current.length + parent.length];
        System.arraycopy(current, 0, result, 0, current.length);
        System.arraycopy(parent, 0, result, current.length, parent.length);

        return result;
    }

    private static void writeValue(NbtCompound nbt, String key, Object value) {
        if (value instanceof Integer i) {
            nbt.putInt(key, i);
        } else if (value instanceof Boolean b) {
            nbt.putBoolean(key, b);
        } else if (value instanceof Long l) {
            nbt.putLong(key, l);
        } else if (value instanceof String s) {
            nbt.putString(key, s);
        } else if (value instanceof NbtConvertible c) {
            NbtCompound sub = new NbtCompound();
            c.kittylib$writeNbt(sub);
            nbt.put(key, sub);
        } else {
            throw new IllegalArgumentException("Unsupported NBT type: " + value.getClass());
        }
    }

    private static Object readValue(NbtCompound nbt, String key, Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return nbt.getInt(key);
        }
        if (type == boolean.class || type == Boolean.class) {
            return nbt.getBoolean(key);
        }
        if (type == long.class || type == Long.class) {
            return nbt.getLong(key);
        }
        if (type == String.class) {
            return nbt.getString(key);
        }
        if (NbtConvertible.class.isAssignableFrom(type)) {
            try {
                NbtConvertible obj = (NbtConvertible) type.getDeclaredConstructor().newInstance();
                obj.kittylib$readNbt(nbt.getCompound(key));
                return obj;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create NbtConvertible: " + type, e);
            }
        }

        throw new IllegalArgumentException("Unsupported NBT type: " + type);
    }
}