package kr.jm.utils.http;

import com.fasterxml.jackson.core.type.TypeReference;
import kr.jm.utils.JMInputStream;
import kr.jm.utils.JMString;
import kr.jm.utils.exception.JMException;
import kr.jm.utils.helper.JMJson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 * The type Jm http requester.
 */
public class JMHttpRequester {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JMHttpRequester.class);
    private static final Charset UTF_8 = Charset.forName(JMInputStream.UTF_8);
    private CloseableHttpClient HttpClient;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMHttpRequester getInstance() {
        return JMHttpRequester.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMHttpRequester INSTANCE = new JMHttpRequester();
    }

    /**
     * Gets rest api response as object.
     *
     * @param <T>           the type parameter
     * @param uri           the uri
     * @param typeReference the type reference
     * @return the rest api response as object
     */
    public <T> T getRestApiResponseAsObject(String uri, TypeReference<T> typeReference) {
        try {
            return JMJson.getInstance().withJsonString(getResponseAsString(uri), typeReference);
        } catch (Exception e) {
            throw JMException.handleExceptionAndReturnRuntimeEx(log, e,
                    "getRestApiResponseAsObject", uri, typeReference);
        }
    }

    /**
     * Gets response as string.
     *
     * @param uri the uri
     * @return the response as string
     */
    public String getResponseAsString(String uri) {
        return request(new HttpGet(uri));
    }

    /**
     * Gets response as string.
     *
     * @param uri         the uri
     * @param charsetName the charset name
     * @return the response as string
     */
    public String getResponseAsString(String uri, String charsetName) {
        return request(new HttpGet(uri), charsetName);
    }


    /**
     * Gets response as string.
     *
     * @param uri    the uri
     * @param header the header
     * @return the response as string
     */
    public String getResponseAsString(URI uri, Header header) {
        return getResponseAsString(uri.toString(), header);
    }

    /**
     * Gets response as string.
     *
     * @param uri    the uri
     * @param header the header
     * @return the response as string
     */
    public String getResponseAsString(String uri, Header header) {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader(header);
        request(httpGet);
        return request(httpGet);
    }

    /**
     * Build uri uri.
     *
     * @param httpOrHttps the http or https
     * @param host        the host
     * @param path        the path
     * @param paramMap    the param map
     * @return the uri
     */
    public URI buildUri(String httpOrHttps, String host, String path, Map<String, String> paramMap) {
        try {
            return new URIBuilder().setScheme(httpOrHttps).setHost(host).setPath(path)
                    .setParameters(buildNameValuePareList(paramMap)).build();
        } catch (URISyntaxException e) {
            throw JMException.handleExceptionAndReturnRuntimeEx(log, e, "getResponseAsString", httpOrHttps, host, path,
                    paramMap);
        }
    }

    /**
     * Build name value pare list list.
     *
     * @param keyValueMap the key value map
     * @return the list
     */
    public List<NameValuePair> buildNameValuePareList(Map<String, String> keyValueMap) {
        return keyValueMap.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(toList());
    }

    /**
     * Post response as string string.
     *
     * @param uri        the uri
     * @param header     the header
     * @param httpEntity the http entity
     * @return the string
     */
    public String postResponseAsString(URI uri, Header header, HttpEntity httpEntity) {
        return postResponseAsString(uri.toString(), header, httpEntity);
    }

    /**
     * Post response as string string.
     *
     * @param uri        the uri
     * @param header     the header
     * @param httpEntity the http entity
     * @return the string
     */
    public String postResponseAsString(String uri, Header header, HttpEntity httpEntity) {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader(header);
        httpPost.setEntity(httpEntity);
        return request(httpPost);
    }

    /**
     * Build http entity string entity.
     *
     * @param object the object
     * @return the string entity
     */
    public StringEntity buildHttpEntity(Object object) {
        return buildHttpEntity(JMJson.getInstance().toJsonString(object));
    }

    /**
     * Build http entity string entity.
     *
     * @param string the string
     * @return the string entity
     */
    public StringEntity buildHttpEntity(String string) {
        return buildHttpEntity(string, UTF_8);
    }

    /**
     * Build http entity string entity.
     *
     * @param string  the string
     * @param charset the charset
     * @return the string entity
     */
    public StringEntity buildHttpEntity(String string, Charset charset) {
        return new StringEntity(string, charset);
    }

    /**
     * Request string.
     *
     * @param httpUriRequest the http uri request
     * @return the string
     */
    public String request(HttpUriRequest httpUriRequest) {
        return request(httpUriRequest, Charset.defaultCharset());
    }

    /**
     * Request string.
     *
     * @param httpUriRequest the http uri request
     * @param charSetName    the char set name
     * @return the string
     */
    public String request(HttpUriRequest httpUriRequest, String charSetName) {
        return request(httpUriRequest, Charset.forName(charSetName));
    }

    /**
     * Request string.
     *
     * @param httpUriRequest the http uri request
     * @param charSet        the char set
     * @return the string
     */
    public String request(HttpUriRequest httpUriRequest, Charset charSet) {
        try (CloseableHttpResponse response = getHttpClient().execute(httpUriRequest)) {
            HttpEntity httpEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300)
                return EntityUtils.toString(httpEntity, charSet);
            throw new IllegalStateException(response.getStatusLine().getStatusCode() + JMString.SPACE +
                    response.getStatusLine().getReasonPhrase());
        } catch (IOException e) {
            throw JMException.handleExceptionAndReturnRuntimeEx(log, e, "request", httpUriRequest);
        }
    }

    /**
     * Gets http client.
     *
     * @return the http client
     */
    public CloseableHttpClient getHttpClient() {
        return HttpClient == null ? HttpClient = HttpClients.createDefault() : HttpClient;
    }

    /**
     * Sets http client.
     *
     * @param httpClient the http client
     * @return the http client
     */
    public CloseableHttpClient setHttpClient(CloseableHttpClient httpClient) {
        return HttpClient = httpClient;
    }

}
