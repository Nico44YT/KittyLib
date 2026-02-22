package nico.kittylib.api.util;

import net.minecraft.world.World;

public class KittyLibScheduledTask {
    public int duration;
    public KittyLibScheduledAction action;

    public KittyLibScheduledTask(int duration, KittyLibScheduledAction action) {
        this.duration = duration;
        this.action = action;
    }

    public void tick(World world) {
        action.run(world, duration -= 1);
    }

    public void stop() {
        this.duration = 0;
    }
}