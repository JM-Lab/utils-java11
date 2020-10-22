package kr.jm.utils.helper;

import kr.jm.utils.JMOptional;
import kr.jm.utils.JMStream;
import kr.jm.utils.JMString;
import kr.jm.utils.enums.OS;
import kr.jm.utils.exception.JMException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static kr.jm.utils.helper.JMLog.debug;

/**
 * The type Jm path.
 */
public class JMPath {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JMPath.class);

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMPath getInstance() {
        return JMPath.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMPath INSTANCE = new JMPath();
    }

    private JMFile jmFile;
    private final Predicate<Path> directoryFilter;
    private final Predicate<Path> regularFileFilter;
    private final Predicate<Path> executableFilter;
    private final Predicate<Path> readableFilter;
    private final Predicate<Path> writableFilter;
    private final Predicate<Path> symbolicLinkFilter;
    private final Predicate<Path> hiddenFilter;
    private final Predicate<Path> notExistFilter;
    private final Predicate<Path> existFilter;
    private final Predicate<Path> directoryAndNotSymbolicLinkFilter;
    private final Comparator<Path> directoryFirstComparator;

    /**
     * Instantiates a new Jm path.
     */
    public JMPath() {
        directoryFilter = Files::isDirectory;
        regularFileFilter = Files::isRegularFile;
        executableFilter = Files::isExecutable;
        readableFilter = Files::isReadable;
        writableFilter = Files::isWritable;
        symbolicLinkFilter = Files::isSymbolicLink;
        hiddenFilter = JMPath::isHidden;
        notExistFilter = Files::notExists;
        existFilter = Files::exists;
        directoryAndNotSymbolicLinkFilter = directoryFilter.and(symbolicLinkFilter.negate());
        directoryFirstComparator = (p1, p2) ->
                isDirectory(p1) && !isDirectory(p2) ? -1 : !isDirectory(p1) && isDirectory(p2) ? 1 : p1.compareTo(p2);
    }

    /**
     * Gets directory filter.
     *
     * @return the directory filter
     */
    public Predicate<Path> getDirectoryFilter() {
        return directoryFilter;
    }

    /**
     * Gets regular file filter.
     *
     * @return the regular file filter
     */
    public Predicate<Path> getRegularFileFilter() {
        return regularFileFilter;
    }

    /**
     * Gets executable filter.
     *
     * @return the executable filter
     */
    public Predicate<Path> getExecutableFilter() {
        return executableFilter;
    }

    /**
     * Gets readable filter.
     *
     * @return the readable filter
     */
    public Predicate<Path> getReadableFilter() {
        return readableFilter;
    }

    /**
     * Gets writable filter.
     *
     * @return the writable filter
     */
    public Predicate<Path> getWritableFilter() {
        return writableFilter;
    }

    /**
     * Gets symbolic link filter.
     *
     * @return the symbolic link filter
     */
    public Predicate<Path> getSymbolicLinkFilter() {
        return symbolicLinkFilter;
    }

    /**
     * Gets hidden filter.
     *
     * @return the hidden filter
     */
    public Predicate<Path> getHiddenFilter() {
        return hiddenFilter;
    }

    /**
     * Gets not exist filter.
     *
     * @return the not exist filter
     */
    public Predicate<Path> getNotExistFilter() {
        return notExistFilter;
    }

    /**
     * Gets exist filter.
     *
     * @return the exist filter
     */
    public Predicate<Path> getExistFilter() {
        return existFilter;
    }

    /**
     * Gets directory and not symbolic link filter.
     *
     * @return the directory and not symbolic link filter
     */
    public Predicate<Path> getDirectoryAndNotSymbolicLinkFilter() {
        return directoryAndNotSymbolicLinkFilter;
    }

    /**
     * Is directory boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean isDirectory(Path path) {
        return directoryFilter.test(path);
    }

    /**
     * Is regular file boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean isRegularFile(Path path) {
        return regularFileFilter.test(path);
    }

    /**
     * Is executable boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean isExecutable(Path path) {
        return executableFilter.test(path);
    }

    /**
     * Is readable boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean isReadable(Path path) {
        return readableFilter.test(path);
    }

    /**
     * Is writable boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean isWritable(Path path) {
        return writableFilter.test(path);
    }

    /**
     * Is symbolic link boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean isSymbolicLink(Path path) {
        return symbolicLinkFilter.test(path);
    }

    /**
     * Not exists boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean notExists(Path path) {
        return notExistFilter.test(path);
    }

    /**
     * Exists boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean exists(Path path) {
        return existFilter.test(path);
    }

    /**
     * Is directory and not symbolic link boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean isDirectoryAndNotSymbolicLink(Path path) {
        return directoryAndNotSymbolicLinkFilter.test(path);
    }

    /**
     * Gets directory first comparator.
     *
     * @return the directory first comparator
     */
    public Comparator<Path> getDirectoryFirstComparator() {
        return directoryFirstComparator;
    }


    /**
     * Gets file store path list.
     *
     * @return the file store path list
     */
    public List<Path> getFileStorePathList() {
        return JMFile.getInstance().getFileStoreList().stream().map(Object::toString)
                .map(toString -> toString.substring(0, toString.indexOf(JMString.SPACE))).map(this::getPath)
                .collect(toList());
    }

    /**
     * Is hidden boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isHidden(Path path) {
        try {
            return Files.isHidden(path);
        } catch (IOException e) {
            return true;
        }
    }

    /**
     * Gets parent as opt.
     *
     * @param path the path
     * @return the parent as opt
     */
    public Optional<Path> getParentAsOpt(Path path) {
        return Optional.ofNullable(path.getParent()).filter(existFilter);
    }

    /**
     * Gets root path stream.
     *
     * @return the root path stream
     */
    public Stream<Path> getRootPathStream() {
        return getJMFile().getRootFileList().stream().map(File::toPath);
    }

    private JMFile getJMFile() {
        return Objects.isNull(this.jmFile) ? this.jmFile = JMFile.getInstance() : this.jmFile;
    }

    /**
     * Gets root directory stream.
     *
     * @return the root directory stream
     */
    public Stream<Path> getRootDirectoryStream() {
        return getRootPathStream().filter(directoryFilter);
    }

    /**
     * Gets path.
     *
     * @param path the path
     * @return the path
     */
    public Path getPath(String path) {
        return FileSystems.getDefault().getPath(path).toAbsolutePath();
    }

    /**
     * Gets current path.
     *
     * @return the current path
     */
    public Path getCurrentPath() {
        return getPath(OS.getUserWorkingDir());
    }

    /**
     * Gets user home.
     *
     * @return the user home
     */
    public Path getUserHome() {
        return getPath(OS.getUserHomeDir());
    }

    /**
     * Gets children path stream.
     *
     * @param path the path
     * @return the children path stream
     */
    public Stream<Path> getChildrenPathStream(Path path) {
        return Optional.ofNullable(path.toFile().listFiles()).map(listFiles -> Stream.of(listFiles).map(File::toPath))
                .orElseGet(Stream::empty);
    }

    /**
     * Gets children path stream.
     *
     * @param path   the path
     * @param filter the filter
     * @return the children path stream
     */
    public Stream<Path> getChildrenPathStream(Path path, Predicate<Path> filter) {
        return getChildrenPathStream(path).filter(filter);
    }

    /**
     * Gets child directory path stream.
     *
     * @param path the path
     * @return the child directory path stream
     */
    public Stream<Path> getChildDirectoryPathStream(Path path) {
        return getChildrenPathStream(path).filter(directoryAndNotSymbolicLinkFilter);
    }

    /**
     * Gets child directory path stream.
     *
     * @param path   the path
     * @param filter the filter
     * @return the child directory path stream
     */
    public Stream<Path> getChildDirectoryPathStream(Path path, Predicate<Path> filter) {
        return getChildrenPathStream(path).filter(directoryAndNotSymbolicLinkFilter.and(filter));
    }

    /**
     * Gets child file path stream.
     *
     * @param path the path
     * @return the child file path stream
     */
    public Stream<Path> getChildFilePathStream(Path path) {
        return getChildrenPathStream(path).filter(regularFileFilter);
    }

    /**
     * Gets child file path stream.
     *
     * @param path   the path
     * @param filter the filter
     * @return the child file path stream
     */
    public Stream<Path> getChildFilePathStream(Path path, Predicate<Path> filter) {
        return getChildrenPathStream(path).filter(regularFileFilter.and(filter));
    }

    /**
     * Gets ancestor path list.
     *
     * @param startPath the start path
     * @return the ancestor path list
     */
    public List<Path> getAncestorPathList(Path startPath) {
        List<Path> ancestorPathList = new ArrayList<>();
        buildPathListOfAncestorDirectory(ancestorPathList, startPath);
        Collections.reverse(ancestorPathList);
        return ancestorPathList;
    }

    private void buildPathListOfAncestorDirectory(List<Path> pathListOfAncestorDirectory, Path path) {
        Optional.ofNullable(path.getParent()).stream().peek(pathListOfAncestorDirectory::add)
                .forEach(p -> buildPathListOfAncestorDirectory(pathListOfAncestorDirectory, p));
    }

    /**
     * Gets sub directory path list.
     *
     * @param startDirectoryPath the start directory path
     * @return the sub directory path list
     */
    public List<Path> getSubDirectoryPathList(Path startDirectoryPath) {
        return getSubPathList(startDirectoryPath, directoryAndNotSymbolicLinkFilter);
    }

    /**
     * Gets sub directory path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @return the sub directory path list
     */
    public List<Path> getSubDirectoryPathList(Path startDirectoryPath, int maxDepth) {
        return getSubPathList(startDirectoryPath, maxDepth, directoryAndNotSymbolicLinkFilter);
    }

    /**
     * Gets sub directory path list.
     *
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @return the sub directory path list
     */
    public List<Path> getSubDirectoryPathList(Path startDirectoryPath, Predicate<Path> filter) {
        return getSubPathList(startDirectoryPath, Integer.MAX_VALUE, directoryAndNotSymbolicLinkFilter.and(filter));
    }

    /**
     * Gets sub directory path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @return the sub directory path list
     */
    public List<Path> getSubDirectoryPathList(Path startDirectoryPath, int maxDepth, Predicate<Path> filter) {
        return getSubPathList(startDirectoryPath, maxDepth, directoryAndNotSymbolicLinkFilter.and(filter));
    }

    /**
     * Gets sub file path list.
     *
     * @param startDirectoryPath the start directory path
     * @return the sub file path list
     */
    public List<Path> getSubFilePathList(Path startDirectoryPath) {
        return getSubPathList(startDirectoryPath, regularFileFilter);
    }

    /**
     * Gets sub file path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @return the sub file path list
     */
    public List<Path> getSubFilePathList(Path startDirectoryPath, int maxDepth) {
        return getSubPathList(startDirectoryPath, maxDepth, regularFileFilter);
    }

    /**
     * Gets sub file path list.
     *
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @return the sub file path list
     */
    public List<Path> getSubFilePathList(Path startDirectoryPath, Predicate<Path> filter) {
        return getSubPathList(startDirectoryPath, regularFileFilter.and(filter));
    }

    /**
     * Gets sub file path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @return the sub file path list
     */
    public List<Path> getSubFilePathList(Path startDirectoryPath, int maxDepth, Predicate<Path> filter) {
        return getSubPathList(startDirectoryPath, maxDepth, regularFileFilter.and(filter));
    }

    /**
     * Gets sub path list.
     *
     * @param startDirectoryPath the start directory path
     * @return the sub path list
     */
    public List<Path> getSubPathList(Path startDirectoryPath) {
        return getSubPathList(startDirectoryPath, Integer.MAX_VALUE);
    }

    /**
     * Gets sub path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @return the sub path list
     */
    public List<Path> getSubPathList(Path startDirectoryPath, int maxDepth) {
        return getSubPathList(startDirectoryPath, maxDepth, getTrue());
    }

    private Predicate<Path> getTrue() {
        return path -> true;
    }

    /**
     * Gets sub path list.
     *
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @return the sub path list
     */
    public List<Path> getSubPathList(Path startDirectoryPath, Predicate<Path> filter) {
        return getSubPathList(startDirectoryPath, Integer.MAX_VALUE, filter);
    }

    /**
     * Gets sub path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @return the sub path list
     */
    public List<Path> getSubPathList(Path startDirectoryPath, int maxDepth, Predicate<Path> filter) {
        List<Path> subPathList = Collections.synchronizedList(new ArrayList<>());
        if (isDirectory(startDirectoryPath))
            buildSubPath(startDirectoryPath, maxDepth, filter, subPathList);
        return subPathList;
    }

    private void buildSubPath(Path startDirectoryPath, int maxDepth, Predicate<Path> filter, List<Path> pathList) {
        addAndGetDirectoryStream(pathList, getChildrenPathStream(startDirectoryPath), filter).parallel()
                .forEach(getDrillDownFunction(maxDepth, filter, pathList));
    }

    private Stream<Path> addAndGetDirectoryStream(List<Path> pathList, Stream<Path> pathStream,
            Predicate<Path> filter) {
        return pathStream.filter(filter).peek(pathList::add).filter(directoryAndNotSymbolicLinkFilter);
    }

    private void drillDown(Path directoryPath, int maxDepth, List<Path> pathList, Predicate<Path> filter) {
        if (maxDepth < 1)
            return;
        addAndGetDirectoryStream(pathList, getChildrenPathStream(directoryPath), filter)
                .forEach(getDrillDownFunction(maxDepth, filter, pathList));
    }

    private Consumer<Path> getDrillDownFunction(int maxDepth, Predicate<Path> filter, List<Path> pathList) {
        return path -> drillDown(path, maxDepth - 1, pathList, filter);
    }

    /**
     * Consume path list.
     *
     * @param isParallel the is parallel
     * @param pathList   the path list
     * @param consumer   the consumer
     */
    public void consumePathList(boolean isParallel, List<Path> pathList, Consumer<Path> consumer) {
        JMStream.buildStream(isParallel, pathList).forEach(consumer);
    }

    /**
     * Consume path list.
     *
     * @param pathList the path list
     * @param consumer the consumer
     */
    public void consumePathList(List<Path> pathList, Consumer<Path> consumer) {
        for (Path path : pathList)
            consumer.accept(path);
    }

    /**
     * Consume sub file paths.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @param consumer           the consumer
     */
    public void consumeSubFilePaths(Path startDirectoryPath, int maxDepth, Predicate<Path> filter,
            Consumer<Path> consumer) {
        consumePathList(true, getSubFilePathList(startDirectoryPath, maxDepth, filter), consumer);
    }

    /**
     * Consume sub file paths.
     *
     * @param startDirectoryPath the start directory path
     * @param consumer           the consumer
     */
    public void consumeSubFilePaths(Path startDirectoryPath, Consumer<Path> consumer) {
        consumeSubFilePaths(startDirectoryPath, Integer.MAX_VALUE, getTrue(), consumer);
    }

    /**
     * Consume sub file paths.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param consumer           the consumer
     */
    public void consumeSubFilePaths(Path startDirectoryPath, int maxDepth, Consumer<Path> consumer) {
        consumeSubFilePaths(startDirectoryPath, maxDepth, getTrue(), consumer);
    }

    /**
     * Consume sub file paths.
     *
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @param consumer           the consumer
     */
    public void consumeSubFilePaths(Path startDirectoryPath, Predicate<Path> filter, Consumer<Path> consumer) {
        consumeSubFilePaths(startDirectoryPath, Integer.MAX_VALUE, filter, consumer);
    }

    /**
     * Apply path list and get result list list.
     *
     * @param <R>        the type parameter
     * @param isParallel the is parallel
     * @param pathList   the path list
     * @param function   the function
     * @return the list
     */
    public <R> List<R> applyPathListAndGetResultList(boolean isParallel, List<Path> pathList,
            Function<Path, R> function) {
        return JMStream.buildStream(isParallel, pathList).map(function).collect(toList());
    }

    /**
     * Apply path list and get result list list.
     *
     * @param <R>      the type parameter
     * @param pathList the path list
     * @param function the function
     * @return the list
     */
    public <R> List<R> applyPathListAndGetResultList(List<Path> pathList, Function<Path, R> function) {
        return pathList.parallelStream().map(function).collect(toList());
    }

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @param function           the function
     * @param isParallel         the is parallel
     * @return the list
     */
    public <R> List<R> applySubFilePathsAndGetAppliedList(Path startDirectoryPath, int maxDepth, Predicate<Path> filter,
            Function<Path, R> function, boolean isParallel) {
        return applyPathListAndGetResultList(isParallel, getSubFilePathList(startDirectoryPath, maxDepth, filter),
                function);
    }

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @param function           the function
     * @return the list
     */
    public <R> List<R> applySubFilePathsAndGetAppliedList(Path startDirectoryPath, int maxDepth, Predicate<Path> filter,
            Function<Path, R> function) {
        return applySubFilePathsAndGetAppliedList(startDirectoryPath, maxDepth, filter, function, true);
    }

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param function           the function
     * @return the list
     */
    public <R> List<R> applySubFilePathsAndGetAppliedList(Path startDirectoryPath, Function<Path, R> function) {
        return applySubFilePathsAndGetAppliedList(startDirectoryPath, Integer.MAX_VALUE, getTrue(), function);
    }

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param function           the function
     * @return the list
     */
    public <R> List<R> applySubFilePathsAndGetAppliedList(Path startDirectoryPath, int maxDepth,
            Function<Path, R> function) {
        return applySubFilePathsAndGetAppliedList(startDirectoryPath, maxDepth, getTrue(), function);
    }

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @param function           the function
     * @return the list
     */
    public <R> List<R> applySubFilePathsAndGetAppliedList(Path startDirectoryPath, Predicate<Path> filter,
            Function<Path, R> function) {
        return applySubFilePathsAndGetAppliedList(startDirectoryPath, Integer.MAX_VALUE, filter, function);
    }

    /**
     * Gets file path extension as opt.
     *
     * @param path the path
     * @return the file path extension as opt
     */
    public Optional<String> getFilePathExtensionAsOpt(Path path) {
        return JMOptional.getNullableAndFilteredOptional(path, regularFileFilter).map(this::getLastName)
                .map(JMString::getExtension).filter(Predicate.not(String::isEmpty));
    }

    /**
     * Gets last name.
     *
     * @param path the path
     * @return the last name
     */
    public String getLastName(Path path) {
        return path.toFile().getName();
    }

    /**
     * Gets path name in os.
     *
     * @param path the path
     * @return the path name in os
     */
    public String getPathNameInOS(Path path) {
        return getJMFile().getFileName(path.toFile());
    }

    /**
     * Gets last modified.
     *
     * @param path the path
     * @return the last modified
     */
    public long getLastModified(Path path) {
        return path.toFile().lastModified();
    }

    /**
     * Gets path type description.
     *
     * @param path the path
     * @return the path type description
     */
    public String getPathTypeDescription(Path path) {
        return getJMFile().getFileTypeDescription(path.toFile());
    }

    /**
     * Gets size.
     *
     * @param path the path
     * @return the size
     */
    public long getSize(Path path) {
        return path.toFile().length();
    }

    /**
     * Gets sub files count.
     *
     * @param dirPath the dir path
     * @return the sub files count
     */
    public int getSubFilesCount(Path dirPath) {
        return getSubFilePathList(dirPath).size();
    }

    /**
     * Gets sub directories count.
     *
     * @param dirPath the dir path
     * @return the sub directories count
     */
    public int getSubDirectoriesCount(Path dirPath) {
        return getSubDirectoryPathList(dirPath).size();
    }

    /**
     * Gets sub paths count.
     *
     * @param dirPath the dir path
     * @return the sub paths count
     */
    public int getSubPathsCount(Path dirPath) {
        return getSubPathList(dirPath).size();
    }

    /**
     * Extract sub path path.
     *
     * @param basePath   the base path
     * @param sourcePath the source path
     * @return the path
     */
    public Path extractSubPath(Path basePath, Path sourcePath) {
        return sourcePath.subpath(basePath.getNameCount() - 1, sourcePath.getNameCount());
    }

    /**
     * Build relative destination path path.
     *
     * @param destinationDirPath the destination dir path
     * @param baseDirPath        the base dir path
     * @param sourcePath         the source path
     * @return the path
     */
    public Path buildRelativeDestinationPath(Path destinationDirPath, Path baseDirPath, Path sourcePath) {
        return destinationDirPath.resolve(extractSubPath(baseDirPath, sourcePath));
    }

    /**
     * Build path string.
     *
     * @param strings the strings
     * @return the string
     */
    public String buildPath(String... strings) {
        return JMString.joiningWith(OS.getFileSeparator(), strings);
    }

    /**
     * Build buffered append writer writer.
     *
     * @param path    the path
     * @param charset the charset
     * @return the writer
     */
    public Writer buildBufferedAppendWriter(Path path, Charset charset) {
        try {
            if (notExists(path))
                createFileWithParentDirectories(path);
            return Files.newBufferedWriter(path, charset, StandardOpenOption.APPEND);
        } catch (IOException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "buildBufferedAppendWriter", path, charset);
        }
    }

    /**
     * Build buffered append writer writer.
     *
     * @param path the path
     * @return the writer
     */
    public Writer buildBufferedAppendWriter(Path path) {
        return buildBufferedAppendWriter(path, StandardCharsets.UTF_8);
    }

    /**
     * Read string string.
     *
     * @param targetPath the target path
     * @return the string
     */
    public String readString(Path targetPath) {
        try {
            return Files.readString(targetPath);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "readString", targetPath);
        }
    }

    /**
     * Copy path.
     *
     * @param sourcePath      the source path
     * @param destinationPath the destination path
     * @param options         the options
     * @return the path
     */
    public Path copy(Path sourcePath, Path destinationPath, CopyOption... options) {
        return operate(sourcePath, destinationPath, "copy", finalPath -> {
            try {
                return Files.copy(sourcePath, finalPath, options);
            } catch (Exception e) {
                return JMException.handleExceptionAndReturnNull(log, e, "copy", sourcePath, destinationPath, options);
            }
        });
    }

    /**
     * Move path.
     *
     * @param sourcePath      the source path
     * @param destinationPath the destination path
     * @param options         the options
     * @return the path
     */
    public Path move(Path sourcePath, Path destinationPath, CopyOption... options) {
        return operate(sourcePath, destinationPath, "move", finalPath -> {
            try {
                return Files.move(sourcePath, finalPath, options);
            } catch (Exception e) {
                return JMException.handleExceptionAndReturnNull(log, e, "move", sourcePath, destinationPath, options);
            }
        });
    }

    /**
     * Move dir optional.
     *
     * @param sourceDirPath      the source dir path
     * @param destinationDirPath the destination dir path
     * @param options            the options
     * @return the optional
     */
    public Optional<Path> moveDir(Path sourceDirPath, Path destinationDirPath, CopyOption... options) {
        return operateDir(sourceDirPath, destinationDirPath, sourceDirPath,
                path -> move(path, destinationDirPath, options));
    }

    private Path operate(Path sourcePath, Path destinationPath, String method,
            Function<Path, Path> resultPathFunction) {
        debug(log, method, sourcePath, destinationPath);
        Path finalPath = isDirectory(destinationPath) ? destinationPath
                .resolve(sourcePath.getFileName()) : buildParentAndDestinationPath(destinationPath);
        if (sourcePath.equals(finalPath))
            throw new RuntimeException("Already Exist !!!");
        return resultPathFunction.apply(finalPath);
    }

    private Path buildParentAndDestinationPath(Path destinationPath) {
        makeSureParentsDirPath(destinationPath.getParent());
        return destinationPath;
    }

    private <T, R> Optional<R> operateDir(Path sourceDirPath, Path destinationDirPath, T target,
            Function<T, R> operateDirFunction) {
        makeSureParentsDirPath(destinationDirPath);
        return Optional.ofNullable(isDirectory(sourceDirPath) && isDirectory(destinationDirPath) ? operateDirFunction
                .apply(target) : null);
    }

    private void makeSureParentsDirPath(Path destinationDirPath) {
        if (!exists(destinationDirPath))
            createDirectories(destinationDirPath);
    }

    /**
     * Delete boolean.
     *
     * @param targetPath the target path
     * @return the boolean
     */
    public boolean delete(Path targetPath) {
        debug(log, "delete", targetPath);
        try {
            Files.delete(targetPath);
            return true;
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnFalse(log, e,
                    "delete", targetPath);
        }
    }

    /**
     * Delete all boolean.
     *
     * @param targetPath the target path
     * @return the boolean
     */
    public boolean deleteAll(Path targetPath) {
        debug(log, "deleteAll", targetPath);
        return isDirectory(targetPath) ? deleteDir(targetPath) : delete(targetPath);
    }

    /**
     * Delete dir boolean.
     *
     * @param targetDirPath the target dir path
     * @return the boolean
     */
    public boolean deleteDir(Path targetDirPath) {
        debug(log, "deleteDir", targetDirPath);
        List<Path> subPathList = getSubPathList(targetDirPath);
        Collections.reverse(subPathList);
        return deleteBulkThenFalseList(subPathList).size() == 0 && delete(targetDirPath);
    }

    /**
     * Delete bulk then false list list.
     *
     * @param bulkPathList the bulk path list
     * @return the list
     */
    public List<Path> deleteBulkThenFalseList(List<Path> bulkPathList) {
        debug(log, "deleteBulkThenFalseList", bulkPathList);
        return bulkPathList.stream().filter(Predicate.not(this::deleteAll)).collect(toList());
    }

    /**
     * Delete on exit path.
     *
     * @param path the path
     * @return the path
     */
    public Path deleteOnExit(Path path) {
        debug(log, "deleteOnExit", path);
        path.toFile().deleteOnExit();
        return path;
    }

    /**
     * Create temp file path as opt optional.
     *
     * @param path the path
     * @return the optional
     */
    public Optional<Path> createTempFilePathAsOpt(Path path) {
        debug(log, "createTempFilePathAsOpt", path);
        String[] prefixSuffix = JMFile.getInstance().getPrefixSuffix(path.toFile());
        try {
            return Optional.of(Files.createTempFile(prefixSuffix[0], prefixSuffix[1])).filter(existFilter)
                    .map(this::deleteOnExit);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnEmptyOptional(log, e, "createTempFilePathAsOpt", path);
        }
    }

    /**
     * Create temp dir path as opt optional.
     *
     * @param path the path
     * @return the optional
     */
    public Optional<Path> createTempDirPathAsOpt(Path path) {
        debug(log, "createTempDirPathAsOpt", path);
        try {
            return Optional.of(Files.createTempDirectory(path.toString())).filter(existFilter).map(this::deleteOnExit);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnEmptyOptional(log, e, "createTempDirPathAsOpt", path);
        }
    }

    /**
     * Create file optional.
     *
     * @param path  the path
     * @param attrs the attrs
     * @return the optional
     */
    public Optional<Path> createFile(Path path, FileAttribute<?>... attrs) {
        debug(log, "createFile", path, attrs);
        try {
            return Optional.of(Files.createFile(path, attrs));
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnEmptyOptional(log, e, "createFile", path);
        }
    }

    /**
     * Create directories optional.
     *
     * @param dirPath the dir path
     * @param attrs   the attrs
     * @return the optional
     */
    public Optional<Path> createDirectories(Path dirPath, FileAttribute<?>... attrs) {
        debug(log, "createDirectories", dirPath, attrs);
        try {
            return Optional.of(Files.createDirectories(dirPath, attrs));
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnEmptyOptional(log, e, "createDirectories", dirPath);
        }
    }

    /**
     * Create directory optional.
     *
     * @param dirPath the dir path
     * @param attrs   the attrs
     * @return the optional
     */
    public Optional<Path> createDirectory(Path dirPath, FileAttribute<?>... attrs) {
        debug(log, "createDirectory", dirPath, attrs);
        try {
            return Optional.of(Files.createDirectory(dirPath, attrs));
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnEmptyOptional(log, e, "createDirectory", dirPath);
        }
    }

    /**
     * Create file with parent directories.
     *
     * @param path  the path
     * @param attrs the attrs
     */
    public void createFileWithParentDirectories(Path path, FileAttribute<?>... attrs) {
        createDirectories(path.getParent(), attrs);
        createFile(path, attrs);
    }

    /**
     * Delete dir on exist boolean.
     *
     * @param dirPath the dir path
     * @return the boolean
     */
    public boolean deleteDirOnExist(Path dirPath) {
        return exists(dirPath) && deleteDir(dirPath);
    }

    /**
     * Read string string.
     *
     * @param targetPath  the target path
     * @param charsetName the charset name
     * @return the string
     */
    public String readString(Path targetPath, String charsetName) {
        try {
            return Files.readString(targetPath, Charset.forName(charsetName));
        } catch (IOException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "readString", charsetName);
        }
    }

    /**
     * Read lines list.
     *
     * @param targetPath the target path
     * @return the list
     */
    public List<String> readLines(Path targetPath) {
        try {
            return Files.readAllLines(targetPath);
        } catch (IOException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "readLines", targetPath);
        }
    }

    /**
     * Read lines list.
     *
     * @param targetPath  the target path
     * @param charsetName the charset name
     * @return the list
     */
    public List<String> readLines(Path targetPath, String charsetName) {
        try {
            return Files.readAllLines(targetPath, Charset.forName(charsetName));
        } catch (IOException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "readLines", targetPath, charsetName);
        }
    }

    /**
     * Gets line stream.
     *
     * @param path the path
     * @return the line stream
     */
    public Stream<String> getLineStream(Path path) {
        return getLineStream(path, null);
    }

    /**
     * Gets line stream.
     *
     * @param path    the path
     * @param charset the charset
     * @return the line stream
     */
    public Stream<String> getLineStream(Path path, Charset charset) {
        try {
            return charset == null ? Files.lines(path) : Files.lines(path, charset);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturn(log, e, "getLineStream", Stream::empty, path, charset);
        }
    }
}