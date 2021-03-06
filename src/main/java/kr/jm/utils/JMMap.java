package kr.jm.utils;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static kr.jm.utils.JMString.DOT;

/**
 * The interface Jm map.
 */
public interface JMMap {

    /**
     * Put get new v.
     *
     * @param <V>      the type parameter
     * @param <K>      the type parameter
     * @param map      the map
     * @param key      the key
     * @param newValue the new value
     * @return the v
     */
    static <V, K> V putGetNew(Map<K, V> map, K key, V newValue) {
            map.put(key, newValue);
            return newValue;
    }

    /**
     * Gets or put get new.
     *
     * @param <V>              the type parameter
     * @param <K>              the type parameter
     * @param map              the map
     * @param key              the key
     * @param newValueSupplier the new value supplier
     * @return the or put get new
     */
    static <V, K> V getOrPutGetNew(Map<K, V> map, K key, Supplier<V> newValueSupplier) {
        return map.computeIfAbsent(key, k -> newValueSupplier.get());
    }

    /**
     * Build entry stream stream.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the stream
     */
    static <K, V> Stream<Entry<K, V>> buildEntryStream(Map<K, V> map) {
        return map.entrySet().stream();
    }

    /**
     * New changed key map map.
     *
     * @param <K>                 the type parameter
     * @param <V>                 the type parameter
     * @param <NK>                the type parameter
     * @param map                 the map
     * @param changingKeyFunction the changing key function
     * @return the map
     */
    static <K, V, NK> Map<NK, V> newChangedKeyMap(Map<K, V> map, Function<K, NK> changingKeyFunction) {
        return buildEntryStream(map)
                .collect(toMap(entry -> changingKeyFunction.apply(entry.getKey()), Entry::getValue));
    }

    /**
     * New changed key with entry map map.
     *
     * @param <K>                 the type parameter
     * @param <V>                 the type parameter
     * @param <NK>                the type parameter
     * @param map                 the map
     * @param changingKeyFunction the changing key function
     * @return the map
     */
    static <K, V, NK> Map<NK, V> newChangedKeyWithEntryMap(Map<K, V> map,
            Function<Entry<K, V>, NK> changingKeyFunction) {
        return buildEntryStream(map).collect(toMap(changingKeyFunction, Entry::getValue));
    }

    /**
     * New filtered changed key map map.
     *
     * @param <K>                 the type parameter
     * @param <V>                 the type parameter
     * @param <NK>                the type parameter
     * @param map                 the map
     * @param filter              the filter
     * @param changingKeyFunction the changing key function
     * @return the map
     */
    static <K, V, NK> Map<NK, V> newFilteredChangedKeyMap(Map<K, V> map, Predicate<? super Entry<K, V>> filter,
            Function<K, NK> changingKeyFunction) {
        return buildEntryStream(map).filter(filter)
                .collect(toMap(entry -> changingKeyFunction.apply(entry.getKey()), Entry::getValue));
    }

    /**
     * New filtered changed key with entry map map.
     *
     * @param <K>                 the type parameter
     * @param <V>                 the type parameter
     * @param <NK>                the type parameter
     * @param map                 the map
     * @param filter              the filter
     * @param changingKeyFunction the changing key function
     * @return the map
     */
    static <K, V, NK> Map<NK, V> newFilteredChangedKeyWithEntryMap(Map<K, V> map, Predicate<? super Entry<K, V>> filter,
            Function<Entry<K, V>, NK> changingKeyFunction) {
        return buildEntryStream(map).filter(filter).collect(toMap(changingKeyFunction, Entry::getValue));
    }

    /**
     * New changed value map map.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <NV>                  the type parameter
     * @param map                   the map
     * @param changingValueFunction the changing value function
     * @return the map
     */
    static <K, V, NV> Map<K, NV> newChangedValueMap(Map<K, V> map, Function<V, NV> changingValueFunction) {
        return buildEntryStream(map)
                .collect(toMap(Entry::getKey, entry -> changingValueFunction.apply(entry.getValue())));

    }

