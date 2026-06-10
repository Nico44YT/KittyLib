package nico.kittylib.api.entity.boat;

import net.minecraft.entity.EntityDimensions;

public interface KittyLibBoat {
    EntityDimensions BOAT_DIMENSIONS = EntityDimensions.fixed(1.375F, 0.5625F);
    KittyLibBoatEntity.KittyLibBoatType getBoatVariant();
}