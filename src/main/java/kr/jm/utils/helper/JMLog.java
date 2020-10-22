package kr.jm.utils.helper;

import kr.jm.utils.JMArrays;
import kr.jm.utils.JMOptional;
import kr.jm.utils.JMString;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The type Jm log.
 */
public class JMLog {

    /**
     * Info.
     *
     * @param log        the log
     * @param methodName the method name
     * @param params     the params
     */
    public static void info(Logger log, String methodName, Object... params) {
        if (log.isInfoEnabled())
            log.info(buildMethodLogString(methodName, params));
    }

    /**
     * Error.
     *
     * @param log        the log
     * @param methodName the method name
     * @param params     the params
     */
    public static void error(Logger log, String methodName, Object... params) {
        log.error(buildMethodLogString(methodName, params));
    }

    /**
     * Error for exception.
     *
     * @param log        the log
     * @param throwable  the throwable
     * @param methodName the method name
     */
    public static void errorForException(Logger log, Throwable throwable,
            String methodName) {
        log.error(buildMethodLogString(methodName), throwable);
    }

    /**
     * Error for exception.
     *
     * @param log        the log
     * @param throwable  the throwable
     * @param methodName the method name
     * @param params     the params
     */
    public static void errorForException(Logger log, Throwable throwable,
            String methodName, Object... params) {
        log.error(buildMethodLogString(methodName, params), throwable);
    }

    private static String buildMethodLogString(String methodName) {
        return methodName + "()";
    }

    /**
     * Build method log string string.
     *
     * @param methodName the method name
     * @param params     the params
     * @return the string
     */
    static String buildMethodLogString(String methodName, Object... params) {
        if (JMArrays.isNullOrEmpty(params))
            return buildMethodLogString(methodName);
        StringBuilder methodLogStringBuilder = new StringBuilder(methodName);
        methodLogStringBuilder.append("(");
        JMOptional.getOptional(params).map(Arrays::stream)
                .ifPresent(stream -> methodLogStringBuilder.append(stream.map(JMString::toString)
                        .collect(Collectors.joining(", "))));
        methodLogStringBuilder.append(")");
        return methodLogStringBuilder.toString();
    }


    /**
     * Debug.
     *
     * @param log        the log
     * @param methodName the method name
     * @param params     the params
     */
    public static void debug(Logger log, String methodName, Object... params) {
        if (log.isDebugEnabled())
            log.debug(buildMethodLogString(methodName, params));
    }

    /**
     * Warn.
     *
     * @param log        the log
     * @param methodName the method name
     * @param params     the params
     */
    public static void warn(Logger log, String methodName, Object... params) {
        if (log.isWarnEnabled())
            log.warn(buildMethodLogString(methodName, params));
    }

}
