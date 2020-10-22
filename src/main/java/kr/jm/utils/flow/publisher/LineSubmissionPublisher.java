package kr.jm.utils.flow.publisher;

import kr.jm.utils.JMOptional;
import kr.jm.utils.JMResources;
import kr.jm.utils.helper.JMFile;
import kr.jm.utils.helper.JMPath;

import java.io.File;
import java.util.stream.Stream;

/**
 * The type Line submission publisher.
 */
public class LineSubmissionPublisher extends JMSubmissionPublisher<String> {
    /**
     * Submit file path int.
     *
     * @param filePath the file path
     * @return the int
     */
    public int submitFilePath(String filePath) {
        return submitFile(JMPath.getInstance().getPath(filePath).toFile());
    }

    /**
     * Submit file int.
     *
     * @param file the file
     * @return the int
     */
    public int submitFile(File file) {
        return submitStream(JMFile.getInstance().readLines(file).stream());

    }

    /**
     * Submit stream int.
     *
     * @param stream the stream
     * @return the int
     */
    public int submitStream(Stream<String> stream) {
        return stream.mapToInt(this::submit).sum();
    }


    /**
     * Submit classpath int.
     *
     * @param resourceClasspath the resource classpath
     * @return the int
     */
    public int submitClasspath(String resourceClasspath) {
        return submitStream(JMResources.readLines(resourceClasspath).stream());
    }

    /**
     * Submit file path or classpath int.
     *
     * @param filePathOrResourceClasspath the file path or resource classpath
     * @return the int
     */
    public int submitFilePathOrClasspath(String filePathOrResourceClasspath) {
        return submitStream(JMOptional.getOptional(JMFile.getInstance().readLines(filePathOrResourceClasspath))
                .orElseGet(() -> JMResources.readLines(filePathOrResourceClasspath)).stream());
    }
}
