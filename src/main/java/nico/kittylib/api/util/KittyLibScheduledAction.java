package nico.kittylib.api.util;

import net.minecraft.world.World;

@FunctionalInterface
public interface KittyLibScheduledAction {
    void run(World world, int timeLeft);
}
