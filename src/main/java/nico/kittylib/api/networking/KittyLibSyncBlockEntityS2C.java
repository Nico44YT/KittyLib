package nico.kittylib.api.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nico.kittylib.KittyLibMain;

public record KittyLibSyncBlockEntityS2C(BlockPos blockPos, NbtCompound data,
                                         RegistryKey<World> worldRegistryKey) implements CustomPayload {
    private static final Identifier PACKET_ID = KittyLibMain.id("sync_block_entity");
    public static final CustomPayload.Id<KittyLibSyncBlockEntityS2C> ID = new CustomPayload.Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, KittyLibSyncBlockEntityS2C> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, KittyLibSyncBlockEntityS2C::blockPos,
            PacketCodecs.NBT_COMPOUND, KittyLibSyncBlockEntityS2C::data,
            null, KittyLibSyncBlockEntityS2C::worldRegistryKey,
            KittyLibSyncBlockEntityS2C::new
    );

    public static void syncToAll(BlockEntity blockEntity) {
        World world = blockEntity.getWorld();
        if (world == null || world.isClient()) return;

        blockEntity.getWorld().getPlayers().forEach(player -> {
            ServerPlayNetworking.send((ServerPlayerEntity) player, new KittyLibSyncBlockEntityS2C(blockEntity.getPos(), blockEntity.createNbt(world.getRegistryManager()), world.getRegistryKey()));
        });
    }

    public static void syncTo(ServerPlayerEntity player, BlockEntity blockEntity) {
        World world = blockEntity.getWorld();
        if (world == null || world.isClient()) return;

        ServerPlayNetworking.send(player, new KittyLibSyncBlockEntityS2C(blockEntity.getPos(), blockEntity.createNbt(world.getRegistryManager()), world.getRegistryKey()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
