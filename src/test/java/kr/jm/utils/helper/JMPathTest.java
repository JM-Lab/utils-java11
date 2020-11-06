package kr.jm.utils.helper;

import kr.jm.utils.JMCollections;
import kr.jm.utils.JMStream;
import kr.jm.utils.enums.OS;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.*;

public class JMPathTest {

    private JMPath jmPath;

    @Before
    public void setUp() throws Exception {
        this.jmPath = JMPath.getInstance();
    }

    @Test
    public void testGetRootPaths() {
        jmPath.getRootPathStream().forEach(System.out::println);
        JMStream.buildStream(getRoot().getFileSystem().getRootDirectories()).flatMap(jmPath::getChildrenPathStream)
                .forEach(System.out::println);
    }

    private Path getRoot() {
        return jmPath.getPath("/");
    }

    @Test
    public void testGetPath() {
        System.out.println(jmPath.getChildDirectoryPathStream(jmPath.getPath("[/]")).count());
        System.out.println(jmPath.getPath("~/"));
        System.out.println(Paths.get("~"));
        System.out.println(jmPath.getPath(OS.getUserHomeDir()));
    }

    @Test
    public void testGetCurrentPath() {
        System.out.println(jmPath.getCurrentPath());
    }

    @Test
    public void testGetUserHome() {
        System.out.println(jmPath.getUserHome());
    }

    @Test
    public void testGetRootDirectories() {
        jmPath.getRootPathStream()
                .forEach(path -> jmPath.getChildrenPathStream(path).forEach(p -> System.out.println(p)));
    }

    @Test
    public void testGetFileStorePathList() {
        System.out.println(jmPath.getFileStorePathList());
        jmPath.getFileStorePathList().stream().map(jmPath::getChildrenPathStream).forEach(System.out::println);
    }

    @Test
    public void testGetChildFilePathStreamOptionalPath() {
        System.out.println(jmPath.getRootDirectoryStream());
        jmPath.getRootDirectoryStream()
                .forEach(path -> jmPath.getChildFilePathStream(path).forEach(p -> System.out.print(p + " ")));
        System.out.println();
        jmPath.getRootDirectoryStream().forEach(
                path -> jmPath.getChildFilePathStream(path, jmPath.getHiddenFilter().negate())
                        .forEach(p -> System.out.print(p + " ")));
    }

    @Test
    public void testGetChildDirectoryPathStreamOptional() {
        System.out.println(jmPath.getRootDirectoryStream());
        jmPath.getRootDirectoryStream()
                .forEach(path -> jmPath.getChildDirectoryPathStream(path).forEach(p -> System.out.print(p + " ")));
        System.out.println();
        jmPath.getRootDirectoryStream().forEach(
                path -> jmPath.getChildDirectoryPathStream(path, jmPath.getHiddenFilter().negate())
                        .forEach(p -> System.out.print(p + " ")));
    }

    @Test
    public void testGetChildrenPathStreamOptionalPath() {
        System.out.println(jmPath.getRootDirectoryStream());
        List<String> rootChildPaths = jmPath.getRootDirectoryStream().flatMap(path -> jmPath.getChildrenPathStream(path)
                .filter(jmPath.getDirectoryAndNotSymbolicLinkFilter().or(jmPath.getRegularFileFilter())))
                .map(Path::toAbsolutePath).map(Path::toString).collect(toList());
        System.out.println(rootChildPaths);
        List<String> rootChildFilePaths =
                jmPath.getRootDirectoryStream().flatMap(path -> jmPath.getChildFilePathStream(path))
                        .map(Path::toAbsolutePath).map(Path::toString).collect(toList());
        System.out.println(rootChildFilePaths);
        List<String> rootChildDirPaths =
                jmPath.getRootDirectoryStream().flatMap(path -> jmPath.getChildDirectoryPathStream(path))
                        .map(Path::toAbsolutePath).map(Path::toString).collect(toList());

        System.out.println(rootChildDirPaths);

        assertEquals(rootChildPaths.size(), rootChildDirPaths.size() + rootChildFilePaths.size());
        assertEquals(JMCollections.sort(rootChildPaths).toString(),
                Stream.concat(rootChildDirPaths.stream(), rootChildFilePaths.stream()).sorted().collect(toList())
                        .toString());
    }

