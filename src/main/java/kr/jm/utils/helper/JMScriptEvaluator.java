package kr.jm.utils.helper;

import kr.jm.utils.exception.JMException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Objects;

/**
 * The type Jm script evaluator.
 */
public class JMScriptEvaluator {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JMScriptEvaluator.class);

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMScriptEvaluator getInstance() {
        return JMScriptEvaluator.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMScriptEvaluator INSTANCE = new JMScriptEvaluator();
    }

    private ScriptEngine scriptEngine;

    /**
     * Eval object.
     *
     * @param script the script
     * @return the object
     */
    public Object eval(String script) {
        try {
            return getScriptEngine().eval(script);
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnNull(log, e, "eval", script);
        }
    }

    private ScriptEngine getScriptEngine() {
        return Objects.requireNonNullElseGet(scriptEngine,
                () -> scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript"));
    }

}
