package kr.jm.utils.helper;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * The type Jm word splitter.
 */
public class JMWordSplitter {

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMWordSplitter getInstance() {
        return JMWordSplitter.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMWordSplitter INSTANCE = new JMWordSplitter();
    }

    /**
     * Instantiates a new Jm word splitter.
     */
    public JMWordSplitter() {
        splitPattern = Pattern.compile(DEFAULT_SPLIT_WORD_REGEX);
    }

    /**
     * Instantiates a new Jm word splitter.
     *
     * @param splitWordRegex the split word regex
     */
    public JMWordSplitter(String splitWordRegex) {
        splitPattern = Pattern.compile(splitWordRegex);
    }

    /**
     * The constant DEFAULT_SPLIT_WORD_REGEX.
     */
    public static final String DEFAULT_SPLIT_WORD_REGEX = "\\W+";
    private Pattern splitPattern;

    /**
     * Gets split pattern.
     *
     * @return the split pattern
     */
    public Pattern getSplitPattern() {
        return splitPattern;
    }

    /**
     * Split as stream stream.
     *
     * @param splitPattern the split pattern
     * @param text         the text
     * @return the stream
     */
    public Stream<String> splitAsStream(Pattern splitPattern, String text) {
        return splitPattern.splitAsStream(text);
    }

    /**
     * Split as list list.
     *
     * @param splitPattern the split pattern
     * @param text         the text
     * @return the list
     */
    public List<String> splitAsList(Pattern splitPattern, String text) {
        return splitAsStream(splitPattern, text).collect(toList());
    }

    /**
     * Split string [ ].
     *
     * @param splitPattern the split pattern
     * @param text         the text
     * @return the string [ ]
     */
    public String[] split(Pattern splitPattern, String text) {
        return splitPattern.split(text);
    }

    /**
     * Split as stream stream.
     *
     * @param text the text
     * @return the stream
     */
    public Stream<String> splitAsStream(String text) {
        return splitAsStream(splitPattern, text);
    }

    /**
     * Split as list list.
     *
     * @param text the text
     * @return the list
     */
    public List<String> splitAsList(String text) {
        return splitAsList(splitPattern, text);
    }

    /**
     * Split string [ ].
     *
     * @param text the text
     * @return the string [ ]
     */
    public String[] split(String text) {
        return split(splitPattern, text);
    }

}
