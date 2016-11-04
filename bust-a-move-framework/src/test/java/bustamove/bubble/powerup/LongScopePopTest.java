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
import bustamove.game.GameData.GameDifficulty;
import bustamove.screen.config.GameConfig;

import static org.junit.Assert.*;

/**
 * Created by Calvin Nhieu on 10/13/2016.
 */
@RunWith(Parameterized.class)
public class LongScopePopTest {
    private Bubble bubble;
    private int initialCannonLength;
    private int expectedLength;
    private String message;

    @Before
    public void setUp() throws Exception {
        bubble = new SimpleBubble(200, 200, SimpleBubble.ColorChoice.BLUE, false);
        bubble = new LongScope(bubble);
        bubble.setGameHead(new GameData(0, 1, GameDifficulty.NORMAL));
        bubble.getGameHead().getCannon().setLength(initialCannonLength);
        bubble.pop();
    }

    public LongScopePopTest(int initial, int expected, String msg) {
        initialCannonLength = initial;
        expectedLength = expected;
        message = msg;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
            {
                GameConfig.DEFAULT_CANNON_LENGTH,
                GameConfig.DEFAULT_CANNON_LENGTH + GameConfig.CANNON_LENGTH_INCREMENT,
                "Should increment Cannon length by CANNON_LENGTH_INCREMENT."
            },
            {
                GameConfig.MAX_CANNON_LENGTH,
                GameConfig.MAX_CANNON_LENGTH,
                "Should not increment Cannon length greater than MAX_CANNON_LENGTH."
            }
        });
    }

    @Test
    public void popCallCannonLengthTest() throws Exception {
        assertEquals(message, expectedLength,
            bubble.getGameHead().getCannon().getLength());
    }
}
