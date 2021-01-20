package kr.jm.utils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The interface Jm optional.
 */
public interface JMOptional {

    /**
     * Gets optional.
     *
     * @param string the string
     * @return the optional
     */
    static Optional<String> getOptional(String string) {
        return Optional.ofNullable(string).filter(Predicate.not(String::isBlank));
    }

    /**
     * Gets optional if true.
     *
     * @param <T>    the type parameter
     * @param bool   the bool
     * @param target the target
     * @return the optional if true
     */
    static <T> Optional<T> getOptionalIfTrue(boolean bool, T target) {
        return bool ? Optional.ofNullable(target) : Optional.empty();
    }

    /**
     * Gets nullable and filtered optional.
     *
     * @param <T>       the type parameter
     * @param target    the target
     * @param predicate the predicate
     * @return the nullable and filtered optional
     */
    static <T> Optional<T> getNullableAndFilteredOptional(T target, Predicate<T> predicate) {
        return Optional.ofNullable(target).filter(predicate);
    }

    /**
     * Gets optional if exist.
     *
     * @param <T>                   the type parameter
     * @param <R>                   the type parameter
     * @param optional              the optional
     * @param returnBuilderFunction the return builder function
     * @return the optional if exist
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static <T, R> Optional<R> getOptionalIfExist(Optional<T> optional, Function<T, R> returnBuilderFunction) {
        return optional.map(returnBuilderFunction);
    }

    /**
     * Gets optional if exist.
     *
     * @param <T1>                  the type parameter
     * @param <T2>                  the type parameter
     * @param <R>                   the type parameter
     * @param firstOptional         the first optional
     * @param secondOptional        the second optional
     * @param returnBuilderFunction the return builder function
     * @return the optional if exist
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static <T1, T2, R> Optional<R> getOptionalIfExist(Optional<T1> firstOptional, Optional<T2> secondOptional,
            BiFunction<T1, T2, R> returnBuilderFunction) {
        return firstOptional.flatMap(t1 -> secondOptional.map(t2 -> returnBuilderFunction.apply(t1, t2)));
    }

    /**
     * Gets optional if exist.
     *
     * @param <T>                   the type parameter
     * @param <C>                   the type parameter
     * @param <R>                   the type parameter
     * @param collection            the collection
     * @param returnBuilderFunction the return builder function
     * @return the optional if exist
     */
    static <T, C extends Collection<T>, R> Optional<R> getOptionalIfExist(C collection,
            Function<C, R> returnBuilderFunction) {
        return getOptional(collection).map(returnBuilderFunction);
    }

    /**
     * Gets optional if exist.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <M>                   the type parameter
     * @param <R>                   the type parameter
     * @param map                   the map
     * @param returnBuilderFunction the return builder function
     * @return the optional if exist
     */
    static <K, V, M extends Map<K, V>, R> Optional<R> getOptionalIfExist(M map,
            Function<M, R> returnBuilderFunction) {
        return getOptional(map).map(returnBuilderFunction);
    }

    /**
     * Gets value optional if exist.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <R>                   the type parameter
     * @param map                   the map
     * @param key                   the key
     * @param returnBuilderFunction the return builder function
     * @return the value optional if exist
     */
    static <K, V, R> Optional<R> getValueOptionalIfExist(Map<K, V> map, K key,
            Function<V, R> returnBuilderFunction) {
        return getOptional(map, key).map(returnBuilderFunction);
    }

    /**
     * Gets optional.
     *
     * @param <T>        the type parameter
     * @param <C>        the type parameter
     * @param collection the collection
     * @return the optional
     */
    static <T, C extends Collection<T>> Optional<C> getOptional(C collection) {
        return Optional.ofNullable(collection).filter(c -> c.size() > 0);
    }

    /**
     * Gets optional.
     *
     * @param <T>   the type parameter
     * @param array the array
     * @return the optional
     */
    static <T> Optional<T[]> getOptional(T[] array) {
        return Optional.ofNullable(array).filter(a -> a.length > 0);
    }

    /**
     * Gets optional.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param <M> the type parameter
     * @param map the map
     * @return the optional
     */
    static <K, V, M extends Map<K, V>> Optional<M> getOptional(M map) {
        return Optional.ofNullable(map).filter(m -> m.size() > 0);
    }

    /**
     * Gets optional.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param <M> the type parameter
     * @param map the map
     * @param key the key
     * @return the optional
     */
    static <K, V, M extends Map<K, V>> Optional<V> getOptional(M map, K key) {
        return getOptional(map).map(m -> m.get(key));
    }

    /**
     * If exist.
     *
     * @param <E>        the type parameter
     * @param <T>        the type parameter
     * @param collection the collection
     * @param consumer   the consumer
     */
    static <E, T extends Collection<E>> void ifExist(T collection, Consumer<T> consumer) {
        getOptional(collection).ifPresent(consumer);
    }

    /**
     * Is present all boolean.
     *
     * @param optionals the optionals
     * @return the boolean
     */
    static boolean isPresentAll(Optional<?>... optionals) {
        for (Optional<?> optional : optionals)
            if (optional.isEmpty())
                return false;
        return true;
    }

    /**
     * Gets present list.
     *
     * @param <T>       the type parameter
     * @param optionals the optionals
     * @return the present list
     */
    @SafeVarargs
    static <T> List<T> getPresentList(Optional<T>... optionals) {
        return Arrays.stream(optionals).filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList());
    }

}
