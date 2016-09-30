package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Winer Bao on 9/29/2016.
 */
@RunWith(Parameterized.class)
public class BubbleFireTest {
    private Bubble bubble;
    private boolean fForCannon;
    private int fAngle;
    private Bubble.State fExpectedState;
    private double fExpectedXSpeed;
    private double fExpectedYSpeed;
    private String fMessage;

    @Before
    public void setUp() throws Exception {
        bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, fForCannon);
    }

    //Inject via constructor
    public BubbleFireTest(boolean forCannon, int angle, Bubble.State expectedState,
                          double expectedXSpeed, double expectedYSpeed,
                          String message) {
        this.fForCannon = forCannon;
        this.fAngle = angle;
        this.fExpectedState = expectedState;
        this.fExpectedXSpeed = expectedXSpeed;
        this.fExpectedYSpeed = expectedYSpeed;
        this.fMessage = message;
    }

    // Assumes Bubble.SPEED = 3
    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
                {true, 0, Bubble.State.FIRING, 0, -3, "Should return state = "
                        + "FIRING, xSpeed = 0 and ySpeed = -3 when angle = 0 and "
                        + "forCannon = true"},
                {true, 90, Bubble.State.FIRING, -3, 0, "Should return state = "
                        + "FIRING, xSpeed = -3 and ySpeed = 0 when angle = 90 and "
                        + "forCannon = true"},
                {true, -90, Bubble.State.FIRING, 3, 0, "Should return state = "
                        + "FIRING, xSpeed = 3 and ySpeed = 0 when angle = -90 and "
                        + "forCannon = true"},
                {true, 45, Bubble.State.FIRING, -2.1213, -2.1213, "Should return "
                        + "state = FIRING, xSpeed = -2.1213 and ySpeed = -2.1213 "
                        + "when the angle = 45 and forCannon = true"},
                {true, -45, Bubble.State.FIRING, 2.1213, -2.1213, "Should return "
                        + "state = FIRING, xSpeed = 2.1213 and ySpeed = -2.1213 "
                        + "when the angle is -45 and forCannon = true"},
                /* FUTURE TESTS
                {false, 0, Bubble.State.LANDED, 0, 0, "Should not be able to fire "
                        + "LANDED bubbles"}
                */
        });
    }

    @Test
    public void fireBubbleTest() throws Exception {
        bubble.fire(fAngle);

        assertEquals(fMessage, fExpectedState, bubble.getState());
        assertEquals(fMessage, fExpectedXSpeed, bubble.getXSpeed(), 0.0001);
        assertEquals(fMessage, fExpectedYSpeed, bubble.getYSpeed(), 0.0001);
    }
}