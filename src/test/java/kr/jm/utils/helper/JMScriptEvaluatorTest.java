package kr.jm.utils.helper;

import org.junit.Test;

import static org.junit.Assert.*;

public class JMScriptEvaluatorTest {

    @Test
    public void eval() {
        JMScriptEvaluator jmScriptEvaluator = JMScriptEvaluator.getInstance();
        Object eval = jmScriptEvaluator.eval("(2.33 + 4) * 3.141592");
        System.out.println(eval);
        assertEquals((2.33 + 4) * 3.141592, eval);
        double expected = 9d + 14d / 3d - 6d * (113d + 43d);
        System.out.println(expected);
        assertEquals(expected, jmScriptEvaluator.eval("9 + 14 / 3 - 6 * (113 + 43)"));
        assertEquals(1560, jmScriptEvaluator.eval("(10 + 15 / 3 - 5) * (113 + 43)"));
        assertNull(jmScriptEvaluator.eval("1+3 = 4"));
        long millis = System.currentTimeMillis() -
                Double.valueOf(jmScriptEvaluator.eval("Date.now()").toString()).longValue();
        System.out.println(millis);
        assertTrue(millis <= 0);

    }
}