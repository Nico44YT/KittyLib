package nico.kittylib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import nico.kittylib.api.networking.KittyLibOpenScreenS2C;
import nico.kittylib.api.networking.KittyLibSyncBlockEntityS2C;
import nico.kittylib.api.util.KittyLibIdentifier;
import nico.kittylib.internal.command.KittyLibInternalCommands;
import nico.kittylib.internal.networking.OpenBlockBoundScreenS2C;
import nico.kittylib.internal.scheduler.ImplementedScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KittyLibMain implements ModInitializer {

    public static String MOD_ID = "kittylib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ServerTickEvents.END_WORLD_TICK.register(ImplementedScheduler::tick);

        CommandRegistrationCallback.EVENT.register(KittyLibInternalCommands::registerAll);

        registerNetworkPayloads();
    }

    public static KittyLibIdentifier id(String name) {
        return KittyLibIdentifier.of(MOD_ID, name);
    }

    protected static void registerNetworkPayloads() {
        PayloadTypeRegistry.playS2C().register(KittyLibOpenScreenS2C.ID, KittyLibOpenScreenS2C.CODEC);
        PayloadTypeRegistry.playS2C().register(KittyLibSyncBlockEntityS2C.ID, KittyLibSyncBlockEntityS2C.CODEC);
        PayloadTypeRegistry.playS2C().register(OpenBlockBoundScreenS2C.ID, OpenBlockBoundScreenS2C.CODEC);
    }
}
