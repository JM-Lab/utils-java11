package kr.jm.utils.stats;

import kr.jm.utils.helper.JM2DepthMap;

import java.util.List;
import java.util.Map;

/**
 * The type Number stats.
 */
public class NumberStats extends JM2DepthMap<String, String, Number> {

    /**
     * Instantiates a new Number stats.
     */
    public NumberStats() {
        super();
    }

    /**
     * Instantiates a new Number stats.
     *
     * @param metricsMap the metrics map
     */
    public NumberStats(Map<String, Map<String, Number>> metricsMap) {
        super(metricsMap);
    }


    /**
     * Merge.
     *
     * @param key                       the key
     * @param statsFieldStringNumberMap the stats field string number map
     */
    public void merge(String key,
            Map<String, Number> statsFieldStringNumberMap) {
        put(key, getStatsMap(key).merge(StatsMap.changeIntoStatsMap
                (statsFieldStringNumberMap)).getStatsFieldStringMap());
    }

    /**
     * Merge number stats.
     *
     * @param statsNumberMap the stats number map
     * @return the number stats
     */
    public NumberStats merge(NumberStats statsNumberMap) {
        statsNumberMap.forEach(this::merge);
        return this;
    }

    /**
     * Merge all number stats.
     *
     * @param numberStatsList the number stats list
     * @return the number stats
     */
    public static NumberStats mergeAll(List<NumberStats> numberStatsList) {
        return numberStatsList.stream()
                .reduce(new NumberStats(), NumberStats::merge);
    }

    /**
     * Gets stats map.
     *
     * @param key the key
     * @return the stats map
     */
    public StatsMap getStatsMap(String key) {
        return StatsMap.changeIntoStatsMap(get(key));
    }

    public String toString() {return "NumberStats(super=" + super.toString() + ")";}
}
