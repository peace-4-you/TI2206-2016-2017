package bustamove.bubble.powerup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import bustamove.bubble.SimpleBubble;
import bustamove.bubble.Bubble;

import static org.junit.Assert.*;

/**
 * Created by Calvin Nhieu on 10/13/2016.
 */
@RunWith(Parameterized.class)
public class PowerUpApplyTest {
    private Bubble bubble;
    private Bubble puBubble;
    private int numTests;

    @Before
    public void setUp() throws Exception {
        bubble = new SimpleBubble(200, 200, SimpleBubble.ColorChoice.BLUE, false);
        puBubble = bubble;
        for (int i = 0; i < numTests; i++) {
            puBubble = PowerUp.apply(puBubble);
        }
    }

    public PowerUpApplyTest(int n) {
        this.numTests = n;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
            {5},
        });
    }

    @Test
    public void applyCallRootBubbleTest() throws Exception {
        assertEquals("Should wrap a non-powerup root Bubble component.",
            true, puBubble.getRootBubble() instanceof SimpleBubble);
        assertEquals("Should wrap initial bubble as root Bubble component.",
            bubble, puBubble.getRootBubble());
    }
}
