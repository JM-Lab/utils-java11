package kr.jm.utils.time;

import kr.jm.utils.JMMap;
import kr.jm.utils.exception.JMException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * The type Jm time.
 */
public class JMTime {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JMTime.class);

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMTime getInstance() {
        return JMTime.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMTime INSTANCE = new JMTime(Locale.US);
    }

    private Locale locale;

    /**
     * Instantiates a new Jm time.
     *
     * @param locale the locale
     */
    public JMTime(Locale locale) {
        this.locale = locale;
        isoInstantMillsZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; // 2014-03-21T18:31:23.000Z
        isoOffsetDateTimeMills = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; // 2014-03-21T18:31:23.000+0900
        isoInstantTimezoneName = "yyyy-MM-dd'T'HH:mm:ssz"; // 2014-03-21T18:31:23KST
        isoInstantMillsTimezoneName = "yyyy-MM-dd'T'HH:mm:ss.SSSz"; // 2014-03-21T18:31:23.000KST
        isoLocalDateTimeMills = "yyyy-MM-dd'T'HH:mm:ss.SSS"; // 2014-03-21T18:31:23.000
        basicIsoDateTimeMillsOffset = "yyyyMMddHHmmss.SSSZ"; // 20140321183123.000+0900
        basicIsoDateTimeMillsTimezoneName = "yyyyMMddHHmmss.SSSz"; // 20140321183123.000KST
        basicIsoDateTimeMills = "yyyyMMddHHmmss.SSS";        // 20140321183123.000
        isoInstant = "yyyy-MM-dd'T'HH:mm:ssZ";        // 20140321183123+0900
        isoInstantZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";        // 2014-03-21T18:31:23Z
        isoLocalDateTime = "yyyy-MM-dd'T'HH:mm:ss";

        utcZoneId = ZoneId.of("UTC");

        defaultZoneOffset = OffsetDateTime.now().getOffset();
        defaultZoneId = ZoneId.systemDefault();

        dateTimeFormatterCache = new WeakHashMap<>();
        zoneIdCache = new WeakHashMap<>();
        zoneOffsetCache = new WeakHashMap<>();


        utc0000 = "+0000";
        dateTimeFormatZoneInfoPattern = Pattern.compile("[+|-][0-9]{4}$");
    }

    public ZoneOffset extractZoneOffset(String zoneId) {
        return ZoneId.of(zoneId).getRules().getOffset(Instant.now());
    }

    /**
     * Gets locale.
     *
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Gets iso instant mills z.
     *
     * @return the iso instant mills z
     */
    public String getIsoInstantMillsZ() {
        return isoInstantMillsZ;
    }

    /**
     * Gets iso offset date time mills.
     *
     * @return the iso offset date time mills
     */
    public String getIsoOffsetDateTimeMills() {
        return isoOffsetDateTimeMills;
    }

    /**
     * Gets iso instant timezone name.
     *
     * @return the iso instant timezone name
     */
    public String getIsoInstantTimezoneName() {
        return isoInstantTimezoneName;
    }

    /**
     * Gets iso instant mills timezone name.
     *
     * @return the iso instant mills timezone name
     */
    public String getIsoInstantMillsTimezoneName() {
        return isoInstantMillsTimezoneName;
    }

    /**
     * Gets iso local date time mills.
     *
     * @return the iso local date time mills
     */
    public String getIsoLocalDateTimeMills() {
        return isoLocalDateTimeMills;
    }

    /**
     * Gets basic iso date time mills offset.
     *
     * @return the basic iso date time mills offset
     */
    public String getBasicIsoDateTimeMillsOffset() {
        return basicIsoDateTimeMillsOffset;
    }

    /**
     * Gets basic iso date time mills timezone name.
     *
     * @return the basic iso date time mills timezone name
     */
    public String getBasicIsoDateTimeMillsTimezoneName() {
        return basicIsoDateTimeMillsTimezoneName;
    }

    /**
     * Gets basic iso date time mills.
     *
     * @return the basic iso date time mills
     */
    public String getBasicIsoDateTimeMills() {
        return basicIsoDateTimeMills;
    }

    /**
     * Gets iso instant.
     *
     * @return the iso instant
     */
    public String getIsoInstant() {
        return isoInstant;
    }

    /**
     * Gets iso instant z.
     *
     * @return the iso instant z
     */
    public String getIsoInstantZ() {
        return isoInstantZ;
    }

    /**
     * Gets iso local date time.
     *
     * @return the iso local date time
     */
    public String getIsoLocalDateTime() {
        return isoLocalDateTime;
    }

    /**
     * Gets utc zone id.
     *
     * @return the utc zone id
     */
    public ZoneId getUtcZoneId() {
        return utcZoneId;
    }

    /**
     * Gets default zone id.
     *
     * @return the default zone id
     */
    public ZoneId getDefaultZoneId() {
        return defaultZoneId;
    }

    /**
     * Gets default zone offset.
     *
     * @return the default zone offset
     */
    public ZoneOffset getDefaultZoneOffset() {
        return defaultZoneOffset;
    }

    /**
     * Gets date time format zone info pattern.
     *
     * @return the date time format zone info pattern
     */
    public Pattern getDateTimeFormatZoneInfoPattern() {
        return dateTimeFormatZoneInfoPattern;
    }

    private final String isoInstantMillsZ;
    private final String isoOffsetDateTimeMills;
    private final String isoInstantTimezoneName;
    private final String isoInstantMillsTimezoneName;
    private final String isoLocalDateTimeMills;
    private final String basicIsoDateTimeMillsOffset;
    private final String basicIsoDateTimeMillsTimezoneName;
    private final String basicIsoDateTimeMills;
    private final String isoInstant;
    private final String isoInstantZ;
    private final String isoLocalDateTime;
    private final ZoneId utcZoneId;
    private final ZoneId defaultZoneId;
    private final ZoneOffset defaultZoneOffset;

    private final Map<String, DateTimeFormatter> dateTimeFormatterCache;
    private final Map<String, ZoneId> zoneIdCache;
    private final Map<String, ZoneOffset> zoneOffsetCache;

    private final String utc0000;
    private final Pattern dateTimeFormatZoneInfoPattern;

    /**
     * Gets current timestamp as iso instant.
     *
     * @return the current timestamp as iso instant
     */
    public String getCurrentTimestampAsIsoInstant() {
        return getTimeAsIsoInstant(System.currentTimeMillis());
    }

    /**
     * Gets current timestamp.
     *
     * @return the current timestamp
     */
    public String getCurrentTimestamp() {
        return getTime(System.currentTimeMillis());
    }

    /**
     * Gets current timestamp.
     *
     * @param dateTimeFormat the date time format
     * @return the current timestamp
     */
    public String getCurrentTimestamp(String dateTimeFormat) {
        return getTime(System.currentTimeMillis(), dateTimeFormat);
    }

    /**
     * Gets current timestamp.
     *
     * @param dateTimeFormat the date time format
     * @param zoneId         the zone id
     * @return the current timestamp
     */
    public String getCurrentTimestamp(String dateTimeFormat, ZoneId zoneId) {
        return getTime(System.currentTimeMillis(), dateTimeFormat, zoneId);
    }

    /**
     * Gets time.
     *
     * @param epochMillis the epoch millis
     * @return the time
     */
    public String getTime(long epochMillis) {
        return getTimeAsIsoInstantMills(epochMillis);
    }

    /**
     * Gets time as iso instant.
     *
     * @param epochMillis the epoch millis
     * @return the time as iso instant
     */
    public String getTimeAsIsoInstant(long epochMillis) {
        return getTime(epochMillis, isoInstantZ);
    }

    /**
     * Gets time as iso instant mills.
     *
     * @param epochMillis the epoch millis
     * @return the time as iso instant mills
     */
    public String getTimeAsIsoInstantMills(long epochMillis) {
        return getTime(epochMillis, isoInstantMillsZ);
    }

    /**
     * Gets time as iso offset time mills.
     *
     * @param epochMillis the epoch millis
     * @return the time as iso offset time mills
     */
    public String getTimeAsIsoOffsetTimeMills(long epochMillis) {
        return getTime(epochMillis, isoOffsetDateTimeMills);
    }

    /**
     * Gets time as iso instant mills timezone name.
     *
     * @param epochMillis the epoch millis
     * @return the time as iso instant mills timezone name
     */
    public String getTimeAsIsoInstantMillsTimezoneName(long epochMillis) {
        return getTime(epochMillis, isoInstantMillsTimezoneName);
    }

    /**
     * Gets time as iso local date time mills.
     *
     * @param epochMillis the epoch millis
     * @return the time as iso local date time mills
     */
    public String getTimeAsIsoLocalDateTimeMills(long epochMillis) {
        return getTime(epochMillis, isoLocalDateTimeMills);
    }

    /**
     * Gets time as basic iso date time mills offset.
     *
     * @param epochMillis the epoch millis
     * @return the time as basic iso date time mills offset
     */
    public String getTimeAsBasicIsoDateTimeMillsOffset(long epochMillis) {
        return getTime(epochMillis, basicIsoDateTimeMillsOffset);
    }

    /**
     * Gets time as basic iso date time mills timezone name.
     *
     * @param epochMillis the epoch millis
     * @return the time as basic iso date time mills timezone name
     */
    public String getTimeAsBasicIsoDateTimeMillsTimezoneName(long epochMillis) {
        return getTime(epochMillis, basicIsoDateTimeMillsTimezoneName);
    }

    /**
     * Gets time as basic iso date time mills.
     *
     * @param epochMillis the epoch millis
     * @return the time as basic iso date time mills
     */
    public String getTimeAsBasicIsoDateTimeMills(long epochMillis) {
        return getTime(epochMillis, basicIsoDateTimeMills);
    }

    /**
     * Gets time.
     *
     * @param epochMillis    the epoch millis
     * @param dateTimeFormat the date time format
     * @return the time
     */
    public String getTime(long epochMillis, String dateTimeFormat) {
        return getTime(epochMillis, dateTimeFormat, utcZoneId);
    }

    /**
     * Gets time in default zone.
     *
     * @param epochMillis    the epoch millis
     * @param dateTimeFormat the date time format
     * @return the time in default zone
     */
    public String getTimeInDefaultZone(long epochMillis, String dateTimeFormat) {
        return getTime(epochMillis, dateTimeFormat, defaultZoneId);
    }

    /**
     * Gets date time formatter.
     *
     * @param dateTimeFormat the date time format
     * @return the date time formatter
     */
    public DateTimeFormatter getDateTimeFormatter(String dateTimeFormat) {
        return JMMap.getOrPutGetNew(dateTimeFormatterCache, dateTimeFormat,
                () -> DateTimeFormatter.ofPattern(dateTimeFormat, locale));
    }

    /**
     * Gets date time formatter.
     *
     * @param dateTimeFormat the date time format
     * @param zoneId         the zone id
     * @return the date time formatter
     */
    public DateTimeFormatter getDateTimeFormatter(String dateTimeFormat, ZoneId zoneId) {
        return JMMap.getOrPutGetNew(dateTimeFormatterCache, dateTimeFormat + zoneId,
                () -> DateTimeFormatter.ofPattern(dateTimeFormat, locale).withZone(zoneId));
    }

    /**
     * Gets time.
     *
     * @param epochMillis    the epoch millis
     * @param dataTimeFormat the data time format
     * @param zoneId         the zone id
     * @return the time
     */
    public String getTime(long epochMillis, String dataTimeFormat, ZoneId zoneId) {
        return getTime(epochMillis, getDateTimeFormatter(dataTimeFormat), zoneId);
    }

    /**
     * Gets time.
     *
     * @param epochMillis       the epoch millis
     * @param dateTimeFormatter the date time formatter
     * @param zoneId            the zone id
     * @return the time
     */
    public String getTime(long epochMillis, DateTimeFormatter dateTimeFormatter, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), zoneId).format(dateTimeFormatter);
    }

    /**
     * Gets time as iso instant.
     *
     * @param timestampString the timestamp string
     * @return the time as iso instant
     */
    public String getTimeAsIsoInstant(String timestampString) {
        return getTimeAsIsoInstant(changeToEpochMillis(timestampString));
    }

    /**
     * Gets time as iso instant mills.
     *
     * @param timestampString the timestamp string
     * @return the time as iso instant mills
     */
    public String getTimeAsIsoInstantMills(String timestampString) {
        return getTimeAsIsoInstantMills(changeToEpochMillis(timestampString));
    }

    /**
     * Gets time as local date.
     *
     * @param timestampString the timestamp string
     * @return the time as local date
     */
    public String getTimeAsLocalDate(String timestampString) {
        return getTimeAsLocalDate(changeToEpochMillis(timestampString));
    }

    private String getTimeAsLocalDate(long epochMillis) {
        return getTime(epochMillis, isoLocalDateTime);
    }

    /**
     * Gets time.
     *
     * @param timestampString   the timestamp string
     * @param newDateTimeFormat the new date time format
     * @param zoneId            the zone id
     * @return the time
     */
    public String getTime(String timestampString, String newDateTimeFormat, ZoneId zoneId) {
        return getTime(changeToEpochMillis(timestampString, zoneId), newDateTimeFormat, zoneId);
    }

    /**
     * Gets time.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @param newDateTimeFormat             the new date time format
     * @param zoneId                        the zone id
     * @return the time
     */
    public String getTime(String timestampString, String timestampStringDateTimeFormat, String newDateTimeFormat,
            ZoneId zoneId) {
        return getTime(changeToEpochMillis(timestampString, timestampStringDateTimeFormat, zoneId), newDateTimeFormat,
                zoneId);
    }

    /**
     * Gets time.
     *
     * @param timestampString   the timestamp string
     * @param newDateTimeFormat the new date time format
     * @return the time
     */
    public String getTime(String timestampString, String newDateTimeFormat) {
        return getTime(timestampString, getDateTimeFormatter(newDateTimeFormat));
    }

    /**
     * Gets time.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @param newDateTimeFormat             the new date time format
     * @return the time
     */
    public String getTime(String timestampString, String timestampStringDateTimeFormat, String newDateTimeFormat) {
        return getTime(changeToEpochMillis(timestampString, timestampStringDateTimeFormat), newDateTimeFormat);
    }


    /**
     * Gets time.
     *
     * @param timestampString      the timestamp string
     * @param newDateTimeFormatter the new date time formatter
     * @param zoneID               the zone id
     * @return the time
     */
    public String getTime(String timestampString, DateTimeFormatter newDateTimeFormatter, ZoneId zoneID) {
        return getZonedDataTime(timestampString).withZoneSameInstant(zoneID).format(newDateTimeFormatter);
    }

    /**
     * Gets time.
     *
     * @param timestampString      the timestamp string
     * @param newDateTimeFormatter the new date time formatter
     * @return the time
     */
    public String getTime(String timestampString, DateTimeFormatter newDateTimeFormatter) {
        return getZonedDataTime(timestampString).format(newDateTimeFormatter);
    }

    /**
     * Gets time as iso instant.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @param zoneId                        the zone id
     * @return the time as iso instant
     */
    public String getTimeAsIsoInstant(String timestampString, String timestampStringDateTimeFormat, ZoneId zoneId) {
        return Optional.ofNullable(zoneId).map(zId -> getTimeAsIsoInstant(
                        changeToEpochMillis(timestampString, timestampStringDateTimeFormat, zId)))
                .orElseGet(() -> getTimeAsIsoInstant(timestampString, timestampStringDateTimeFormat));
    }

    /**
     * Gets time as iso instant.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @return the time as iso instant
     */
    public String getTimeAsIsoInstant(String timestampString, String timestampStringDateTimeFormat) {
        return getTimeAsIsoInstant(changeToEpochMillis(timestampString, timestampStringDateTimeFormat));
    }

    /**
     * Gets time as iso instant mills.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @return the time as iso instant mills
     */
    public String getTimeAsIsoInstantMills(String timestampString, String timestampStringDateTimeFormat) {
        return getTimeAsIsoInstantMills(changeToEpochMillis(timestampString, timestampStringDateTimeFormat));
    }

    /**
     * Gets time as iso instant mills.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @param zoneId                        the zone id
     * @return the time as iso instant mills
     */
    public String getTimeAsIsoInstantMills(String timestampString, String timestampStringDateTimeFormat,
            ZoneId zoneId) {
        return Optional.ofNullable(zoneId).map(zId -> getTimeAsIsoInstantMills(
                        changeToEpochMillis(timestampString, timestampStringDateTimeFormat, zId)))
                .orElseGet(() -> getTimeAsIsoInstant(timestampString, timestampStringDateTimeFormat));
    }

    /**
     * Gets time as offset date time.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @return the time as offset date time
     */
    public String getTimeAsOffsetDateTime(String timestampString, String timestampStringDateTimeFormat) {
        return getOffsetDateTime(changeToEpochMillis(timestampString, timestampStringDateTimeFormat)).toString();
    }

    /**
     * Gets time as offset date time.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @param zoneOffset                    the zone offset
     * @return the time as offset date time
     */
    public String getTimeAsOffsetDateTime(String timestampString, String timestampStringDateTimeFormat,
            ZoneOffset zoneOffset) {
        return Optional.ofNullable(zoneOffset).map(ZoneOffset::normalized).map(zoneId -> getOffsetDateTime(
                        changeToEpochMillis(timestampString, timestampStringDateTimeFormat, zoneId)).toString())
                .orElseGet(() -> getTimeAsOffsetDateTime(timestampString, timestampStringDateTimeFormat));
    }

    /**
     * Gets zoned data time.
     *
     * @param timestampString the timestamp string
     * @return the zoned data time
     */
    public ZonedDateTime getZonedDataTime(String timestampString) {
        try {
            return ZonedDateTime.parse(timestampString);
        } catch (Exception e) {
            return getZonedDataTime(extractDateTimeFormatForTimestampString(changeZTo0000(timestampString)));
        }
    }

    /**
     * Gets zoned data time.
     *
     * @param timestampString the timestamp string
     * @param zoneId          the zone id
     * @return the zoned data time
     */
    public ZonedDateTime getZonedDataTime(String timestampString, ZoneId zoneId) {
        return getZonedDataTime(timestampString).withZoneSameInstant(zoneId);
    }

    private String changeZTo0000(String timestampString) {
        int index = timestampString.length() - 1;
        char lastChar = timestampString.charAt(index);
        return lastChar == 'Z' || lastChar == 'z'
                ? timestampString.substring(0, index) + utc0000 : timestampString;
    }

    /**
     * Change to epoch millis long.
     *
     * @param timestampString the timestamp string
     * @return the long
     */
    public long changeToEpochMillis(String timestampString) {
        try {
            return getEpochMilli(getZonedDataTime(timestampString));
        } catch (Exception e) {
            return changeToEpochMillis(timestampString, defaultZoneId);
        }
    }

    /**
     * Change to epoch millis long.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @return the long
     */
    public long changeToEpochMillis(String timestampString, String timestampStringDateTimeFormat) {
        try {
            return getEpochMilli(
                    ZonedDateTime.parse(timestampString, getDateTimeFormatter(timestampStringDateTimeFormat)));
        } catch (Exception e) {
            return changeToEpochMillis(timestampString, timestampStringDateTimeFormat, defaultZoneId);
        }
    }

    /**
     * Change to epoch millis long.
     *
     * @param timestampString               the timestamp string
     * @param timestampStringDateTimeFormat the timestamp string date time format
     * @param zoneId                        the zone id
     * @return the long
     */
    public long changeToEpochMillis(String timestampString, String timestampStringDateTimeFormat, ZoneId zoneId) {
        try {
            return getEpochMilli(
                    LocalDateTime.parse(timestampString, getDateTimeFormatter(timestampStringDateTimeFormat))
                            .atZone(Optional.ofNullable(zoneId).orElse(defaultZoneId)));
        } catch (Exception e) {
            return JMException
                    .handleExceptionAndReturn(log, e, "changeToEpochMillis", () -> Long.MIN_VALUE, timestampString,
                            zoneId);
        }
    }

    /**
     * Change to epoch millis long.
     *
     * @param timestampString the timestamp string
     * @param zoneId          the zone id
     * @return the long
     */
    public long changeToEpochMillis(String timestampString, ZoneId zoneId) {
        try {
            return getEpochMilli(getZonedDataTime(timestampString, zoneId));
        } catch (Exception e) {
            return changeToEpochMillis(timestampString, extractDateTimeFormatForTimestampString(timestampString),
                    zoneId);
        }
    }

    private String extractDateTimeFormatForTimestampString(String timestampString) {
        boolean isContainsDot = timestampString.contains(".");
        boolean isContainsPlusOrMinus = dateTimeFormatZoneInfoPattern.matcher(timestampString).find();
        int length = timestampString.length();
        if (isContainsDot && isContainsPlusOrMinus && length == 28)
            return isoOffsetDateTimeMills;
        else if (isContainsDot && !isContainsPlusOrMinus) {
            switch (length) {
                case 26:
                    return isoInstantMillsTimezoneName;
                case 23:
                    return isoLocalDateTimeMills;
                case 22:
                    return isoInstantTimezoneName;
            }
        } else if (!isContainsDot && !isContainsPlusOrMinus && length == 19)
            return isoLocalDateTime;
        else if (!isContainsDot && isContainsPlusOrMinus && length == 24)
            return isoInstant;
        throw new RuntimeException("Don't Support Format ISO Timestamp!!! - " + timestampString);
    }

    /**
     * Gets epoch milli.
     *
     * @param zonedDateTime the zoned date time
     * @return the epoch milli
     */
    public long getEpochMilli(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant().toEpochMilli();
    }

    /**
     * Gets zone id.
     *
     * @param zoneId the zone id
     * @return the zone id
     */
    public ZoneId getZoneId(String zoneId) {
        return JMMap.getOrPutGetNew(zoneIdCache, zoneId,
                () -> Optional.ofNullable(zoneId).map(ZoneId::of).orElse(defaultZoneId));
    }

    /**
     * Gets epoch milli.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
     * @param hour       the hour
     * @param minute     the minute
     * @param second     the second
     * @param mills      the mills
     * @param zoneId     the zone id
     * @return the epoch milli
     */
    public long getEpochMilli(int year, int month, int dayOfMonth, int hour, int minute, int second, int mills,
            String zoneId) {
        return getEpochMilli(year, month, dayOfMonth, hour, minute, second,
                Long.valueOf(TimeUnit.MILLISECONDS.toNanos(mills)).intValue(), getZoneId(zoneId));
    }

    /**
     * Gets time millis with nano.
     *
     * @param year         the year
     * @param month        the month
     * @param dayOfMonth   the day of month
     * @param hour         the hour
     * @param minute       the minute
     * @param second       the second
     * @param nanoOfSecond the nano of second
     * @param zoneId       the zone id
     * @return the time millis with nano
     */
    public long getTimeMillisWithNano(int year, int month, int dayOfMonth, int hour, int minute, int second,
            int nanoOfSecond, String zoneId) {
        return getEpochMilli(year, month, dayOfMonth, hour, minute, second, nanoOfSecond, getZoneId(zoneId));
    }

    /**
     * Gets epoch milli.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
     * @param hour       the hour
     * @param minute     the minute
     * @param second     the second
     * @param zoneId     the zone id
     * @return the epoch milli
     */
    public long getEpochMilli(int year, int month, int dayOfMonth, int hour, int minute, int second, String zoneId) {
        return getEpochMilli(year, month, dayOfMonth, hour, minute, second, getZoneId(zoneId));
    }

    /**
     * Gets epoch milli.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
     * @param hour       the hour
     * @param minute     the minute
     * @param second     the second
     * @param zoneId     the zone id
     * @return the epoch milli
     */
    public long getEpochMilli(int year, int month, int dayOfMonth, int hour, int minute, int second, ZoneId zoneId) {
        return getEpochMilli(year, month, dayOfMonth, hour, minute, second, 0, zoneId);
    }

    /**
     * Gets epoch milli.
     *
     * @param year         the year
     * @param month        the month
     * @param dayOfMonth   the day of month
     * @param hour         the hour
     * @param minute       the minute
     * @param second       the second
     * @param nanoOfSecond the nano of second
     * @param zoneId       the zone id
     * @return the epoch milli
     */
    public long getEpochMilli(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond,
            ZoneId zoneId) {
        return getEpochMilli(ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond, zoneId));
    }

    /**
     * Gets epoch milli.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
     * @param hour       the hour
     * @param minute     the minute
     * @param zoneId     the zone id
     * @return the epoch milli
     */
    public long getEpochMilli(int year, int month, int dayOfMonth, int hour, int minute, ZoneId zoneId) {
        return getEpochMilli(year, month, dayOfMonth, hour, minute, 0, zoneId);
    }

    /**
     * Gets epoch milli.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
     * @param hour       the hour
     * @param zoneId     the zone id
     * @return the epoch milli
     */
    public long getEpochMilli(int year, int month, int dayOfMonth, int hour, ZoneId zoneId) {
        return getEpochMilli(year, month, dayOfMonth, hour, 0, 0, zoneId);
    }

    /**
     * Gets epoch milli.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
     * @param zoneId     the zone id
     * @return the epoch milli
     */
    public long getEpochMilli(int year, int month, int dayOfMonth, ZoneId zoneId) {
        return getEpochMilli(year, month, dayOfMonth, 0, 0, 0, zoneId);
    }

    /**
     * Gets epoch milli.
     *
     * @param year   the year
     * @param month  the month
     * @param zoneId the zone id
     * @return the epoch milli
     */
    public long getEpochMilli(int year, int month, ZoneId zoneId) {
        return getEpochMilli(year, month, 0, 0, 0, 0, zoneId);
    }

    /**
     * Gets epoch milli.
     *
     * @param year   the year
     * @param zoneId the zone id
     * @return the epoch milli
     */
    public long getEpochMilli(int year, ZoneId zoneId) {
        return getEpochMilli(year, 0, 0, 0, 0, 0, zoneId);
    }

    /**
     * Gets zoned data time.
     *
     * @param timestamp the timestamp
     * @param zoneId    the zone id
     * @return the zoned data time
     */
    public ZonedDateTime getZonedDataTime(long timestamp, ZoneId zoneId) {
        return Instant.ofEpochMilli(timestamp).atZone(zoneId);
    }

    /**
     * Gets zoned data time.
     *
     * @param timestamp the timestamp
     * @return the zoned data time
     */
    public ZonedDateTime getZonedDataTime(long timestamp) {
        return getZonedDataTime(timestamp, defaultZoneId);
    }

    /**
     * Gets offset date time.
     *
     * @param timestamp  the timestamp
     * @param zoneOffset the zone offset
     * @return the offset date time
     */
    public OffsetDateTime getOffsetDateTime(long timestamp, ZoneOffset zoneOffset) {
        return Instant.ofEpochMilli(timestamp).atOffset(zoneOffset);
    }

    /**
     * Gets offset date time.
     *
     * @param timestamp    the timestamp
     * @param zoneOffsetId the zone offset id
     * @return the offset date time
     */
    public OffsetDateTime getOffsetDateTime(long timestamp, String zoneOffsetId) {
        return getOffsetDateTime(timestamp, getZoneOffset(zoneOffsetId));
    }

    /**
     * Gets zone offset.
     *
     * @param zoneOffsetId the zone offset id
     * @return the zone offset
     */
    public ZoneOffset getZoneOffset(String zoneOffsetId) {
        return JMMap.getOrPutGetNew(zoneOffsetCache, zoneOffsetId, () -> ZoneOffset.of(zoneOffsetId));
    }

    /**
     * Gets offset date time.
     *
     * @param timestamp the timestamp
     * @return the offset date time
     */
    public OffsetDateTime getOffsetDateTime(long timestamp) {
        return getOffsetDateTime(timestamp, defaultZoneOffset);
    }

}