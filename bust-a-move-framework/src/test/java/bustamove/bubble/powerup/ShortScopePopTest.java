package bustamove.bubble.powerup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import bustamove.bubble.SimpleBubble;
import bustamove.bubble.Bubble;
import bustamove.game.GameData;
import bustamove.screen.config.GameConfig;

import static org.junit.Assert.*;

/**
 * Created by Calvin Nhieu on 10/13/2016.
 */
@RunWith(Parameterized.class)
public class ShortScopePopTest {
    private Bubble bubble;
    private int initialCannonLength;
    private int expectedLength;
    private String message;

    @Before
    public void setUp() throws Exception {
        bubble = new SimpleBubble(200, 200, SimpleBubble.ColorChoice.BLUE, false);
        bubble = new ShortScope(bubble);
        bubble.setGameHead(new GameData(0, 1));
        bubble.getGameHead().getCannon().setLength(initialCannonLength);
        bubble.pop();
    }

    public ShortScopePopTest(int initial, int expected, String msg) {
        initialCannonLength = initial;
        expectedLength = expected;
        message = msg;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
            {
                GameConfig.DEFAULT_CANNON_LENGTH,
                GameConfig.DEFAULT_CANNON_LENGTH - GameConfig.CANNON_LENGTH_INCREMENT,
                "Should decrement Cannon length by CANNON_LENGTH_INCREMENT."
            },
            {
                GameConfig.MIN_CANNON_LENGTH,
                GameConfig.MIN_CANNON_LENGTH,
                "Should not decrement Cannon length lesser than MIN_CANNON_LENGTH."
            }
        });
    }

    @Test
    public void popCallCannonLengthTest() throws Exception {
        assertEquals(message, expectedLength,
            bubble.getGameHead().getCannon().getLength());
    }
}
