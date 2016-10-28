package bustamove.system;

import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Unit testing for the Log class
 * Created by Winer Bao on Octorber 20th 2016.
 */
@RunWith(Parameterized.class)
public class LogTest {
    private enum Type {
        SINGLETON
    }

    private Type type;
    private Object expectedResult;
    private String message;

    public LogTest(Type t, Object expected, String msg) {
        type = t;
        expectedResult = expected;
        message = msg;
    }

    @Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][]{
                {Type.SINGLETON, Log.getInstance(), "Should return the same instance"},
        });
    }

    @Test
    public void LogSingleton() {
        try {
            Assume.assumeTrue(type == Type.SINGLETON);
            assertEquals(message, expectedResult, Log.getInstance());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }
}