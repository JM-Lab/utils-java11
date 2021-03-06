package kr.jm.utils.flow.publisher;

import kr.jm.utils.JMArrays;
import kr.jm.utils.JMResources;
import kr.jm.utils.JMThread;
import kr.jm.utils.flow.subscriber.JMSubscriberBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BulkSubmissionPublisherTest {
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
    }

    private BulkSubmissionPublisher<String> bulkSubmissionPublisher;

    @Before
    public void setUp() {
        this.bulkSubmissionPublisher = new BulkSubmissionPublisher<>(10);
    }

    @After
    public void tearDown() {
        this.bulkSubmissionPublisher.close();
    }

    @Test
    public void submit() {
        AtomicInteger atomicInteger = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        bulkSubmissionPublisher
                .subscribe(JMSubscriberBuilder.getSOPLSubscriber(list ->
                        atomicInteger.incrementAndGet() + " " +
                                count.addAndGet(list.size()) + " list - " +
                                list));
        bulkSubmissionPublisher.submit(JMArrays
                .toArray(JMResources.readLines("webAccessLogSample.txt")));
        System.out.println(atomicInteger);
        JMThread.sleep(100);
        System.out.println(atomicInteger);
        System.out.println(count);
        Assert.assertEquals(102, atomicInteger.longValue());
        Assert.assertEquals(1020, count.longValue());
    }

    @Test
    public void submitSingle() {
        AtomicInteger atomicInteger = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        bulkSubmissionPublisher
                .subscribe(JMSubscriberBuilder.getSOPLSubscriber(list ->
                        atomicInteger.incrementAndGet() + " " +
                                count.addAndGet(list.size()) + " list - " +
                                list));
        JMResources.readLines("webAccessLogSample.txt").stream().collect(Collectors.groupingBy(s -> s.length() / 10))
                .values().forEach(bulkSubmissionPublisher::submit);
        JMThread.sleep(100);
        System.out.println(atomicInteger);
        System.out.println(count);
        Assert.assertEquals(102, atomicInteger.longValue());
        Assert.assertEquals(1020, count.longValue());
    }
}