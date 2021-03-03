package kr.jm.utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;
import static kr.jm.utils.JMStream.buildTokenStream;
import static kr.jm.utils.JMString.LINE_SEPARATOR;

/**
 * The interface Jm collections.
 */
public interface JMCollections {

    /**
     * Is not null or empty boolean.
     *
     * @param collection the collection
     * @return the boolean
     */
    static boolean isNotNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    /**
     * Is null or empty boolean.
     *
     * @param collection the collection
     * @return the boolean
     */
    static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * Gets last.
     *
     * @param <T>  the type parameter
     * @param <L>  the type parameter
     * @param list the list
     * @return the last
     */
    static <T, L extends List<T>> T getLast(L list) {
        return isNullOrEmpty(list) ? null : list.get(list.size() - 1);
    }

    /**
     * Sort list.
     *
     * @param <T>  the type parameter
     * @param <L>  the type parameter
     * @param list the list
     * @return the list
     */
    static <T extends Comparable<T>, L extends List<T>> List<T> sort(L list) {
        Collections.sort(list);
        return list;
    }

    /**
     * Sort l.
     *
     * @param <T>        the type parameter
     * @param <L>        the type parameter
     * @param list       the list
     * @param comparator the comparator
     * @return the l
     */
    static <T, L extends List<T>> L sort(L list, Comparator<? super T> comparator) {
        list.sort(comparator);
        return list;
    }

    /**
     * Build list list.
     *
     * @param <E>     the type parameter
     * @param objects the objects
     * @return the list
     */
    @SafeVarargs
    static <E> List<E> buildList(E... objects) {
        return JMOptional.getOptional(objects).map(Arrays::asList).orElseGet(ArrayList::new);
    }

    /**
     * Build list list.
     *
     * @param <E>      the type parameter
     * @param iterable the iterable
     * @return the list
     */
    static <E> List<E> buildList(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).collect(toList());
    }

    /**
     * Build merged list list.
     *
     * @param <E>         the type parameter
     * @param collection1 the collection 1
     * @param collection2 the collection 2
     * @return the list
     */
    static <E> List<E> buildMergedList(Collection<E> collection1, Collection<E> collection2) {
        List<E> mergedList = new ArrayList<>(collection1);
        mergedList.addAll(collection2);
        return mergedList;
    }

    /**
     * Build list from csv list.
     *
     * @param csvString the csv string
     * @return the list
     */
    static List<String> buildListFromCsv(String csvString) {
        return buildList(JMArrays.buildArrayFromCsv(csvString));
    }

    /**
     * Build list with delimiter list.
     *
     * @param stringWithDelimiter the string with delimiter
     * @param delimiter           the delimiter
     * @return the list
     */
    static List<String> buildListWithDelimiter(String stringWithDelimiter, String delimiter) {
        return buildTokenStream(stringWithDelimiter, delimiter).collect(toList());
    }

    /**
     * Build list by line list.
     *
     * @param stringByLine the string by line
     * @return the list
     */
    static List<String> buildListByLine(String stringByLine) {
        return buildListWithDelimiter(stringByLine, LINE_SEPARATOR);
    }

    /**
     * Split into sub list list.
     *
     * @param <E>        the type parameter
     * @param list       the list
     * @param targetSize the target size
     * @return the list
     */
    static <E> List<List<E>> splitIntoSubList(List<E> list, int targetSize) {
        int listSize = list.size();
        return JMStream.numberRange(0, listSize, targetSize)
                .mapToObj(index -> list.subList(index, Math.min(index + targetSize, listSize))).collect(toList());
    }

    /**
     * Gets reversed.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the reversed
     */
    static <T> List<T> getReversed(Collection<T> collection) {
        List<T> reversedList = new ArrayList<>(collection);
        Collections.reverse(reversedList);
        return reversedList;
    }

    /**
     * Transform list list.
     *
     * @param <T>               the type parameter
     * @param <R>               the type parameter
     * @param collection        the collection
     * @param transformFunction the transform function
     * @return the list
     */
    static <T, R> List<R> transformList(Collection<T> collection, Function<T, R> transformFunction) {
        return collection.stream().map(transformFunction).collect(toList());
    }

    /**
     * New synchronized list list.
     *
     * @param <T> the type parameter
     * @return the list
     */
    static <T> List<T> newSynchronizedList() {
        return Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * New set set.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the set
     */
    static <T> Set<T> newSet(Collection<T> collection) {
        return new HashSet<>(collection);
    }

    /**
     * New list list.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the list
     */
    static <T> List<T> newList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    /**
     * New map map.
     *
     * @param <K>        the type parameter
     * @param <V>        the type parameter
     * @param collection the collection
     * @return the map
     */
    static <K, V> Map<K, V> newMap(Collection<Map.Entry<K, V>> collection) {
        return collection.stream().collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * New map map.
     *
     * @param <K>     the type parameter
     * @param <V>     the type parameter
     * @param entries the entries
     * @return the map
     */
    @SafeVarargs
    static <K, V> Map<K, V> newMap(Map.Entry<K, V>... entries) {
        return newMap(Arrays.asList(entries));
    }

    /**
     * Add and get t.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @param item       the item
     * @return the t
     */
    static <T> T addAndGet(Collection<T> collection, T item) {
        collection.add(item);
        return item;
    }

    /**
     * Build new list list.
     *
     * @param <T>               the type parameter
     * @param <R>               the type parameter
     * @param collection        the collection
     * @param transformFunction the transform function
     * @return the list
     */
    static <T, R> List<R> buildNewList(Collection<T> collection, Function<T, R> transformFunction) {
        return collection.stream().map(transformFunction).collect(Collectors.toList());
    }

    /**
     * New map map.
     *
     * @param <K>     the type parameter
     * @param <V>     the type parameter
     * @param initMap the init map
     * @return the map
     */
    static <K, V> Map<K, V> newMap(Map<K, V> initMap) {
        return new HashMap<>(initMap);
    }

    static <T> List<List<T>> zip(List<T>... lists) {
        // ex) [1, 2, 3], [4, 5, 6] -> [[1, 4], [2, 5], [3, 6]]
        // ex) [1], [4, 5, 6], [7, 8] -> [[1, 4, 7], [5, 8], [6]]
        List<List<T>> zipped = new ArrayList<>();
        for (List<T> list : lists) {
            for (int i = 0, listSize = list.size(); i < listSize; i++) {
                List<T> list2;
                if (i >= zipped.size())
                    zipped.add(list2 = new ArrayList<>());
                else
                    list2 = zipped.get(i);
                list2.add(list.get(i));
            }
        }
        return zipped;
    }

    static <T> List<List<T>> generatePermutations(List<T>... lists) {
        ArrayList<List<T>> result = new ArrayList<>();
        generatePermutations(lists, result, 0, new ArrayList<>());
        return result;
    }

    private static <T> void generatePermutations(List<T>[] lists, List<List<T>> result, int depth,
            List<T> current) {
        if (depth == lists.length) {
            result.add(current);
            return;
        }
        for (int i = 0; i < lists[depth].size(); i++) {
            ArrayList<T> newCurrent = new ArrayList<>(current);
            newCurrent.add(lists[depth].get(i));
            generatePermutations(lists, result, depth + 1, newCurrent);
        }
    }

}
