package nico.kittylib.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.world.World;
import nico.kittylib.KittyLibMain;
import nico.kittylib.api.networking.KittyLibSyncBlockEntityS2C;
import nico.kittylib.api.screen.BlockBoundScreenProvider;
import nico.kittylib.internal.networking.OpenBlockBoundScreenS2C;
import nico.kittylib.internal.scheduler.ImplementedScheduler;

public class KittyLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_WORLD_TICK.register(ImplementedScheduler::tick);

        ClientPlayNetworking.registerGlobalReceiver(KittyLibSyncBlockEntityS2C.TYPE, (packet, player, sender) -> {
            World world = player.getWorld();

            if(packet.worldRegistryKey().equals(world.getRegistryKey())) {
                var blockEntity = world.getBlockEntity(packet.blockPos());
                if(blockEntity != null) blockEntity.readNbt(packet.data());
                else {
                    KittyLibMain.LOGGER.warn("Tried syncing block entity at {} but no block entity was found!", packet.blockPos());
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(OpenBlockBoundScreenS2C.TYPE, (packet, player, sender) -> {
            if (player == null) return;
            World world = player.getWorld();
            BlockState blockState = world.getBlockState(packet.pos());

            if(blockState.getBlock() instanceof BlockBoundScreenProvider provider) {
                Screen screen = provider.createScreen(world, packet.pos(), blockState, player);
                MinecraftClient.getInstance().setScreen(screen);
            }
        });
    }
}
