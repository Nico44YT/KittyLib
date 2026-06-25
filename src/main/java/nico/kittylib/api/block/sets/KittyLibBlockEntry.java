package nico.kittylib.api.block.sets;

import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nico.kittylib.api.java.function.TriFunction;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class KittyLibBlockEntry<T extends KittyLibBlockSet> {

    private final Identifier entryIdentifier;
    private final TriFunction<T, String, KittyLibBlockEntry<T>, String> namingFunction;
    private final Function<T, Block> blockFunction;
    private final BiConsumer<Block, BlockFamily.Builder> blockFamilyConsumer;

    public KittyLibBlockEntry(Identifier entryIdentifier, TriFunction<T, String, KittyLibBlockEntry<T>, String> namingFunction, Function<T, Block> blockFunction, BiConsumer<Block, BlockFamily.Builder> blockFamilyConsumer) {
        this.entryIdentifier = entryIdentifier;
        this.namingFunction = namingFunction;
        this.blockFunction = blockFunction;
        this.blockFamilyConsumer = blockFamilyConsumer;
    }

    public Block register(T blockSet) {
        Identifier id = blockSet.applyIdFunction(namingFunction.apply(blockSet, blockSet.getSetName(), this));
        return Registry.register(Registries.BLOCK, id, blockFunction.apply(blockSet));
    }

    public Identifier getEntryIdentifier() {
        return entryIdentifier;
    }

    public TriFunction<T, String, KittyLibBlockEntry<T>, String> namingFunction() {
        return namingFunction;
    }

    public Function<T, Block> blockFunction() {
        return blockFunction;
    }

    public BiConsumer<Block, BlockFamily.Builder> blockFamilyConsumer() {
        return blockFamilyConsumer;
    }

}