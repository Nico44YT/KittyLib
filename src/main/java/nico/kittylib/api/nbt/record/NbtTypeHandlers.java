package nico.kittylib.api.nbt.record;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import nico.kittylib.api.java.MapBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NbtTypeHandlers {
    //region // Singleton //
    private static NbtTypeHandlers instance;

    public static NbtTypeHandlers getInstance() {
        if(instance == null) {
            instance = new NbtTypeHandlers();
        }

        return instance;
    }
    //endregion

    public final Map<Class<?>, NbtTypeHandler<?>> HANDLERS;

    private NbtTypeHandlers() {
        HANDLERS = new HashMap<>();

        initHandlers();
    }

    public void initHandlers() {
        MapBuilder<Class<?>, NbtTypeHandler<?>> builder = new MapBuilder<Class<?>, NbtTypeHandler<?>>()
                .put(String.class, new NbtTypeHandler<>(
                        NbtCompound::putString,
                        NbtCompound::getString
                ))

                .put(Integer.class, new NbtTypeHandler<>(
                        NbtCompound::putInt,
                        NbtCompound::getInt
                ))
                .put(int.class, new NbtTypeHandler<>(
                        NbtCompound::putInt,
                        NbtCompound::getInt
                ))

                .put(Boolean.class, new NbtTypeHandler<>(
                        NbtCompound::putBoolean,
                        NbtCompound::getBoolean
                ))
                .put(boolean.class, new NbtTypeHandler<>(
                        NbtCompound::putBoolean,
                        NbtCompound::getBoolean
                ))

                .put(Float.class, new NbtTypeHandler<>(
                        NbtCompound::putFloat,
                        NbtCompound::getFloat
                ))
                .put(float.class, new NbtTypeHandler<>(
                        NbtCompound::putFloat,
                        NbtCompound::getFloat
                ))

                .put(Double.class, new NbtTypeHandler<>(
                        NbtCompound::putDouble,
                        NbtCompound::getDouble
                ))
                .put(double.class, new NbtTypeHandler<>(
                        NbtCompound::putDouble,
                        NbtCompound::getDouble
                ))

                .put(Long.class, new NbtTypeHandler<>(
                        NbtCompound::putLong,
                        NbtCompound::getLong
                ))
                .put(long.class, new NbtTypeHandler<>(
                        NbtCompound::putLong,
                        NbtCompound::getLong
                ))

                .put(int[].class, new NbtTypeHandler<>(
                        NbtCompound::putIntArray,
                        NbtCompound::getIntArray
                ))

                .put(long[].class, new NbtTypeHandler<>(
                        NbtCompound::putLongArray,
                        NbtCompound::getLongArray
                ))

                .put(UUID.class, new NbtTypeHandler<>(
                        NbtCompound::putUuid,
                        NbtCompound::getUuid
                ))

                .put(NbtCompound.class, new NbtTypeHandler<>(
                        NbtCompound::put,
                        NbtCompound::getCompound
                ))

                // Custom
                .put(BlockPos.class, new NbtTypeHandler<>(
                        NbtSerializers::putBlockPos,
                        NbtSerializers::getBlockPos
                ))
                .put(Vec3d.class, new NbtTypeHandler<>(
                        NbtSerializers::putVec3d,
                        NbtSerializers::getVec3d
                ))
                .put(Vec3i.class, new NbtTypeHandler<>(
                        NbtSerializers::putVec3i,
                        NbtSerializers::getVec3i
                ))
                /* TODO
                .put(ItemStack.class, new NbtTypeHandler<>(
                        NbtSerializers::putItemStack,
                        NbtSerializers::getItemStack
                ))*/
                .put(Identifier.class, new NbtTypeHandler<>(
                        NbtSerializers::putIdentifier,
                        NbtSerializers::getIdentifier
                ))
                .put(StatusEffectInstance.class, new NbtTypeHandler<>(
                        NbtSerializers::putStatusEffectInstance,
                        NbtSerializers::getStatusEffectInstance
                ))
                .put(Enum.class, new NbtTypeHandler<>(
                        NbtSerializers::putEnum,
                        NbtSerializers::getEnum
                ))
                .put(NbtList.class, new NbtTypeHandler<>(
                        NbtSerializers::putList,
                        NbtSerializers::getList
                ))
                .put(RegistryKey.class, new NbtTypeHandler<>(
                        NbtSerializers::putRegistryKey,
                        NbtSerializers::getRegistryKey
                ));

        addHandlersForMixins(builder);

        HANDLERS.putAll(builder.build());
    }

    public void addHandlersForMixins(MapBuilder<Class<?>, NbtTypeHandler<?>> mapBuilder) {

    }
}
