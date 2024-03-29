package kr.jm.utils.helper;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JMRegexTest {
    public static String testString = " abCC bbA abCC\n abCC abCC bbA";

    @Test
    public void testFind() {
        assertTrue(new JMRegex(".+\n").find(testString));
        assertTrue(new JMRegex(".+").find(testString));
    }

    @Test
    public void testMatches() {
        assertTrue(new JMRegex(".+\n.+").matches(testString));
        assertFalse(new JMRegex(".+").matches(testString));
    }

    @Test
    public void testGetFindPartList() {
        List<String> matchedList = new JMRegex("\\w").getFindPartList(testString);
        System.out.println(matchedList);
        assertEquals(testString.toCharArray().length - 7, matchedList.size());
    }

    @Test
    public void testGetGroupNameValueMap() {
        // | 를 사용해서 여러 패턴의 이름으로 넣어서 구함 앞의 패턴에 일치하면 뒤에 것은 나오지 않음
        JMRegex jmRegex = new JMRegex("(?<n1>a\\w+)|(?<n2>b\\w+)|(?<n3>\\w+)|(?<n4>\\w+CC)|(?<n5>\\w+A)|" +
                "(?<n6>\\w+)");
        Map<String, String> groupNameValueMap = jmRegex.getFindGroupNameValueMap(testString);
        System.out.println(groupNameValueMap);
        assertEquals("{n1=abCC, n2=bbA}", groupNameValueMap.toString());
        assertEquals(2, groupNameValueMap.size());
    }

    @Test
    public void getMatchedListByGroup() {
        List<String> matchedListByGroup =
                new JMRegex(" (\\w+) (\\w+) (\\w+)\n (\\w+) (\\w+) (\\w+)").getMatchedListByGroup(testString);
        System.out.println(matchedListByGroup);
        System.out.println(matchedListByGroup.size());
        assertEquals(6, matchedListByGroup.size());
    }

    @Test
    public void getGroupNameList() {
        JMRegex jmRegex = new JMRegex(" (?<n1>\\w+) (?<n2>\\w+) (?<n3>\\w+)\n (?<n4>\\w+) (?<n5>\\w+) (?<n6>\\w+)");
        System.out.println(jmRegex.getGroupNameList());
        assertEquals("[n1, n2, n3, n4, n5, n6]", jmRegex.getGroupNameList().toString());
        assertEquals(6, jmRegex.getGroupNameList().size());
    }

    @Test
    public void getGroupNameValueMap() {
        JMRegex jmRegex = new JMRegex(" (?<n1>\\w+) (?<n2>\\w+) (?<n3>\\w+)\n (?<n4>\\w+) (?<n5>\\w+) (?<n6>\\w+)");
        Map<String, String> groupNameValueMap = jmRegex.getGroupNameValueMap(testString);
        System.out.println(groupNameValueMap);
        assertEquals("{n1=abCC, n2=bbA, n3=abCC, n4=abCC, n5=abCC, n6=bbA}", groupNameValueMap.toString());
        assertEquals(6, groupNameValueMap.size());
    }

}