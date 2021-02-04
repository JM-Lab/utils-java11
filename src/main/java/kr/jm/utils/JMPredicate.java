package kr.jm.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * The interface Jm predicate.
 */
public interface JMPredicate {

    /**
     * Gets true.
     *
     * @param <T> the type parameter
     * @return the true
     */
    static <T> Predicate<T> getTrue() {
        return x -> TRUE;
    }

    /**
     * Gets false.
     *
     * @param <T> the type parameter
     * @return the false
     */
    static <T> Predicate<T> getFalse() {
        return x -> FALSE;
    }

    /**
     * Gets boolean.
     *
     * @param <T>  the type parameter
     * @param bool the bool
     * @return the boolean
     */
    static <T> Predicate<T> getBoolean(boolean bool) {
        return x -> bool;
    }

    /**
     * Gets greater.
     *
     * @param x the x
     * @return the greater
     */
    static Predicate<Number> getGreater(Number x) {
        return number -> number.doubleValue() > x.doubleValue();
    }

    /**
     * Gets greater or equal.
     *
     * @param x the x
     * @return the greater or equal
     */
    static Predicate<Number> getGreaterOrEqual(Number x) {
        return number -> number.doubleValue() >= x.doubleValue();
    }

    /**
     * Gets less.
     *
     * @param x the x
     * @return the less
     */
    static Predicate<Number> getLess(Number x) {
        return number -> number.doubleValue() < x.doubleValue();
    }

    /**
     * Gets less or equal.
     *
     * @param x the x
     * @return the less or equal
     */
    static Predicate<Number> getLessOrEqual(Number x) {
        return number -> number.doubleValue() <= x.doubleValue();
    }

    /**
     * Gets greater size.
     *
     * @param x the x
     * @return the greater size
     */
    static Predicate<Collection<?>> getGreaterSize(int x) {
        return collection -> collection.size() > x;
    }

    /**
     * Gets greater map size.
     *
     * @param x the x
     * @return the greater map size
     */
    static Predicate<Map<?, ?>> getGreaterMapSize(int x) {
        return map -> map.size() > x;
    }

    /**
     * Gets greater length.
     *
     * @param x the x
     * @return the greater length
     */
    static Predicate<Object[]> getGreaterLength(int x) {
        return array -> array.length > x;
    }

    /**
     * Gets greater or equal size.
     *
     * @param x the x
     * @return the greater or equal size
     */
    static Predicate<Collection<?>> getGreaterOrEqualSize(int x) {
        return collection -> collection.size() >= x;
    }

    /**
     * Gets greater map or equal size.
     *
     * @param x the x
     * @return the greater map or equal size
     */
    static Predicate<Map<?, ?>> getGreaterMapOrEqualSize(int x) {
        return map -> map.size() > x;
    }

    /**
     * Gets greater or equal length.
     *
     * @param x the x
     * @return the greater or equal length
     */
    static Predicate<Object[]> getGreaterOrEqualLength(int x) {
        return array -> array.length >= x;
    }

    /**
     * Gets less size.
     *
     * @param x the x
     * @return the less size
     */
    static Predicate<Collection<?>> getLessSize(int x) {
        return collection -> collection.size() < x;
    }

    /**
     * Gets less map size.
     *
     * @param x the x
     * @return the less map size
     */
    static Predicate<Map<?, ?>> getLessMapSize(int x) {
        return map -> map.size() < x;
    }

    /**
     * Gets less length.
     *
     * @param x the x
     * @return the less length
     */
    static Predicate<Object[]> getLessLength(int x) {
        return array -> array.length < x;
    }

    /**
     * Gets less or equal size.
     *
     * @param x the x
     * @return the less or equal size
     */
    static Predicate<Collection<?>> getLessOrEqualSize(int x) {
        return collection -> collection.size() <= x;
    }

    /**
     * Gets less map or equal size.
     *
     * @param x the x
     * @return the less map or equal size
     */
    static Predicate<Map<?, ?>> getLessMapOrEqualSize(int x) {
        return map -> map.size() <= x;
    }

    /**
     * Gets less or equal length.
     *
     * @param x the x
     * @return the less or equal length
     */
    static Predicate<Object[]> getLessOrEqualLength(int x) {
        return array -> array.length <= x;
    }

    /**
     * Gets equal size.
     *
     * @param x the x
     * @return the equal size
     */
    static Predicate<Collection<?>> getEqualSize(int x) {
        return collection -> collection.size() == x;
    }


    /**
     * Gets contains.
     *
     * @param x the x
     * @return the contains
     */
    static Predicate<String> getContains(CharSequence x) {
        return string -> string.contains(x);
    }

    /**
     * Gets is empty.
     *
     * @return the is empty
     */
    static Predicate<String> getIsEmpty() {
        return String::isEmpty;
    }

    /**
     * Gets ends with.
     *
     * @param suffix the suffix
     * @return the ends with
     */
    static Predicate<String> getEndsWith(String suffix) {
        return string -> string.endsWith(suffix);
    }

    /**
     * Gets starts with.
     *
     * @param prefix the prefix
     * @return the starts with
     */
    static Predicate<String> getStartsWith(String prefix) {
        return string -> string.startsWith(prefix);
    }

    /**
     * Gets matches.
     *
     * @param regex the regex
     * @return the matches
     */
    static Predicate<String> getMatches(String regex) {
        return string -> string.matches(regex);
    }

    /**
     * Gets is null.
     *
     * @param <T> the type parameter
     * @return the is null
     */
    static <T> Predicate<T> getIsNull() {
        return Objects::isNull;
    }

    /**
     * Gets is not null.
     *
     * @param <T> the type parameter
     * @return the is not null
     */
    static <T> Predicate<T> getIsNotNull() {
        return Objects::nonNull;
    }

    /**
     * Peek predicate.
     *
     * @param <T>      the type parameter
     * @param consumer the consumer
     * @return the predicate
     */
    static <T> Predicate<T> peek(Consumer<T> consumer) {
        return x -> {
            consumer.accept(x);
            return true;
        };
    }

    /**
     * Peek sopl predicate.
     *
     * @param <T> the type parameter
     * @return the predicate
     */
    static <T> Predicate<T> peekSOPL() {
        return peek(System.out::println);
    }

    /**
     * Peek sop predicate.
     *
     * @param <T> the type parameter
     * @return the predicate
     */
    static <T> Predicate<T> peekSOP() {
        return peek(System.out::print);
    }

    /**
     * Negate predicate.
     *
     * @param <T>       the type parameter
     * @param predicate the predicate
     * @return the predicate
     */
    static <T> Predicate<T> negate(Predicate<T> predicate) {
        return predicate.negate();
    }

}
