package kr.jm.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JMCollectionsTest {

    @Test
    public void zip() {
        Assert.assertEquals("[[1, 4], [2, 5], [3, 6]]",
                JMCollections.zip(List.of(1, 2, 3), List.of(4, 5, 6)).toString());
        Assert.assertEquals("[[1, 4, 7], [2, 5, 8], [3, 6]]",
                JMCollections.zip(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8)).toString());
        Assert.assertEquals("[[1, 4, 7], [5, 8], [6]]",
                JMCollections.zip(List.of(1), List.of(4, 5, 6), List.of(7, 8)).toString());
    }

    @Test
    public void generatePermutations() {
        Assert.assertEquals("[[1, 4, 4], [1, 4, 5], [1, 5, 4], [1, 5, 5], [2, 4, 4], [2, 4, 5], [2, 5, 4], [2, 5, 5]]",
                JMCollections.generatePermutations(List.of(1, 2), List.of(4, 5), List.of(4, 5)).toString());
        Assert.assertEquals(
                "[[1, 4, 7], [1, 4, 8], [1, 5, 7], [1, 5, 8], [1, 6, 7], [1, 6, 8], [2, 4, 7], [2, 4, 8], [2, 5, 7], [2, 5, 8], [2, 6, 7], [2, 6, 8], [3, 4, 7], [3, 4, 8], [3, 5, 7], [3, 5, 8], [3, 6, 7], [3, 6, 8]]",
                JMCollections.generatePermutations(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8)).toString());
        Assert.assertEquals("[[1, 4, 7], [1, 4, 8], [1, 5, 7], [1, 5, 8], [1, 6, 7], [1, 6, 8]]",
                JMCollections.generatePermutations(List.of(1), List.of(4, 5, 6), List.of(7, 8)).toString());
    }
}