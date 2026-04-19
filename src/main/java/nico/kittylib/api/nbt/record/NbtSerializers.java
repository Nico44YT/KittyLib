package nico.kittylib.api.nbt.record;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class NbtSerializers {

    //region // BlockPos //
    public static void putBlockPos(NbtCompound nbtCompound, String name, BlockPos blockPos) {
        nbtCompound.putLong(name, blockPos.asLong());
    }

    public static BlockPos getBlockPos(NbtCompound nbtCompound, String name) {
        return BlockPos.fromLong(nbtCompound.getLong(name));
    }
    //endregion

    //region // Vec3d //
    public static void putVec3d(NbtCompound nbtCompound, String name, Vec3d vec3d) {
        NbtCompound container = new NbtCompound();

        container.putDouble("x", vec3d.getX());
        container.putDouble("z", vec3d.getY());
        container.putDouble("y", vec3d.getZ());

        nbtCompound.put(name, container);
    }

    public static Vec3d getVec3d(NbtCompound nbtCompound, String name) {
        NbtCompound container = nbtCompound.getCompound(name);

        return new Vec3d(
                container.getDouble("x"),
                container.getDouble("z"),
                container.getDouble("y")
        );
    }

    //endregion
    //region // Vec3i //
    public static void putVec3i(NbtCompound nbtCompound, String name, Vec3i vec3i) {
        NbtCompound container = new NbtCompound();

        container.putInt("x", vec3i.getX());
        container.putInt("z", vec3i.getY());
        container.putInt("y", vec3i.getZ());

        nbtCompound.put(name, container);
    }

    public static Vec3i getVec3i(NbtCompound nbtCompound, String name) {
        NbtCompound container = nbtCompound.getCompound(name);

        return new Vec3i(
                container.getInt("x"),
                container.getInt("z"),
                container.getInt("y")
        );
    }
    //endregion

    //region// ItemStack //
    public static void putItemStack(NbtCompound nbtCompound, String name, ItemStack itemStack) {
        NbtCompound container = new NbtCompound();
        itemStack.writeNbt(container);
        nbtCompound.put(name, container);
    }

    public static ItemStack getItemStack(NbtCompound nbtCompound, String name) {
        NbtCompound container = nbtCompound.getCompound(name);
        return ItemStack.fromNbt(container);
    }
    //endregion

    //region // Identifier //
    public static void putIdentifier(NbtCompound nbtCompound, String name, Identifier identifier) {
        NbtCompound container = new NbtCompound();

        container.putString("namespace", identifier.getNamespace());
        container.putString("path", identifier.getPath());

        nbtCompound.put(name, container);
    }

    public static Identifier getIdentifier(NbtCompound nbtCompound, String name) {
        NbtCompound container = nbtCompound.getCompound(name);

        return Identifier.of(
                container.getString("namespace"),
                container.getString("path")
        );
    }
    //endregion

    //region // StatusEffectInstance //
    public static void putStatusEffectInstance(NbtCompound nbtCompound, String name, StatusEffectInstance statusEffectInstance) {
        NbtCompound container = new NbtCompound();

        Identifier id = Registries.STATUS_EFFECT.getId(statusEffectInstance.getEffectType());
        assert id != null;
        putIdentifier(container, "identifier", id);

        container.putInt("amplifier", statusEffectInstance.getAmplifier());
        container.putInt("duration", statusEffectInstance.getDuration());

        container.putBoolean("ambient", statusEffectInstance.isAmbient());
        container.putBoolean("showParticles", statusEffectInstance.shouldShowParticles());
        container.putBoolean("showIcon", statusEffectInstance.shouldShowIcon());

        nbtCompound.put(name, container);
    }

    public static StatusEffectInstance getStatusEffectInstance(NbtCompound nbtCompound, String name) {
        NbtCompound container = nbtCompound.getCompound(name);

        Identifier id = getIdentifier(container, "identifier");
        StatusEffect type = Registries.STATUS_EFFECT.get(id);
        assert type != null;

        int amplifier = container.getInt("amplifier");
        int duration = container.getInt("duration");
        boolean ambient = container.getBoolean("ambient");
        boolean showParticles = container.getBoolean("showParticles");
        boolean showIcon = container.getBoolean("showIcon");

        return new StatusEffectInstance(
                type,
                duration,
                amplifier,
                ambient,
                showParticles,
                showIcon
        );
    }
    //endregion

    //region // Enum //
    public static void putEnum(NbtCompound nbtCompound, String name, Enum<?> _enum) {
        NbtCompound container = new NbtCompound();

        container.putString("name", _enum.name());
        container.putString("location", _enum.getDeclaringClass().toString());

        nbtCompound.put(name, container);
    }

    public static Enum<?> getEnum(NbtCompound nbtCompound, String name) {
        NbtCompound container = nbtCompound.getCompound(name);

        return Enum.valueOf(Enum.class, container.getString("name"));
    }
    //endregion
}
