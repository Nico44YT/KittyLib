package nico.test_mod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import nico.test_mod.block.ModBlocks;
import nico.test_mod.item.ModItems;

public class TestMod implements ModInitializer {

    public static String MOD_ID = "test_mod";

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModItems.init();
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
