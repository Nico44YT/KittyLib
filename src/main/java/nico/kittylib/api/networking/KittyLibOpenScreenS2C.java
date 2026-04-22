package nico.kittylib.api.networking;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import nico.kittylib.KittyLibMain;

public record KittyLibOpenScreenS2C(Identifier screenId, NbtCompound additionalData) implements FabricPacket {

    public static final Identifier ID = KittyLibMain.id("open_screen");
    public static final PacketType<KittyLibOpenScreenS2C> TYPE = PacketType.create(ID, KittyLibOpenScreenS2C::fromByteBuf);

    private static KittyLibOpenScreenS2C fromByteBuf(PacketByteBuf packetByteBuf) {
        return new KittyLibOpenScreenS2C(packetByteBuf.readIdentifier(), packetByteBuf.readNbt());
    }

    public static void openScreen(ServerPlayerEntity player, Identifier screenId) {
        openScreen(player, screenId, new NbtCompound());
    }

    public static void openScreen(ServerPlayerEntity player, Identifier screenId, NbtCompound additionalData) {
        ServerPlayNetworking.send(player, new KittyLibOpenScreenS2C(screenId, additionalData));
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeIdentifier(screenId);
        packetByteBuf.writeNbt(additionalData);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
