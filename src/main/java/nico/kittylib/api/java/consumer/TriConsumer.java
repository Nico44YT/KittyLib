package nico.kittylib.api.java.consumer;

public interface TriConsumer<K, V, S> {
    void accept(K k, V v, S s);
}
