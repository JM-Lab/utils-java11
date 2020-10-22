package kr.jm.utils.helper;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JMFileTest {

    private JMFile jmFile;

    @Before
    public void setUp() throws Exception {
        this.jmFile = JMFile.getInstance();
    }

    @Test
    public void getRootFileList() {
        File file = new File("test" + System.currentTimeMillis() + "/a.txt");
        File parentDir = file.getParentFile();
        JMPath.getInstance().deleteAll(parentDir.toPath());
        System.out.println(parentDir.exists());
        assertFalse(parentDir.exists());
        assertTrue(jmFile.createEmptyFile(file));
        assertFalse(jmFile.createEmptyFile(file));
        JMPath.getInstance().deleteAll(parentDir.toPath());
        
        System.out.println(jmFile.getRootFileList());
    }

    @Test
    public void testGetFileStoreList() {
        System.out.println(jmFile.getFileStoreList());
        jmFile.getFileStoreList().stream()
                .forEach(fs -> System.out.print(fs.name() + ", "));
        System.out.println();
        jmFile.getFileStoreList().stream()
                .forEach(fs -> System.out.print(fs.type() + ", "));
        System.out.println();
        jmFile.getFileStoreList().stream()
                .forEach(fs -> System.out.print(fs.toString() + ", "));
        System.out.println();
        jmFile.getFileStoreList().stream().forEach(
                fs -> System.out.print(jmFile.getPath(fs.name()) + "," + " "));
    }
}