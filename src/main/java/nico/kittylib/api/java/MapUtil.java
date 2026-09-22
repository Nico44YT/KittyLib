package nico.kittylib.api.java;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MapUtil {

    /**
     *
     * @param map
     * @return an inverse copy of the map, key -> value, value -> key
     */
    public static <K, V> Map<V, K> inverse(Map<K, V> map) {
        return inverse(map, HashMap::new);
    }

    public static <K, V> Map<V, K> inverse(Map<K, V> map, Supplier<Map<V, K>> supplier) {
        Map<V, K> inverted = supplier.get();
        map.forEach((key, value) -> inverted.put(value, key));
        return inverted;
    }
}
