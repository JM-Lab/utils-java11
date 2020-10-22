package kr.jm.utils.time;

import kr.jm.utils.JMThread;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JMTimeseriesTest {
    @Test
    public void testJMTimeSeries() {
        JMTimeseries<Long> jmTimeSeries = new JMTimeseries<>(2);
        long timestamp = System.currentTimeMillis();
        jmTimeSeries.put(timestamp, timestamp);
        JMThread.sleep(1000);
        timestamp = System.currentTimeMillis();
        jmTimeSeries.put(timestamp, timestamp);
        JMThread.sleep(1000);
        timestamp = System.currentTimeMillis();
        jmTimeSeries.put(timestamp, timestamp);
        JMThread.sleep(1000);
        timestamp = System.currentTimeMillis();
        jmTimeSeries.put(timestamp, timestamp);
        JMThread.sleep(1000);
        timestamp = System.currentTimeMillis();
        jmTimeSeries.put(timestamp, timestamp);

        assertEquals(3,
                jmTimeSeries.getTimestampKeyList().stream().map(JMTime.getInstance()::getTime).peek(System.out::println)
                        .count());
        System.out.println(jmTimeSeries);
        System.out.println(jmTimeSeries.getTimestampKeyList());
    }

}
