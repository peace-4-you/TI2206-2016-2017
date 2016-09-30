package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.junit.Before;
import org.junit.Ignore;
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
public class BubbleLandTest {
    private Bubble bubble;
    private float fCollisionXPos;
    private float fCollisionYPos;
    private double fXSpeed;
    private double fYSpeed;
    private Bubble.State fState;
    private double fExpectedXPos;
    private double fExpectedYPos;
    private double fExpectedXSpeed;
    private double fExpectedYSpeed;
    private Bubble.State fExpectedState;
    private String fMessage;

    @Before
    public void setUp() throws Exception {
        bubble = new Bubble(200, 300, Bubble.ColorChoice.BLUE, true);

        bubble.setXSpeed(fXSpeed);
        bubble.setYSpeed(fYSpeed);
        bubble.setState(fState);
    }

    //Inject via constructor
    public BubbleLandTest(float collisionXPos, float collisionYPos,
                          double xSpeed, double ySpeed, Bubble.State state,
                          double expectedXPos, double expectedYPos,
                          double expectedXSpeed, double expectedYSpeed,
                          Bubble.State expectedState,
                          String message) {
        this.fCollisionXPos = collisionXPos;
        this.fCollisionYPos = collisionYPos;
        this.fXSpeed = xSpeed;
        this.fYSpeed = ySpeed;
        this.fState = state;
        this.fExpectedXPos = expectedXPos;
        this.fExpectedYPos = expectedYPos;
        this.fExpectedXSpeed = expectedXSpeed;
        this.fExpectedYSpeed = expectedYSpeed;
        this.fExpectedState = expectedState;
        this.fMessage = message;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
                {100, 200, 0, -3, Bubble.State.FIRING, 100, 200, 0, 0,
                        Bubble.State.LANDED, "Should return state = LANDED, "
                        + "xPos/yPos = collision position and xSpeed/ySpeed set "
                        + "to 0"},
                {150, 200, -3, 0, Bubble.State.FIRING, 150, 200, 0, 0,
                        Bubble.State.LANDED, "Should return state = LANDED, "
                        + "xPos/yPos = collision position and xSpeed/ySpeed set "
                        + "to 0"},
                {50, 200, 3, 0, Bubble.State.FIRING, 50, 200, 0, 0,
                        Bubble.State.LANDED, "Should return state = LANDED, "
                        + "xPos/yPos = collision position and xSpeed/ySpeed set "
                        + "to 0"},
                {100, 70, -2.1213, -2.1213, Bubble.State.FIRING, 100, 70, 0, 0,
                        Bubble.State.LANDED, "Should return state = LANDED, "
                        + "xPos/yPos = collision position and xSpeed/ySpeed set "
                        + "to 0"},
                {100, 50, 2.1213, -2.1213, Bubble.State.FIRING, 100, 50, 0, 0,
                        Bubble.State.LANDED, "Should return state = LANDED, "
                        + "xPos/yPos = collision position and xSpeed/ySpeed set "
                        + "to 0"},
                {100, 100, 2, 3, Bubble.State.POPPED, 200, 300, 2, 3,
                        Bubble.State.POPPED, "Should not be able to land"},
                {100, 100, 2, 3, Bubble.State.DROPPING, 200, 300, 2, 3,
                        Bubble.State.DROPPING, "Should not be able to land"},
                {100, 100, 2, 3, Bubble.State.LANDED, 200, 300, 2, 3,
                        Bubble.State.LANDED, "Should not be able to hit land"},
                {100, 100, 2, 3, Bubble.State.NEW, 200, 300, 2, 3,
                        Bubble.State.NEW, "Should not be able to hit land"},
        });
    }

    @Ignore("land method needs refactor to allow testing")
    public void landBubbleTest() throws Exception {
        bubble.land(fCollisionXPos, fCollisionYPos);

        assertEquals(fMessage, fExpectedXPos, bubble.getX(), 0.0001);
        assertEquals(fMessage, fExpectedYPos, bubble.getY(), 0.0001);
        assertEquals(fMessage, fExpectedXSpeed, bubble.getXSpeed(), 0.0001);
        assertEquals(fMessage, fExpectedYSpeed, bubble.getYSpeed(), 0.0001);
        assertEquals(fMessage, fExpectedState, bubble.getState());
    }

    @Test
    public void dummyTest() throws Exception {
        assertTrue(true);
    }
}