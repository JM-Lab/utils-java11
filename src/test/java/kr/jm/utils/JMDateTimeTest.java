package kr.jm.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;

public class JMDateTimeTest {

    @Test
    public void getIsoInstantFormat() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2022-03-09T02:57:25.444911001Z");
        System.out.println(JMDateTime.getDefaultIsoInstantFormat(zonedDateTime));
        // ZoneDateTime.now 로 얻은 갑의 nano 값이 보통 000 이라 아래와 같이 나오지 않음
        Assert.assertEquals("2022-03-09T02:57:25.444911001Z", JMDateTime.getDefaultIsoInstantFormat(zonedDateTime));
        Assert.assertEquals("2022-03-09T02:57:25.444911Z",
                JMDateTime.getDefaultIsoInstantFormat(ZonedDateTime.parse("2022-03-09T02:57:25.444911000Z")));
        Assert.assertEquals("2022-03-09T02:57:25Z", JMDateTime.getIsoInstantFormat(zonedDateTime));
        Assert.assertEquals("2022-03-09T02:57:25.444911Z", JMDateTime.getIsoInstantFormatWithMicro(zonedDateTime));
        Assert.assertEquals("2022-03-09T02:57:25.444Z", JMDateTime.getIsoInstantFormatWithMills(zonedDateTime));
        Assert.assertEquals("2022-03-09T02:00:00Z",
                JMDateTime.getDefaultIsoInstantFormat(JMDateTime.extractToHour(zonedDateTime)));
        Assert.assertEquals("2022-03-09T02:57:00Z",
                JMDateTime.getDefaultIsoInstantFormat(JMDateTime.extractToMinute(zonedDateTime)));
    }
}