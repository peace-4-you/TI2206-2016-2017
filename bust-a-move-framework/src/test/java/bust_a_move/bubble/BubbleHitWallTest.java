package bust_a_move.bubble;

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
public class BubbleHitWallTest {
    private Bubble bubble;
    private double fXSpeed;
    private double fYSpeed;
    private Bubble.State fState;
    private double fExpectedXSpeed;
    private double fExpectedYSpeed;
    private String fMessage;

    @Before
    public void setUp() throws Exception {
        bubble = new Bubble(200, 300, Bubble.ColorChoice.BLUE, true);

        bubble.setXSpeed(fXSpeed);
        bubble.setYSpeed(fYSpeed);
        bubble.setState(fState);
    }

    //Inject via constructor
    public BubbleHitWallTest(double xSpeed, double ySpeed, Bubble.State state,
                             double expectedXSpeed, double expectedYSpeed,
                             String message) {
        this.fXSpeed = xSpeed;
        this.fYSpeed = ySpeed;
        this.fState = state;
        this.fExpectedXSpeed = expectedXSpeed;
        this.fExpectedYSpeed = expectedYSpeed;
        this.fMessage = message;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
                {0, -3, Bubble.State.FIRING, 0, -3, " Should return xSpeed = 0 "
                        + "and leave ySpeed unchanged when xSpeed was 0 and state "
                        + "= FIRING"},
                {-3, 0, Bubble.State.FIRING, 3, 0, " Should return xSpeed = 3 "
                        + "and leave ySpeed unchanged when xSpeed was -3 and "
                        + "state = FIRING"},
                {3, 0, Bubble.State.FIRING, -3, 0, " Should return xSpeed = -3 "
                        + "and leave ySpeed unchanged when xSpeed was 3 and state "
                        + "= FIRING"},
                {-2.1213, -2.1213, Bubble.State.FIRING, 2.1213, -2.1213,
                        " Should return xSpeed = 2.1213 and leave ySpeed "
                        + "unchanged when xSpeed was -2.1213 and state = FIRING"},
                {2.1213, -2.1213, Bubble.State.FIRING, -2.1214, -2.1213,
                        " Should return xSpeed = 2.1213 and leave ySpeed "
                        + "unchanged when xSpeed was -2.1213 and state = FIRING"},
                {2, 3, Bubble.State.LANDED, 2, 3, "Should leave xSpeed and "
                        + "ySpeed unchanged when the state = LANDED"},
                {2, 3, Bubble.State.NEW, 2, 3, "Should leave xSpeed and "
                        + "ySpeed unchanged when the state = NEW"},
                {2, 3, Bubble.State.DROPPING, 2, 3, "Should leave xSpeed and "
                        + "ySpeed unchanged when the state = DROPPING"},
                {2, 3, Bubble.State.POPPED, 2, 3, "Should leave xSpeed and "
                        + "ySpeed unchanged when the state = POPPING"}
        });
    }

    @Test
    public void hitWallBubbleTest() throws Exception {
        bubble.hitWall();

        assertEquals(fMessage, fExpectedXSpeed, bubble.getXSpeed(), 0.0001);
        assertEquals(fMessage, fExpectedYSpeed, bubble.getYSpeed(), 0.0001);
    }
}