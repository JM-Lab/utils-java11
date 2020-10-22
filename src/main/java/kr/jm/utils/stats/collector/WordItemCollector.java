package kr.jm.utils.stats.collector;


import kr.jm.utils.JMMap;
import kr.jm.utils.stats.generator.JMWordCountGenerator;

import java.util.Map;

/**
 * The type Word item collector.
 */
public class WordItemCollector extends AbstractItemCollector<String> {

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Map<String, Long>> extractCollectingMap() {
        return JMMap.newChangedValueMap(this, JMWordCountGenerator.getInstance()::buildCountMap);
    }

}
