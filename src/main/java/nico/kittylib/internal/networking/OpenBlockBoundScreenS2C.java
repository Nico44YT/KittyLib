package nico.kittylib.internal.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import nico.kittylib.KittyLibMain;

public record OpenBlockBoundScreenS2C(BlockPos pos) implements CustomPayload {
    private static final Identifier PACKET_ID = KittyLibMain.id("block_bound_screen");
    public static final CustomPayload.Id<OpenBlockBoundScreenS2C> ID = new CustomPayload.Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, OpenBlockBoundScreenS2C> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, OpenBlockBoundScreenS2C::pos,
            OpenBlockBoundScreenS2C::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
