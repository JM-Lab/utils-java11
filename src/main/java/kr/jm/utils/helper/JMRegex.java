package kr.jm.utils.helper;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.rangeClosed;

/**
 * The type Jm regex.
 */
public class JMRegex {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JMRegex.class);
    private List<String> groupNameList;
    private Pattern pattern;

    /**
     * Instantiates a new Jm regex.
     *
     * @param regex the regex
     */
    public JMRegex(String regex) {
        this(regex, -1);
    }

    /**
     * Instantiates a new Jm regex.
     *
     * @param regex the regex
     * @param flag  the flag
     */
    public JMRegex(String regex, int flag) {
        this.groupNameList = getMatchedPartList(getMatchedPartPattern().matcher(regex)).stream()
                .map(s -> s.substring(s.indexOf('<') + 1, s.indexOf('>'))).collect(toList());
        this.pattern = flag == -1 ? Pattern.compile(regex) : Pattern.compile(regex, flag);
    }

    private static class MatchedPartPattern {
        private static final Pattern MATCHED_PART_PATTERN = Pattern.compile("\\(\\?<\\w+>");
    }

    private static Pattern getMatchedPartPattern() {
        return MatchedPartPattern.MATCHED_PART_PATTERN;
    }

    /**
     * Gets pattern.
     *
     * @return the pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Gets group name list.
     *
     * @return the group name list
     */
    public List<String> getGroupNameList() {
        return Collections.unmodifiableList(groupNameList);
    }

    @Override
    public String toString() {
        return "JMRegex{" + "pattern=" + pattern + '}';
    }

    /**
     * Find boolean.
     *
     * @param targetString the target string
     * @return the boolean
     */
    public boolean find(String targetString) {
        return pattern.matcher(targetString).find();
    }


    /**
     * Matches boolean.
     *
     * @param targetString the target string
     * @return the boolean
     */
    public boolean matches(String targetString) {
        return pattern.matcher(targetString).matches();
    }

    /**
     * Gets matched part list.
     *
     * @param targetString the target string
     * @return the matched part list
     */
    public List<String> getMatchedPartList(String targetString) {
        return getMatchedPartList(pattern.matcher(targetString));
    }

    private List<String> getMatchedPartList(Matcher matcher) {
        List<String> matchedList = new ArrayList<>();
        while (matcher.find())
            matchedList.add(matcher.group());
        return matchedList;
    }

    /**
     * Gets matched list by group.
     *
     * @param targetString the target string
     * @return the matched list by group
     */
    public List<String> getMatchedListByGroup(String targetString) {
        return getMatcherOptional(targetString)
                .map(matcher -> rangeClosed(1, matcher.groupCount())
                        .mapToObj(matcher::group).map(Object::toString)
                        .collect(toList())).orElseGet(Collections::emptyList);
    }

    /**
     * Gets group name value map.
     *
     * @param targetString the target string
     * @return the group name value map
     */
    public Map<String, String> getGroupNameValueMap(String targetString) {
        return getMatcherOptional(targetString)
                .map(matcher -> groupNameList.stream().collect(Collectors.toMap(Function.identity(), matcher::group)))
                .orElseGet(Collections::emptyMap);
    }

    private Optional<Matcher> getMatcherOptional(String targetString) {
        Optional<Matcher> matcherOptional = Optional.of(pattern.matcher(targetString)).filter(Matcher::matches);
        if (matcherOptional.isEmpty()) {
            log.warn("Wrong Match Pattern Occur !!! - pattern = {}, targetString = {}", pattern.pattern(),
                    targetString);
            return Optional.empty();
        }
        return matcherOptional;
    }

}
