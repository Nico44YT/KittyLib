package nico.kittylib.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.world.World;
import nico.kittylib.KittyLibMain;
import nico.kittylib.api.client.screen.KittyLibScreenRegistry;
import nico.kittylib.api.networking.KittyLibOpenScreenS2C;
import nico.kittylib.api.networking.KittyLibSyncBlockEntityS2C;
import nico.kittylib.api.screen.BlockBoundScreenProvider;
import nico.kittylib.internal.networking.OpenBlockBoundScreenS2C;
import nico.kittylib.internal.scheduler.ImplementedScheduler;

public class KittyLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_WORLD_TICK.register(ImplementedScheduler::tick);

        ClientPlayNetworking.registerGlobalReceiver(KittyLibSyncBlockEntityS2C.ID, (packet, context) -> {
            context.client().execute(() -> {
                World world = context.player().getWorld();

                if (!packet.worldRegistryKey().equals(world.getRegistryKey())) {
                    KittyLibMain.LOGGER.warn("Tried syncing block entity at {} but no block entity was found!", packet.blockPos());
                    return;
                }
                var blockEntity = world.getBlockEntity(packet.blockPos());
                if (blockEntity != null) blockEntity.read(packet.data(), world.getRegistryManager());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(OpenBlockBoundScreenS2C.ID, (packet, context) -> {
            context.client().execute(() -> {
                World world = context.player().getWorld();
                BlockState blockState = world.getBlockState(packet.pos());

                if (blockState.getBlock() instanceof BlockBoundScreenProvider provider) {
                    Screen screen = provider.createScreen(world, packet.pos(), blockState, context.player());
                    MinecraftClient.getInstance().setScreen(screen);
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(KittyLibOpenScreenS2C.ID, (packet, context) -> {
            context.client().execute(() -> {
                Screen screen = KittyLibScreenRegistry.getScreen(packet.screenId(), packet.additionalData());
                context.client().setScreen(screen);
            });
        });
    }
}
