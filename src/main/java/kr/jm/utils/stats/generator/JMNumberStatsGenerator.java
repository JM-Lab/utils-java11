package kr.jm.utils.stats.generator;

import kr.jm.utils.JMMap;
import kr.jm.utils.JMOptional;
import kr.jm.utils.stats.NumberSummaryStatistics;
import kr.jm.utils.stats.StatsField;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * The type Jm number stats generator.
 */
public class JMNumberStatsGenerator {

    /**
     * Build stats map map.
     *
     * @param numberCollection the number collection
     * @return the map
     */
    public static Map<String, Number> buildStatsMap(Collection<Number> numberCollection) {
        return JMMap.newChangedKeyMap(JMOptional.getOptional(numberCollection).map(NumberSummaryStatistics::new)
                .map(NumberSummaryStatistics::getStatsFieldMap).orElseGet(Collections::emptyMap), StatsField::name);
    }

}
