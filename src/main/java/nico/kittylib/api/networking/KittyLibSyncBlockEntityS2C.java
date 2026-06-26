package nico.kittylib.api.networking;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nico.kittylib.KittyLibMain;

import java.util.function.Predicate;

public record KittyLibSyncBlockEntityS2C(BlockPos blockPos, NbtCompound data,
                                         RegistryKey<World> worldRegistryKey) implements FabricPacket {
    private static final Identifier ID = KittyLibMain.id("sync_block_entity");
    public static final PacketType<KittyLibSyncBlockEntityS2C> TYPE = PacketType.create(ID, KittyLibSyncBlockEntityS2C::createFromByteBuf);

    private static KittyLibSyncBlockEntityS2C createFromByteBuf(PacketByteBuf packetByteBuf) {
        return new KittyLibSyncBlockEntityS2C(packetByteBuf.readBlockPos(), packetByteBuf.readNbt(), packetByteBuf.readRegistryKey(RegistryKeys.WORLD));
    }

    public static void syncToAll(BlockEntity blockEntity) {
        syncToAll(player -> true, blockEntity);
    }

    public static void syncToAll(Predicate<PlayerEntity> predicate, BlockEntity blockEntity) {
        World world = blockEntity.getWorld();
        if (world == null || world.isClient()) return;

        blockEntity.getWorld().getPlayers().forEach(player -> {
            if(!predicate.test(player)) return;
            ServerPlayNetworking.send((ServerPlayerEntity) player, new KittyLibSyncBlockEntityS2C(blockEntity.getPos(), blockEntity.createNbt(), world.getRegistryKey()));
        });
    }

    public static void syncTo(ServerPlayerEntity player, BlockEntity blockEntity) {
        World world = blockEntity.getWorld();
        if (world == null || world.isClient()) return;

        ServerPlayNetworking.send(player, new KittyLibSyncBlockEntityS2C(blockEntity.getPos(), blockEntity.createNbt(), world.getRegistryKey()));
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.blockPos);
        packetByteBuf.writeNbt(this.data);
        packetByteBuf.writeRegistryKey(this.worldRegistryKey);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
