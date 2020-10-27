package kr.jm.utils.helper.etc;

import kr.jm.utils.JMOptional;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class RestfulResourceObjectUpdater<T> {
    private RestfulResourceStringUpdater restfulResourceStringUpdater;
    private Function<String, T> transformer;
    private Consumer<T> updateConsumer;
    private T cachedResource;

    /**
     * Instantiates a new Restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     */
    public RestfulResourceObjectUpdater(String restfulResourceUrl, Function<String, T> transformer) {
        this(restfulResourceUrl, 0, transformer);
    }

    public RestfulResourceObjectUpdater(String restfulResourceUrl, Function<String, T> transformer,
            Consumer<T> updateConsumer) {
        this(restfulResourceUrl, 0, 0, transformer, updateConsumer);
    }

    /**
     * Instantiates a new Restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     */
    public RestfulResourceObjectUpdater(String restfulResourceUrl, int periodSeconds,
            Function<String, T> transformer) {
        this(restfulResourceUrl, periodSeconds, 0, transformer);
    }

    public RestfulResourceObjectUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis,
            Function<String, T> transformer) {
        this(restfulResourceUrl, periodSeconds, initialDelayMillis, transformer, null);
    }

    /**
     * Instantiates a new Restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     * @param initialDelayMillis the initial delay millis
     */
    public RestfulResourceObjectUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis,
            Function<String, T> transformer, Consumer<T> updateConsumer) {
        this.transformer = transformer;
        this.updateConsumer = updateConsumer;
        this.restfulResourceStringUpdater = new RestfulResourceStringUpdater(restfulResourceUrl, periodSeconds,
                initialDelayMillis, this::updateResourceCache);
    }

    private Optional<T> updateResourceCache(String resourceString) {
        return JMOptional.getOptional(resourceString).map(transformer).map(this::updateResourceCache);
    }

    private T updateResourceCache(T updateResource) {
        Optional.ofNullable(updateConsumer).ifPresent(tConsumer -> tConsumer.accept(updateResource));
        return this.cachedResource = updateResource;
    }

    /**
     * Update resource optional.
     *
     * @return the optional
     */
    public Optional<T> updateResource() {
        return this.restfulResourceStringUpdater.updateResource().flatMap(this::updateResourceCache);
    }

    /**
     * Gets restful resource url.
     *
     * @return the restful resource url
     */
    public String getRestfulResourceUrl() {
        return this.restfulResourceStringUpdater.getRestfulResourceUrl();
    }

    /**
     * Gets cached json string.
     *
     * @return the cached json string
     */
    public String getCachedString() {
        return this.restfulResourceStringUpdater.getCachedString();
    }

    /**
     * Gets cached resource.
     *
     * @return the cached resource
     */
    public T getCachedResource() {
        return this.cachedResource;
    }

    public void setRestfulResourceUrl(String restfulResourceUrl) {
        this.restfulResourceStringUpdater.setRestfulResourceUrl(restfulResourceUrl);
    }

    }