package nico.kittylib.api.block.sets;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class KittyLibColoredBlockSet extends KittyLibBlockSet {
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> BLACK = create("black", Items.BLACK_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> BLUE = create("blue", Items.BLUE_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> BROWN = create("brown", Items.BROWN_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> CYAN = create("cyan", Items.CYAN_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> GRAY = create("gray", Items.GRAY_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> GREEN = create("green", Items.GREEN_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> LIGHT_BLUE = create("light_blue", Items.LIGHT_BLUE_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> LIGHT_GRAY = create("light_gray", Items.LIGHT_GRAY_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> LIME = create("lime", Items.LIME_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> MAGENTA = create("magenta", Items.MAGENTA_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> ORANGE = create("orange", Items.ORANGE_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> PINK = create("pink", Items.PINK_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> PURPLE = create("purple", Items.PURPLE_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> RED = create("red", Items.RED_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> WHITE = create("white", Items.WHITE_DYE);
    public static final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> YELLOW = create("yellow", Items.YELLOW_DYE);

    public static final List<KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> ALL = new LinkedList<>(List.of(
            WHITE, LIGHT_GRAY, GRAY, BLACK, BROWN, RED, ORANGE, YELLOW, LIME, GREEN, CYAN, LIGHT_BLUE, BLUE, PURPLE, MAGENTA, PINK
    ));

    private final Function<AbstractBlock.Settings, Block> blockFunction;

    public KittyLibColoredBlockSet(String setName, SetEntryNamingFunction<? super KittyLibBlockSet> namingFunction, Function<AbstractBlock.Settings, Block> blockFunction, Function<String, Identifier> identifierFunction) {
        super(setName, namingFunction, identifierFunction);

        this.blockFunction = blockFunction;
    }

    public final KittyLibColoredBlockSet registerForAll(final AbstractBlock.Settings settings) {
        ALL.forEach(entry -> {
            this.entriesMap.put(entry, entry.register(this, settings));
        });

        return this;
    }

    @SafeVarargs
    public final KittyLibColoredBlockSet registerColored(final AbstractBlock.Settings settings, final KittyLibColoredBlockEntry<KittyLibColoredBlockSet>... entries) {
        for (KittyLibColoredBlockEntry<KittyLibColoredBlockSet> entry : entries) {
            this.entriesMap.put(entry, entry.register(this, settings));
        }

        return this;
    }

    public Block getBlock(final KittyLibColoredBlockEntry<KittyLibColoredBlockSet> entry) {
        return this.entriesMap.get(entry);
    }

    public Function<AbstractBlock.Settings, Block> getBlockFunction() {
        return this.blockFunction;
    }

    public static <T extends KittyLibColoredBlockSet> KittyLibColoredBlockEntry<T> create(String color, Item dyeItem) {
        return new KittyLibColoredBlockEntry<>((set, name) -> set.namingFunction.apply(set, name, color), (set, settings) -> set.getBlockFunction().apply(settings), (block, family) -> {
        }, dyeItem);
    }
}