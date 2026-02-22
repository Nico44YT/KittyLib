package nico.kittylib.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import nico.kittylib.internal.scheduler.ImplementedScheduler;

public class KittyLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_WORLD_TICK.register(ImplementedScheduler::tick);
    }
}
