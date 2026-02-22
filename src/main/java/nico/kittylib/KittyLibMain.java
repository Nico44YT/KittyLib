package nico.kittylib;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import nico.kittylib.api.util.KittyLibIdentifier;
import net.fabricmc.api.ModInitializer;
import nico.kittylib.internal.scheduler.ImplementedScheduler;

public class KittyLibMain implements ModInitializer {

    public static String MOD_ID = "kittylib";

    @Override
    public void onInitialize() {
        ServerTickEvents.END_WORLD_TICK.register(ImplementedScheduler::tick);
    }

    public static KittyLibIdentifier id(String name) {
        return KittyLibIdentifier.of(MOD_ID, name);
    }
}
