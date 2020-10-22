package kr.jm.utils;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.IntPredicate;
import java.util.stream.*;

/**
 * The interface Jm stream.
 */
public interface JMStream {

    /**
     * Number range int stream.
     *
     * @param startInclusive the start inclusive
     * @param endExclusive   the end exclusive
     * @param interval       the interval
     * @return the int stream
     */
    static IntStream numberRange(int startInclusive, int endExclusive,
            int interval) {
        return numberRange(startInclusive, endExclusive, interval,
                n -> n < endExclusive);
    }

    /**
     * Number range closed int stream.
     *
     * @param startInclusive the start inclusive
     * @param endInclusive   the end inclusive
     * @param interval       the interval
     * @return the int stream
     */
    static IntStream numberRangeClosed(int startInclusive,
            int endInclusive, int interval) {
        return numberRange(startInclusive, endInclusive, interval,
                n -> n <= endInclusive);
    }

    /**
     * Number range int stream.
     *
     * @param start     the start
     * @param end       the end
     * @param interval  the interval
     * @param predicate the predicate
     * @return the int stream
     */
    static IntStream numberRange(int start, int end, int interval,
            IntPredicate predicate) {
        return numberRangeWithCount(start, interval,
                (end - start) / interval + 1).filter(predicate);
    }

    /**
     * Number range with count int stream.
     *
     * @param start    the start
     * @param interval the interval
     * @param count    the count
     * @return the int stream
     */
    static IntStream numberRangeWithCount(int start, int interval,
            int count) {
        return IntStream.iterate(start, n -> n + interval).limit(count);
    }

    /**
     * Increase range int stream.
     *
     * @param size the size
     * @return the int stream
     */
    static IntStream increaseRange(int size) {
        return IntStream.range(0, size);
    }


    /**
     * Build int stream int stream.
     *
     * @param <N>              the type parameter
     * @param numberCollection the number collection
     * @return the int stream
     */
    static <N extends Number> IntStream
    buildIntStream(Collection<N> numberCollection) {
        return numberCollection.stream().mapToInt(Number::intValue);
    }

    /**
     * Build long stream long stream.
     *
     * @param <N>              the type parameter
     * @param numberCollection the number collection
     * @return the long stream
     */
    static <N extends Number> LongStream
    buildLongStream(Collection<N> numberCollection) {
        return numberCollection.stream().mapToLong(Number::longValue);
    }

    /**
     * Build double stream double stream.
     *
     * @param <N>              the type parameter
     * @param numberCollection the number collection
     * @return the double stream
     */
    static <N extends Number> DoubleStream
    buildDoubleStream(Collection<N> numberCollection) {
        return numberCollection.stream().mapToDouble(Number::doubleValue);
    }

    /**
     * Build reversed stream stream.
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return the stream
     */
    static <T> Stream<T> buildReversedStream(List<T> list) {
        ArrayList<T> reversedList = new ArrayList<>(list);
        Collections.reverse(reversedList);
        return reversedList.stream();
    }

    /**
     * Build stream stream.
     *
     * @param <T>   the type parameter
     * @param array the array
     * @return the stream
     */
    @SafeVarargs
    static <T> Stream<T> buildStream(T... array) {
        return JMOptional.getOptional(array).stream().flatMap(Arrays::stream);
    }

    /**
     * Build stream stream.
     *
     * @param <T>      the type parameter
     * @param iterable the iterable
     * @return the stream
     */
    static <T> Stream<T> buildStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Build stream stream.
     *
     * @param <T>      the type parameter
     * @param iterator the iterator
     * @return the stream
     */
    static <T> Stream<T> buildStream(Iterator<T> iterator) {
        return buildStream(() -> iterator);
    }

    /**
     * Build stream stream.
     *
     * @param <T>         the type parameter
     * @param enumeration the enumeration
     * @return the stream
     */
    static <T> Stream<T> buildStream(Enumeration<T> enumeration) {
        return buildStream(new Iterator<>() {
            @Override
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            @Override
            public T next() {
                return enumeration.nextElement();
            }
        });
    }

    /**
     * Build token stream stream.
     *
     * @param text      the text
     * @param delimiter the delimiter
     * @return the stream
     */
    static Stream<String> buildTokenStream(String text,
            String delimiter) {
        return JMStream.buildStream(delimiter == null ? new StringTokenizer(
                text) : new StringTokenizer(text, delimiter))
                .map(o -> (String) o);
    }

    /**
     * Build token stream stream.
     *
     * @param text the text
     * @return the stream
     */
    static Stream<String> buildTokenStream(String text) {
        return buildTokenStream(text, null);
    }

    /**
     * Build stream stream.
     *
     * @param <T>        the type parameter
     * @param isParallel the is parallel
     * @param collection the collection
     * @return the stream
     */
    static <T> Stream<T> buildStream(boolean isParallel,
            Collection<T> collection) {
        return isParallel ? collection.parallelStream() : collection.stream();
    }

    /**
     * Build concat stream stream.
     *
     * @param <T>     the type parameter
     * @param sample1 the sample 1
     * @param sample2 the sample 2
     * @return the stream
     */
    static <T> Stream<T> buildConcatStream(List<T> sample1,
            List<T> sample2) {
        return Stream.concat(sample1.stream(), sample2.stream());
    }

    /**
     * Build random number stream double stream.
     *
     * @param count the count
     * @return the double stream
     */
    static DoubleStream buildRandomNumberStream(int count) {
        return IntStream.range(0, count).mapToDouble(i -> Math.random());
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
        return JMOptional.getOptional(map).map(Map::entrySet).stream().flatMap(Collection::stream);
    }

    /**
     * Build stream stream.
     *
     * @param object the object
     * @return the stream
     */
    static Stream<?> buildStream(Object object) {
        if (object instanceof Collection)
            return ((Collection<?>) object).stream();
        else if (object.getClass().isArray())
            return Arrays.stream((Object[]) object);
        else
            return Stream.of(object);
    }

}