    /**
     * New changed value with entry map map.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <NV>                  the type parameter
     * @param map                   the map
     * @param changingValueFunction the changing value function
     * @return the map
     */
    static <K, V, NV> Map<K, NV> newChangedValueWithEntryMap(Map<K, V> map,
            Function<Entry<K, V>, NV> changingValueFunction) {
        return buildEntryStream(map).collect(toMap(Entry::getKey, changingValueFunction));
    }

    /**
     * New filtered changed value map map.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <NV>                  the type parameter
     * @param map                   the map
     * @param filter                the filter
     * @param changingValueFunction the changing value function
     * @return the map
     */
    static <K, V, NV> Map<K, NV> newFilteredChangedValueMap(Map<K, V> map, Predicate<Entry<K, V>> filter,
            Function<V, NV> changingValueFunction) {
        return buildEntryStream(map).filter(filter)
                .collect(toMap(Entry::getKey, entry -> changingValueFunction.apply(entry.getValue())));
    }

    /**
     * New filtered changed value with entry map map.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <NV>                  the type parameter
     * @param map                   the map
     * @param filter                the filter
     * @param changingValueFunction the changing value function
     * @return the map
     */
    static <K, V, NV> Map<K, NV> newFilteredChangedValueWithEntryMap(Map<K, V> map, Predicate<Entry<K, V>> filter,
            Function<Entry<K, V>, NV> changingValueFunction) {
        return buildEntryStream(map).filter(filter).collect(toMap(Entry::getKey, changingValueFunction));
    }

    /**
     * New changed key value map map.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <NK>                  the type parameter
     * @param <NV>                  the type parameter
     * @param map                   the map
     * @param changingKeyFunction   the changing key function
     * @param changingValueFunction the changing value function
     * @return the map
     */
    static <K, V, NK, NV> Map<NK, NV> newChangedKeyValueMap(Map<K, V> map, Function<K, NK> changingKeyFunction,
            Function<V, NV> changingValueFunction) {
        return buildEntryStream(map).collect(toMap(entry -> changingKeyFunction.apply(entry.getKey()),
                entry -> changingValueFunction.apply(entry.getValue())));
    }

    /**
     * New filtered changed key value map map.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <NK>                  the type parameter
     * @param <NV>                  the type parameter
     * @param map                   the map
     * @param filter                the filter
     * @param changingKeyFunction   the changing key function
     * @param changingValueFunction the changing value function
     * @return the map
     */
    static <K, V, NK, NV> Map<NK, NV> newFilteredChangedKeyValueMap(Map<K, V> map,
            Predicate<? super Entry<K, V>> filter, Function<K, NK> changingKeyFunction,
            Function<V, NV> changingValueFunction) {
        return buildEntryStream(map).filter(filter).collect(toMap(entry -> changingKeyFunction.apply(entry.getKey()),
                entry -> changingValueFunction.apply(entry.getValue())));
    }

    /**
     * New changed key value with entry map map.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <NK>                  the type parameter
     * @param <NV>                  the type parameter
     * @param map                   the map
     * @param changingKeyFunction   the changing key function
     * @param changingValueFunction the changing value function
     * @return the map
     */
    static <K, V, NK, NV> Map<NK, NV> newChangedKeyValueWithEntryMap(Map<K, V> map,
            Function<Entry<K, V>, NK> changingKeyFunction, Function<Entry<K, V>, NV> changingValueFunction) {
        return buildEntryStream(map).collect(toMap(changingKeyFunction, changingValueFunction));
    }

    /**
     * New filtered changed key value with entry map map.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <NK>                  the type parameter
     * @param <NV>                  the type parameter
     * @param map                   the map
     * @param filter                the filter
     * @param changingKeyFunction   the changing key function
     * @param changingValueFunction the changing value function
     * @return the map
     */
    static <K, V, NK, NV> Map<NK, NV> newFilteredChangedKeyValueWithEntryMap(Map<K, V> map,
            Predicate<? super Entry<K, V>> filter, Function<Entry<K, V>, NK> changingKeyFunction,
            Function<Entry<K, V>, NV> changingValueFunction) {
        return buildEntryStream(map).filter(filter).collect(toMap(changingKeyFunction, changingValueFunction));
    }

