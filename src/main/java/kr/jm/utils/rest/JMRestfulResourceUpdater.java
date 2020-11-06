package kr.jm.utils.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import kr.jm.utils.JMOptional;
import kr.jm.utils.JMRestfulResource;
import kr.jm.utils.JMThread;
import kr.jm.utils.exception.JMException;
import kr.jm.utils.helper.JMJson;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The type Jm restful resource updater.
 *
 * @param <T> the type parameter
 */
public class JMRestfulResourceUpdater<T> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JMRestfulResourceUpdater.class);
    private String restfulResourceUrl;
    private TypeReference<T> typeReference;
    private String cachedJsonString;
    private T cachedResource;

    /**
     * Instantiates a new Jm restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     */
    public JMRestfulResourceUpdater(String restfulResourceUrl) {
        this(restfulResourceUrl, 0);
    }

    /**
     * Instantiates a new Jm restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     */
    public JMRestfulResourceUpdater(String restfulResourceUrl, int periodSeconds) {
        this(restfulResourceUrl, periodSeconds, 0);
    }

    /**
     * Instantiates a new Jm restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     * @param initialDelayMillis the initial delay millis
     */
    public JMRestfulResourceUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis) {
        this(restfulResourceUrl, periodSeconds, initialDelayMillis, null);
    }

    /**
     * Instantiates a new Jm restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     * @param initialDelayMillis the initial delay millis
     * @param updateConsumer     the update consumer
     */
    public JMRestfulResourceUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis,
            Consumer<T> updateConsumer) {
        this(restfulResourceUrl, periodSeconds, initialDelayMillis, updateConsumer, new TypeReference<>() {});
    }

    /**
     * Instantiates a new Jm restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     * @param initialDelayMillis the initial delay millis
     * @param updateConsumer     the update consumer
     * @param typeReference      the type reference
     */
    public JMRestfulResourceUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis,
            Consumer<T> updateConsumer, TypeReference<T> typeReference) {
        this.restfulResourceUrl = restfulResourceUrl;
        this.typeReference = typeReference;
        if (initialDelayMillis == 0)
            update(updateConsumer);
        if (periodSeconds > 0) {
            long periodMillis = TimeUnit.SECONDS.toMillis(periodSeconds);
            JMThread.runWithScheduleAtFixedRate(initialDelayMillis > 0 ? initialDelayMillis : periodMillis,
                    periodMillis, () -> update(updateConsumer));
        }
    }

    private void update(Consumer<T> updateConsumer) {
        Optional.ofNullable(updateConsumer).ifPresentOrElse(this::updateResource, this::updateResourceWithLog);
    }

    /**
     * Update resource with log optional.
     *
     * @return the optional
     */
    public Optional<T> updateResourceWithLog() {
        Optional<T> resourceOptional = updateResource();
        log.info("Updated Resource - {}, url - {}", resourceOptional.isPresent() ? "YES" : "NO", restfulResourceUrl);
        return resourceOptional;
    }

    /**
     * Update resource optional.
     *
     * @return the optional
     */
    public Optional<T> updateResource() {
        try {
            return JMOptional.getOptional(JMRestfulResource.getStringWithRestOrClasspathOrFilePath(restfulResourceUrl))
                    .filter(Predicate.not(resource -> resource.equals(cachedJsonString))).map(this::setJsonStringCache)
                    .map(jsonString -> JMJson.getInstance().withJsonString(jsonString, typeReference))
                    .map(this::setResource);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnEmptyOptional(log, e, "updateResource", restfulResourceUrl);
        }
    }

    private T setResource(T resource) {
        return this.cachedResource = resource;
    }

    /**
     * Update resource.
     *
     * @param updateConsumer the update consumer
     */
    public void updateResource(Consumer<T> updateConsumer) {
        updateResourceWithLog().ifPresent(updateConsumer);
    }

    private String setJsonStringCache(String jsonStringCache) {
        return this.cachedJsonString = jsonStringCache;
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
     * Gets type reference.
     *
     * @return the type reference
     */
    public TypeReference<T> getTypeReference() {
        return typeReference;
    }

    /**
     * Gets cached json string.
     *
     * @return the cached json string
     */
    public String getCachedJsonString() {
        return cachedJsonString;
    }

    /**
     * Gets cached resource.
     *
     * @return the cached resource
     */
    public T getCachedResource() {
        return cachedResource;
    }
}