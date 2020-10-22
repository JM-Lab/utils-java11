package kr.jm.utils;

import kr.jm.utils.helper.JMPattern;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The interface Jm string.
 */
public interface JMString {

    /**
     * The constant PIPE.
     */
    String PIPE = "|";

    /**
     * The constant EMPTY.
     */
    String EMPTY = "";

    /**
     * The constant UNDERSCORE.
     */
    String UNDERSCORE = "_";

    /**
     * The constant HYPHEN.
     */
    String HYPHEN = "-";

    /**
     * The constant COLON.
     */
    String COLON = ":";

    /**
     * The constant SEMICOLON.
     */
    String SEMICOLON = ";";

    /**
     * The constant COMMA.
     */
    String COMMA = ",";

    /**
     * The constant SPACE.
     */
    String SPACE = " ";

    /**
     * The constant DOT.
     */
    String DOT = ".";

    /**
     * The constant LINE_SEPARATOR.
     */
    String LINE_SEPARATOR = System.lineSeparator();
    /**
     * The constant IPV4Pattern.
     */
    String IPV4Pattern =
            "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    /**
     * The constant IPV6Pattern.
     */
    String IPV6Pattern =
            "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
    /**
     * The constant NumberPattern.
     */
    String NumberPattern = "[+-]?\\d+(\\.\\d+)?";
    /**
     * The constant WordPattern.
     */
    String WordPattern = "\\S+";

    /**
     * Is number boolean.
     *
     * @param numberString the number string
     * @return the boolean
     */
    static boolean isNumber(String numberString) {
        return JMPattern.getInstance().getNumberPattern().matcher(numberString).matches();
    }

    /**
     * Is word boolean.
     *
     * @param wordString the word string
     * @return the boolean
     */
    static boolean isWord(String wordString) {
        return JMPattern.getInstance().getWordPattern().matcher(wordString).matches();
    }


    /**
     * Joining with string.
     *
     * @param delimiter    the delimiter
     * @param stringStream the string stream
     * @return the string
     */
    static String joiningWith(CharSequence delimiter, Stream<String> stringStream) {
        return stringStream.collect(Collectors.joining(delimiter));
    }

    /**
     * Joining with comma string.
     *
     * @param stringList the string list
     * @return the string
     */
    static String joiningWithComma(List<String> stringList) {
        return joiningWith(COMMA, stringList);
    }

    /**
     * Joining with underscore string.
     *
     * @param stringList the string list
     * @return the string
     */
    static String joiningWithUnderscore(List<String> stringList) {
        return joiningWith(UNDERSCORE, stringList);
    }

    /**
     * Joining with space string.
     *
     * @param stringList the string list
     * @return the string
     */
    static String joiningWithSpace(List<String> stringList) {
        return joiningWith(SPACE, stringList);
    }

    /**
     * Joining with semicolon string.
     *
     * @param stringList the string list
     * @return the string
     */
    static String joiningWithSemicolon(List<String> stringList) {
        return joiningWith(SEMICOLON, stringList);
    }

    /**
     * Joining with dot string.
     *
     * @param stringList the string list
     * @return the string
     */
    static String joiningWithDot(List<String> stringList) {
        return joiningWith(DOT, stringList);
    }

    /**
     * Joining with pipe string.
     *
     * @param stringList the string list
     * @return the string
     */
    static String joiningWithPipe(List<String> stringList) {
        return joiningWith(PIPE, stringList);
    }

    /**
     * Joining with pipe string.
     *
     * @param strings the strings
     * @return the string
     */
    static String joiningWithPipe(String... strings) {
        return joiningWith(PIPE, strings);
    }

    /**
     * Joining with dot string.
     *
     * @param strings the strings
     * @return the string
     */
    static String joiningWithDot(String... strings) {
        return joiningWith(DOT, strings);
    }

    /**
     * Joining with comma string.
     *
     * @param strings the strings
     * @return the string
     */
    static String joiningWithComma(String... strings) {
        return joiningWith(COMMA, strings);
    }

    /**
     * Joining with underscore string.
     *
     * @param strings the strings
     * @return the string
     */
    static String joiningWithUnderscore(String... strings) {
        return joiningWith(UNDERSCORE, strings);
    }

