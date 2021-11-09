package kr.jm.utils.rest;

import kr.jm.utils.JMOptional;
import kr.jm.utils.JMRestfulResource;
import kr.jm.utils.JMString;
import kr.jm.utils.JMThread;
import kr.jm.utils.exception.JMException;
import kr.jm.utils.helper.JMLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
        this(restfulResourceUrl, 60);
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
        if (initialDelayMillis <= 0)
            updateResource();
        long periodMillis = TimeUnit.SECONDS.toMillis(periodSeconds);
        if (periodSeconds > 0)
            JMThread.runWithScheduleAtFixedRate(initialDelayMillis <= 0 ? periodMillis : initialDelayMillis,
                    periodMillis, this::updateResource);
        else if (initialDelayMillis > 0)
            JMThread.runWithSchedule(initialDelayMillis, this::updateResource);
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
            if (isUpdatedResource(JMRestfulResource.getStringWithRestOrClasspathOrFilePath(restfulResourceUrl)))
                return JMOptional.getOptional(
                                JMRestfulResource.getStringWithRestOrClasspathOrFilePath(restfulResourceUrl))
                        .filter(this::isUpdatedResource).map(this::updateJsonStringCache);
            JMLog.info(log, "updateResource");
            return Optional.empty();
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnEmptyOptional(log, e, "update", restfulResourceUrl);
        }
    }

    private boolean isUpdatedResource(String newResource) {
        return JMOptional.getOptional(newResource).filter(r -> !r.equals(cachedString)).isPresent();
    }

    private String updateJsonStringCache(String updatedJsonString) {
        Optional.ofNullable(updateConsumer).ifPresent(consumer -> consumer.accept(updatedJsonString));
        log.info("Updated Resource - url - {}, bytes - {}, diff - {}", restfulResourceUrl,
                updatedJsonString.getBytes().length, JMString.truncate(StringUtils.difference(this.cachedString,
                        updatedJsonString), 1024));
        return this.cachedString = updatedJsonString;
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