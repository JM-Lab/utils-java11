package kr.jm.utils.helper;

import kr.jm.utils.JMArrays;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JMLogTest {

    @Test
    public void buildMethodLogString() {
        Assert.assertEquals("buildMethodLogString()", JMLog.buildMethodLogString("buildMethodLogString"));
        Assert.assertEquals("buildMethodLogString(null, class java.lang.Object, [a, b], [ok], {say=yes}, [], [], {})",
                JMLog.buildMethodLogString("buildMethodLogString", null,
                        new Object().getClass(), JMArrays.buildArray("a", "b"), List.of("ok"), Map.of("say", "yes"),
                        JMArrays.buildArray(), List.of(), Map.of()));
    }
}