    /**
     * Joining with space string.
     *
     * @param strings the strings
     * @return the string
     */
    static String joiningWithSpace(String... strings) {
        return joiningWith(SPACE, strings);
    }

    /**
     * Joining with semicolon string.
     *
     * @param strings the strings
     * @return the string
     */
    static String joiningWithSemicolon(String... strings) {
        return joiningWith(SEMICOLON, strings);
    }

    /**
     * Is not null or empty boolean.
     *
     * @param string the string
     * @return the boolean
     */
    static boolean isNotNullOrEmpty(String string) {
        return !isNullOrEmpty(string);
    }

    /**
     * Is null or empty boolean.
     *
     * @param string the string
     * @return the boolean
     */
    static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Is null or blank boolean.
     *
     * @param string the string
     * @return the boolean
     */
    static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    /**
     * Is not null or blank boolean.
     *
     * @param string the string
     * @return the boolean
     */
    static boolean isNotNullOrBlank(String string) {
        return !isNullOrBlank(string);
    }

    /**
     * Joining string.
     *
     * @param strings the strings
     * @return the string
     */
    static String joining(String... strings) {
        return String.join("", strings);
    }

    /**
     * Joining with string.
     *
     * @param delimiter the delimiter
     * @param strings   the strings
     * @return the string
     */
    static String joiningWith(CharSequence delimiter, String... strings) {
        return joiningWith(delimiter, Arrays.stream(strings));
    }

    /**
     * Joining with string.
     *
     * @param delimiter  the delimiter
     * @param stringList the string list
     * @return the string
     */
    static String joiningWith(CharSequence delimiter, List<String> stringList) {
        return joiningWith(delimiter, stringList.stream());
    }

    /**
     * Split file name into pre suffix string [ ].
     *
     * @param fileName the file name
     * @return the string [ ]
     */
    static String[] splitFileNameIntoPreSuffix(String fileName) {
        String[] preSuffix = {fileName, EMPTY};
        int dotIndex = fileName.lastIndexOf(DOT);
        if (dotIndex > 0) {
            preSuffix[0] = fileName.substring(0, dotIndex);
            preSuffix[1] = fileName.substring(dotIndex);
        }
        return preSuffix;
    }

    /**
     * Gets prefix of file name.
     *
     * @param fileName the file name
     * @return the prefix of file name
     */
    static String getPrefixOfFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf(DOT);
        return dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
    }

    /**
     * Gets extension.
     *
     * @param fileName the file name
     * @return the extension
     */
    static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(DOT);
        return dotIndex > 0 ? fileName.substring(dotIndex) : EMPTY;
    }

    /**
     * Truncate string.
     *
     * @param string         the string
     * @param maxBytesLength the max bytes length
     * @return the string
     */
    static String truncate(String string, int maxBytesLength) {
        byte[] stringBytes = string.getBytes();
        return stringBytes.length > maxBytesLength
                ? new String(stringBytes, 0, maxBytesLength - 1) : string;
    }

    /**
     * Truncate string.
     *
     * @param string         the string
     * @param maxBytesLength the max bytes length
     * @param appendString   the append string
     * @return the string
     */
    static String truncate(String string, int maxBytesLength,
            String appendString) {
        return string.getBytes().length > maxBytesLength ? truncate(string,
                maxBytesLength - appendString.getBytes().length) + appendString
                : string;
    }

    /**
     * Build ip or hostname port pair string.
     *
     * @param ipOrHostname the ip or hostname
     * @param port         the port
     * @return the string
     */
    static String buildIpOrHostnamePortPair(String ipOrHostname,
            int port) {
        return ipOrHostname + ":" + port;
    }

    /**
     * Rounded number format string.
     *
     * @param number       the number
     * @param decimalPoint the decimal point
     * @return the string
     */
    static String roundedNumberFormat(Double number, int decimalPoint) {
        return String.format("%." + decimalPoint + "f", number);
    }

    /**
     * To string string.
     *
     * @param param the param
     * @return the string
     */
    static String toString(Object param) {
        return param == null ? "null" : param.getClass().isArray() ? Arrays.toString((Object[]) param) : param
                .toString();
    }

}
