package kr.jm.utils.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JMJsonTest {

    @Test
    public void testWithJsonString() {
        Map.Entry<String, List<Map.Entry<String, String>>> testEntry = Map.entry("a",
                List.of(Map.entry("1", "2"), Map.entry("3", "4"), Map.entry("5", "6"), Map.entry("7", "8")));
        String testString = JMJson.getInstance().toJsonString(testEntry);
        System.out.println(testString);
        System.out.println(JMJson.getInstance().withJsonString(testString,
                new TypeReference<Map.Entry<String, List<Map.Entry<String, Object>>>>() {}));
        Assert.assertEquals(testEntry.toString(), JMJson.getInstance()
                .withJsonString(testString, new TypeReference<Map.Entry<String, List<Map.Entry<String, Object>>>>() {})
                .toString());

    }

    @Test
    public void testNew() {
        Map.Entry<String, List<Map.Entry<String, String>>> testEntry =
                Map.entry("a", List.of(Map.entry("1", "2"), Map.entry("3", "4"),
                        Map.entry("5", "6"), Map.entry("7", "8")));
        JMJson jmJson = new JMJson(new ObjectMapper());
        Assert.assertNotEquals(jmJson, JMJson.getInstance());
        Assert.assertFalse(jmJson == JMJson.getInstance());
        String testString = jmJson.toJsonString(testEntry);
        System.out.println(testString);
        System.out.println(jmJson.withJsonString(testString,
                new TypeReference<Map.Entry<String, List<Map.Entry<String, Object>>>>() {}));
        Assert.assertEquals(testEntry.toString(),
                jmJson.withJsonString(testString,
                        new TypeReference<Map.Entry<String, List<Map.Entry<String, Object>>>>() {}).toString());

    }
}