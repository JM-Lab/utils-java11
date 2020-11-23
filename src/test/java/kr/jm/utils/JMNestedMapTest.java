package kr.jm.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JMNestedMapTest {

    @Test
    public void testJMNestedMap() {
        Map<String, Object> d4Map = new HashMap<>(Map.of("d4", "lastValue"));
        Map<String, Object> d2Map = new HashMap<>(Map.of("d2", Map.of("d3", d4Map)));
        Map<String, Object> nestedMap = new HashMap<>(Map.of("d1", d2Map, "d1-2", Map.of("d2-2", Map.of())));
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

        nestedMap.put("list", List.of("a", "akljdf", "1355"));
        nestedMap.put("map", Map.of("baa", 1, "a1111", "2", "03", 3));
        System.out.println(new TreeMap<>(Map.of("baa", 1, "a1111", "2", "03", 3)));
        Assert.assertEquals(
                "{d1={d2={d3={d4=lastValue}}}, d1-2={d2-2={}}, list=[a, akljdf, 1355], map={03=3, a1111=2, baa=1}}",
                JMNestedMap.toString(nestedMap));


    }
}