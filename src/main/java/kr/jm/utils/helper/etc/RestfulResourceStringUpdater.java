package kr.jm.utils.helper.etc;

import kr.jm.utils.JMOptional;
import kr.jm.utils.JMRestfulResource;
import kr.jm.utils.JMThread;
import kr.jm.utils.exception.JMException;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The type Restful resource string updater.
 */
public class RestfulResourceStringUpdater {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RestfulResourceStringUpdater.class);
    private String restfulResourceUrl;
    private Consumer<String> updateConsumer;
    private String cachedString;

    /**
     * Instantiates a new Restful resource string updater.
     *
     * @param restfulResourceUrl the restful resource url
     */
    public RestfulResourceStringUpdater(String restfulResourceUrl) {
        this(restfulResourceUrl, 0);
    }

    /**
     * Instantiates a new Restful resource string updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     */
    public RestfulResourceStringUpdater(String restfulResourceUrl, int periodSeconds) {
        this(restfulResourceUrl, periodSeconds, 0);
    }

    /**
     * Instantiates a new Restful resource string updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     * @param initialDelayMillis the initial delay millis
     */
    public RestfulResourceStringUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis) {
        this(restfulResourceUrl, periodSeconds, initialDelayMillis, null);
    }

    /**
     * Instantiates a new Restful resource string updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     * @param initialDelayMillis the initial delay millis
     * @param updateConsumer     the update consumer
     */
    public RestfulResourceStringUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis,
            Consumer<String> updateConsumer) {
        setRestfulResourceUrl(restfulResourceUrl);
        this.updateConsumer = updateConsumer;
        if (initialDelayMillis == 0)
            updateResource();
        if (periodSeconds > 0) {
            long periodMillis = TimeUnit.SECONDS.toMillis(periodSeconds);
            JMThread.runWithScheduleAtFixedRate(initialDelayMillis > 0 ? initialDelayMillis : periodMillis,
                    periodMillis, this::updateResource);
        }
    }

    /**
     * Sets restful resource url.
     *
     * @param restfulResourceUrl the restful resource url
     */
    public void setRestfulResourceUrl(String restfulResourceUrl) {
        this.restfulResourceUrl = restfulResourceUrl;
    }

    /**
     * Update resource optional.
     *
     * @return the optional
     */
    public synchronized Optional<String> updateResource() {
        try {
            return JMOptional.getOptional(JMRestfulResource.getStringWithRestOrClasspathOrFilePath(restfulResourceUrl))
                    .filter(Predicate.not(s -> s.equals(cachedString))).map(this::updateJsonStringCache);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnEmptyOptional(log, e, "update", restfulResourceUrl);
        }
    }

    private String updateJsonStringCache(String jsonStringCache) {
        Optional.ofNullable(updateConsumer).ifPresent(consumer -> consumer.accept(jsonStringCache));
        log.info("Updated Resource - bytes - {}, url - {}", jsonStringCache.getBytes().length, restfulResourceUrl);
        return this.cachedString = jsonStringCache;
    }

    /**
     * Gets restful resource url.
     *
     * @return the restful resource url
     */
    public String getRestfulResourceUrl() {
        return restfulResourceUrl;
    }

    /**
     * Gets cached string.
     *
     * @return the cached string
     */
    public String getCachedString() {
        return cachedString;
    }

}