package nico.kittylib.mixin.injections;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import nico.kittylib.api.nbt.record.NbtHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NbtCompound.class)
public abstract class NbtCompoundMixin implements NbtHolder {
    @Shadow
    @Nullable
    public abstract NbtElement put(String key, NbtElement element);

    @Override
    public NbtCompound kittylib$getNbt() {
        return (NbtCompound)(Object)this;
    }

    @Override
    public NbtCompound kittylib$getOrCreateNbt() {
        return (NbtCompound)(Object)this;
    }

    @Override
    public NbtCompound kittylib$getSubNbt(String key) {
        return ((NbtCompound)(Object)this).getCompound(key);
    }

    @Override
    public NbtCompound kittylib$getOrCreateSubNbt(String key) {
        NbtCompound subNbt = ((NbtCompound)(Object)this).getCompound(key);
        return subNbt != null ? subNbt : new NbtCompound();
    }

    @Override
    public void kittylib$setNbt(NbtCompound nbtCompound) {
        ((NbtCompound)(Object)this).copyFrom(nbtCompound);
    }

    @Override
    public void kittylib$setSubNbt(String key, NbtCompound nbtCompound) {
        ((NbtCompound)(Object)this).put(key, nbtCompound);
    }
}
