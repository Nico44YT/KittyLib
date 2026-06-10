package nico.kittylib.api.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import nico.kittylib.KittyLibMain;

public record KittyLibOpenScreenS2C(Identifier screenId, NbtCompound additionalData) implements CustomPayload {
    public static final Identifier PACKET_ID = KittyLibMain.id("open_screen");
    public static final CustomPayload.Id<KittyLibOpenScreenS2C> ID = new CustomPayload.Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, KittyLibOpenScreenS2C> CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, KittyLibOpenScreenS2C::screenId,
            PacketCodecs.NBT_COMPOUND, KittyLibOpenScreenS2C::additionalData,
            KittyLibOpenScreenS2C::new
    );

    public static void openScreen(ServerPlayerEntity player, Identifier screenId) {
        openScreen(player, screenId, new NbtCompound());
    }

    public static void openScreen(ServerPlayerEntity player, Identifier screenId, NbtCompound additionalData) {
        ServerPlayNetworking.send(player, new KittyLibOpenScreenS2C(screenId, additionalData));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
