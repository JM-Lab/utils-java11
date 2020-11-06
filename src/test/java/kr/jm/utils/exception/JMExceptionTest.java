package kr.jm.utils.exception;

import kr.jm.utils.JMStream;
import kr.jm.utils.JMThread;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JMExceptionTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JMExceptionTest.class);

    @Test
    public void getErrorHistoryManagerOptional() {
        JMException.enableErrorHistory();
        long count = JMStream.numberRangeClosed(1, 503, 1).parallel().peek(i -> {
            try {
                JMException.throwRunTimeException("Exception - " + i);
            } catch (Exception e) {
                JMException.handleException(log, e, "testLogException");
            }
        }).count();
        JMThread.sleep(100);
        System.out.println(count);
        assertTrue(JMException.getErrorHistoryManagerOptional().isPresent());
        assertEquals(count, JMException.getErrorHistoryManagerOptional().get().getTotalErrorCount());
        assertEquals(500, JMException.getErrorHistoryManagerOptional().get().getErrorMessageHistoryList().size());
    }

}