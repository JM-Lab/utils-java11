package kr.jm.utils.stats.collector;

import kr.jm.utils.JMString;

import java.util.List;
import java.util.Map;

/**
 * The type Word number collector bundle.
 */
public class WordNumberCollectorBundle {
    private String collectorId;
    private WordItemCollector wordCollector;
    private NumberItemCollector numberCollector;

    /**
     * Instantiates a new Word number collector bundle.
     *
     * @param collectorId the collector id
     */
    public WordNumberCollectorBundle(String collectorId) {
        this.collectorId = collectorId;
        this.wordCollector = new WordItemCollector();
        this.numberCollector = new NumberItemCollector();
    }

    /**
     * Instantiates a new Word number collector bundle.
     */
    protected WordNumberCollectorBundle() {}

    /**
     * Gets collector id.
     *
     * @return the collector id
     */
    public String getCollectorId() {
        return collectorId;
    }

    /**
     * Add word word number collector bundle.
     *
     * @param key  the key
     * @param word the word
     * @return the word number collector bundle
     */
    public WordNumberCollectorBundle addWord(String key, String word) {
        wordCollector.add(key, word);
        return this;
    }

    /**
     * Add word list word number collector bundle.
     *
     * @param key      the key
     * @param wordList the word list
     * @return the word number collector bundle
     */
    public WordNumberCollectorBundle addWordList(String key,
            List<String> wordList) {
        wordCollector.addAll(key, wordList);
        return this;
    }

    /**
     * Build word count metrics map map.
     *
     * @return the map
     */
    public Map<String, Map<String, Long>> buildWordCountMetricsMap() {
        return wordCollector.extractCollectingMap();
    }

    /**
     * Add number word number collector bundle.
     *
     * @param key    the key
     * @param number the number
     * @return the word number collector bundle
     */
    public WordNumberCollectorBundle addNumber(String key, Number number) {
        numberCollector.add(key, number);
        return this;
    }

    /**
     * Add number list word number collector bundle.
     *
     * @param key        the key
     * @param numberList the number list
     * @return the word number collector bundle
     */
    public WordNumberCollectorBundle addNumberList(String key,
            List<Number> numberList) {
        numberCollector.addAll(key, numberList);
        return this;
    }

    /**
     * Build number stats metrics map map.
     *
     * @return the map
     */
    public Map<String, Map<String, Number>> buildNumberStatsMetricsMap() {
        return numberCollector.extractCollectingMap();
    }

    /**
     * Add data word number collector bundle.
     *
     * @param key  the key
     * @param data the data
     * @return the word number collector bundle
     */
    public WordNumberCollectorBundle addData(String key, String data) {
        if (JMString.isNumber(data))
            addNumber(key, Double.valueOf(data));
        else
            addWord(key, data);
        return this;
    }

    /**
     * Merge word number collector bundle.
     *
     * @param wordNumberCollector the word number collector
     * @return the word number collector bundle
     */
    public WordNumberCollectorBundle merge(
            WordNumberCollectorBundle wordNumberCollector) {
        wordCollector.merge(wordNumberCollector.wordCollector);
        numberCollector.merge(wordNumberCollector.numberCollector);
        return this;
    }

    /**
     * Merge all word number collector bundle.
     *
     * @param wordNumberCollectorList the word number collector list
     * @return the word number collector bundle
     */
    public WordNumberCollectorBundle mergeAll(
            List<WordNumberCollectorBundle> wordNumberCollectorList) {
        for (WordNumberCollectorBundle wordNumberCollector : wordNumberCollectorList)
            merge(wordNumberCollector);
        return this;
    }


    /**
     * Gets word collector.
     *
     * @return the word collector
     */
    public WordItemCollector getWordCollector() {return this.wordCollector;}

    /**
     * Gets number collector.
     *
     * @return the number collector
     */
    public NumberItemCollector getNumberCollector() {return this.numberCollector;}

    @Override
    public String toString() {
        return "WordNumberCollectorBundle{" + "collectorId='" + collectorId +
                '\'' + ", wordCollector=" + wordCollector +
                ", numberCollector=" + numberCollector + '}';
    }
}
