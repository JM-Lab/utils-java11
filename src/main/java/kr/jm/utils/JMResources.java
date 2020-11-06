package kr.jm.utils;

import kr.jm.utils.exception.JMException;
import kr.jm.utils.helper.JMFile;
import kr.jm.utils.helper.JMPath;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipFile;

/**
 * The interface Jm resources.
 */
public interface JMResources {

    /**
     * The constant log.
     */
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JMResources.class);
    /**
     * The constant UTF_8.
     */
    String UTF_8 = JMInputStream.UTF_8;

    /**
     * Sets system property if is null.
     *
     * @param key   the key
     * @param value the value
     */
    static void setSystemPropertyIfIsNull(String key, Object value) {
        if (!System.getProperties().containsKey(key))
            System.setProperty(key, value.toString());
    }

    /**
     * Gets system property.
     *
     * @param key the key
     * @return the system property
     */
    static String getSystemProperty(String key) {
        return System.getProperty(key);
    }

    /**
     * Gets resource url.
     *
     * @param classpath the classpath
     * @return the resource url
     */
    static URL getResourceURL(String classpath) {
        return ClassLoader.getSystemResource(classpath);
    }

    /**
     * Gets url.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the url
     */
    static URL getURL(String classpathOrFilePath) {
        try {
            return getURI(classpathOrFilePath).toURL();
        } catch (MalformedURLException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "getURL", classpathOrFilePath);
        }
    }

    /**
     * Gets resource uri.
     *
     * @param classpath the classpath
     * @return the resource uri
     */
    static URI getResourceURI(String classpath) {
        try {
            return getResourceURL(classpath).toURI();
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "getResourceURI", classpath);
        }
    }

    /**
     * Gets uri.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the uri
     */
    static URI getURI(String classpathOrFilePath) {
        return Optional.ofNullable(getResourceURI(classpathOrFilePath))
                .orElseGet(() -> new File(classpathOrFilePath).toURI());
    }

    /**
     * Gets resource input stream.
     *
     * @param classpath the classpath
     * @return the resource input stream
     */
    static InputStream getResourceInputStream(String classpath) {
        return ClassLoader.getSystemResourceAsStream(classpath);
    }

    /**
     * Gets file resource input stream.
     *
     * @param path the path
     * @return the file resource input stream
     */
    static InputStream getFileResourceInputStream(String path) {
        return JMOptional.getOptional(path).map(JMPath.getInstance()::getPath).map(JMResources::newFileInputStream)
                .orElse(null);
    }

    private static InputStream newFileInputStream(Path path) {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "newFileInputStream", path);
        }
    }

    /**
     * Gets resource input stream for zip.
     *
     * @param zipFilePath the zip file path
     * @param entryName   the entry name
     * @return the resource input stream for zip
     */
    static InputStream getResourceInputStreamForZip(String zipFilePath, String entryName) {
        return getResourceInputStreamForZip(
                Optional.of(JMFile.getInstance().getPath(zipFilePath)).filter(JMPath.getInstance()::exists)
                        .map(Path::toFile).orElse(null), entryName);
    }

    /**
     * Gets resource input stream for zip.
     *
     * @param zip       the zip
     * @param entryName the entry name
     * @return the resource input stream for zip
     */
    static InputStream getResourceInputStreamForZip(File zip, String entryName) {
        return Optional.ofNullable(zip).map(JMResources::newZipFile)
                .map(zipFile -> getResourceInputStreamForZip(zipFile, entryName)).orElse(null);
    }

    /**
     * Gets resource input stream for zip.
     *
     * @param zipFile   the zip file
     * @param entryName the entry name
     * @return the resource input stream for zip
     */
    static InputStream getResourceInputStreamForZip(ZipFile zipFile, String entryName) {
        try {
            return zipFile.getInputStream(zipFile.getEntry(entryName));
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "newZipFile", zipFile, entryName);
        }
    }

    private static ZipFile newZipFile(File file) {
        try {
            return new ZipFile(file);
        } catch (IOException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "newZipFile", file);
        }
    }

    /**
     * Gets properties.
     *
     * @param classpath the classpath
     * @return the properties
     */
    static Properties getProperties(String classpath) {
        Properties properties = new Properties();
        try (InputStream is = getResourceInputStream(classpath)) {
            properties.load(is);
            return properties;
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "getProperties", classpath);
        }
    }

    /**
     * Gets properties.
     *
     * @param propertiesFile the properties file
     * @return the properties
     */
    static Properties getProperties(File propertiesFile) {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader(propertiesFile))) {
            properties.load(reader);
            return properties;
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "getProperties", propertiesFile);
        }
    }

    /**
     * Save properties boolean.
     *
     * @param inProperties the in properties
     * @param saveFile     the save file
     * @param comment      the comment
     * @return the boolean
     */
    static boolean saveProperties(Properties inProperties, File saveFile, String comment) {
        try {
            if (!saveFile.exists())
                JMFile.getInstance().createEmptyFile(saveFile);
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            inProperties.store(writer, comment);
            writer.close();
            return true;
        } catch (IOException e) {
            return JMException.handleExceptionAndReturnFalse(log, e, "saveProperties", inProperties, saveFile, comment);
        }

    }

    /**
     * Read string for zip string.
     *
     * @param zipFilePath the zip file path
     * @param entryName   the entry name
     * @param charsetName the charset name
     * @return the string
     */
    static String readStringForZip(String zipFilePath, String entryName, String charsetName) {
        return JMInputStream.toString(getResourceInputStreamForZip(zipFilePath, entryName), charsetName);
    }

    /**
     * Read string for zip string.
     *
     * @param zipFilePath the zip file path
     * @param entryName   the entry name
     * @return the string
     */
    static String readStringForZip(String zipFilePath, String entryName) {
        return JMInputStream.toString(getResourceInputStreamForZip(zipFilePath, entryName));
    }


    /**
     * Read string string.
     *
     * @param resourceClasspath the resource classpath
     * @return the string
     */
    static String readString(String resourceClasspath) {
        return JMInputStream.toString(getResourceInputStream(resourceClasspath));
    }

    /**
     * Read string string.
     *
     * @param resourceClasspath the resource classpath
     * @param charsetName       the charset name
     * @return the string
     */
    static String readString(String resourceClasspath, String charsetName) {
        return JMInputStream.toString(getResourceInputStream(resourceClasspath), charsetName);
    }


    /**
     * Read lines list.
     *
     * @param resourceClasspath the resource classpath
     * @return the list
     */
    static List<String> readLines(String resourceClasspath) {
        return JMInputStream.readLines(getResourceInputStream(resourceClasspath));
    }

    /**
     * Read lines list.
     *
     * @param resourceClasspath the resource classpath
     * @param charsetName       the charset name
     * @return the list
     */
    static List<String> readLines(String resourceClasspath, String charsetName) {
        return JMInputStream.readLines(getResourceInputStream(resourceClasspath), charsetName);
    }

    /**
     * Read lines for zip list.
     *
     * @param zipFilePath the zip file path
     * @param entryName   the entry name
     * @return the list
     */
    static List<String> readLinesForZip(String zipFilePath, String entryName) {
        return JMInputStream.readLines(getResourceInputStreamForZip(zipFilePath, entryName));
    }

    /**
     * Read lines for zip list.
     *
     * @param zipFilePath the zip file path
     * @param entryName   the entry name
     * @param charsetName the charset name
     * @return the list
     */
    static List<String> readLinesForZip(String zipFilePath, String entryName, String charsetName) {
        return JMInputStream.readLines(getResourceInputStreamForZip(zipFilePath, entryName), charsetName);
    }

    /**
     * Gets string with classpath or file path.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the string with classpath or file path
     */
    static String getStringWithClasspathOrFilePath(
            String classpathOrFilePath) {
        return getStringWithClasspathOrFilePath(classpathOrFilePath,
                UTF_8);
    }

    /**
     * Gets string with classpath or file path.
     *
     * @param classpathOrFilePath the classpath or file path
     * @param charsetName         the charset name
     * @return the string with classpath or file path
     */
    static String getStringWithClasspathOrFilePath(
            String classpathOrFilePath, String charsetName) {
        return getStringOptionalWithClasspath(classpathOrFilePath, charsetName)
                .orElseGet(() -> getStringOptionalWithFilePath
                        (classpathOrFilePath, charsetName).orElse(null));
    }

    /**
     * Read lines with classpath or file path list.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the list
     */
    static List<String> readLinesWithClasspathOrFilePath(String classpathOrFilePath) {
        return getStringListOptionalWithClasspath(classpathOrFilePath).filter(JMCollections::isNotNullOrEmpty)
                .orElseGet(() -> JMFile.getInstance().readLines(classpathOrFilePath));
    }

    /**
     * Gets string list as opt with classpath.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the string list as opt with classpath
     */
    static Optional<List<String>> getStringListOptionalWithClasspath(String classpathOrFilePath) {
        return getResourceInputStreamOptional(classpathOrFilePath).map(JMInputStream::readLines);
    }

    /**
     * Read lines with file path or classpath list.
     *
     * @param filePathOrClasspath the file path or classpath
     * @return the list
     */
    static List<String> readLinesWithFilePathOrClasspath(String filePathOrClasspath) {
        return JMOptional.getOptional(JMFile.getInstance().readLines(filePathOrClasspath)).orElseGet(
                () -> getStringListOptionalWithClasspath(filePathOrClasspath).orElseGet(Collections::emptyList));
    }

    /**
     * Gets string with file path or classpath.
     *
     * @param filePathOrClasspath the file path or classpath
     * @param charsetName         the charset name
     * @return the string with file path or classpath
     */
    static String getStringWithFilePathOrClasspath(String filePathOrClasspath, String charsetName) {
        return getStringOptionalWithFilePath(filePathOrClasspath, charsetName)
                .orElseGet(() -> getStringOptionalWithClasspath(filePathOrClasspath, charsetName).orElse(null));
    }

    /**
     * Gets string with file path or classpath.
     *
     * @param filePathOrClasspath the file path or classpath
     * @return the string with file path or classpath
     */
    static String getStringWithFilePathOrClasspath(String filePathOrClasspath) {
        return getStringWithFilePathOrClasspath(filePathOrClasspath, UTF_8);
    }

    /**
     * Gets string as opt with file path.
     *
     * @param filePath    the file path
     * @param charsetName the charset name
     * @return the string as opt with file path
     */
    static Optional<String> getStringOptionalWithFilePath(String filePath, String charsetName) {
        return JMOptional.getOptional(JMFile.getInstance().readString(filePath, charsetName));
    }

    /**
     * Gets string as opt with file path.
     *
     * @param filePath the file path
     * @return the string as opt with file path
     */
    static Optional<String> getStringOptionalWithFilePath(String filePath) {
        return getStringOptionalWithFilePath(filePath, UTF_8);
    }

    /**
     * Gets string as opt with classpath.
     *
     * @param classpath   the classpath
     * @param charsetName the charset name
     * @return the string as opt with classpath
     */
    static Optional<String> getStringOptionalWithClasspath(String classpath, String charsetName) {
        return getResourceInputStreamOptional(classpath)
                .map(resourceInputStream -> JMInputStream.toString(resourceInputStream, charsetName));
    }

    /**
     * Gets string as opt with classpath.
     *
     * @param classpath the classpath
     * @return the string as opt with classpath
     */
    static Optional<String> getStringOptionalWithClasspath(String classpath) {
        return getStringOptionalWithClasspath(classpath, UTF_8);
    }

    /**
     * Gets resource input stream as opt.
     *
     * @param classpath the classpath
     * @return the resource input stream as opt
     */
    static Optional<InputStream> getResourceInputStreamOptional(String classpath) {
        return Optional.ofNullable(getResourceInputStream(classpath));
    }

    /**
     * Gets resource bundle.
     *
     * @param baseName the base name
     * @return the resource bundle
     */
    static ResourceBundle getResourceBundle(String baseName) {
        return ResourceBundle.getBundle(baseName);
    }

    /**
     * Gets resource bundle.
     *
     * @param baseName     the base name
     * @param targetLocale the target locale
     * @return the resource bundle
     */
    static ResourceBundle getResourceBundle(String baseName, Locale targetLocale) {
        Locale.setDefault(targetLocale);
        return ResourceBundle.getBundle(baseName);
    }
}
