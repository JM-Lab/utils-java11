package kr.jm.utils.exception;

import kr.jm.utils.helper.JMLog;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * The type Jm exception.
 */
public class JMException {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Optional<ErrorHistoryManager> ErrorHistoryManagerOptional = Optional.empty();

    /**
     * Enable error history.
     */
    public static void enableErrorHistory() {
        ErrorHistoryManagerOptional = Optional.of(new ErrorHistoryManager());
    }

    /**
     * Gets error history manager optional.
     *
     * @return the error history manager optional
     */
    public static Optional<ErrorHistoryManager> getErrorHistoryManagerOptional() {
        return ErrorHistoryManagerOptional;
    }

    /**
     * Handle exception.
     *
     * @param log        the log
     * @param throwable  the throwable
     * @param methodName the method name
     * @param params     the params
     */
    public static void handleException(Logger log, Throwable throwable, String methodName, Object... params) {
        if (params.length > 0)
            JMLog.errorForException(log, throwable, methodName, params);
        else
            JMLog.errorForException(log, throwable, methodName);
        ErrorHistoryManagerOptional
                .ifPresent(errorHistoryManager -> errorHistoryManager.recordErrorMessageHistory(throwable));
    }

    /**
     * Log exception.
     *
     * @param log        the log
     * @param throwable  the throwable
     * @param methodName the method name
     * @param params     the params
     */
    @Deprecated
    public static void logException(Logger log, Throwable throwable, String methodName, Object... params) {
        handleException(log, throwable, methodName, params);
    }

    /**
     * Handle exception and return null t.
     *
     * @param <T>       the type parameter
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the t
     */
    @SuppressWarnings("SameReturnValue")
    public static <T> T handleExceptionAndReturnNull(Logger log, Throwable throwable, String method, Object... params) {
        handleException(log, throwable, method, params);
        return null;
    }

    /**
     * Handle exception and return false boolean.
     *
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the boolean
     */
    @SuppressWarnings("SameReturnValue")
    public static boolean handleExceptionAndReturnFalse(Logger log, Throwable throwable, String method,
            Object... params) {
        handleException(log, throwable, method, params);
        return false;
    }

    /**
     * Handle exception and return t.
     *
     * @param <T>            the type parameter
     * @param log            the log
     * @param throwable      the throwable
     * @param method         the method
     * @param returnSupplier the return supplier
     * @param params         the params
     * @return the t
     */
    public static <T> T handleExceptionAndReturn(Logger log, Throwable throwable, String method,
            Supplier<T> returnSupplier, Object... params) {
        handleException(log, throwable, method, params);
        return returnSupplier.get();
    }

    /**
     * Handle exception and throw runtime ex t.
     *
     * @param <T>       the type parameter
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the t
     */
    public static <T> T handleExceptionAndThrowRuntimeEx(Logger log, Throwable throwable, String method,
            Object... params) {
        handleException(log, throwable, method, params);
        throw new RuntimeException(throwable);
    }

    /**
     * Handle exception and return runtime ex runtime exception.
     *
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the runtime exception
     */
    public static RuntimeException handleExceptionAndReturnRuntimeEx(Logger log, Throwable throwable, String method,
            Object... params) {
        handleException(log, throwable, method, params);
        return new RuntimeException(throwable);
    }

    /**
     * Handle exception and return empty optional optional.
     *
     * @param <T>       the type parameter
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the optional
     */
    public static <T> Optional<T> handleExceptionAndReturnEmptyOptional(Logger log, Throwable throwable, String method,
            Object... params) {
        handleException(log, throwable, method, params);
        return Optional.empty();
    }

    /**
     * Gets dont support method runtime exception.
     *
     * @param methodName the method name
     * @return the dont support method runtime exception
     */
    public static RuntimeException getDontSupportMethodRuntimeException(String methodName) {
        return newRunTimeException("Don't Support " + methodName + " Method !!!");
    }

    /**
     * Log runtime exception.
     *
     * @param log              the log
     * @param exceptionMessage the exception message
     * @param method           the method
     * @param params           the params
     */
    public static void logRuntimeException(Logger log, String exceptionMessage, String method, Object... params) {
        handleException(log, newRunTimeException(exceptionMessage), method, params);
    }

    /**
     * New run time exception runtime exception.
     *
     * @param exceptionMessage the exception message
     * @return the runtime exception
     */
    public static RuntimeException newRunTimeException(String exceptionMessage) {
        return new RuntimeException(exceptionMessage);
    }

    /**
     * Throw run time exception t.
     *
     * @param <T>              the type parameter
     * @param exceptionMessage the exception message
     * @return the t
     */
    public static <T> T throwRunTimeException(String exceptionMessage) {
        throw newRunTimeException(exceptionMessage);
    }
}
