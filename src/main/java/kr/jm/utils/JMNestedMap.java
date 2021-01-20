package kr.jm.utils;

import java.util.*;

/**
 * The interface Jm nested map.
 */
public interface JMNestedMap {

    /**
     * Gets or new nested map.
     *
     * @param map              the map
     * @param nestedSeriesKeys the nested series keys
     * @return the or new nested map
     */
    static Map<String, Object> getOrNewNestedMap(Map<String, Object> map, String... nestedSeriesKeys) {
        return JMArrays.isNullOrEmpty(nestedSeriesKeys) ? map : getOrNewNestedMap(
                digNestedMap(map, nestedSeriesKeys[0]), extractNestNestedSeriesKeys(nestedSeriesKeys));
    }

    /**
     * Gets last object to string.
     *
     * @param map              the map
     * @param nestedSeriesKeys the nested series keys
     * @return the last object to string
     */
    static String getLastObjectToString(Map<String, Object> map, String... nestedSeriesKeys) {
        return getLastObjectOptional(map, nestedSeriesKeys).map(Object::toString).orElse(null);
    }

    /**
     * Gets last object to string optional.
     *
     * @param map              the map
     * @param nestedSeriesKeys the nested series keys
     * @return the last object to string optional
     */
    static Optional<String> getLastObjectToStringOptional(Map<String, Object> map, String... nestedSeriesKeys) {
        return getLastObjectOptional(map, nestedSeriesKeys).map(Objects::toString);
    }

    /**
     * Gets last object.
     *
     * @param map              the map
     * @param nestedSeriesKeys the nested series keys
     * @return the last object
     */
    static Object getLastObject(Map<String, Object> map, String... nestedSeriesKeys) {
        return nestedSeriesKeys.length == 1 ? map.get(nestedSeriesKeys[0]) : nestedSeriesKeys.length > 1 ?
                getLastObject(map, nestedSeriesKeys.length - 1, nestedSeriesKeys) : null;
    }

    /**
     * Gets last object optional.
     *
     * @param map              the map
     * @param nestedSeriesKeys the nested series keys
     * @return the last object optional
     */
    static Optional<Object> getLastObjectOptional(Map<String, Object> map, String... nestedSeriesKeys) {
        return Optional.ofNullable(getLastObject(map, nestedSeriesKeys));
    }

    private static Object getLastObject(Map<String, Object> map, int lastIndex, String[] nestedSeriesKeys) {
        return getLastObjectOptional(map, lastIndex, nestedSeriesKeys).orElse(null);
    }

    private static Optional<Object> getLastObjectOptional(Map<String, Object> map, int lastIndex,
            String[] nestedSeriesKeys) {
        return JMOptional.getOptional(getNestedMap(map, Arrays.copyOfRange(nestedSeriesKeys, 0, lastIndex)),
                nestedSeriesKeys[lastIndex]);
    }

    /**
     * Gets nested map.
     *
     * @param map              the map
     * @param nestedSeriesKeys the nested series keys
     * @return the nested map
     */
    static Map<String, Object> getNestedMap(Map<String, Object> map, String... nestedSeriesKeys) {
        return JMArrays.isNullOrEmpty(nestedSeriesKeys) ? map : JMOptional.getOptional(digNestedMapOrNull(map,
                nestedSeriesKeys[0])).map(nestedMap -> getNestedMap(nestedMap,
                extractNestNestedSeriesKeys(nestedSeriesKeys))).orElse(null);
    }

    /**
     * Gets nested map optional.
     *
     * @param map              the map
     * @param nestedSeriesKeys the nested series keys
     * @return the nested map optional
     */
    static Optional<Map<String, Object>> getNestedMapOptional(Map<String, Object> map, String... nestedSeriesKeys) {
        return Optional.ofNullable(getNestedMap(map, nestedSeriesKeys));
    }

    private static Map<String, Object> digNestedMapOrNull(Map<String, Object> map, String key) {
        return (Map<String, Object>) map.get(key);
    }

    private static String[] extractNestNestedSeriesKeys(String[] nestedSeriesKeys) {
        return nestedSeriesKeys.length < 2 ? JMArrays.EMPTY_STRINGS : Arrays
                .copyOfRange(nestedSeriesKeys, 1, nestedSeriesKeys.length);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> digNestedMap(Map<String, Object> map, String key) {
        return (Map<String, Object>) map.computeIfAbsent(key, k -> new HashMap<>());
    }

    /**
     * To string string.
     *
     * @param inputTargetMeta the input target meta
     * @return the string
     */
    static String toString(Map<String, Object> inputTargetMeta) {
        return changeValue(inputTargetMeta).toString();
    }

    private static Object changeValue(Object o) {
        if (o instanceof Map)
            return digNestedValue((Map<String, Object>) o);
        return o;
    }

    private static Map<String, Object> digNestedValue(Map<String, Object> map) {
        return new TreeMap<>(JMMap.newChangedValueMap(map, o -> changeValue(o)));
    }
}
