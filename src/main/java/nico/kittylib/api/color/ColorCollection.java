package nico.kittylib.api.color;

import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import nico.kittylib.api.java.consumer.TriConsumer;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ColorCollection<T extends DyeColored> {

    private final Map<DyeColor, T> map;

    public ColorCollection(@Nullable String prefix, @Nullable String suffix, Function<String, Identifier> identifierFunction, Function<DyeColor, T> factory, Registry<T> registry) {
        this.map = new HashMap<>();

        for (DyeColor color : DyeColor.values()) {
            if (prefix == null) prefix = "";
            if (suffix == null) suffix = "";

            Identifier id = identifierFunction.apply(prefix + color.name() + suffix);
            T registered = Registry.register(registry, id, factory.apply(color));

            this.map.put(color, registered);
        }
    }

    public T get(DyeColor color) {
        return this.map.get(color);
    }

    public void forEach(BiConsumer<DyeColor, T> consumer) {
        this.map.forEach(consumer);
    }

    public void forEach(TriConsumer<Integer, DyeColor, T> consumer) {
        AtomicInteger index = new AtomicInteger(0);
        this.map.forEach(((color, t) -> {
            consumer.accept(index.getAndIncrement(), color, t);
        }));
    }
}
