package kr.jm.utils.stats;

import kr.jm.utils.JMOptional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Count map.
 *
 * @param <V> the type parameter
 */
public class CountMap<V> implements Map<V, Long> {

    private final Map<V, Long> countMap;

    /**
     * Instantiates a new Count map.
     */
    public CountMap() {
        this.countMap = new ConcurrentHashMap<>();
    }

    /**
     * Instantiates a new Count map.
     *
     * @param map the map
     */
    public CountMap(Map<V, Long> map) {
        this.countMap = map;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#size()
     */
    @Override
    public int size() {
        return countMap.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return countMap.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        return countMap.containsKey(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        return countMap.containsValue(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#get(java.lang.Object)
     */
    @Override
    public Long get(Object key) {
        return countMap.get(key);
    }

    @Override
    public Long put(V key, Long value) {
        return countMap.put(key, value);
    }

    @Override
    public Long remove(Object key) {
        return countMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends V, ? extends Long> m) {
        countMap.putAll(m);
    }

    @Override
    public void clear() {
        countMap.clear();
    }

    @Override
    public Set<V> keySet() {
        return countMap.keySet();
    }

    @Override
    public Collection<Long> values() {
        return countMap.values();
    }

    @Override
    public Set<Entry<V, Long>> entrySet() {
        return countMap.entrySet();
    }

    @Override
    public String toString() {
        return "JMCountMap{" + "countMap=" + countMap + '}';
    }

    /**
     * Increment and get long.
     *
     * @param value the value
     * @return the long
     */
    public long incrementAndGet(V value) {
        synchronized (countMap) {
            countMap.put(value, getCount(value) + 1);
            return countMap.get(value);
        }
    }

    /**
     * Gets count.
     *
     * @param value the value
     * @return the count
     */
    public long getCount(V value) {
        return JMOptional.getOptional(countMap, value).orElse(0L);
    }

    /**
     * Merge count map.
     *
     * @param countMap the count map
     * @return the count map
     */
    public CountMap<V> merge(CountMap<V> countMap) {
        synchronized (this.countMap) {
            countMap.forEach((value, count) -> this.countMap
                    .put(value, getCount(value) + count));
            return this;
        }
    }


}
