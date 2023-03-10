package kr.jm.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JMResourcesTest {

    @Test
    public void testGetURL() {
        System.out.println(JMResources.getURL("LICENSE"));
    }


    @Test
    public void testReadStringForZip() {
        Assert.assertEquals("hello\n", JMResources.readStringForZip("src/test/resources/test.zip", "test/test.txt"));
    }

    @Test
    public void testReadLinesForZip() {
        List<String> linesForZip = JMResources.readLinesForZip("src/test/resources/test.zip", "test/test.txt");
        Assert.assertEquals("[hello]", linesForZip.toString());
    }

    @Test
    public void getFileOrClasspathResourceInputStream() throws IOException {
        InputStream license = JMResources.getFileOrClasspathResourceInputStream("LICENSE");
        Assert.assertEquals(11311, license.readAllBytes().length);
    }
}
