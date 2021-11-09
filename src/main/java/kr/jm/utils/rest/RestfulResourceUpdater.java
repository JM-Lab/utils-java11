package kr.jm.utils.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import kr.jm.utils.helper.JMJson;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * The type Restful resource updater.
 *
 * @param <T> the type parameter
 */
public class RestfulResourceUpdater<T> {
    private JMJson jmJson = new JMJson(new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true));
    private RestfulResourceObjectUpdater<T> restfulResourceObjectUpdater;

    /**
     * Instantiates a new Restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     */
    public RestfulResourceUpdater(String restfulResourceUrl) {
        this(restfulResourceUrl, 60);
    }

    /**
     * Instantiates a new Restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     */
    public RestfulResourceUpdater(String restfulResourceUrl, int periodSeconds) {
        this(restfulResourceUrl, periodSeconds, 0);
    }

    /**
     * Instantiates a new Restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     * @param initialDelayMillis the initial delay millis
     */
    public RestfulResourceUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis) {
        this(restfulResourceUrl, periodSeconds, initialDelayMillis, null);
    }

    /**
     * Instantiates a new Restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     * @param initialDelayMillis the initial delay millis
     * @param updateConsumer     the update consumer
     */
    public RestfulResourceUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis,
            Consumer<T> updateConsumer) {
        this(restfulResourceUrl, periodSeconds, initialDelayMillis, updateConsumer, new TypeReference<>() {});
    }

    /**
     * Instantiates a new Restful resource updater.
     *
     * @param restfulResourceUrl the restful resource url
     * @param periodSeconds      the period seconds
     * @param initialDelayMillis the initial delay millis
     * @param updateConsumer     the update consumer
     * @param typeReference      the type reference
     */
    public RestfulResourceUpdater(String restfulResourceUrl, int periodSeconds, long initialDelayMillis,
            Consumer<T> updateConsumer, TypeReference<T> typeReference) {
        this.restfulResourceObjectUpdater = new RestfulResourceObjectUpdater<>(restfulResourceUrl, periodSeconds,
                initialDelayMillis, string -> jmJson.withJsonString(string, typeReference), updateConsumer);
    }

    /**
     * Update resource optional.
     *
     * @return the optional
     */
    public Optional<T> updateResource() {
        return this.restfulResourceObjectUpdater.updateResource();
    }

    /**
     * Gets restful resource url.
     *
     * @return the restful resource url
     */
    public String getRestfulResourceUrl() {
        return this.restfulResourceObjectUpdater.getRestfulResourceUrl();
    }

    /**
     * Gets cached string.
     *
     * @return the cached string
     */
    public String getCachedString() {
        return this.restfulResourceObjectUpdater.getCachedString();
    }

    /**
     * Gets cached resource.
     *
     * @return the cached resource
     */
    public T getCachedResource() {
        return this.restfulResourceObjectUpdater.getCachedResource();
    }

    /**
     * Sets restful resource url.
     *
     * @param restfulResourceUrl the restful resource url
     */
    public void setRestfulResourceUrl(String restfulResourceUrl) {
        this.restfulResourceObjectUpdater.setRestfulResourceUrl(restfulResourceUrl);
    }

}