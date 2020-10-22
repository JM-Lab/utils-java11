package kr.jm.utils.exception;

import kr.jm.utils.time.LastTimestampDataManager;
import kr.jm.utils.time.TimestampData;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;

/**
 * The type Error history manager.
 */
public class ErrorHistoryManager {
    private final LastTimestampDataManager<String> lastTimestampDataManager;
    private final LongAdder totalErrorCount;

    /**
     * Instantiates a new Error history manager.
     */
    public ErrorHistoryManager() {
        this.lastTimestampDataManager = new LastTimestampDataManager<>(
                Integer.parseInt(Optional.ofNullable(System.getProperty("error.history.size")).orElse("500")));
        this.totalErrorCount = new LongAdder();
    }

    /**
     * Record error message history.
     *
     * @param throwable the throwable
     */
    public void recordErrorMessageHistory(Throwable throwable) {
        synchronized (totalErrorCount) {
            this.lastTimestampDataManager.addData(getStackTraceString(throwable));
            this.totalErrorCount.increment();
        }
    }

    private String getStackTraceString(Throwable throwable) {
        StringWriter stacktraceStringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stacktraceStringWriter));
        return stacktraceStringWriter.toString();
    }

    /**
     * Gets error message history list.
     *
     * @return the error message history list
     */
    public List<TimestampData<String>> getErrorMessageHistoryList() {
        return this.lastTimestampDataManager.getTimestampDataList();
    }

    /**
     * Clear all.
     */
    public synchronized void clearAll() {
        removeErrorMessageHistoryList();
        resetErrorCount();
    }

    /**
     * Remove error message history list.
     */
    public void removeErrorMessageHistoryList() {
        this.lastTimestampDataManager.clearTimestampDataList();
    }

    /**
     * Gets total error count.
     *
     * @return the total error count
     */
    public long getTotalErrorCount() {
        return totalErrorCount.longValue();
    }

    /**
     * Reset error count.
     */
    public void resetErrorCount() {
        totalErrorCount.reset();
    }

}
