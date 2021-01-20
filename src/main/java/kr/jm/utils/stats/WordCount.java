package kr.jm.utils.stats;

import kr.jm.utils.JMOptional;
import kr.jm.utils.helper.JM2DepthMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Word count.
 */
public class WordCount extends JM2DepthMap<String, String, Long> {

    /**
     * Instantiates a new Word count.
     */
    public WordCount() {
        super();
    }

    /**
     * Instantiates a new Word count.
     *
     * @param metricsMap the metrics map
     */
    public WordCount(Map<String, Map<String, Long>> metricsMap) {
        super(metricsMap);
    }

    /**
     * Merge all word count.
     *
     * @param numberListMapList the number list map list
     * @return the word count
     */
    public static WordCount mergeAll(List<WordCount> numberListMapList) {
        return numberListMapList.stream().reduce(new WordCount(), WordCount::merge);
    }

    /**
     * Merge.
     *
     * @param key      the key
     * @param countMap the count map
     */
    public void merge(String key, Map<String, Long> countMap) {
        countMap.forEach((word, count) -> put(key, word, count + getCount(key, word)));
    }

    /**
     * Merge word count.
     *
     * @param wordCount the word count
     * @return the word count
     */
    public WordCount merge(WordCount wordCount) {
        wordCount.forEach(this::merge);
        return this;
    }

    /**
     * Gets count.
     *
     * @param key  the key
     * @param word the word
     * @return the count
     */
    public long getCount(String key, String word) {
        return Optional.ofNullable(get(key, word)).orElse(0L);
    }

    /**
     * Gets count map.
     *
     * @param key the key
     * @return the count map
     */
    public CountMap<String> getCountMap(String key) {
        return new CountMap<>(JMOptional.getOptional(this, key).orElseGet(Collections::emptyMap));
    }

    public String toString() {return "WordCount(super=" + super.toString() + ")";}
}
