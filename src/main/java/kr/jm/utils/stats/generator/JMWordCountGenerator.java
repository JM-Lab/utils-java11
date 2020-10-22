package kr.jm.utils.stats.generator;

import kr.jm.utils.helper.JMPath;
import kr.jm.utils.helper.JMWordSplitter;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Jm word count generator.
 */
public class JMWordCountGenerator {

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMWordCountGenerator getInstance() {
        return JMWordCountGenerator.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMWordCountGenerator INSTANCE = new JMWordCountGenerator();
    }

    private JMWordCountGenerator() {
        splitPattern = Pattern.compile(JMWordSplitter.DEFAULT_SPLIT_WORD_REGEX);
    }

    private Pattern splitPattern;

    /**
     * Build count map map.
     *
     * @param pattern the pattern
     * @param path    the path
     * @return the map
     */
    public Map<String, Long> buildCountMap(Pattern pattern, Path path) {
        return buildCountMap(JMPath.getInstance().getLineStream(path).flatMap(pattern::splitAsStream));
    }

    /**
     * Build count map map.
     *
     * @param pattern the pattern
     * @param text    the text
     * @return the map
     */
    public Map<String, Long> buildCountMap(Pattern pattern, String text) {
        return buildCountMap(pattern.splitAsStream(text));
    }

    /**
     * Build count map map.
     *
     * @param wordList the word list
     * @return the map
     */
    public Map<String, Long> buildCountMap(List<String> wordList) {
        return buildCountMap(wordList.stream());
    }

    /**
     * Build count map map.
     *
     * @param wordStream the word stream
     * @return the map
     */
    public Map<String, Long> buildCountMap(Stream<String> wordStream) {
        return wordStream.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Build count map map.
     *
     * @param path the path
     * @return the map
     */
    public Map<String, Long> buildCountMap(Path path) {
        return buildCountMap(splitPattern, path);
    }

    /**
     * Build count map map.
     *
     * @param text the text
     * @return the map
     */
    public Map<String, Long> buildCountMap(String text) {
        return buildCountMap(splitPattern, text);
    }

}
