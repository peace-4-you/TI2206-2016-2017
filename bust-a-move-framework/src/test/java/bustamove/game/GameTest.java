package bustamove.game;

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
 * Unit testing for the game class
 * Created by Winer Bao on Octorber 14th 2016.
 */
@RunWith(Parameterized.class)
public class GameTest {
    private enum Type {
        SINGLETON
    }

    private Type type;
    private Object expectedResult;
    private String message;

    public GameTest(Type t, Object expected, String msg) {
        type = t;
        expectedResult = expected;
        message = msg;
    }

    @Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][]{
                {Type.SINGLETON, Game.getInstance(), "Should return the same instance"},
        });
    }

    @Test
    public void bubbleSingleton() {
        try {
            Assume.assumeTrue(type == Type.SINGLETON);
            assertEquals(message, expectedResult, Game.getInstance());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }
}
