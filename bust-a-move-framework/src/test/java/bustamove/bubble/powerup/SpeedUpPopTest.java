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
import bustamove.gamestate.GameConfig;

import static org.junit.Assert.*;

/**
 * Created by Calvin Nhieu on 10/13/2016.
 */
@RunWith(Parameterized.class)
public class SpeedUpPopTest {
    private Bubble bubble;
    private double initialSpeed;
    private double expectedSpeed;
    private String message;
    private double delta;

    @Before
    public void setUp() throws Exception {
        bubble = new SimpleBubble(200, 200, SimpleBubble.ColorChoice.BLUE, false);
        bubble = new SpeedUp(bubble);
        bubble.setGameHead(new GameData(0, 1));
        bubble.getGameHead().setBubbleSpeed(initialSpeed);
        bubble.pop();
    }

    public SpeedUpPopTest(double initial, double expected, String msg) {
        initialSpeed = initial;
        expectedSpeed = expected;
        message = msg;
        delta = 0.0001;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
            {
                GameConfig.DEFAULT_BUBBLE_SPEED,
                GameConfig.DEFAULT_BUBBLE_SPEED + GameConfig.BUBBLE_SPEEDUP,
                "Should increment bubble speed by BUBBLE_SPEEDUP."
            },
            {
                GameConfig.MAX_BUBBLE_SPEED,
                GameConfig.MAX_BUBBLE_SPEED,
                "Should not increment bubble speed greater than MAX_BUBBLE_SPEED."
            }
        });
    }

    @Test
    public void popCallBubbleSpeedTest() throws Exception {
        assertEquals(message, expectedSpeed,
            bubble.getGameHead().getBubbleSpeed(), delta);
    }
}
