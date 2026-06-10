package nico.kittylib.mixin.injections;

import nico.kittylib.api.nbt.NbtConvertible;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vec3i.class)
public abstract class Vec3iMixin implements NbtConvertible {
    @Shadow
    private int x;

    @Shadow
    private int y;

    @Shadow
    private int z;

    @Override
    public void kittylib$writeNbt(NbtCompound nbt) {
        nbt.putInt("x", this.x);
        nbt.putInt("y", this.y);
        nbt.putInt("z", this.z);
    }

    @Override
    public void kittylib$readNbt(NbtCompound nbt) {
        this.x = nbt.getInt("x");
        this.y = nbt.getInt("y");
        this.z = nbt.getInt("z");
    }
}
