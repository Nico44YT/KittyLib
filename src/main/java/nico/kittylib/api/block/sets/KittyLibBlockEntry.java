package nico.kittylib.api.block.sets;

import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nico.kittylib.api.java.function.TriFunction;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class KittyLibBlockEntry<T extends KittyLibBlockSet> {

    private final TriFunction<T, String, KittyLibBlockEntry<T>, String> namingFunction;
    private final BiFunction<T, Block.Settings, Block> blockFunction;
    private final BiConsumer<Block, BlockFamily.Builder> blockFamilyConsumer;

    public KittyLibBlockEntry(TriFunction<T, String, KittyLibBlockEntry<T>, String> namingFunction, BiFunction<T, Block.Settings, Block> blockFunction, BiConsumer<Block, BlockFamily.Builder> blockFamilyConsumer) {
        this.namingFunction = namingFunction;
        this.blockFunction = blockFunction;
        this.blockFamilyConsumer = blockFamilyConsumer;
    }

    public Block register(T blockSet, Block.Settings blockSettings) {
        Identifier id = blockSet.applyIdFunction(namingFunction.apply(blockSet, blockSet.getSetName(), this));
        return Registry.register(Registries.BLOCK, id, blockFunction.apply(blockSet, blockSettings));
    }

    public TriFunction<T, String, KittyLibBlockEntry<T>, String> namingFunction() {
        return namingFunction;
    }

    public BiFunction<T, Block.Settings, Block> blockFunction() {
        return blockFunction;
    }

    public BiConsumer<Block, BlockFamily.Builder> blockFamilyConsumer() {
        return blockFamilyConsumer;
    }

}