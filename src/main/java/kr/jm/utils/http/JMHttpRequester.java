package kr.jm.utils.http;

import kr.jm.utils.JMString;
import kr.jm.utils.exception.JMException;
import kr.jm.utils.helper.JMLog;
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
     * Gets response as string.
     *
     * @param uri the uri
     * @return the response as string
     */
    public String getResponseAsString(String uri) {
        return getResponseAsString(Map.of(), uri);
    }

    /**
     * Gets response as string.
     *
     * @param uri         the uri
     * @param charsetName the charset name
     * @return the response as string
     */
    public String getResponseAsString(String uri, String charsetName) {
        return getResponseAsString(Map.of(), uri, charsetName);
    }

    /**
     * Gets response as string.
     *
     * @param uri      the uri
     * @param paramMap the param map
     * @return the response as string
     */
    public String getResponseAsString(String uri, Map<String, String> paramMap) {
        return getResponseAsString(Map.of(), uri, paramMap);
    }

    /**
     * Gets response as string.
     *
     * @param uri         the uri
     * @param paramMap    the param map
     * @param charsetName the charset name
     * @return the response as string
     */
    public String getResponseAsString(String uri, Map<String, String> paramMap, String charsetName) {
        return getResponseAsString(Map.of(), uri, paramMap, charsetName);
    }

    /**
     * Gets response as string.
     *
     * @param headerMap the header map
     * @param uri       the uri
     * @return the response as string
     */
    public String getResponseAsString(Map<String, String> headerMap, String uri) {
        return getResponseAsString(headerMap, uri, Map.of());
    }

    /**
     * Gets response as string.
     *
     * @param headerMap   the header map
     * @param uri         the uri
     * @param charsetName the charset name
     * @return the response as string
     */
    public String getResponseAsString(Map<String, String> headerMap, String uri, String charsetName) {
        return getResponseAsString(headerMap, uri, Map.of(), charsetName);
    }

    /**
     * Gets response as string.
     *
     * @param headerMap the header map
     * @param uri       the uri
     * @param paramMap  the param map
     * @return the response as string
     */
    public String getResponseAsString(Map<String, String> headerMap, String uri, Map<String, String> paramMap) {
        return getResponseAsString(headerMap, uri, paramMap, Charset.defaultCharset());
    }

    /**
     * Gets response as string.
     *
     * @param headerMap   the header map
     * @param uri         the uri
     * @param paramMap    the param map
     * @param charsetName the charset name
     * @return the response as string
     */
    public String getResponseAsString(Map<String, String> headerMap, String uri, Map<String, String> paramMap,
            String charsetName) {
        return buildResponseAsString(buildGetRequest(headerMap, uri, paramMap), Charset.forName(charsetName));
    }

    /**
     * Gets response as string.
     *
     * @param headerMap the header map
     * @param uri       the uri
     * @param paramMap  the param map
     * @param charset   the charset
     * @return the response as string
     */
    public String getResponseAsString(Map<String, String> headerMap, String uri, Map<String, String> paramMap,
            Charset charset) {
        return buildResponseAsString(buildGetRequest(headerMap, uri, paramMap), charset);
    }

    /**
     * Build get request http get.
     *
     * @param uri the uri
     * @return the http get
     */
    public HttpGet buildGetRequest(String uri) {
        return buildGetRequest(Map.of(), uri);
    }

    /**
     * Build get request http get.
     *
     * @param headerMap the header map
     * @param uri       the uri
     * @return the http get
     */
    public HttpGet buildGetRequest(Map<String, String> headerMap, String uri) {
        return buildGetRequest(headerMap, uri, Map.of());
    }

    /**
     * Build get request http get.
     *
     * @param uri      the uri
     * @param paramMap the param map
     * @return the http get
     */
    public HttpGet buildGetRequest(String uri, Map<String, String> paramMap) {
        return buildGetRequest(Map.of(), buildUri(uri, paramMap));
    }

    /**
     * Build get request http get.
     *
     * @param headerMap the header map
     * @param uri       the uri
     * @param paramMap  the param map
     * @return the http get
     */
    public HttpGet buildGetRequest(Map<String, String> headerMap, String uri, Map<String, String> paramMap) {
        return buildGetRequest(headerMap, buildUri(uri, paramMap));
    }

    /**
     * Build get request http get.
     *
     * @param headerMap the header map
     * @param uri       the uri
     * @return the http get
     */
    public HttpGet buildGetRequest(Map<String, String> headerMap, URI uri) {
        HttpGet httpGet = new HttpGet(uri);
        headerMap.forEach(httpGet::addHeader);
        return httpGet;
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
                    .setParameters(buildNameValueParamList(paramMap)).build();
        } catch (URISyntaxException e) {
            throw JMException.handleExceptionAndReturnRuntimeEx(log, e, "buildUri", httpOrHttps, host, path,
                    paramMap);
        }
    }

    /**
     * Build uri uri.
     *
     * @param uri      the uri
     * @param paramMap the param map
     * @return the uri
     */
    public URI buildUri(String uri, Map<String, String> paramMap) {
        try {
            return new URIBuilder(uri).setParameters(buildNameValueParamList(paramMap)).build();
        } catch (URISyntaxException e) {
            throw JMException.handleExceptionAndReturnRuntimeEx(log, e, "buildUri", uri, paramMap);
        }
    }

    private List<NameValuePair> buildNameValueParamList(Map<String, String> paramMap) {
        return paramMap.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(toList());
    }


    /**
     * Post response as string string.
     *
     * @param uri  the uri
     * @param body the body
     * @return the string
     */
    public String postResponseAsString(String uri, String body) {
        return postResponseAsString(Map.of(), uri, body);
    }

    /**
     * Post response as string string.
     *
     * @param uri         the uri
     * @param body        the body
     * @param charsetName the charset name
     * @return the string
     */
    public String postResponseAsString(String uri, String body, String charsetName) {
        return postResponseAsString(Map.of(), uri, body, charsetName);
    }

    /**
     * Post response as string string.
     *
     * @param headerMap the header map
     * @param uri       the uri
     * @param body      the body
     * @return the string
     */
    public String postResponseAsString(Map<String, String> headerMap, String uri, String body) {
        return postResponseAsString(headerMap, uri, Map.of(), body);
    }

    /**
     * Post response as string string.
     *
     * @param headerMap   the header map
     * @param uri         the uri
     * @param body        the body
     * @param charsetName the charset name
     * @return the string
     */
    public String postResponseAsString(Map<String, String> headerMap, String uri, String body, String charsetName) {
        return postResponseAsString(headerMap, uri, Map.of(), body, charsetName);
    }

    /**
     * Post response as string string.
     *
     * @param uri      the uri
     * @param paramMap the param map
     * @param body     the body
     * @return the string
     */
    public String postResponseAsString(String uri, Map<String, String> paramMap, String body) {
        return postResponseAsString(Map.of(), uri, paramMap, body);
    }

    /**
     * Post response as string string.
     *
     * @param uri         the uri
     * @param paramMap    the param map
     * @param body        the body
     * @param charsetName the charset name
     * @return the string
     */
    public String postResponseAsString(String uri, Map<String, String> paramMap, String body, String charsetName) {
        return postResponseAsString(Map.of(), uri, paramMap, body, charsetName);
    }

    /**
     * Post response as string string.
     *
     * @param headerMap the header map
     * @param uri       the uri
     * @param paramMap  the param map
     * @param body      the body
     * @return the string
     */
    public String postResponseAsString(Map<String, String> headerMap, String uri, Map<String, String> paramMap,
            String body) {
        return postResponseAsString(headerMap, uri, paramMap, body, Charset.defaultCharset());
    }


    /**
     * Post response as string string.
     *
     * @param headerMap   the header map
     * @param uri         the uri
     * @param paramMap    the param map
     * @param body        the body
     * @param charsetName the charset name
     * @return the string
     */
    public String postResponseAsString(Map<String, String> headerMap, String uri,
            Map<String, String> paramMap, String body, String charsetName) {
        return postResponseAsString(headerMap, uri, paramMap, body, Charset.forName(charsetName));
    }

    /**
     * Post response as string string.
     *
     * @param headerMap the header map
     * @param uri       the uri
     * @param paramMap  the param map
     * @param body      the body
     * @param charset   the charset
     * @return the string
     */
    public String postResponseAsString(Map<String, String> headerMap, String uri,
            Map<String, String> paramMap, String body, Charset charset) {
        return buildResponseAsString(buildPostRequest(headerMap, uri, paramMap, new StringEntity(body, charset)),
                charset);
    }

    /**
     * Build post request http post.
     *
     * @param headerMap      the header map
     * @param uri            the uri
     * @param paramMap       the param map
     * @param bodyHttpEntity the body http entity
     * @return the http post
     */
    public HttpPost buildPostRequest(Map<String, String> headerMap, String uri, Map<String, String> paramMap,
            HttpEntity bodyHttpEntity) {
        return buildPostRequest(headerMap, buildUri(uri, paramMap), bodyHttpEntity);
    }

    /**
     * Build post request http post.
     *
     * @param headerMap      the header map
     * @param uri            the uri
     * @param bodyHttpEntity the body http entity
     * @return the http post
     */
    public HttpPost buildPostRequest(Map<String, String> headerMap, URI uri, HttpEntity bodyHttpEntity) {
        HttpPost httpPost = new HttpPost(uri);
        headerMap.forEach(httpPost::addHeader);
        httpPost.setEntity(bodyHttpEntity);
        return httpPost;
    }

    /**
     * Build post request http post.
     *
     * @param headerMap  the header map
     * @param uri        the uri
     * @param paramMap   the param map
     * @param bodyString the body string
     * @return the http post
     */
    public HttpPost buildPostRequest(Map<String, String> headerMap, String uri, Map<String, String> paramMap,
            String bodyString) {
        return buildPostRequest(headerMap, uri, paramMap, buildStringEntity(bodyString));
    }

    /**
     * Build post request http post.
     *
     * @param headerMap  the header map
     * @param uri        the uri
     * @param bodyString the body string
     * @return the http post
     */
    public HttpPost buildPostRequest(Map<String, String> headerMap, URI uri,
            String bodyString) {
        return buildPostRequest(headerMap, uri, buildStringEntity(bodyString));
    }

    private String buildResponseAsString(HttpUriRequest httpUriRequest, Charset charset) {
        try (CloseableHttpResponse response = execute(httpUriRequest)) {
            return EntityUtils.toString(response.getEntity(), charset);
        } catch (IOException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "buildResponseAsString", httpUriRequest);
        }
    }

    private CloseableHttpResponse handleFinalResponse(CloseableHttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300)
            return response;
        throw new IllegalStateException(response.getStatusLine().getStatusCode() + JMString.SPACE +
                response.getStatusLine().getReasonPhrase());
    }

    /**
     * Build string entity string entity.
     *
     * @param string the string
     * @return the string entity
     */
    public StringEntity buildStringEntity(String string) {
        return new StringEntity(string, Charset.defaultCharset());
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

    /**
     * Execute closeable http response.
     *
     * @param httpUriRequest the http uri request
     * @return the closeable http response
     * @throws IOException the io exception
     */
    public CloseableHttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
        JMLog.debug(log, "execute", httpUriRequest);
        return handleFinalResponse(getHttpClient().execute(httpUriRequest));
    }

}
