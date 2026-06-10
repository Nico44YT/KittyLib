package nico.kittylib.api.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nico.kittylib.internal.networking.OpenBlockBoundScreenS2C;

public interface BlockBoundScreenProvider {
    @Environment(EnvType.CLIENT)
    Screen createScreen(World world, BlockPos pos, BlockState state, PlayerEntity player);

    default void openScreen(ServerPlayerEntity serverPlayerEntity, BlockPos pos) {
        ServerPlayNetworking.send(serverPlayerEntity, new OpenBlockBoundScreenS2C(pos));
    }
}
