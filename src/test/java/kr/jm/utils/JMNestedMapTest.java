package kr.jm.utils;

import kr.jm.utils.helper.JMJson;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JMNestedMapTest {

    @Test
    public void testJMNestedMap() {
        Map<String, Object> d4Map = new HashMap<>(Map.of("d4", "lastValue"));
        Map<String, Object> d2Map = JMJson.getInstance().transformToMap(Map.of("d2", Map.of("d3", d4Map)));
        Map<String, Object> nestedMap =
                JMJson.getInstance().transformToMap(Map.of("d1", d2Map, "d1-2", Map.of("d2-2", Map.of())));
        Assert.assertEquals("{d3={d4=lastValue}}",
                JMNestedMap.getOrNewNestedMap(nestedMap, "d1", "d2").toString());
        Assert.assertEquals("{d2-2={}}", JMNestedMap.getLastObject(nestedMap, "d1-2").toString());
        Assert.assertEquals(null, JMNestedMap.getLastObject(nestedMap, "d1-2s"));
        Assert.assertEquals("lastValue", JMNestedMap.getLastObjectToString(nestedMap, "d1", "d2", "d3", "d4"));
        Assert.assertEquals("{}", JMNestedMap.getLastObjectToString(nestedMap, "d1-2", "d2-2"));
        Assert.assertEquals("null", JMNestedMap.getLastObjectToString(nestedMap, "d1-2", "d2-2", "d2-3"));
        Assert.assertEquals("null", JMNestedMap.getLastObjectToString(nestedMap, "key"));
        Assert.assertEquals("lastValue", JMNestedMap.getLastObjectToString(d4Map, "d4"));
        Assert.assertEquals(null, JMNestedMap.getNestedMap(nestedMap, "d1-3", "d2-2", "d2-3"));

    }
}