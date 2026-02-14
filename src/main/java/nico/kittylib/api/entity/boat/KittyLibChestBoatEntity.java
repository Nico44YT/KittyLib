package nico.kittylib.api.entity.boat;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public abstract class KittyLibChestBoatEntity extends ChestBoatEntity implements KittyLibBoat {
    public KittyLibChestBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    public abstract KittyLibBoatEntity.KittyLibBoatType getBoatVariant();
    public abstract Item asItem();

    @Override
    @Deprecated
    public final Type getVariant() {
        return null;
    }
}