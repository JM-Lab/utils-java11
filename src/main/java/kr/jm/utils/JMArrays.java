package kr.jm.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * The interface Jm arrays.
 */
public interface JMArrays {

    /**
     * The constant EMPTY_STRINGS.
     */
    String[] EMPTY_STRINGS = new String[0];

    /**
     * Build array e [ ].
     *
     * @param <E>     the type parameter
     * @param objects the objects
     * @return the e [ ]
     */
    @SafeVarargs
    static <E> E[] buildArray(E... objects) {
        return objects;
    }

    /**
     * Sort v [ ].
     *
     * @param <V>   the type parameter
     * @param array the array
     * @return the v [ ]
     */
    @SafeVarargs
    static <V extends Comparable<V>> V[] sort(V... array) {
        Arrays.sort(array);
        return array;
    }

    /**
     * Sort v [ ].
     *
     * @param <V>        the type parameter
     * @param array      the array
     * @param comparator the comparator
     * @return the v [ ]
     */
    static <V> V[] sort(V[] array, Comparator<? super V> comparator) {
        Arrays.sort(array, comparator);
        return array;
    }

    /**
     * Gets last.
     *
     * @param <V>   the type parameter
     * @param array the array
     * @return the last
     */
    @SafeVarargs
    static <V> V getLast(V... array) {
        return JMArrays.isNullOrEmpty(array) ? null : array[array.length - 1];
    }

    /**
     * Is null or empty boolean.
     *
     * @param <V>   the type parameter
     * @param array the array
     * @return the boolean
     */
    @SafeVarargs
    static <V> boolean isNullOrEmpty(V... array) {
        return array == null || array.length == 0;
    }

    /**
     * Is not null or empty boolean.
     *
     * @param <V>   the type parameter
     * @param array the array
     * @return the boolean
     */
    @SafeVarargs
    static <V> boolean isNotNullOrEmpty(V... array) {
        return !isNullOrEmpty(array);
    }

    /**
     * Build array from csv string [ ].
     *
     * @param csvString the csv string
     * @return the string [ ]
     */
    static String[] buildArrayFromCsv(String csvString) {
        return csvString.split(JMString.COMMA);
    }

    /**
     * Build array with delimiter string [ ].
     *
     * @param stringWithDelimiter the string with delimiter
     * @param delimiter           the delimiter
     * @return the string [ ]
     */
    static String[] buildArrayWithDelimiter(String stringWithDelimiter, String delimiter) {
        return toArray(JMCollections.buildListWithDelimiter(stringWithDelimiter, delimiter));
    }

    /**
     * To array string [ ].
     *
     * @param stringCollection the string collection
     * @return the string [ ]
     */
    static String[] toArray(Collection<String> stringCollection) {
        return JMOptional.getOptional(stringCollection).map(Collection::size).map(String[]::new)
                .map(stringCollection::toArray).orElse(EMPTY_STRINGS);
    }

    /**
     * Get empty string array string [ ].
     *
     * @return the string [ ]
     */
    @SuppressWarnings("SameReturnValue")
    static String[] getEmptyStringArray() {
        return EMPTY_STRINGS;
    }

    /**
     * Build string array string [ ].
     *
     * @param objects the objects
     * @return the string [ ]
     */
    static String[] buildStringArray(Object... objects) {
        return JMOptional.getOptional(objects).map(Arrays::stream)
                .map(stream -> stream.map(Object::toString).collect(Collectors.toList())
                        .toArray(new String[objects.length])).orElse(EMPTY_STRINGS);
    }

    /**
     * Build object array object [ ].
     *
     * @param objects the objects
     * @return the object [ ]
     */
    static Object[] buildObjectArray(Object... objects) {
        return buildArray(objects);
    }

    static <T> T[] add(T[] array, T element) {
        int length = array.length;
        array = Arrays.copyOf(array, length + 1);
        array[length] = element;
        return array;
    }
}
