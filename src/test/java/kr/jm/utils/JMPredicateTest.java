package kr.jm.utils;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class JMPredicateTest {

    @Test
    public void negate() {
        String testString = "a";
        Optional.ofNullable(testString)
                .filter(JMPredicate.negate(string -> string.equals("")))
                .filter(JMPredicate.peekSOPL())
                .ifPresent(string -> assertEquals("a", string));
        Optional.ofNullable(testString).filter(JMPredicate.getBoolean(true))
                .filter(JMPredicate.peekSOPL())
                .ifPresent(string -> assertEquals("a", string));
    }

}
