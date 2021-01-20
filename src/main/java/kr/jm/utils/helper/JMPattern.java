package kr.jm.utils.helper;

import kr.jm.utils.JMString;

import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.regex.Pattern;

/**
 * The type Jm pattern.
 */
public class JMPattern {

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMPattern getInstance() {
        return JMPattern.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMPattern INSTANCE = new JMPattern();
    }

    private Map<String, Pattern> patternCache;

    private Map<String, Pattern> getPatternCache() {
        return Optional.ofNullable(this.patternCache).orElseGet(() -> this.patternCache = new WeakHashMap<>());
    }

    /**
     * Gets number pattern.
     *
     * @return the number pattern
     */
    public Pattern getNumberPattern() {
        return compile(JMString.NumberPattern);
    }

    /**
     * Gets word pattern.
     *
     * @return the word pattern
     */
    public Pattern getWordPattern() {
        return compile(JMString.WordPattern);
    }

    /**
     * Compile pattern.
     *
     * @param regexString the regex string
     * @return the pattern
     */
    public Pattern compile(String regexString) {
        return getPatternCache().computeIfAbsent(regexString, Pattern::compile);
    }

    /**
     * Compile pattern.
     *
     * @param regexString the regex string
     * @param flags       the flags
     * @return the pattern
     */
    public Pattern compile(String regexString, int flags) {
        return getPatternCache().computeIfAbsent(regexString + flags, regex -> Pattern.compile(regex, flags));
    }

}
