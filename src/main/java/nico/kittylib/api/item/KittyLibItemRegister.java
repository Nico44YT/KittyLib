package nico.kittylib.api.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public record KittyLibItemRegister(Function<String, Identifier> idFactory) {
    public static KittyLibItemRegister of(String namespace) {
        return new KittyLibItemRegister(name -> Identifier.tryParse(namespace, name));
    }

    public static KittyLibItemRegister of(Function<String, Identifier> idFactory) {
        return new KittyLibItemRegister(idFactory);
    }

    public <T extends Item> T register(String name, Item.Settings settings, Function<Item.Settings, T> itemFactory) {
        return Registry.register(Registries.ITEM, idFactory.apply(name), itemFactory.apply(settings));
    }

    public static <T extends Item> T register(Identifier id, Item.Settings settings, Function<Item.Settings, T> itemFactory) {
        return Registry.register(Registries.ITEM, id, itemFactory.apply(settings));
    }

}