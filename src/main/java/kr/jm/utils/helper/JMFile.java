package kr.jm.utils.helper;

import kr.jm.utils.JMString;
import kr.jm.utils.enums.OS;
import kr.jm.utils.exception.JMException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;

/**
 * The type Jm file.
 */
public class JMFile {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JMFile.class);

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMFile getInstance() {
        return JMFile.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMFile INSTANCE = new JMFile();
    }

    private FileView fileView;
    private FileSystemView fileSystemView;

    /**
     * Gets file system view.
     *
     * @return the file system view
     */
    public FileSystemView getFileSystemView() {
        return Optional.ofNullable(fileSystemView)
                .orElseGet(() -> this.fileSystemView = FileSystemView.getFileSystemView());
    }

    private FileView getFileView() {
        return Optional.ofNullable(fileView).orElseGet(() -> {
            JFileChooser jFileChooser = new JFileChooser();
            return fileView = jFileChooser.getUI().getFileView(jFileChooser);
        });
    }

    /**
     * Gets file description.
     *
     * @param file the file
     * @return the file description
     */
    public String getFileDescription(File file) {
        return getFileView().getDescription(file);
    }

    /**
     * Gets home directory file.
     *
     * @return the home directory file
     */
    public File getHomeDirectoryFile() {
        return getFileSystemView().getHomeDirectory();
    }

    /**
     * Gets default directory file.
     *
     * @return the default directory file
     */
    public File getDefaultDirectoryFile() {
        return getFileSystemView().getDefaultDirectory();
    }

    /**
     * Gets icon.
     *
     * @param file the file
     * @return the icon
     */
    public Icon getIcon(File file) {
        return isMac() ? getFileView().getIcon(file) : getFileSystemView().getSystemIcon(file);
    }

    private boolean isMac() {
        return OS.getOS() == OS.MAC;
    }

    /**
     * Gets file name.
     *
     * @param file the file
     * @return the file name
     */
    public String getFileName(File file) {
        return isMac() ? getFileView().getName(file) : getFileSystemView().getSystemDisplayName(file);
    }

    /**
     * Gets file type description.
     *
     * @param file the file
     * @return the file type description
     */
    public String getFileTypeDescription(File file) {
        return isMac() ? getFileView().getTypeDescription(file) : getFileSystemView().getSystemTypeDescription(file);
    }

    /**
     * Gets root file list.
     *
     * @return the root file list
     */
    public List<File> getRootFileList() {
        return Arrays.asList(getFileSystemView().getRoots());
    }

    /**
     * Append writer.
     *
     * @param writer the writer
     * @param string the string
     * @return the writer
     */
    public Writer append(Writer writer, String string) {
        try {
            writer.append(string);
            writer.flush();
            return writer;
        } catch (IOException e) {
            return JMException.handleExceptionAndReturn(log, e, "append", () -> writer, string);
        }
    }

    /**
     * Append line writer.
     *
     * @param writer the writer
     * @param line   the line
     * @return the writer
     */
    public Writer appendLine(Writer writer, String line) {
        return append(writer, line + JMString.LINE_SEPARATOR);
    }

    /**
     * Write string boolean.
     *
     * @param inputString the input string
     * @param targetFile  the target file
     * @return the boolean
     */
    public boolean writeString(String inputString, File targetFile) {
        if (!targetFile.exists()) {
            try {
                Files.write(targetFile.toPath(), inputString.getBytes());
            } catch (IOException e) {
                return JMException.handleExceptionAndReturnFalse(log, e, "writeString", inputString, targetFile);
            }
        }
        return true;
    }

    /**
     * Read string string.
     *
     * @param targetFile the target file
     * @return the string
     */
    public String readString(File targetFile) {
        try {
            return Files.readString(targetFile.toPath());
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "readString", targetFile);
        }
    }

    /**
     * Read string string.
     *
     * @param targetFile  the target file
     * @param charsetName the charset name
     * @return the string
     */
    public String readString(File targetFile, String charsetName) {
        return JMPath.getInstance().readString(targetFile.toPath(), charsetName);
    }


    /**
     * Read string string.
     *
     * @param filePath    the file path
     * @param charsetName the charset name
     * @return the string
     */
    public String readString(String filePath, String charsetName) {
        return JMPath.getInstance().readString(getPath(filePath), charsetName);
    }

    /**
     * Read lines list.
     *
     * @param targetFile the target file
     * @return the list
     */
    public List<String> readLines(File targetFile) {
        return JMPath.getInstance().readLines(targetFile.toPath());
    }


    /**
     * Read lines list.
     *
     * @param targetFile  the target file
     * @param charsetName the charset name
     * @return the list
     */
    public List<String> readLines(File targetFile, String charsetName) {
        return JMPath.getInstance().readLines(targetFile.toPath(), charsetName);
    }


    /**
     * Read lines list.
     *
     * @param filePath the file path
     * @return the list
     */
    public List<String> readLines(String filePath) {
        return JMPath.getInstance().readLines(getPath(filePath));
    }

    /**
     * Read lines list.
     *
     * @param filePath    the file path
     * @param charsetName the charset name
     * @return the list
     */
    public List<String> readLines(String filePath, String charsetName) {
        return JMPath.getInstance().readLines(getPath(filePath), charsetName);
    }

    /**
     * Gets path.
     *
     * @param filePath the file path
     * @return the path
     */
    public Path getPath(String filePath) {
        return FileSystems.getDefault().getPath(filePath);
    }

    /**
     * Gets extension.
     *
     * @param file the file
     * @return the extension
     */
    public String getExtension(File file) {
        return JMString.getExtension(file.getName());
    }

    /**
     * Gets prefix.
     *
     * @param file the file
     * @return the prefix
     */
    public String getPrefix(File file) {
        return JMString.getPrefixOfFileName(file.getName());
    }

    /**
     * Get prefix suffix string [ ].
     *
     * @param file the file
     * @return the string [ ]
     */
    public String[] getPrefixSuffix(File file) {
        return JMString.splitFileNameIntoPreSuffix(file.getName());
    }

    /**
     * Create temp file file.
     *
     * @param file the file
     * @return the file
     */
    public File createTempFile(File file) {
        String[] prefixSuffix = getPrefixSuffix(file);
        try {
            File tempFile = File.createTempFile(prefixSuffix[0], prefixSuffix[1]);
            tempFile.deleteOnExit();
            return tempFile;
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "createTempFile", file);
        }
    }

    /**
     * Gets line stream.
     *
     * @param filePath the file path
     * @return the line stream
     */
    public Stream<String> getLineStream(String filePath) {
        return JMPath.getInstance().getLineStream(getPath(filePath));
    }

    /**
     * Gets line stream.
     *
     * @param filePath the file path
     * @param charset  the charset
     * @return the line stream
     */
    public Stream<String> getLineStream(String filePath, Charset charset) {
        return JMPath.getInstance().getLineStream(getPath(filePath), charset);
    }


    /**
     * Create empty file boolean.
     *
     * @param file the file
     * @return the boolean
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public boolean createEmptyFile(File file) {
        try {
            file.getParentFile().mkdirs();
            return file.createNewFile();
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnFalse(log, e, "createEmptyFile", file);
        }
    }

    /**
     * Gets file store list.
     *
     * @return the file store list
     */
    public List<FileStore> getFileStoreList() {
        return StreamSupport.stream(FileSystems.getDefault().getFileStores().spliterator(), false).collect(toList());
    }

}
