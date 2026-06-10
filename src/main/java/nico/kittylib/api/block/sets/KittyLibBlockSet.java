package nico.kittylib.api.block.sets;

import com.google.common.base.Suppliers;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class KittyLibBlockSet {
    public static final KittyLibBlockEntry<KittyLibBlockSet> BASE = new KittyLibBlockEntry<>((set, name) -> set.namingFunction.apply(set, name, null), (set, settings) -> new Block(settings), (block, family) -> {
    });
    public static final KittyLibBlockEntry<KittyLibBlockSet> SLAB = new KittyLibBlockEntry<>((set, name) -> set.namingFunction.apply(set, name, "slab"), (set, settings) -> new SlabBlock(settings), (block, family) -> family.slab(block));
    public static final KittyLibBlockEntry<KittyLibBlockSet> STAIRS = new KittyLibBlockEntry<>((set, name) -> set.namingFunction.apply(set, name, "stairs"), (set, settings) -> new StairsBlock(set.getBlock(BASE).getDefaultState(), settings), (block, family) -> family.stairs(block));
    public static final KittyLibBlockEntry<KittyLibBlockSet> WALL = new KittyLibBlockEntry<>((set, name) -> set.namingFunction.apply(set, name, "wall"), (set, settings) -> new WallBlock(settings), (block, family) -> family.wall(block));

    protected final String setName;
    protected final SetEntryNamingFunction<? super KittyLibBlockSet> namingFunction;
    protected final Function<String, Identifier> identifierFunction;
    protected final LinkedHashMap<KittyLibBlockEntry<? extends KittyLibBlockSet>, Block> entriesMap;
    protected final Supplier<TagKey<Block>> blockTagSupplier;
    protected final Supplier<TagKey<Item>> itemTagSupplier;

    public KittyLibBlockSet(final String setName, final Function<String, Identifier> identifierFunction) {
        this(setName, (set, setName2, additive) -> setName2 + (additive != null ? "_" + additive : ""), identifierFunction);
    }

    public KittyLibBlockSet(final String setName, final SetEntryNamingFunction<? super KittyLibBlockSet> namingFunction, final Function<String, Identifier> identifierFunction) {
        this.setName = setName;
        this.namingFunction = namingFunction;
        this.identifierFunction = identifierFunction;

        this.entriesMap = new LinkedHashMap<>();

        this.blockTagSupplier = Suppliers.memoize(() -> TagKey.of(RegistryKeys.BLOCK, identifierFunction.apply(setName)));
        this.itemTagSupplier = Suppliers.memoize(() -> TagKey.of(RegistryKeys.ITEM, identifierFunction.apply(setName)));
    }

    public @Nullable Block getBase() {
        return this.entriesMap.get(BASE);
    }

    @SafeVarargs
    public final KittyLibBlockSet register(final AbstractBlock.Settings settings, final KittyLibBlockEntry<KittyLibBlockSet>... entries) {
        for (KittyLibBlockEntry<KittyLibBlockSet> entry : entries) {
            this.entriesMap.put(entry, entry.register(this, settings));
        }

        return this;
    }

    /*
        public KittyLibBlockSet register(final Map<AbstractBlock.Settings, KittyLibBlockEntry<KittyLibBlockSet>> entries) {
        this.entriesMap.clear();
        this.entriesMap.putAll(entriesMap);

        return this;
    }
     */

    public Block getBlock(final KittyLibBlockEntry<KittyLibBlockSet> entry) {
        return this.entriesMap.get(entry);
    }

    public List<Block> getAllBlocks() {
        return this.entriesMap.values().stream().sorted(Comparator.comparingInt(Registries.BLOCK::getRawId)).toList();
    }

    public BlockFamily.Builder applyBlockFamily(final BlockFamily.Builder builder) {
        this.entriesMap.forEach((entry, block) -> {
            entry.blockFamilyConsumer().accept(block, builder);
        });

        return builder;
    }

    public BlockFamily createBlockFamily() {
        return this.applyBlockFamily(new BlockFamily.Builder(this.getBlock(BASE))).build();
    }

    public TagKey<Block> getBlockTag() {
        return blockTagSupplier.get();
    }

    public TagKey<Item> getItemTag() {
        return itemTagSupplier.get();
    }

    public String getSetName() {
        return setName;
    }

    public Identifier applyIdFunction(String name) {
        return identifierFunction.apply(name);
    }

    public Map<KittyLibBlockEntry<? extends KittyLibBlockSet>, Block> getEntriesMap() {
        return entriesMap;
    }

    @FunctionalInterface
    public interface SetEntryNamingFunction<T> {
        String apply(T set, String setName, String additive);
    }
}
