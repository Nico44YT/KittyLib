package nico.test_mod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class TestMod implements ModInitializer {

    public static String MOD_ID = "test_mod";

    @Override
    public void onInitialize() {

    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
