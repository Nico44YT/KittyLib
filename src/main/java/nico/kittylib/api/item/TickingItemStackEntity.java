package nico.kittylib.api.item;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface TickingItemStackEntity {
    void groundTick(ItemStack stack, World world, ItemEntity itemEntity, double x, double y, double z);
    void itemFrameTick(ItemStack stack, World world, ItemFrameEntity frameEntity, double x, double y, double z);
}
