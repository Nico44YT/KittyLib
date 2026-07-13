package nico.kittylib.api.util.schedule;

import net.minecraft.world.World;

@FunctionalInterface
public interface KittyLibScheduledAction {
    void run(World world, int timeLeft);
}
