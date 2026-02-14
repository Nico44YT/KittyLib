package nico.kittylib;

import nico.kittylib.api.util.KittyLibIdentifier;
import net.fabricmc.api.ModInitializer;

public class KittyLibMain implements ModInitializer {

    public static String MOD_ID = "kittylib";

    @Override
    public void onInitialize() {

    }

    public static KittyLibIdentifier id(String name) {
        return KittyLibIdentifier.of(MOD_ID, name);
    }
}
