package nico.kittylib.api.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class ListBuilder<V> {

    private final List<V> list;

    public ListBuilder(List<V> list) {
        this.list = list;
    }

    public ListBuilder() {
        this(new ArrayList<>());
    }

    public ListBuilder<V> add(V value) {
        list.add(value);
        return this;
    }

    public ListBuilder<V> add(Consumer<ListBuilder<V>> consumer) {
        consumer.accept(this);
        return this;
    }

    public ListBuilder<V> addAll(V... values) {
        for (V value : values) this.add(value);
        return this;
    }

    public ListBuilder<V> addAll(Collection<V> values) {
        this.list.addAll(values);
        return this;
    }

    public List<V> build() {
        return list;
    }
}