    @Test
    public void testGetSubPathsStreamOptionalPath() throws Exception {
        Path startDirectoryPath = getRoot();
        long orElse = jmPath.getChildrenPathStream(startDirectoryPath).count();
        long count = Files.walk(startDirectoryPath, 1).skip(1).count();
        int size = jmPath.getSubPathList(startDirectoryPath, 1).size();
        System.out.println(orElse + " " + count + " " + size);
        assertEquals(count, orElse);
        assertEquals(count, size);
        List<Path> list = jmPath.getSubFilePathList(startDirectoryPath, 3);
        System.out.println(list.size());
    }

    @Ignore
    @Test
    public void testConsumeSubFilePaths() {
        Path startDirectoryPath = jmPath.getUserHome();
        List<Path> list = Collections.synchronizedList(new ArrayList<>());
        jmPath.consumeSubFilePaths(startDirectoryPath, path -> path.toString().contains(".png"), list::add);
    }

    @Test
    public void testConsumeSubFilePathsAndGetAppliedList() {
        Path startDirectoryPath = getRoot();
        List<Path> consumedList = jmPath.applySubFilePathsAndGetAppliedList(
                startDirectoryPath, 3, path -> path.toString().contains(".png"), path -> startDirectoryPath);
        System.out.println(consumedList);
    }

    @Test
    public void testConsumeSubFilePathsAndGetAppliedList2() {
        Path startDirectoryPath = jmPath.getCurrentPath();
        System.out.println(
                jmPath.applySubFilePathsAndGetAppliedList(startDirectoryPath, p -> true, Function.identity()).size());
    }

    @Test
    public void testGetLastName() {
        Path path = jmPath.getPath("/09A0F357-E206-444C-83CA-0475947F8718/Data/7/3/Attachments/37857/2/Mail 첨부 파일.png");
        assertEquals("Mail 첨부 파일.png", jmPath.getLastName(path));
    }

    @Test
    public void testPathInfo() {
        Path path = jmPath.getPath("/dev");
        System.out.println(jmPath.getDirectoryFilter().test(path));
        System.out.println(jmPath.getExistFilter().test(path));
    }

    @Test
    public void testGetAncestorPathList() {
        System.out.println(jmPath.getAncestorPathList(jmPath.getUserHome()));
        Path path = jmPath.getPath("$jlkaj");
        System.out.println(path);
        System.out.println(jmPath.exists(path));
        List<Path> ancestorPathList = jmPath.getAncestorPathList(path);
        System.out.println(ancestorPathList);
        assertEquals(path.getRoot(), ancestorPathList.get(0));
        assertEquals(path.getParent(), ancestorPathList.get(ancestorPathList.size() - 1));
    }

