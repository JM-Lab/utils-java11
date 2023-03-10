package kr.jm.utils.http;

import org.junit.Test;

import java.net.URI;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JMHttpRequesterTest {
    @Test
    public void buildUri() {
        URI uri = JMHttpRequester.getInstance().buildUri(
                "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=AIzaSyAHmiVm2ztLkFGozXvLobozuiXbmPXBw",
                Map.of("uuid", "abc"));
        System.out.println(uri);
        assertEquals("https", uri.getScheme());
        assertEquals("www.googleapis.com", uri.getHost());
        assertEquals("/identitytoolkit/v3/relyingparty/verifyPassword", uri.getPath());
        assertEquals("key=AIzaSyAHmiVm2ztLkFGozXvLobozuiXbmPXBw&uuid=abc", uri.getQuery());
    }
}