package kr.jm.utils;

import java.util.*;

/**
 * The type Nested map helper.
 */
public interface JMNestedMap {

    static Map<String, Object> getOrNewNestedMap(Map<String, Object> map, String... nestedSeriesKeys) {
        return JMArrays.isNullOrEmpty(nestedSeriesKeys) ? map : getOrNewNestedMap(
                digNestedMap(map, nestedSeriesKeys[0]), extractNestNestedSeriesKeys(nestedSeriesKeys));
    }

    static String getLastObjectToString(Map<String, Object> map, String... nestedSeriesKeys) {
        return Objects.toString(getLastObject(map, nestedSeriesKeys));
    }

    static Optional<String> getLastObjectToStringOptional(Map<String, Object> map, String... nestedSeriesKeys) {
        return getLastObjectOptional(map, nestedSeriesKeys).map(Objects::toString);
    }

    static Object getLastObject(Map<String, Object> map, String... nestedSeriesKeys) {
        return nestedSeriesKeys.length == 1 ? map.get(nestedSeriesKeys[0]) : nestedSeriesKeys.length > 1 ?
                getLastObject(map, nestedSeriesKeys.length - 1, nestedSeriesKeys) : null;
    }

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

    static Map<String, Object> getNestedMap(Map<String, Object> map, String... nestedSeriesKeys) {
        return JMArrays.isNullOrEmpty(nestedSeriesKeys) ? map : JMOptional.getOptional(digNestedMapOrNull(map,
                nestedSeriesKeys[0])).map(nestedMap -> getNestedMap(nestedMap,
                extractNestNestedSeriesKeys(nestedSeriesKeys))).orElse(null);
    }

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
}
