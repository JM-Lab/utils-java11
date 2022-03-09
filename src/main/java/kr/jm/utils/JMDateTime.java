package kr.jm.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The interface Jm arrays.
 */
public interface JMDateTime {

    /**
     * Gets default iso instant format.
     *
     * @param zonedDateTime the zonedDateTime
     * @return the default iso instant format
     */
    static String getDefaultIsoInstantFormat(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    /**
     * Gets iso instant format.
     *
     * @param zonedDateTime the zonedDateTime
     * @return the iso instant format
     */
    static String getIsoInstantFormat(ZonedDateTime zonedDateTime) {
        return getDefaultIsoInstantFormat(extractToSec(zonedDateTime));
    }

    /**
     * Extract to sec zoned date time.
     *
     * @param zonedDateTime the zoned date time
     * @return the zoned date time
     */
    static ZonedDateTime extractToSec(ZonedDateTime zonedDateTime) {
        return ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(),
                zonedDateTime.getHour(), zonedDateTime.getMinute(), zonedDateTime.getSecond(), 0,
                zonedDateTime.getZone());
    }

    /**
     * Gets iso instant format with mills.
     *
     * @param zonedDateTime the zonedDateTime
     * @return the iso instant format with mills
     */
    static String getIsoInstantFormatWithMills(ZonedDateTime zonedDateTime) {
        return getDefaultIsoInstantFormat(extractToMillis(zonedDateTime));
    }

    /**
     * Extract to millis zoned date time.
     *
     * @param zonedDateTime the zoned date time
     * @return the zoned date time
     */
    static ZonedDateTime extractToMillis(ZonedDateTime zonedDateTime) {
        return ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(),
                zonedDateTime.getHour(), zonedDateTime.getMinute(), zonedDateTime.getSecond(),
                zonedDateTime.getNano() / 1_000_000 * 1_000_000, zonedDateTime.getZone());
    }

    /**
     * Gets iso instant format with micro.
     *
     * @param zonedDateTime the zonedDateTime
     * @return the iso instant format with micro
     */
    static String getIsoInstantFormatWithMicro(ZonedDateTime zonedDateTime) {
        return getDefaultIsoInstantFormat(
                ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(),
                        zonedDateTime.getHour(), zonedDateTime.getMinute(), zonedDateTime.getSecond(),
                        zonedDateTime.getNano() / 1_000 * 1_000, zonedDateTime.getZone()));
    }

    /**
     * Extract to micro zoned date time.
     *
     * @param zonedDateTime the zoned date time
     * @return the zoned date time
     */
    static ZonedDateTime extractToMicro(ZonedDateTime zonedDateTime) {
        return ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(),
                zonedDateTime.getHour(), zonedDateTime.getMinute(), zonedDateTime.getSecond(),
                zonedDateTime.getNano() / 1_000 * 1_000, zonedDateTime.getZone());
    }

    /**
     * Extract to minute zoned date time.
     *
     * @param zonedDateTime the zoned date time
     * @return the zoned date time
     */
    static ZonedDateTime extractToMinute(ZonedDateTime zonedDateTime) {
        return ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(),
                zonedDateTime.getHour(), zonedDateTime.getMinute(), 0, 0, zonedDateTime.getZone());
    }

    /**
     * Extract to hour zoned date time.
     *
     * @param zonedDateTime the zoned date time
     * @return the zoned date time
     */
    static ZonedDateTime extractToHour(ZonedDateTime zonedDateTime) {
        return ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(),
                zonedDateTime.getHour(), 0, 0, 0, zonedDateTime.getZone());
    }

}
