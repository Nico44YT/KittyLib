package nico.kittylib.api.client.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class KittyLibScreenRegistry {
    private static final Map<Identifier, Function<NbtCompound, Supplier<Screen>>> SCREEN_MAP = new HashMap<>();

    public static void register(Identifier id, Function<NbtCompound, Supplier<Screen>> screenFunction) {
        SCREEN_MAP.put(id, screenFunction);
    }

    public static void register(Identifier id, Supplier<Screen> screen) {
        SCREEN_MAP.put(id, (nbt) -> screen);
    }

    public static Screen getScreen(Identifier id) {
        return getScreen(id, new NbtCompound());
    }

    public static Screen getScreen(Identifier id, NbtCompound additionalData) {
        return SCREEN_MAP.get(id).apply(additionalData).get();
    }
}
