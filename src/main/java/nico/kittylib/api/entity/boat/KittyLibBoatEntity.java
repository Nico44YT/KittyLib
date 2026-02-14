package nico.kittylib.api.entity.boat;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class KittyLibBoatEntity extends BoatEntity implements KittyLibBoat {
    public KittyLibBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    protected KittyLibBoatEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public abstract KittyLibBoatType getBoatVariant();
    public abstract Item asItem();

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    @Deprecated
    public final Type getVariant() {
        return null;
    }

    public record KittyLibBoatType(Block baseBlock, Identifier id, boolean raft) {

    }
}