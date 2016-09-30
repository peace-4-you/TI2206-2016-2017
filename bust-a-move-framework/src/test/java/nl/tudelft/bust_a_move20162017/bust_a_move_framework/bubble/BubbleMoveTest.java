package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import bust_a_move.bubble.Bubble;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Winer Bao on 9/30/2016.
 */
@RunWith(Parameterized.class)
public class BubbleMoveTest {
    private Bubble bubble;
    private double fXSpeed;
    private double fYSpeed;
    private Bubble.State fState;
    private double fExpectedXPos;
    private double fExpectedYPos;
    private String fMessage;

    @Before
    public void setUp() throws Exception {
        bubble = new Bubble(200, 300, Bubble.ColorChoice.BLUE, true);

        bubble.setXSpeed(fXSpeed);
        bubble.setYSpeed(fYSpeed);
        bubble.setState(fState);
    }

    //Inject via constructor
    public BubbleMoveTest(double xSpeed, double ySpeed, Bubble.State state, double expectedXPos, double expectedYPos,
                          String message) {
        this.fXSpeed = xSpeed;
        this.fYSpeed = ySpeed;
        this.fState = state;
        this.fExpectedXPos = expectedXPos;
        this.fExpectedYPos = expectedYPos;
        this.fMessage = message;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
                {0, -3, Bubble.State.FIRING, 200, 297, "Should return x = 200 "
                        + "and y = 297 when xSpeed = 0, ySpeed = -3 and state = "
                        + "FIRING"},
                {-3, 0, Bubble.State.FIRING, 197, 300, "Should return x = 197 "
                        + "and y = 300 when xSpeed = -3, ySpeed = 0 and state = "
                        + "FIRING"},
                {3, 0, Bubble.State.FIRING, 203, 300, "Should return x = 203 "
                        + "and y = 300 when xSpeed = 3, ySpeed = 0 and state = "
                        + "FIRING"},
                {-2.1213, -2.1213, Bubble.State.FIRING, 197.8787, 297.8787,
                        "Should return x = 197.8787 and y = 297,8787 when xSpeed "
                        + "=  -2.1213, ySpeed = -2.1213 and state = FIRING"},
                {2.1213, -2.1213, Bubble.State.FIRING, 202.1213, 297.8787,
                        "Should return x = 202.1214 and y = 297,8787 when xSpeed "
                        + "=  2.1213, ySpeed = -2.1213 and state = FIRING"},
                /* FUTURE TESTS
                {2, 3, Bubble.State.POPPING, 2, 3, "Should not be able to hit wall"},
                {2, 3, Bubble.State.DROPPING, 2, 3, "Should not be able to hit wall"},
                {2, 3, Bubble.State.LANDED, 2, 3, "Should not be able to hit wall"},
                {2, 3, Bubble.State.NEW, 2, 3, "Should not be able to hit wall"},
                */
        });
    }

    @Test
    public void moveBubbleTest() throws Exception {
        bubble.move();

        assertEquals(fMessage, fExpectedXPos, bubble.getX(), 0.0001);
        assertEquals(fMessage, fExpectedYPos, bubble.getY(), 0.0001);
    }
}