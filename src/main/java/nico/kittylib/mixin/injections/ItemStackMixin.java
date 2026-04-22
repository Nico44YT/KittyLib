package nico.kittylib.mixin.injections;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import nico.kittylib.api.nbt.ItemStackInjection;
import nico.kittylib.api.nbt.record.NbtHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;
import java.util.function.Supplier;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackInjection, NbtHolder {
    @Shadow
    public abstract boolean hasNbt();

    @Shadow
    public abstract NbtCompound getOrCreateNbt();

    @Shadow
    public abstract void setNbt(@Nullable NbtCompound nbt);

    @Shadow
    @Nullable
    public abstract NbtCompound getNbt();

    @Override
    public NbtElement kittylib$getElement(String key) {
        if(!hasNbt()) return null;
        assert getNbt() != null;
        return getNbt().get(key);
    }

    @Override
    public NbtElement kittylib$getElementOrElse(String key, Supplier<NbtElement> elseSupplier) {
        return hasNbt() && kittylib$getNbt().contains(key) ? kittylib$getNbt().get(key) : elseSupplier.get();
    }

    @Override
    public Optional<NbtElement> kittylib$maybeGetElement(String key) {
        return Optional.ofNullable(kittylib$getElement(key));
    }

    @Override
    public void kittylib$putElement(String key, NbtElement nbtElement) {
        NbtCompound nbtCompound = getOrCreateNbt();
        nbtCompound.put(key, nbtElement);
        this.setNbt(nbtCompound);
    }

    @Override
    public NbtCompound kittylib$getNbt() {
        return getOrCreateNbt();
    }

    @Override
    public void kittylib$writeNbt(NbtCompound nbtCompound) {
        setNbt(nbtCompound);
    }
}
