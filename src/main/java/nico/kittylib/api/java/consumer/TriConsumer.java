package nico.kittylib.api.java.consumer;

import java.util.Objects;

public interface TriConsumer<K, V, S> {

    /**
     * Performs this operation on the given argument.
     *
     * @param k the input argument
     * @param v the input argument
     * @param s the input argument
     */
    void accept(K k, V v, S s);

    /**
     * Returns a composed {@code QuadConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code QuadConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default TriConsumer<K, V, S> andThen(TriConsumer<? super K, ? super V, ? super S> after) {
        Objects.requireNonNull(after);
        return (K k, V v, S s) -> {
            accept(k, v, s);
            after.accept(k, v, s);
        };
    }
}
