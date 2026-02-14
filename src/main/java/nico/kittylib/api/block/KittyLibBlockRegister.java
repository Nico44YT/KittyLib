package nico.kittylib.api.block;

import nico.kittylib.api.item.KittyLibItemRegister;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;

public record KittyLibBlockRegister(Function<String, Identifier> idFactory) {

    public static KittyLibBlockRegister of(String namespace) {
        return new KittyLibBlockRegister(name -> Identifier.of(namespace, name));
    }

    public static KittyLibBlockRegister of(Function<String, Identifier> idFactory) {
        return new KittyLibBlockRegister(idFactory);
    }

    public <T extends Block> T register(String name, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory) {
        return register(idFactory.apply(name), settings, blockFactory);
    }

    public <T extends Block> T register(String name, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory, Item.Settings itemSettings) {
        return register(idFactory.apply(name), settings, blockFactory, itemSettings);
    }

    public <T extends Block> T register(String name, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory, Item.Settings itemSettings, BiFunction<T, Item.Settings, Item> itemFactory) {
        return register(idFactory.apply(name), settings, blockFactory, itemSettings, itemFactory);
    }

    public static <T extends Block> T register(Identifier id, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory) {
        return Registry.register(Registries.BLOCK, id, blockFactory.apply(settings));
    }

    public static <T extends Block> T register(Identifier id, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory, Item.Settings itemSettings) {
        return register(id, settings, blockFactory, itemSettings, BlockItem::new);
    }

    public static <T extends Block> T register(Identifier id, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory, Item.Settings itemSettings, BiFunction<T, Item.Settings, Item> itemBiFunction) {
        T block = Registry.register(Registries.BLOCK, id, blockFactory.apply(settings));
        KittyLibItemRegister.register(id, itemSettings, ($) -> itemBiFunction.apply(block, $));
        return block;
    }
}