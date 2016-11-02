package bustamove.sound;

import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import bustamove.system.SoundHandler;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Unit testing for the SoundHandler class
 * Created by Winer Bao on Octorber 20th 2016.
 */
@RunWith(Parameterized.class)
public class SoundHandlerTest {
    private enum Type {
        SINGLETON
    }

    private Type type;
    private Object expectedResult;
    private String message;

    public SoundHandlerTest(Type t, Object expected, String msg) {
        type = t;
        expectedResult = expected;
        message = msg;
    }

    @Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][]{
                {Type.SINGLETON, SoundHandler.getInstance(), "Should return the same instance"},
        });
    }

    @Test
    public void bubbleSingleton() {
        try {
            Assume.assumeTrue(type == Type.SINGLETON);
            assertEquals(message, expectedResult, SoundHandler.getInstance());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }
}
