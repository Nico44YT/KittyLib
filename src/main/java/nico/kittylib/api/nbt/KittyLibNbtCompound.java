package nico.kittylib.api.nbt;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class KittyLibNbtCompound extends NbtCompound {
    public KittyLibNbtCompound() {
        this(new NbtCompound());
    }

    public KittyLibNbtCompound(NbtCompound nbtCompound) {
        super();
        this.copyFrom(nbtCompound);
    }

    //region / * Identifier * /
    public void putIdentifier(String key, Identifier value) {
        NbtCompound idNbt = new NbtCompound();

        idNbt.putString("path", value.getPath());
        idNbt.putString("namespace", value.getNamespace());

        this.put(key, idNbt);
    }

    public Identifier getIdentifier(String key) {
        NbtCompound idNbt = this.getCompound(key);

        return Identifier.of(idNbt.getString("namespace"), idNbt.getString("path"));
    }

    public void putIdentifierArray(String key, Identifier[] value) {
        NbtCompound arrayNbt = new NbtCompound();

        arrayNbt.putInt("length", value.length);

        for(int i = 0; i<value.length; i++) {
            Identifier _value = value[i];
            NbtCompound idNbt = new NbtCompound();

            idNbt.putString("path", _value.getPath());
            idNbt.putString("namespace", _value.getNamespace());

            arrayNbt.put(String.valueOf(i), idNbt);
        }

        this.put(key, arrayNbt);
    }

    public Identifier[] getIdentifierArray(String key) {
        NbtCompound arrayNbt = getCompound(key);

        int length = arrayNbt.getInt("length");

        Identifier[] returnable = new Identifier[length];

        for(int i = 0; i<length; i++) {
            NbtCompound idNbt = arrayNbt.getCompound(String.valueOf(i));

            returnable[i] = Identifier.of(idNbt.getString("namespace"), idNbt.getString("path"));
        }

        return returnable;
    }
    //endregion

    //region / * Enum * /
    public <T extends Enum<T>> void putEnum(String key, Enum<T> value) {
        NbtCompound enumNbt = new NbtCompound();

        enumNbt.putString("name", value.name());

        this.put(key, enumNbt);
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumClass) {
        NbtCompound enumNbt = this.getCompound(key);

        return Enum.valueOf(enumClass, enumNbt.getString("name"));
    }
    //endregion

    //region / * BlockPos * /
    public void putBlockPos(String key, BlockPos blockPos) {
        NbtCompound blockPosNbt = new NbtCompound();

        blockPosNbt.putLong("position", blockPos.asLong());

        this.put(key, blockPosNbt);
    }

    public BlockPos getBlockPos(String key) {
        NbtCompound blockPosNbt = this.getCompound(key);

        return BlockPos.fromLong(blockPosNbt.getLong("position"));
    }
    //endregion
}