    /**
     * New filtered map map.
     *
     * @param <K>    the type parameter
     * @param <V>    the type parameter
     * @param map    the map
     * @param filter the filter
     * @return the map
     */
    static <K, V> Map<K, V> newFilteredMap(Map<K, V> map, Predicate<? super Entry<K, V>> filter) {
        return buildEntryStream(map).filter(filter).collect(toMap(Entry::getKey, Entry::getValue));
    }

    /**
     * Sort map.
     *
     * @param <K>        the type parameter
     * @param <V>        the type parameter
     * @param map        the map
     * @param comparator the comparator
     * @return the map
     */
    static <K, V> Map<K, V> sort(Map<K, V> map, Comparator<K> comparator) {
        TreeMap<K, V> sortedMap = new TreeMap<>(comparator);
        sortedMap.putAll(map);
        return sortedMap;
    }

    /**
     * Sort map.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the map
     */
    static <K extends Comparable<K>, V> Map<K, V> sort(Map<K, V> map) {
        return new TreeMap<>(map);
    }

    /**
     * Sorted stream stream.
     *
     * @param <K>        the type parameter
     * @param <V>        the type parameter
     * @param map        the map
     * @param comparator the comparator
     * @return the stream
     */
    static <K, V> Stream<Entry<K, V>> sortedStream(Map<K, V> map, Comparator<? super Entry<K, V>> comparator) {
        return buildEntryStream(map).sorted(comparator);
    }

    /**
     * Sorted stream stream.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the stream
     */
    static <K extends Comparable<K>, V> Stream<Entry<K, V>> sortedStream(Map<K, V> map) {
        return buildEntryStream(map).sorted(Entry.comparingByKey());
    }

    /**
     * Sort by value map.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the map
     */
    static <K, V extends Comparable<V>> Map<K, V> sortByValue(Map<K, V> map) {
        return sort(map, comparing(map::get));
    }

    /**
     * Sorted stream by value stream.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the stream
     */
    static <K, V extends Comparable<V>> Stream<Entry<K, V>> sortedStreamByValue(Map<K, V> map) {
        return buildEntryStream(map).sorted(Entry.comparingByValue());
    }

    /**
     * Is not null or empty boolean.
     *
     * @param map the map
     * @return the boolean
     */
    static boolean isNotNullOrEmpty(Map<?, ?> map) {
        return !JMMap.isNullOrEmpty(map);
    }

    /**
     * Is null or empty boolean.
     *
     * @param map the map
     * @return the boolean
     */
    static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    /**
     * New combined map map.
     *
     * @param <K>    the type parameter
     * @param <V>    the type parameter
     * @param keys   the keys
     * @param values the values
     * @return the map
     */
    static <K, V> Map<K, V> newCombinedMap(K[] keys, V[] values) {
        HashMap<K, V> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++)
            map.put(keys[i], getValueOfIndex(values, i));
        return map;
    }

    private static <V> V getValueOfIndex(V[] values, int i) {
        try {
            return values[i];
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * New flat key map map.
     *
     * @param map the map
     * @return the map
     */
    static Map<String, Object> newFlatKeyMap(Map<String, ?> map) {
        return newFlatKeyMap(new HashMap<>(), map);
    }

    private static Map<String, Object> newFlatKeyMap(Map<String, Object> newMap, Map<String, ?> map) {
        map.forEach((key, value) -> newFlatKeyMap(newMap, key, value));
        return newMap;
    }

    private static void newFlatKeyMap(Map<String, Object> newMap, String parentKey, Object value) {
        if (value instanceof Map)
            newFlatKeyMap(newMap,
                    JMMap.newFilteredChangedKeyMap((Map<?, ?>) value, entry -> Objects.nonNull(entry.getValue()),
                            key -> parentKey + DOT + key));
        else
            newMap.put(parentKey, value);
    }

    /**
     * New map map.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the map
     */
    static <K, V> Map<K, V> newMap(Map<K, V> map) {
        return Optional.ofNullable(map).map(HashMap::new).orElseGet(HashMap::new);
    }

}
