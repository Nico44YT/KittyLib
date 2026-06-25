package nico.kittylib.api.block.sets;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class KittyLibColoredBlockSet extends KittyLibBlockSet {
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> BLACK = settings -> create(KittyLibBlockEntryIdentifiers.BLACK, settings.mapColor(DyeColor.BLACK), "black", Items.BLACK_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> BLUE = settings -> create(KittyLibBlockEntryIdentifiers.BLUE, settings.mapColor(DyeColor.BLUE), "blue", Items.BLUE_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> BROWN = settings -> create(KittyLibBlockEntryIdentifiers.BROWN, settings.mapColor(DyeColor.BROWN), "brown", Items.BROWN_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> CYAN = settings -> create(KittyLibBlockEntryIdentifiers.CYAN, settings.mapColor(DyeColor.CYAN), "cyan", Items.CYAN_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> GRAY = settings -> create(KittyLibBlockEntryIdentifiers.GRAY, settings.mapColor(DyeColor.GRAY), "gray", Items.GRAY_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> GREEN = settings -> create(KittyLibBlockEntryIdentifiers.GREEN, settings.mapColor(DyeColor.GREEN), "green", Items.GREEN_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> LIGHT_BLUE = settings -> create(KittyLibBlockEntryIdentifiers.LIGHT_BLUE, settings.mapColor(DyeColor.LIGHT_BLUE), "light_blue", Items.LIGHT_BLUE_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> LIGHT_GRAY = settings -> create(KittyLibBlockEntryIdentifiers.LIGHT_GRAY, settings.mapColor(DyeColor.LIGHT_GRAY), "light_gray", Items.LIGHT_GRAY_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> LIME = settings -> create(KittyLibBlockEntryIdentifiers.LIME, settings.mapColor(DyeColor.LIME), "lime", Items.LIME_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> MAGENTA = settings -> create(KittyLibBlockEntryIdentifiers.MAGENTA, settings.mapColor(DyeColor.MAGENTA), "magenta", Items.MAGENTA_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> ORANGE = settings -> create(KittyLibBlockEntryIdentifiers.ORANGE, settings.mapColor(DyeColor.ORANGE), "orange", Items.ORANGE_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> PINK = settings -> create(KittyLibBlockEntryIdentifiers.PINK, settings.mapColor(DyeColor.PINK), "pink", Items.PINK_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> PURPLE = settings -> create(KittyLibBlockEntryIdentifiers.PURPLE, settings.mapColor(DyeColor.PURPLE), "purple", Items.PURPLE_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> RED = settings -> create(KittyLibBlockEntryIdentifiers.RED, settings.mapColor(DyeColor.RED), "red", Items.RED_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> WHITE = settings -> create(KittyLibBlockEntryIdentifiers.WHITE, settings.mapColor(DyeColor.WHITE), "white", Items.WHITE_DYE);
    public static final Function<AbstractBlock.Settings, KittyLibColoredBlockEntry<KittyLibColoredBlockSet>> YELLOW = settings -> create(KittyLibBlockEntryIdentifiers.YELLOW, settings.mapColor(DyeColor.YELLOW), "yellow", Items.YELLOW_DYE);

    public static final Function<AbstractBlock.Settings, List<KittyLibColoredBlockEntry<KittyLibColoredBlockSet>>> ALL = settings -> new LinkedList<>(List.of(
            WHITE.apply(settings), LIGHT_GRAY.apply(settings), GRAY.apply(settings), BLACK.apply(settings),
            BROWN.apply(settings), RED.apply(settings), ORANGE.apply(settings), YELLOW.apply(settings),
            LIME.apply(settings), GREEN.apply(settings), CYAN.apply(settings), LIGHT_BLUE.apply(settings), BLUE.apply(settings),
            PURPLE.apply(settings), MAGENTA.apply(settings), PINK.apply(settings)
    ));

    private final Function<AbstractBlock.Settings, Block> blockFunction;

    public KittyLibColoredBlockSet(String setName, OldSetEntryNamingFunction<? super KittyLibBlockSet> namingFunction, Function<AbstractBlock.Settings, Block> blockFunction, Function<String, Identifier> identifierFunction) {
        this(setName, (set, entry, setName2, additive) -> namingFunction.apply(set, setName2, additive), blockFunction, identifierFunction);
    }

    public KittyLibColoredBlockSet(String setName, SetEntryNamingFunction<? super KittyLibBlockSet> namingFunction, Function<AbstractBlock.Settings, Block> blockFunction, Function<String, Identifier> identifierFunction) {
        super(setName, namingFunction, identifierFunction);

        this.blockFunction = blockFunction;
    }

    public final KittyLibColoredBlockSet registerForAll(AbstractBlock.Settings settings) {
        ALL.apply(settings).forEach(entry -> {
            this.entriesMap.put(entry.getEntryIdentifier(), new Pair<>(entry, entry.register(this)));
        });

        return this;
    }

    @SafeVarargs
    public final KittyLibColoredBlockSet registerColored(final KittyLibColoredBlockEntry<KittyLibColoredBlockSet>... entries) {
        for (KittyLibColoredBlockEntry<KittyLibColoredBlockSet> entry : entries) {
            this.entriesMap.put(entry.getEntryIdentifier(), new Pair<>(entry, entry.register(this)));
        }

        return this;
    }

    public Function<AbstractBlock.Settings, Block> getBlockFunction() {
        return this.blockFunction;
    }

    public static <T extends KittyLibColoredBlockSet> KittyLibColoredBlockEntry<T> create(Identifier entryId, AbstractBlock.Settings settings, String color, Item dyeItem) {
        return new KittyLibColoredBlockEntry<>(entryId, (set, name, entry) -> set.namingFunction.apply(set, entry, name, color), (set) -> set.getBlockFunction().apply(settings), (block, family) -> {
        }, dyeItem);
    }
}