    @Test
    public void testDelete() throws Exception {
        Path startDirectoryPath = Paths.get("test");
        Path d1Path = startDirectoryPath.resolve("d1");
        Path d2Path = d1Path.resolve("d2");
        Files.createDirectories(d2Path);
        System.out.println(jmPath.getSubPathList(startDirectoryPath));
        assertTrue(!jmPath.delete(startDirectoryPath));
        assertTrue(jmPath.exists(startDirectoryPath));
        assertEquals(0,
                jmPath.deleteBulkThenFalseList(JMCollections.getReversed(jmPath.getSubPathList(startDirectoryPath)))
                        .size());
        System.out.println(jmPath.getSubPathList(startDirectoryPath));
        assertTrue(jmPath.exists(startDirectoryPath));
        Files.createDirectories(d2Path);
        Files.createFile(startDirectoryPath.resolve("test.file"));
        Files.createFile(d1Path.resolve("test.file"));
        Files.createFile(d2Path.resolve("test.file"));
        System.out.println(jmPath.getSubPathList(startDirectoryPath));
        jmPath.deleteDir(startDirectoryPath);
        assertTrue(!jmPath.exists(startDirectoryPath));
        System.out.println(jmPath.getSubPathList(startDirectoryPath));

        Files.createDirectories(d2Path);
        Files.createFile(startDirectoryPath.resolve("test.file"));
        Files.createFile(d1Path.resolve("test.file"));
        Files.createFile(d2Path.resolve("test.file"));
        assertFalse(jmPath.delete(startDirectoryPath));
        assertTrue(jmPath.deleteDir(startDirectoryPath));
    }

    @Test
    public void testCopy() throws Exception {
        Path startDirectoryPath = Paths.get("test");
        jmPath.deleteDir(startDirectoryPath);
        Path d1Path = startDirectoryPath.resolve("d1");
        Path d2Path = d1Path.resolve("d2");
        Files.createDirectories(d2Path);
        Path rootFilePath = startDirectoryPath.resolve("test.file");
        Files.createFile(rootFilePath);
        Path d1FilePath = d1Path.resolve("test.file");
        Files.createFile(d1FilePath);
        Path d2FilePath = d2Path.resolve("test.file");

        Files.createFile(d2FilePath);
        List<Path> subPathList = jmPath.getSubPathList(startDirectoryPath);
        System.out.println(subPathList);
        assertTrue(jmPath.copy(d1FilePath, d2Path) == null);
        jmPath.copy(d1FilePath, d2Path.resolve("test2.file"));
        System.out.println(subPathList);
        int size = subPathList.size();
        List<Path> subPathList2 = jmPath.getSubPathList(d1Path);
        int size2 = subPathList2.size();
        System.out.println(jmPath.getSubPathList(startDirectoryPath));
        jmPath.deleteDir(startDirectoryPath);

    }

    @Test
    public void testMove() throws Exception {
        Path startDirectoryPath = Paths.get("test");
        jmPath.deleteDir(startDirectoryPath);
        Path d1Path = startDirectoryPath.resolve("d1");
        Path d2Path = d1Path.resolve("d2");
        Files.createDirectories(d2Path);
        Path rootFilePath = startDirectoryPath.resolve("test.file");
        Files.createFile(rootFilePath);
        Path d1FilePath = d1Path.resolve("test.file");
        Files.createFile(d1FilePath);
        Path d2FilePath = d2Path.resolve("test.file");
        Files.createFile(d2FilePath);
        List<Path> subPathList = jmPath.getSubPathList(startDirectoryPath);
        System.out.println(subPathList);
        assertTrue(jmPath.move(d1FilePath, d2Path) == null);
        jmPath.move(d1FilePath, d2Path.resolve("test2.file"));
        assertTrue(!jmPath.exists(d1FilePath));
        System.out.println(subPathList);
        int size = subPathList.size();
        System.out.println(jmPath.getSubPathList(startDirectoryPath));
        Path copyd1 = startDirectoryPath.resolve("copyd1");
        jmPath.moveDir(d1Path, copyd1);
        subPathList = jmPath.getSubPathList(startDirectoryPath);
        System.out.println(subPathList);
        assertEquals(subPathList.size(), size + 1);
        List<Path> subPathList2 =
                jmPath.getSubPathList(jmPath.move(copyd1, d2Path));
        System.out.println(subPathList2);
        System.out.println(jmPath.getSubPathList(d1Path));
        assertEquals(5, jmPath.getSubPathList(d1Path).size());
        System.out.println(jmPath.getSubPathList(startDirectoryPath));
        jmPath.deleteDir(startDirectoryPath);
    }
}
