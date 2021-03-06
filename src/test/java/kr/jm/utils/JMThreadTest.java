package kr.jm.utils;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.LongAdder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JMThreadTest {

    @Test
    public void testRunAsyncRunnableExecutor() {
        JMThread.runAsync(() -> {
            throw new RuntimeException("Error!!!");
        });

    }

    @Test
    public void testRunAsyncSupplyExecutor() {
        JMThread.supplyAsync(() -> 1 / 0, e -> 0);
    }

    @Test
    public void testGetThreadQueue() {
        ExecutorService es = Executors.newFixedThreadPool(1);
        System.out.println(JMThread.getThreadQueue(es).size());
        assertEquals(0, JMThread.getThreadQueue(es).size());
        es = Executors.newCachedThreadPool();
        System.out.println(JMThread.getThreadQueue(es).size());
        assertEquals(0, JMThread.getThreadQueue(es).size());
        es = Executors.newSingleThreadExecutor();
        try {
            assertEquals(0, JMThread.getThreadQueue(es).size());
            System.out.println(JMThread.getThreadQueue(es).size());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported ExecutorService"));
        }
        es = JMThread.newSingleThreadPool();
        System.out.println(JMThread.getThreadQueue(es).size());
        assertEquals(0, JMThread.getThreadQueue(es).size());
    }

    @Test
    public void testRunWithSchedule() throws Exception {
        System.out.println(ZonedDateTime.now());
        long delayMillis = 1000;
        Long delayMillisResult = JMThread.runWithSchedule(delayMillis, System::currentTimeMillis).get();
        System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(delayMillisResult), ZoneId.systemDefault()));
        LongAdder count = new LongAdder();
        ScheduledFuture<?> scheduledFuture =
                JMThread.runWithScheduleAtFixedRateOnStartTime(ZonedDateTime.now().plusSeconds(1), delayMillis, () -> {
                    System.out.println(ZonedDateTime.now());
                    count.increment();
                    JMThread.sleep(1500);
                });
        JMThread.sleep(4600);
        // true면 돌고있는 것 인터럽드 걸고 중지, false 면 끝난뒤 중
        System.out.println(scheduledFuture.cancel(true));
        JMThread.sleep(1000);
        System.out.println(scheduledFuture.cancel(true));
        JMThread.sleep(1000);

        assertEquals(3, count.intValue());

        count.reset();

        scheduledFuture = JMThread.runWithScheduleWithFixedDelay(delayMillis,
                delayMillis, () -> {
                    System.out.println(ZonedDateTime.now());
                    count.increment();
                    JMThread.sleep(1500);
                });
        JMThread.sleep(4500);
        // true면 돌고있는 것 인터럽드 걸고 중지, false 면 끝난뒤 중
        System.out.println(scheduledFuture.cancel(false));
        JMThread.sleep(1000);
        System.out.println(scheduledFuture.cancel(false));
        JMThread.sleep(1000);
        assertEquals(2, count.intValue());
    }

}
