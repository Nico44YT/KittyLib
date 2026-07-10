package nico.kittylib.api.block.sets;

import com.google.common.base.Suppliers;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import nico.kittylib.api.java.function.QuadFunction;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class KittyLibBlockSet {
    public static final BiFunction<Function<KittyLibBlockSet, AbstractBlock.Settings>, SetEntryNamingFunction<KittyLibBlockSet>, KittyLibBlockEntry<KittyLibBlockSet>> NAMED_BASE = (settings, named) -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.BASE, (set, name, entry) -> named.apply(set, entry, name, null), (set) -> new Block(settings.apply(set)), (block, family) -> {
    });
    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> BASE = settings -> KittyLibBlockSet.NAMED_BASE.apply(settings, (set, entry, name, additive) -> name);

    public static final QuadFunction<
            Identifier,
            String,
            Function<KittyLibBlockSet, Block>,
            BiConsumer<Block, BlockFamily.Builder>,
            KittyLibBlockEntry<KittyLibBlockSet>> FACTORY =
            (entryId, named, blockFunction, blockFamilyConsumer) ->
                    new KittyLibBlockEntry<>(
                            entryId,
                            (set, name, entry) -> set.namingFunction.apply(set, entry, name, named),
                            blockFunction,
                            blockFamilyConsumer
                    );

    public static final QuadFunction<Identifier, String, Function<KittyLibBlockSet, AbstractBlock.Settings>, BiConsumer<Block, BlockFamily.Builder>, KittyLibBlockEntry<KittyLibBlockSet>> BASIC_BLOCK = (entryId, named, settings, blockFamilyConsumer) -> FACTORY.apply(entryId, named, (set) -> new Block(settings.apply(set)), blockFamilyConsumer);

    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> CRACKED = settings -> BASIC_BLOCK.apply(KittyLibBlockEntryIdentifiers.CRACKED, "cracked", settings, (block, family) -> family.cracked(block));
    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> CHISELED = settings -> BASIC_BLOCK.apply(KittyLibBlockEntryIdentifiers.CHISELED, "chiseled", settings, (block, family) -> family.chiseled(block));
    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> CUT = settings -> BASIC_BLOCK.apply(KittyLibBlockEntryIdentifiers.CUT, "cut", settings, (block, family) -> family.cut(block));
    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> MOSAIC = settings -> BASIC_BLOCK.apply(KittyLibBlockEntryIdentifiers.MOSAIC, "mosaic", settings, (block, family) -> family.mosaic(block));

    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> SLAB = settings -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.SLAB, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "slab"), (set) -> new SlabBlock(settings.apply(set)), (block, family) -> family.slab(block));
    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> STAIRS = settings -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.STAIRS, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "stairs"), (set) -> new StairsBlock(set.getBase().getDefaultState(), settings.apply(set)), (block, family) -> family.stairs(block));

    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> WALL = settings -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.WALL, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "wall"), (set) -> new WallBlock(settings.apply(set)), (block, family) -> family.wall(block));

    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> FENCE = settings -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.FENCE, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "fence"), (set) -> new FenceBlock(settings.apply(set)), (block, family) -> family.fence(block));
    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> FENCE_GATE = settings -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.FENCE_GATE, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "fence_gate"), (set) -> new FenceGateBlock(settings.apply(set), set.createWoodType()), (block, family) -> family.fenceGate(block));

    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> CUSTOM_FENCE = settings -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.FENCE, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "fence"), (set) -> new FenceBlock(settings.apply(set)), (block, family) -> family.customFence(block));
    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> CUSTOM_FENCE_GATE = settings -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.FENCE_GATE, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "fence_gate"), (set) -> new FenceGateBlock(settings.apply(set), set.createWoodType()), (block, family) -> family.customFenceGate(block));

    public static final BiFunction<Function<KittyLibBlockSet, AbstractBlock.Settings>, Pair<Integer, Boolean>, KittyLibBlockEntry<KittyLibBlockSet>> BUTTON = (settings, buttonPair) -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.BUTTON, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "button"), (set) -> new ButtonBlock(settings.apply(set), set.getBlockSetType(), buttonPair.getLeft(), buttonPair.getRight()), (block, family) -> family.button(block));
    public static final BiFunction<Function<KittyLibBlockSet, AbstractBlock.Settings>, PressurePlateBlock.ActivationRule, KittyLibBlockEntry<KittyLibBlockSet>> PRESSURE_PLATE = (settings, activationRule) -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.PRESSURE_PLATE, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "pressure_plate"), (set) -> new PressurePlateBlock(activationRule, settings.apply(set), set.getBlockSetType()), (block, family) -> family.pressurePlate(block));

    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> DOOR = settings -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.DOOR, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "door"), (set) -> new DoorBlock(settings.apply(set), set.getBlockSetType()), (block, family) -> family.door(block));
    public static final Function<Function<KittyLibBlockSet, AbstractBlock.Settings>, KittyLibBlockEntry<KittyLibBlockSet>> TRAPDOOR = settings -> new KittyLibBlockEntry<>(KittyLibBlockEntryIdentifiers.TRAPDOOR, (set, name, entry) -> set.namingFunction.apply(set, entry, name, "trapdoor"), (set) -> new TrapdoorBlock(settings.apply(set), set.getBlockSetType()), (block, family) -> family.trapdoor(block));

    protected KittyLibBlockEntry<?> base;
    protected final String setName;
    protected final SetEntryNamingFunction<? super KittyLibBlockSet> namingFunction;
    protected final Function<String, Identifier> identifierFunction;
    protected final LinkedHashMap<Identifier, Pair<KittyLibBlockEntry<?>, Block>> entriesMap;
    protected final Supplier<TagKey<Block>> blockTagSupplier;
    protected final Supplier<TagKey<Item>> itemTagSupplier;
    protected BlockSetType blockSetType;
    protected WoodType woodType;

    public KittyLibBlockSet(final String setName, final Function<String, Identifier> identifierFunction) {
        this(setName, (set, setName2, additive) -> setName2 + (additive != null ? "_" + additive : ""), identifierFunction);
    }

    public KittyLibBlockSet(final String setName, final OldSetEntryNamingFunction<? super KittyLibBlockSet> namingFunction, final Function<String, Identifier> identifierFunction) {
        this(setName, (set, entry, setName2, additive) -> namingFunction.apply(set, setName2, additive), identifierFunction);
    }

    public KittyLibBlockSet(final String setName, final SetEntryNamingFunction<? super KittyLibBlockSet> namingFunction, final Function<String, Identifier> identifierFunction) {
        this.setName = setName;
        this.namingFunction = namingFunction;
        this.identifierFunction = identifierFunction;

        this.entriesMap = new LinkedHashMap<>();

        this.blockTagSupplier = Suppliers.memoize(() -> TagKey.of(RegistryKeys.BLOCK, identifierFunction.apply(setName)));
        this.itemTagSupplier = Suppliers.memoize(() -> TagKey.of(RegistryKeys.ITEM, identifierFunction.apply(setName)));

        this.woodType = WoodType.OAK;
        this.blockSetType = BlockSetType.OAK;
    }

    public @Nullable Block getBase() {
        return this.entriesMap.get(KittyLibBlockEntryIdentifiers.BASE).getRight();
    }

    @SafeVarargs
    public final KittyLibBlockSet register(final KittyLibBlockEntry<KittyLibBlockSet>... entries) {
        for (KittyLibBlockEntry<KittyLibBlockSet> entry : entries) {
            this.entriesMap.put(entry.getEntryIdentifier(), new Pair(entry, entry.register(this)));
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

    public Block getBlock(Identifier entryIdentifier) {
        return this.entriesMap.get(entryIdentifier).getRight();
    }

    public Optional<Block> maybeGetBlock(Identifier entryIdentifier) {
        return Optional.ofNullable(this.entriesMap.getOrDefault(entryIdentifier, new Pair<>(null, null)).getRight());
    }

    public List<Block> getAllBlocks() {
        return this.entriesMap.values().stream().map(Pair::getRight).sorted(Comparator.comparingInt(Registries.BLOCK::getRawId)).toList();
    }

    public BlockFamily.Builder applyBlockFamily(final BlockFamily.Builder builder) {
        this.entriesMap.forEach((entryId, pair) -> {
            var entry = pair.getLeft();
            var block = pair.getRight();
            entry.blockFamilyConsumer().accept(block, builder);
        });

        return builder;
    }

    public BlockFamily createBlockFamily() {
        return this.applyBlockFamily(new BlockFamily.Builder(getBase())).build();
    }

    public KittyLibBlockSet setBlockSetType(final BlockSetType blockSetType) {
        this.blockSetType = blockSetType;
        return this;
    }

    public BlockSetType getBlockSetType() {
        return this.blockSetType;
    }

    public KittyLibBlockSet setWoodType(final WoodType woodType) {
        this.woodType = woodType;
        return this;
    }

    public WoodType createWoodType() {
        return this.woodType;
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

    public Map<Identifier, Pair<KittyLibBlockEntry<?>, Block>> getEntriesMap() {
        return entriesMap;
    }

    @FunctionalInterface
    public interface SetEntryNamingFunction<T> {
        String apply(T set, KittyLibBlockEntry<?> entry, String setName, String additive);
    }

    @FunctionalInterface
    @Deprecated
    public interface OldSetEntryNamingFunction<T> {
        String apply(T set, String setName, String additive);
    }
}