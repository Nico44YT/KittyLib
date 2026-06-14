package nico.kittylib.api.block.sets;

import com.google.common.base.Suppliers;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import nico.kittylib.api.block.sign.KittyLibSignBlock;
import nico.kittylib.api.block.sign.KittyLibWallSignBlock;
import nico.kittylib.api.java.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;

public class KittyLibBlockSet {
    public static final KittyLibBlockEntry<KittyLibBlockSet> BASE = KittyLibBlockSet.NAMED_BASE.apply(null);
    public static final Function<String, KittyLibBlockEntry<KittyLibBlockSet>> NAMED_BASE = (named) -> new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, named), (set, settings) -> new Block(settings), (block, family) -> {
    });

    public static final TriFunction<
            String,
            BiFunction<KittyLibBlockSet, AbstractBlock.Settings, Block>,
            BiConsumer<Block, BlockFamily.Builder>,
            KittyLibBlockEntry<KittyLibBlockSet>> FACTORY =
            (named,  blockFunction,blockFamilyConsumer) ->
                    new KittyLibBlockEntry<>(
                            (set, name, entry) -> set.namingFunction.apply(set, entry, name, named),
                            blockFunction,
                            blockFamilyConsumer
                    );

    public static final BiFunction<String, BiConsumer<Block, BlockFamily.Builder>, KittyLibBlockEntry<KittyLibBlockSet>> BASIC_BLOCK = (named, blockFamilyConsumer) -> FACTORY.apply(named, (set, settings) -> new Block(settings), blockFamilyConsumer);

    public static final KittyLibBlockEntry<KittyLibBlockSet> CRACKED = BASIC_BLOCK.apply("cracked", (block, family) -> family.cracked(block));
    public static final KittyLibBlockEntry<KittyLibBlockSet> CHISELED = BASIC_BLOCK.apply("chiseled", (block, family) -> family.chiseled(block));
    public static final KittyLibBlockEntry<KittyLibBlockSet> CUT = BASIC_BLOCK.apply("cut", (block, family) -> family.cut(block));
    public static final KittyLibBlockEntry<KittyLibBlockSet> MOSAIC = BASIC_BLOCK.apply("mosaic", (block, family) -> family.mosaic(block));

    public static final KittyLibBlockEntry<KittyLibBlockSet> SLAB = new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "slab"), (set, settings) -> new SlabBlock(settings), (block, family) -> family.slab(block));
    public static final KittyLibBlockEntry<KittyLibBlockSet> STAIRS = new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "stairs"), (set, settings) -> new StairsBlock(set.getBase().getDefaultState(), settings), (block, family) -> family.stairs(block));

    public static final KittyLibBlockEntry<KittyLibBlockSet> WALL = new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "wall"), (set, settings) -> new WallBlock(settings), (block, family) -> family.wall(block));

    public static final KittyLibBlockEntry<KittyLibBlockSet> FENCE = new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "fence"), (set, settings) -> new FenceBlock(settings), (block, family) -> family.fence(block));
    public static final KittyLibBlockEntry<KittyLibBlockSet> FENCE_GATE = new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "fence_gate"), (set, settings) -> new FenceGateBlock(set.createWoodType(), settings), (block, family) -> family.fenceGate(block));

    public static final KittyLibBlockEntry<KittyLibBlockSet> CUSTOM_FENCE = new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "fence"), (set, settings) -> new FenceBlock(settings), (block, family) -> family.customFence(block));
    public static final KittyLibBlockEntry<KittyLibBlockSet> CUSTOM_FENCE_GATE = new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "fence_gate"), (set, settings) -> new FenceGateBlock(set.createWoodType(), settings), (block, family) -> family.customFenceGate(block));

    public static final Function<Integer, KittyLibBlockEntry<KittyLibBlockSet>> BUTTON = (pressDuration) -> new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "button"), (set, settings) -> new ButtonBlock(set.getBlockSetType(), pressDuration, settings), (block, family) -> family.button(block));
    public static final Function<Integer, KittyLibBlockEntry<KittyLibBlockSet>> PRESSURE_PLATE = (pressDuration) -> new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "pressure_plate"), (set, settings) -> new PressurePlateBlock(set.getBlockSetType(), settings), (block, family) -> family.pressurePlate(block));

    public static final KittyLibBlockEntry<KittyLibBlockSet> DOOR = new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "door"), (set, settings) -> new DoorBlock(set.getBlockSetType(), settings), (block, family) -> family.door(block));
    public static final KittyLibBlockEntry<KittyLibBlockSet> TRAPDOOR = new KittyLibBlockEntry<>((set, name, entry) -> set.namingFunction.apply(set, entry, name, "trapdoor"), (set, settings) -> new TrapdoorBlock(set.getBlockSetType(), settings), (block, family) -> family.trapdoor(block));

    protected KittyLibBlockEntry<?> base;
    protected final String setName;
    protected final SetEntryNamingFunction<? super KittyLibBlockSet> namingFunction;
    protected final Function<String, Identifier> identifierFunction;
    protected final LinkedHashMap<KittyLibBlockEntry<? extends KittyLibBlockSet>, Block> entriesMap;
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
        return this.entriesMap.get(base);
    }

    /**
     * First entry is set as base of the following
     * @param settings
     * @param entries
     * @return
     */
    @SafeVarargs
    public final KittyLibBlockSet register(final AbstractBlock.Settings settings, final KittyLibBlockEntry<KittyLibBlockSet>... entries) {
        for (KittyLibBlockEntry<KittyLibBlockSet> entry : entries) {
            if(base == null) base = entry;

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

    public Map<KittyLibBlockEntry<? extends KittyLibBlockSet>, Block> getEntriesMap() {
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
