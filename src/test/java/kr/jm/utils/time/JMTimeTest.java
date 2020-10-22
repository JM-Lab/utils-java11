package kr.jm.utils.time;

import org.junit.Before;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JMTimeTest {

    private static final String ASIA_SEOUL = "Asia/Seoul";
    private static final String TIMESTAMP1 = "2014-09-26T06:36:09.327Z";
    private static final String TIMESTAMP2_1 = "2014-09-26T15:36:09.327";
    private static final String TIMESTAMP2 = "2014-09-26T15:36:09.327Z";
    private static final String TIMESTAMP3 = "2014-09-26T23:59:59.900Z";
    private static final String TIMESTAMP4 = "2014-09-26T15:00:00.000Z";
    private static final String TIMESTAMP5 = "2014-09-26T14:59:59.990Z";
    private static final String TIMESTAMP6 = "2014-09-26T14:59:59Z";
    private static final ZoneId ASIA_SEOUL_ZONE_ID = ZoneId.of(ASIA_SEOUL); // GMT, UTC
    private static final String INDEX_FORMAT = "yyyy.MM.dd";
    private static final String DATE_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";
    private static final String DATE_FORMAT2 = "dd/MMM/yyyy:HH:mm:ss";

    private final long timestamp = 1395394283524l;

    private JMTime jmTime;

    @Before
    public void setUp() {
        this.jmTime = JMTime.getInstance();
    }

    @Test
    public void testChangeFormatAndTimeZone() {
        System.out.println(jmTime.getTime(TIMESTAMP1, INDEX_FORMAT, ASIA_SEOUL_ZONE_ID));
        assertTrue(
                "2014.09.26".equals(jmTime.getTime(TIMESTAMP1, INDEX_FORMAT,
                        ASIA_SEOUL_ZONE_ID)));
        System.out.println(jmTime.getTime(TIMESTAMP2_1, INDEX_FORMAT, ASIA_SEOUL_ZONE_ID));
        assertTrue("2014.09.26"
                .equals(jmTime.getTime(TIMESTAMP2_1, INDEX_FORMAT, ASIA_SEOUL_ZONE_ID)));
        System.out.println(jmTime.getTime(TIMESTAMP2, INDEX_FORMAT, ASIA_SEOUL_ZONE_ID));
        assertTrue(
                "2014.09.27".equals(jmTime.getTime(TIMESTAMP2, INDEX_FORMAT,
                        ASIA_SEOUL_ZONE_ID)));
        System.out.println(jmTime.getTime(TIMESTAMP3, INDEX_FORMAT, ASIA_SEOUL_ZONE_ID));
        assertTrue(
                "2014.09.27".equals(jmTime.getTime(TIMESTAMP3, INDEX_FORMAT,
                        ASIA_SEOUL_ZONE_ID)));
        System.out.println(jmTime.getTime(TIMESTAMP4, INDEX_FORMAT, ASIA_SEOUL_ZONE_ID));
        assertTrue(
                "2014.09.27".equals(jmTime.getTime(TIMESTAMP4, INDEX_FORMAT,
                        ASIA_SEOUL_ZONE_ID)));
        System.out.println(jmTime.getTime(TIMESTAMP5, INDEX_FORMAT, ASIA_SEOUL_ZONE_ID));
        assertTrue(
                "2014.09.26".equals(jmTime.getTime(TIMESTAMP5, INDEX_FORMAT,
                        ASIA_SEOUL_ZONE_ID)));

        System.out.println(jmTime.getTime(TIMESTAMP6, INDEX_FORMAT, ASIA_SEOUL_ZONE_ID));
        assertTrue(
                "2014.09.26".equals(jmTime.getTime(TIMESTAMP5, INDEX_FORMAT,
                        ASIA_SEOUL_ZONE_ID)));

        System.out.println(jmTime.getTime(timestamp, jmTime.getIsoOffsetDateTimeMills(), ASIA_SEOUL_ZONE_ID));
        System.out
                .println(jmTime.getTime(timestamp, jmTime.getIsoInstantMillsTimezoneName(), ASIA_SEOUL_ZONE_ID));
        System.out.println(jmTime.getTime(timestamp, jmTime.getIsoLocalDateTimeMills(), ASIA_SEOUL_ZONE_ID));
        System.out.println(
                jmTime.getTime(timestamp, jmTime.getBasicIsoDateTimeMillsOffset(), ASIA_SEOUL_ZONE_ID));
        System.out
                .println(jmTime
                        .getTime(timestamp, jmTime.getBasicIsoDateTimeMillsTimezoneName(), ASIA_SEOUL_ZONE_ID));
        System.out.println(jmTime.getTime(timestamp, jmTime.getBasicIsoDateTimeMills(), ASIA_SEOUL_ZONE_ID));

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(jmTime.getTime(currentTimeMillis, jmTime.getIsoOffsetDateTimeMills()));
        String timeAsDefaultUtcFormat = jmTime.getTimeAsIsoInstantMills(currentTimeMillis);
        System.out.println(timeAsDefaultUtcFormat);

        assertTrue(jmTime.changeToEpochMillis(timeAsDefaultUtcFormat) == currentTimeMillis);

        System.out.println(
                jmTime.getTime(timeAsDefaultUtcFormat, jmTime.getIsoOffsetDateTimeMills(), ASIA_SEOUL_ZONE_ID));

    }

    @Test
    public void testChangeIsoTimestampInUTC() {
        String isoTimestampString = "2015-04-28T10:30:23.000+0900";
        System.out.println(
                jmTime.getTimeAsIsoInstantMills(isoTimestampString));
        assertEquals("2015-04-28T01:30:23.000Z",
                jmTime.getTimeAsIsoInstantMills(isoTimestampString));

        isoTimestampString = "2014-03-21T18:31:23.000Z";
        System.out.println(
                jmTime.getTimeAsIsoInstantMills(isoTimestampString));
        assertEquals("2014-03-21T18:31:23.000Z",
                jmTime.getTimeAsIsoInstantMills(isoTimestampString));

        isoTimestampString = "2015-04-28T10:30:23.000z";
        System.out.println(
                jmTime.getTimeAsIsoInstantMills(isoTimestampString));
        assertEquals("2015-04-28T10:30:23.000Z",
                jmTime.getTimeAsIsoInstantMills(isoTimestampString));

        assertEquals("2015-04-28T10:30:23.000Z",
                jmTime.getTimeAsIsoInstantMills(isoTimestampString));

        assertEquals("2015-04-28T10:30:23.000Z",
                jmTime.getTimeAsIsoInstantMills(isoTimestampString));

        assertEquals("2015-04-28T10:30:23.000Z",
                jmTime.getTimeAsIsoInstantMills(isoTimestampString));

    }

    @Test
    public void testGetTimeMillis() {
        long timeMillis =
                jmTime.getEpochMilli(2015, 4, 16, 00, 2, 00, "Asia/Seoul");
        System.out.println(timeMillis);
        System.out.println(jmTime.getTime(timeMillis));
        System.out.println(jmTime.getTimeAsIsoInstant(timeMillis));

        // Calender 에서는 Jan 이 0 부터 시작 함
        timeMillis = jmTime.getEpochMilli(2015, Calendar.APRIL, 16, 00, 2,
                00, "Asia/Seoul");
        System.out.println(timeMillis);
        System.out.println(jmTime.getTime(timeMillis));
        System.out.println(jmTime.getTimeAsIsoInstant(timeMillis));

    }

    @Test
    public void testChangeTimestampToLong() {
        String dateString = "27/Aug/2000:09:27:09";
        String defaultLogDateFormat = "dd/MMM/yyyy:HH:mm:ss";

        long changeToEpochMillis = jmTime.changeToEpochMillis(dateString, defaultLogDateFormat);
        System.out.println(changeToEpochMillis);
        System.out.println(jmTime.getTimeAsIsoInstant(changeToEpochMillis));
        assertEquals("2000-08-27T00:27:09.000Z", jmTime.getTimeAsIsoInstantMills(changeToEpochMillis));

        changeToEpochMillis = jmTime.changeToEpochMillis(dateString, defaultLogDateFormat, jmTime.getUtcZoneId());
        System.out.println(changeToEpochMillis);
        System.out.println(jmTime.getTimeAsIsoInstant(changeToEpochMillis));
        assertEquals("2000-08-27T09:27:09Z", jmTime.getTimeAsIsoInstant(changeToEpochMillis));

        defaultLogDateFormat = "dd/MM월/yyyy:HH:mm:ss";
        ZoneId timezoneId = ZoneId.of("GMT+2");
        dateString = "27/08월/2000:09:27:09";

        long changeToEpochMillis2 = jmTime.changeToEpochMillis(dateString, defaultLogDateFormat, timezoneId);
        System.out.println(changeToEpochMillis2);
        System.out.println(jmTime.getTimeAsIsoInstant(changeToEpochMillis2));
        assertEquals(ChronoUnit.HOURS.getDuration().toMillis() * 2, changeToEpochMillis - changeToEpochMillis2);
        assertEquals("2000-08-27T07:27:09Z", jmTime.getTimeAsIsoInstant(changeToEpochMillis2));


        changeToEpochMillis = jmTime.changeToEpochMillis(dateString, defaultLogDateFormat, jmTime.getUtcZoneId());
        System.out.println(changeToEpochMillis);
        System.out.println(jmTime.getTimeAsIsoInstant(changeToEpochMillis));

        // zoneId null 이면 시스템 기본 timezoneId를 사용!!!
        changeToEpochMillis = jmTime.changeToEpochMillis(dateString, defaultLogDateFormat, null);
        System.out.println(changeToEpochMillis);
        System.out.println(jmTime.getTimeAsIsoInstant(changeToEpochMillis));

        defaultLogDateFormat = "dd/M월/yyyy:HH:mm:ss Z";
        dateString = "27/8월/2000:09:27:09 -0400";

        changeToEpochMillis = jmTime.changeToEpochMillis(dateString, defaultLogDateFormat);
        System.out.println(changeToEpochMillis);
        System.out.println(jmTime.getTimeAsIsoInstant(changeToEpochMillis));

        assertEquals("2000-08-27T13:27:09Z", jmTime.getTimeAsIsoInstant(changeToEpochMillis));

        // format에 zone 정보를 무시하고 제공된 zoneId를 사용함 !!!
        changeToEpochMillis = jmTime.changeToEpochMillis(dateString, defaultLogDateFormat, timezoneId);
        System.out.println(changeToEpochMillis);
        System.out.println(jmTime.getTimeAsIsoInstant(changeToEpochMillis));
        assertEquals("2000-08-27T07:27:09Z", jmTime.getTimeAsIsoInstant(changeToEpochMillis));

        defaultLogDateFormat = "dd/MM월/yyyy:HH:mm:ss";
        dateString = "27/08월/2000:09:27:09";

        // zone 정보가 없을때 시스템 기본 값으로 적용 됨!!! 한국 +0900 이라 00시가
        changeToEpochMillis = jmTime.changeToEpochMillis(dateString, defaultLogDateFormat);
        System.out.println(changeToEpochMillis);
        System.out.println(jmTime.getTimeAsIsoInstant(changeToEpochMillis));
        assertEquals("2000-08-27T00:27:09Z", jmTime.getTimeAsIsoInstant(changeToEpochMillis));

        changeToEpochMillis = jmTime.changeToEpochMillis(dateString, defaultLogDateFormat, timezoneId);
        System.out.println(changeToEpochMillis);
        System.out.println(jmTime.getTimeAsIsoInstant(changeToEpochMillis));
        assertEquals("2000-08-27T07:27:09Z", jmTime.getTimeAsIsoInstant(changeToEpochMillis));

    }

    @Test
    public void testChangeISOTimestampWithMillsToWithoutMills() {
        String isoTimestampString = "2015-04-28T10:30:23.032+0900";
        System.out.println(jmTime.getTimeAsIsoInstant(isoTimestampString));
        assertEquals("2015-04-28T01:30:23Z", jmTime.getTimeAsIsoInstant(isoTimestampString));
        System.out.println(jmTime.getTimeAsLocalDate(isoTimestampString));
        assertEquals("2015-04-28T01:30:23", jmTime.getTimeAsLocalDate(isoTimestampString));
        isoTimestampString = "2015-04-28T10:30:23.999Z";
        System.out.println(jmTime.getTimeAsIsoInstant(isoTimestampString));
        assertEquals("2015-04-28T10:30:23Z", jmTime.getTimeAsIsoInstant(isoTimestampString));
        System.out.println(jmTime.getTimeAsLocalDate(isoTimestampString));
        assertEquals("2015-04-28T10:30:23", jmTime.getTimeAsLocalDate(isoTimestampString));

    }

    @Test
    public void testChangeTimestampToNewFormat() {
        String simpleDateFormat = "yyyyMMddHHmmssSSS";
        System.out.println(jmTime
                .getTime("20150925133631446", simpleDateFormat, jmTime.getBasicIsoDateTimeMills()));
        assertEquals("20150925043631.446", jmTime
                .getTime("20150925133631446", simpleDateFormat, jmTime.getBasicIsoDateTimeMills()));
        assertEquals("2015-09-25T13:36:31.446+0900", jmTime
                .getTime("20150925133631446", simpleDateFormat,
                        jmTime.getIsoOffsetDateTimeMills(), ASIA_SEOUL_ZONE_ID));
    }

    @Test
    public void testChangeTimestampToIsoInstant() {
        String simpleDateFormat = "yyyyMMddHHmmssSSS";
        System.out.println(jmTime.getTimeAsIsoInstant(simpleDateFormat, "20150925133631446"));
        assertEquals("2015-09-25T04:36:31Z", jmTime.getTimeAsIsoInstant("20150925133631446", simpleDateFormat));
    }

    @Test
    public void testChangeIsoTimestampToLong() {
        long timeMillis = jmTime.changeToEpochMillis("2015-09-25T04:36:31", ASIA_SEOUL_ZONE_ID);
        System.out.println(jmTime.getTimeAsIsoInstant(timeMillis));
        assertEquals("2015-09-24T19:36:31.000Z", jmTime.getTimeAsIsoInstantMills(timeMillis));
        timeMillis = jmTime.changeToEpochMillis("2015-09-25T04:36:31Z", ASIA_SEOUL_ZONE_ID);
        System.out.println(jmTime.getTimeAsIsoInstant(timeMillis));
        assertEquals("2015-09-25T04:36:31.000Z", jmTime.getTimeAsIsoInstantMills(timeMillis));

    }

    @Test
    public void testGetZonedDataTime() {
        long currentTimeMillis = 1483064372217l;
        ZonedDateTime zonedDataTime = jmTime.getZonedDataTime(currentTimeMillis,
                ZoneId.getAvailableZoneIds().stream().findFirst().map(ZoneId::of).get());
        System.out.println(zonedDataTime);
        System.out.println(zonedDataTime.toOffsetDateTime());
        System.out.println(zonedDataTime.toLocalDate());
        System.out.println(zonedDataTime.toLocalDateTime());

        ZonedDateTime zonedDataTime2 =
                jmTime.getZonedDataTime(currentTimeMillis, ZoneId.of("America/Indiana/Indianapolis"));
        System.out.println(zonedDataTime2);
        System.out.println(zonedDataTime2.toOffsetDateTime());
        System.out.println(zonedDataTime2.toLocalDate());
        System.out.println(zonedDataTime2.toLocalDateTime());

        assertEquals(zonedDataTime.withZoneSameInstant(ZoneId.of(ASIA_SEOUL)).toLocalDateTime().toString(),
                zonedDataTime2.withZoneSameInstant(ZoneId.of(ASIA_SEOUL)).toLocalDateTime().toString());
    }

    @Test
    public void testGetOffsetDataTime() {
        long currentTimeMillis = 1483064372217l;
        System.out.println(ZoneOffset.getAvailableZoneIds());
        OffsetDateTime offsetDataTime = jmTime.getOffsetDateTime(currentTimeMillis, "+09:00");
        System.out.println(offsetDataTime);
        System.out.println(offsetDataTime.toZonedDateTime());
        System.out.println(offsetDataTime.toLocalDate());
        System.out.println(offsetDataTime.toLocalDateTime());

        OffsetDateTime offsetDataTime2 =
                jmTime.getOffsetDateTime(currentTimeMillis, "-1800");
        System.out.println(offsetDataTime2);
        System.out.println(offsetDataTime2.toZonedDateTime());
        System.out.println(offsetDataTime2.toLocalDate());
        System.out.println(offsetDataTime2.toLocalDateTime());

        assertEquals(
                offsetDataTime.withOffsetSameInstant(ZoneOffset.of("+0900"))
                        .toLocalDateTime().toString(),
                offsetDataTime2.withOffsetSameInstant(ZoneOffset.of("+0900"))
                        .toLocalDateTime().toString());
    }

    @Test
    public void testChangeTimestampToNewFormatStringZoneIdDateTimeFormatter() {
        System.out.println(jmTime.getTime(TIMESTAMP3,
                DateTimeFormatter.ofPattern(jmTime.getIsoInstantTimezoneName()), ASIA_SEOUL_ZONE_ID
        ));
        assertTrue(jmTime.getTime(TIMESTAMP3,
                DateTimeFormatter.ofPattern(jmTime.getIsoInstantTimezoneName()), ASIA_SEOUL_ZONE_ID
        ).startsWith("2014-09-27"));
        System.out.println(jmTime
                .getTime(TIMESTAMP3, DateTimeFormatter.ofPattern(jmTime.getIsoInstantTimezoneName())));
        assertTrue(jmTime
                .getTime(TIMESTAMP3, DateTimeFormatter.ofPattern(jmTime.getIsoInstantTimezoneName()))
                .startsWith("2014-09-26"));
        assertTrue(jmTime
                .getTime(TIMESTAMP3, DateTimeFormatter.ofPattern(jmTime.getIsoInstantTimezoneName()),
                        ZoneId.of(ASIA_SEOUL)
                ).startsWith("2014-09-27"));
    }

    @Test
    public void testChangeFormatAndTimeZoneToDefaultUtcFormat() {
        System.out.println(jmTime.getCurrentTimestamp(DATE_FORMAT, ASIA_SEOUL_ZONE_ID));
        System.out.println(
                jmTime.getTimeAsIsoInstant("03/Jan/2018:18:44:19", DATE_FORMAT2, ZoneId.of("UTC")));
        assertEquals(jmTime.getTimeAsIsoInstant("03/Jan/2018:18:44:19", DATE_FORMAT2, ASIA_SEOUL_ZONE_ID),
                jmTime.getTimeAsIsoInstant("03/Jan/2018:18:44:19", DATE_FORMAT2, null));
        assertEquals(jmTime.getTimeAsIsoInstant("03/Jan/2018:18:44:19", DATE_FORMAT2, ASIA_SEOUL_ZONE_ID),
                jmTime.getTimeAsIsoInstant("03/Jan/2018:18:44:19", DATE_FORMAT2));
    }

    @Test
    public void testChangeFormatAndTimeZoneToOffsetDateTime() {
        System.out.println(jmTime.getCurrentTimestamp(DATE_FORMAT,
                ASIA_SEOUL_ZONE_ID));
        System.out.println(jmTime.getDefaultZoneId());
        System.out.println(jmTime.getDefaultZoneOffset());
        System.out.println(jmTime.getTimeAsOffsetDateTime("03/Jan/2018:18:44:19", DATE_FORMAT2,
                jmTime.getDefaultZoneOffset()));
        assertEquals("2018-01-05T04:53:37.000Z",
                jmTime
                        .getTime("05/Jan/2018:13:53:37 +0900", DATE_FORMAT, jmTime.getIsoInstantMillsZ()));
        assertEquals(jmTime.getTimeAsOffsetDateTime("03/Jan/2018:18:44:19", DATE_FORMAT2, ZoneOffset.of("+0900")),
                jmTime.getTimeAsOffsetDateTime("03/Jan/2018:18:44:19", DATE_FORMAT2, null));
        assertEquals(jmTime.getTimeAsOffsetDateTime("03/Jan/2018:18:44:19", DATE_FORMAT2, ZoneOffset.of("+0900")),
                jmTime.getTimeAsOffsetDateTime("03/Jan/2018:18:44:19", DATE_FORMAT2));
    }

    @Test
    public void testFormattedTimeString() {
        long timestamp = 1395394283524l;
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        System.out.println(jmTime.getTime(timestamp, jmTime.getIsoOffsetDateTimeMills(), zoneId));
        System.out.println(jmTime.getTime(timestamp, jmTime.getIsoInstantMillsTimezoneName(), zoneId));
        System.out.println(jmTime.getTime(timestamp, jmTime.getIsoLocalDateTimeMills(), zoneId));
        System.out.println(jmTime.getTime(timestamp, jmTime.getBasicIsoDateTimeMillsOffset(), zoneId));
        System.out.println(jmTime.getTime(timestamp, jmTime.getBasicIsoDateTimeMillsTimezoneName(), zoneId));
        System.out.println(jmTime.getTime(timestamp, jmTime.getBasicIsoDateTimeMills(), zoneId));
    }

}