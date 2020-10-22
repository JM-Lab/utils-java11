package kr.jm.utils;

import kr.jm.utils.http.JMHttpRequester;

import java.util.List;

/**
 * The interface Jm restful resource.
 */
public interface JMRestfulResource {

    /**
     * Gets string with rest or classpath or file path.
     *
     * @param resourceWithRestOrClasspathOrFilePath the resource with rest or classpath or file path
     * @param charsetName                           the charset name
     * @return the string with rest or classpath or file path
     */
    static String getStringWithRestOrClasspathOrFilePath(String resourceWithRestOrClasspathOrFilePath,
            String charsetName) {
        return isHttp(resourceWithRestOrClasspathOrFilePath) ? JMHttpRequester.getInstance()
                .getResponseAsString(resourceWithRestOrClasspathOrFilePath, charsetName) : JMResources
                .getStringWithClasspathOrFilePath(resourceWithRestOrClasspathOrFilePath, charsetName);
    }

    private static boolean isHttp(String string) {
        return string.startsWith("http");
    }

    /**
     * Gets string with rest or classpath or file path.
     *
     * @param resourceWithRestOrClasspathOrFilePath the resource with rest or classpath or file path
     * @return the string with rest or classpath or file path
     */
    static String getStringWithRestOrClasspathOrFilePath(String resourceWithRestOrClasspathOrFilePath) {
        return getStringWithRestOrClasspathOrFilePath(resourceWithRestOrClasspathOrFilePath, JMResources.UTF_8);
    }

    /**
     * Read lines with rest or classpath or file path list.
     *
     * @param resourceWithRestOrClasspathOrFilePath the resource with rest or classpath or file path
     * @param charsetName                           the charset name
     * @return the list
     */
    static List<String> readLinesWithRestOrClasspathOrFilePath(String resourceWithRestOrClasspathOrFilePath,
            String charsetName) {
        return JMCollections.buildListByLine(
                getStringWithRestOrClasspathOrFilePath(resourceWithRestOrClasspathOrFilePath, charsetName));
    }

    /**
     * Read lines with rest or classpath or file path list.
     *
     * @param resourceWithRestOrClasspathOrFilePath the resource with rest or classpath or file path
     * @return the list
     */
    static List<String> readLinesWithRestOrClasspathOrFilePath(String resourceWithRestOrClasspathOrFilePath) {
        return readLinesWithRestOrClasspathOrFilePath(resourceWithRestOrClasspathOrFilePath, JMResources.UTF_8);
    }

    /**
     * Gets string with rest or file path or classpath.
     *
     * @param resourceWithRestOrFilePathOrClasspath the resource with rest or file path or classpath
     * @param charsetName                           the charset name
     * @return the string with rest or file path or classpath
     */
    static String getStringWithRestOrFilePathOrClasspath(String resourceWithRestOrFilePathOrClasspath,
            String charsetName) {
        return isHttp(resourceWithRestOrFilePathOrClasspath) ? JMHttpRequester.getInstance()
                .getResponseAsString(resourceWithRestOrFilePathOrClasspath, charsetName) : JMResources
                .getStringWithFilePathOrClasspath(resourceWithRestOrFilePathOrClasspath, charsetName);
    }

    /**
     * Gets string with rest or file path or classpath.
     *
     * @param resourceWithRestOrFilePathOrClasspath the resource with rest or file path or classpath
     * @return the string with rest or file path or classpath
     */
    static String getStringWithRestOrFilePathOrClasspath(String resourceWithRestOrFilePathOrClasspath) {
        return getStringWithRestOrFilePathOrClasspath(resourceWithRestOrFilePathOrClasspath, JMResources.UTF_8);
    }

    /**
     * Read lines with rest or file path or classpath list.
     *
     * @param resourceWithRestOrFilePathOrClasspath the resource with rest or file path or classpath
     * @param charsetName                           the charset name
     * @return the list
     */
    static List<String> readLinesWithRestOrFilePathOrClasspath(String resourceWithRestOrFilePathOrClasspath,
            String charsetName) {
        return JMCollections.buildListByLine(
                getStringWithRestOrClasspathOrFilePath(resourceWithRestOrFilePathOrClasspath, charsetName));
    }

    /**
     * Read lines with rest or file path or classpath list.
     *
     * @param resourceWithRestOrFilePathOrClasspath the resource with rest or file path or classpath
     * @return the list
     */
    static List<String> readLinesWithRestOrFilePathOrClasspath(String resourceWithRestOrFilePathOrClasspath) {
        return readLinesWithRestOrFilePathOrClasspath(resourceWithRestOrFilePathOrClasspath, JMResources.UTF_8);
    }
}
