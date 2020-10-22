package kr.jm.utils.helper.etc;

import kr.jm.utils.JMOptional;
import kr.jm.utils.JMThread;
import kr.jm.utils.exception.JMException;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * The type Std in line consumer.
 */
public class StdInLineConsumer implements AutoCloseable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(StdInLineConsumer.class);
    private ExecutorService executorService;
    private Consumer<String> stdInLineConsumer;

    /**
     * Instantiates a new Std in line consumer.
     *
     * @param stdInLineConsumer the std in line consumer
     */
    public StdInLineConsumer(Consumer<String> stdInLineConsumer) {
        this.stdInLineConsumer = stdInLineConsumer;
    }

    /**
     * Consume std in std in line consumer.
     *
     * @return the std in line consumer
     */
    public StdInLineConsumer consumeStdIn() {
        JMThread.runAsync(this::startStdIn, this.executorService = JMThread.newSingleThreadPool());
        return this;
    }

    private void startStdIn() {
        try (InputStreamReader in = new InputStreamReader(System.in);
                BufferedReader bufferedReader = new BufferedReader(in)) {
            while (!executorService.isShutdown())
                JMOptional.getOptional(bufferedReader.readLine()).ifPresent(stdInLineConsumer);
        } catch (Exception e) {
            JMException.handleException(log, e, "startStdIn");
        } finally {
            close();
        }

    }

    @Override
    public void close() {
        JMThread.awaitTermination(executorService);
    }
}
