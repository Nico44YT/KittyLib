package nico.kittylib.api.block.sets;

import net.minecraft.util.Identifier;
import nico.kittylib.KittyLibMain;

public class KittyLibBlockEntryIdentifiers {
    public static final Identifier BASE = id("base");
    public static final Identifier CRACKED = id("cracked");
    public static final Identifier CHISELED = id("chiseled");
    public static final Identifier CUT = id("cut");
    public static final Identifier MOSAIC = id("mosaic");
    public static final Identifier SLAB = id("slab");
    public static final Identifier STAIRS = id("stairs");
    public static final Identifier WALL = id("wall");
    public static final Identifier FENCE = id("fence");
    public static final Identifier FENCE_GATE = id("fence_gate");
    public static final Identifier BUTTON = id("button");
    public static final Identifier PRESSURE_PLATE = id("pressure_plate");
    public static final Identifier DOOR = id("door");
    public static final Identifier TRAPDOOR = id("trapdoor");

    public static final Identifier BLACK = id("black");
    public static final Identifier BLUE = id("blue");
    public static final Identifier BROWN = id("brown");
    public static final Identifier CYAN = id("cyan");
    public static final Identifier GRAY = id("gray");
    public static final Identifier GREEN = id("green");
    public static final Identifier LIGHT_BLUE = id("light_blue");
    public static final Identifier LIGHT_GRAY = id("light_gray");
    public static final Identifier LIME = id("lime");
    public static final Identifier MAGENTA = id("magenta");
    public static final Identifier ORANGE = id("orange");
    public static final Identifier PINK = id("pink");
    public static final Identifier PURPLE = id("purple");
    public static final Identifier RED = id("red");
    public static final Identifier WHITE = id("white");
    public static final Identifier YELLOW = id("yellow");


    protected static Identifier id(String path) {
        return KittyLibMain.id(path);
    }
}
