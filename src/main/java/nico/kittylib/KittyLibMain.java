package nico.kittylib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import nico.kittylib.api.util.KittyLibIdentifier;
import nico.kittylib.internal.scheduler.ImplementedScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KittyLibMain implements ModInitializer {

    public static String MOD_ID = "kittylib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ServerTickEvents.END_WORLD_TICK.register(ImplementedScheduler::tick);
    }

    public static KittyLibIdentifier id(String name) {
        return KittyLibIdentifier.of(MOD_ID, name);
    }
}
