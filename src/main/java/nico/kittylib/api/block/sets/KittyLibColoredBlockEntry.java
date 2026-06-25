package nico.kittylib.api.block.sets;

import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import nico.kittylib.api.java.function.TriFunction;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class KittyLibColoredBlockEntry<T extends KittyLibColoredBlockSet> extends KittyLibBlockEntry<T> {

    private final Item dyeItem;

    public KittyLibColoredBlockEntry(Identifier entryIdentifier, TriFunction<T, String, KittyLibBlockEntry<T>, String> namingFunction, Function<T, Block> blockFunction, BiConsumer<Block, BlockFamily.Builder> blockFamilyConsumer, Item dyeItem) {
        super(entryIdentifier, namingFunction, blockFunction, blockFamilyConsumer);

        this.dyeItem = dyeItem;
    }

    public Item dyeItem() {
        return dyeItem;
    }
}