package nico.kittylib.api.block.sets;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class KittyLibColoredBlockEntry<T extends KittyLibColoredBlockSet> extends KittyLibBlockEntry<T> {

    private final Item dyeItem;

    public KittyLibColoredBlockEntry(BiFunction<T, String, String> namingFunction, BiFunction<T, AbstractBlock.Settings, Block> blockFunction, BiConsumer<Block, BlockFamily.Builder> blockFamilyConsumer, Item dyeItem) {
        super(namingFunction, blockFunction, blockFamilyConsumer);

        this.dyeItem = dyeItem;
    }

    public Item dyeItem() {
        return dyeItem;
    }
}
