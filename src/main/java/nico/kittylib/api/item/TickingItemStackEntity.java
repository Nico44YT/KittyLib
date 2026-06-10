package nico.kittylib.api.item;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface TickingItemStackEntity {
    void groundTick(ItemStack stack, World world, ItemEntity instance, double x, double y, double z);
}
