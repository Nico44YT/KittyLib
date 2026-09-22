package nico.test_mod.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nico.test_mod.TestMod;
import nico.test_mod.item.custom.TestTooltipItem;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {
    private static final Supplier<Item.Settings> DEFAULT_SETTINGS = Item.Settings::new;

    public static Item TOOLTIP_TEST = register("tooltip_test", TestTooltipItem::new);

    public static void init() {

    }

    public static <T extends Item> T register(String name, Function<Item.Settings, T> factory) {
        return register(name, DEFAULT_SETTINGS.get(), factory);
    }

    public static <T extends Item> T register(Identifier id, Function<Item.Settings, T> factory) {
        return register(id, DEFAULT_SETTINGS.get(), factory);
    }

    public static <T extends Item> T register(String name, Item.Settings settings, Function<Item.Settings, T> factory) {
        return register(TestMod.id(name), settings, factory);
    }

    public static <T extends Item> T register(Identifier id, Item.Settings settings, Function<Item.Settings, T> factory) {
        return Registry.register(Registries.ITEM, id, factory.apply(settings));
    }
}
