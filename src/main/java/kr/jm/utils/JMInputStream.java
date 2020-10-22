package kr.jm.utils;

import kr.jm.utils.exception.JMException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * The interface Jm input stream.
 */
public interface JMInputStream {

    /**
     * The constant log.
     */
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JMInputStream.class);
    /**
     * The constant UTF_8.
     */
    String UTF_8 = StandardCharsets.UTF_8.displayName();

    /**
     * To string string.
     *
     * @param inputStream the input stream
     * @return the string
     */
    static String toString(InputStream inputStream) {
        return toString(inputStream, UTF_8, new StringBuilder());
    }

    /**
     * To string string.
     *
     * @param inputStream the input stream
     * @param charsetName the charset name
     * @return the string
     */
    static String toString(InputStream inputStream, String charsetName) {
        return toString(inputStream, charsetName, new StringBuilder());
    }

    private static String toString(InputStream inputStream, String charsetName, StringBuilder stringBuilder) {
        try {
            consumeInputStream(inputStream, charsetName, line -> appendLine(stringBuilder, line));
            return stringBuilder.toString();
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e,
                    "toString", inputStream, charsetName);
        }

    }

    private static void appendLine(StringBuilder stringBuilder, String line) {
        stringBuilder.append(line);
        stringBuilder.append(JMString.LINE_SEPARATOR);
    }

    /**
     * Read lines list.
     *
     * @param inputStream the input stream
     * @param charsetName the charset name
     * @return the list
     */
    static List<String> readLines(InputStream inputStream, String charsetName) {
        List<String> stringList = new ArrayList<>();
        try {
            consumeInputStream(inputStream, charsetName, stringList::add);
        } catch (Exception e) {
            return JMException
                    .handleExceptionAndReturn(log, e, "readLines", Collections::emptyList, inputStream, charsetName);
        }
        return stringList;
    }

    /**
     * Read lines list.
     *
     * @param inputStream the input stream
     * @return the list
     */
    static List<String> readLines(InputStream inputStream) {
        return readLines(inputStream, UTF_8);
    }


    /**
     * Consume input stream.
     *
     * @param inputStream the input stream
     * @param charsetName the charset name
     * @param consumer    the consumer
     */
    static void consumeInputStream(InputStream inputStream, String charsetName, Consumer<String> consumer) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charsetName))) {
            for (String line = br.readLine(); line != null;
                    line = br.readLine())
                consumer.accept(line);
        } catch (IOException e) {
            JMException.handleExceptionAndThrowRuntimeEx(log, e,
                    "consumeInputStream", inputStream, charsetName, consumer);
        }
    }
}
