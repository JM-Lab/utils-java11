package kr.jm.utils.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import kr.jm.utils.JMResources;
import kr.jm.utils.JMRestfulResource;
import kr.jm.utils.exception.JMException;
import org.slf4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * The type Jm json.
 */
public class JMJson {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JMJson.class);

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMJson getInstance() {
        return JMJson.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMJson INSTANCE = new JMJson(
                new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                        .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL));
    }

    /**
     * Instantiates a new Jm json.
     *
     * @param objectMapper the object mapper
     */
    public JMJson(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.mapTypeReference = newTypeReference();
        this.listTypeReference = newTypeReference();
        this.mapListTypeReference = newTypeReference();
    }

    private ObjectMapper objectMapper;

    private final TypeReference<Map<String, Object>> mapTypeReference;
    private final TypeReference<List<Object>> listTypeReference;
    private final TypeReference<List<Map<String, Object>>> mapListTypeReference;

    private <T> TypeReference<T> newTypeReference() {
        return new TypeReference<>() {};
    }

    /**
     * Gets map type reference.
     *
     * @return the map type reference
     */
    public TypeReference<Map<String, Object>> getMapTypeReference() {
        return mapTypeReference;
    }

    /**
     * Gets list type reference.
     *
     * @return the list type reference
     */
    public TypeReference<List<Object>> getListTypeReference() {
        return listTypeReference;
    }

    /**
     * Gets map list type reference.
     *
     * @return the map list type reference
     */
    public TypeReference<List<Map<String, Object>>> getMapListTypeReference() {
        return mapListTypeReference;
    }

    /**
     * Gets map or list type reference.
     *
     * @param <T> the type parameter
     * @return the map or list type reference
     */
    public <T> TypeReference<T> getMapOrListTypeReference() {
        return new TypeReference<>() {};
    }

    /**
     * To json string string.
     *
     * @param <D>        the type parameter
     * @param dataObject the data object
     * @return the string
     */
    public <D> String toJsonString(D dataObject) {
        try {
            return objectMapper.writeValueAsString(dataObject);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "toJsonString", dataObject);
        }
    }

    /**
     * To json string string.
     *
     * @param jsonFile the json file
     * @return the string
     */
    public String toJsonString(File jsonFile) {
        return JMFile.getInstance().readString(jsonFile);
    }

    /**
     * To json file file.
     *
     * @param jsonString     the json string
     * @param returnJsonFile the return json file
     * @return the file
     */
    public File toJsonFile(String jsonString, File returnJsonFile) {
        try {
            objectMapper.writeValue(returnJsonFile, jsonString);
            return returnJsonFile;
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "toJsonFile", jsonString);
        }
    }

    /**
     * To json file file.
     *
     * @param <D>            the type parameter
     * @param dataObject     the data object
     * @param returnJsonFile the return json file
     * @return the file
     */
    public <D> File toJsonFile(D dataObject, File returnJsonFile) {
        try {
            objectMapper.writeValue(returnJsonFile, dataObject);
            return returnJsonFile;
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "toJsonFile", dataObject);
        }
    }


    /**
     * With bytes t.
     *
     * @param <T>           the type parameter
     * @param bytes         the bytes
     * @param typeReference the type reference
     * @return the t
     */
    public <T> T withBytes(byte[] bytes, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(bytes, typeReference);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "withBytes", new String(bytes));
        }
    }

    /**
     * With bytes t.
     *
     * @param <T>   the type parameter
     * @param bytes the bytes
     * @param c     the c
     * @return the t
     */
    public <T> T withBytes(byte[] bytes, Class<T> c) {
        try {
            return objectMapper.readValue(bytes, c);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "withBytes", new String(bytes));
        }
    }

    /**
     * With json string t.
     *
     * @param <T>           the type parameter
     * @param jsonString    the json string
     * @param typeReference the type reference
     * @return the t
     */
    public <T> T withJsonString(String jsonString, TypeReference<T> typeReference) {
        return withBytes(jsonString.getBytes(), typeReference);
    }

    /**
     * To map map.
     *
     * @param jsonObjectString the json object string
     * @return the map
     */
    public Map<String, Object> toMap(String jsonObjectString) {
        return withJsonString(jsonObjectString, mapTypeReference);
    }

    /**
     * To map with json resource map.
     *
     * @param resourceClasspath the resource classpath
     * @return the map
     */
    public Map<String, Object> toMapWithJsonResource(String resourceClasspath) {
        return withJsonResource(resourceClasspath, mapTypeReference);
    }

    /**
     * To list list.
     *
     * @param jsonListString the json list string
     * @return the list
     */
    public List<Object> toList(String jsonListString) {
        return withJsonString(jsonListString, listTypeReference);
    }

    /**
     * To list with json resource list.
     *
     * @param resourceClasspath the resource classpath
     * @return the list
     */
    public List<Object> toListWithJsonResource(String resourceClasspath) {
        return withJsonResource(resourceClasspath, listTypeReference);
    }

    /**
     * To map list list.
     *
     * @param jsonMapListString the json map list string
     * @return the list
     */
    public List<Map<String, Object>> toMapList(String jsonMapListString) {
        return withJsonString(jsonMapListString, mapListTypeReference);
    }

    /**
     * To map list with json resource list.
     *
     * @param resourceClasspath the resource classpath
     * @return the list
     */
    public List<Map<String, Object>> toMapListWithJsonResource(String resourceClasspath) {
        return withJsonResource(resourceClasspath, mapListTypeReference);
    }

    /**
     * With json string t.
     *
     * @param <T>        the type parameter
     * @param jsonString the json string
     * @param c          the c
     * @return the t
     */
    public <T> T withJsonString(String jsonString, Class<T> c) {
        return withBytes(jsonString.getBytes(), c);
    }

    /**
     * With json file t.
     *
     * @param <T>           the type parameter
     * @param jsonFile      the json file
     * @param typeReference the type reference
     * @return the t
     */
    public <T> T withJsonFile(File jsonFile, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(jsonFile, typeReference);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e,
                    "withJsonFile", jsonFile);
        }
    }

    /**
     * With json file t.
     *
     * @param <T>      the type parameter
     * @param jsonFile the json file
     * @param c        the c
     * @return the t
     */
    public <T> T withJsonFile(File jsonFile, Class<T> c) {
        try {
            return objectMapper.readValue(jsonFile, c);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e,
                    "withJsonFile", jsonFile);
        }
    }

    /**
     * With json input stream t.
     *
     * @param <T>           the type parameter
     * @param inputStream   the input stream
     * @param typeReference the type reference
     * @return the t
     */
    public <T> T withJsonInputStream(InputStream inputStream, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "withJsonInputStream", inputStream);
        }
    }

    /**
     * With json input stream t.
     *
     * @param <T>         the type parameter
     * @param inputStream the input stream
     * @param c           the c
     * @return the t
     */
    public <T> T withJsonInputStream(InputStream inputStream, Class<T> c) {
        try {
            return objectMapper.readValue(inputStream, c);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "withJsonInputStream", inputStream);
        }
    }

    /**
     * With json resource t.
     *
     * @param <T>               the type parameter
     * @param resourceClasspath the resource classpath
     * @param typeReference     the type reference
     * @return the t
     */
    public <T> T withJsonResource(String resourceClasspath, TypeReference<T> typeReference) {
        return withJsonInputStream(JMResources.getResourceInputStream(resourceClasspath), typeReference);
    }

    /**
     * With json resource t.
     *
     * @param <T>               the type parameter
     * @param resourceClasspath the resource classpath
     * @param c                 the c
     * @return the t
     */
    public <T> T withJsonResource(String resourceClasspath, Class<T> c) {
        return withJsonInputStream(JMResources.getResourceInputStream(resourceClasspath), c);
    }

    /**
     * With rest or classpath or file path t.
     *
     * @param <T>                                  the type parameter
     * @param resourceRestUrlOrClasspathOrFilePath the resource rest url or classpath or file path
     * @param typeReference                        the type reference
     * @return the t
     */
    public <T> T withRestOrClasspathOrFilePath(String resourceRestUrlOrClasspathOrFilePath,
            TypeReference<T> typeReference) {
        return withJsonString(
                JMRestfulResource.getStringWithRestOrClasspathOrFilePath(resourceRestUrlOrClasspathOrFilePath),
                typeReference);
    }

    /**
     * With rest or file path or classpath t.
     *
     * @param <T>                               the type parameter
     * @param resourceRestOrFilePathOrClasspath the resource rest or file path or classpath
     * @param typeReference                     the type reference
     * @return the t
     */
    public <T> T withRestOrFilePathOrClasspath(String resourceRestOrFilePathOrClasspath,
            TypeReference<T> typeReference) {
        return withJsonString(
                JMRestfulResource.getStringWithRestOrFilePathOrClasspath(resourceRestOrFilePathOrClasspath),
                typeReference);
    }

    /**
     * With rest or classpath or file path t.
     *
     * @param <T>                                  the type parameter
     * @param resourceRestUrlOrClasspathOrFilePath the resource rest url or classpath or file path
     * @param tClass                               the t class
     * @return the t
     */
    public <T> T withRestOrClasspathOrFilePath(String resourceRestUrlOrClasspathOrFilePath, Class<T> tClass) {
        return withJsonString(
                JMRestfulResource.getStringWithRestOrClasspathOrFilePath(resourceRestUrlOrClasspathOrFilePath), tClass);
    }

    /**
     * With rest or file path or classpath t.
     *
     * @param <T>                               the type parameter
     * @param resourceRestOrFilePathOrClasspath the resource rest or file path or classpath
     * @param tClass                            the t class
     * @return the t
     */
    public <T> T withRestOrFilePathOrClasspath(String resourceRestOrFilePathOrClasspath, Class<T> tClass) {
        return withJsonString(
                JMRestfulResource.getStringWithRestOrFilePathOrClasspath(resourceRestOrFilePathOrClasspath), tClass);
    }

    /**
     * With classpath or file path t.
     *
     * @param <T>                         the type parameter
     * @param resourceClasspathOrFilePath the resource classpath or file path
     * @param tClass                      the t class
     * @return the t
     */
    public <T> T withClasspathOrFilePath(String resourceClasspathOrFilePath, Class<T> tClass) {
        return withJsonString(JMResources.getStringWithClasspathOrFilePath(resourceClasspathOrFilePath), tClass);
    }

    /**
     * With file path or classpath t.
     *
     * @param <T>                         the type parameter
     * @param resourceFilePathOrClasspath the resource file path or classpath
     * @param tClass                      the t class
     * @return the t
     */
    public <T> T withFilePathOrClasspath(String resourceFilePathOrClasspath, Class<T> tClass) {
        return withJsonString(JMResources.getStringWithFilePathOrClasspath(resourceFilePathOrClasspath), tClass);
    }

    /**
     * With classpath or file path t.
     *
     * @param <T>                         the type parameter
     * @param resourceClasspathOrFilePath the resource classpath or file path
     * @param typeReference               the type reference
     * @return the t
     */
    public <T> T withClasspathOrFilePath(String resourceClasspathOrFilePath, TypeReference<T> typeReference) {
        return withJsonString(JMResources.getStringWithClasspathOrFilePath(resourceClasspathOrFilePath), typeReference);
    }

    /**
     * With file path or classpath t.
     *
     * @param <T>                         the type parameter
     * @param resourceFilePathOrClasspath the resource file path or classpath
     * @param typeReference               the type reference
     * @return the t
     */
    public <T> T withFilePathOrClasspath(String resourceFilePathOrClasspath, TypeReference<T> typeReference) {
        return withJsonString(JMResources.getStringWithFilePathOrClasspath(resourceFilePathOrClasspath), typeReference);
    }

    /**
     * Transform to map map.
     *
     * @param <T>    the type parameter
     * @param object the object
     * @return the map
     */
    public <T> Map<String, Object> transformToMap(T object) {
        return transform(object, mapTypeReference);
    }

    /**
     * Transform t 2.
     *
     * @param <T1>          the type parameter
     * @param <T2>          the type parameter
     * @param object        the object
     * @param typeReference the type reference
     * @return the t 2
     */
    public <T1, T2> T2 transform(T1 object, TypeReference<T2> typeReference) {
        try {
            return objectMapper.convertValue(object, typeReference);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "transform", object);
        }
    }

    /**
     * Transform t 2.
     *
     * @param <T1>      the type parameter
     * @param <T2>      the type parameter
     * @param object    the object
     * @param typeClass the type class
     * @return the t 2
     */
    public <T1, T2> T2 transform(T1 object, Class<T2> typeClass) {
        try {
            return objectMapper.convertValue(object, typeClass);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "transform", object);
        }
    }

    /**
     * To pretty string string.
     *
     * @param jsonString the json string
     * @return the string
     */
    public String toPrettyString(String jsonString) {
        return toPrettyJsonString(withJsonString(jsonString, Object.class));
    }

    /**
     * To pretty json string string.
     *
     * @param <D>    the type parameter
     * @param object the object
     * @return the string
     */
    public <D> String toPrettyJsonString(D object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "toPrettyJsonString", object);
        }
    }


    /**
     * Println json string.
     *
     * @param <D>    the type parameter
     * @param Object the object
     */
    public <D> void printlnJsonString(D Object) {
        System.out.println(toJsonString(Object));
    }

    /**
     * Println pretty json string.
     *
     * @param <D>    the type parameter
     * @param Object the object
     */
    public <D> void printlnPrettyJsonString(D Object) {
        System.out.println(toPrettyJsonString(Object));
    }
}
