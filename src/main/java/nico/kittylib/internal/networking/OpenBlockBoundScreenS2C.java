package nico.kittylib.internal.networking;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import nico.kittylib.KittyLibMain;

public record OpenBlockBoundScreenS2C(BlockPos pos) implements FabricPacket {
    private static final Identifier ID = KittyLibMain.id("block_bound_screen");
    public static final PacketType<OpenBlockBoundScreenS2C> TYPE = PacketType.create(ID, OpenBlockBoundScreenS2C::createFromByteBuf);

    private static OpenBlockBoundScreenS2C createFromByteBuf(PacketByteBuf packetByteBuf) {
        return new OpenBlockBoundScreenS2C(packetByteBuf.readBlockPos());
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(pos);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
