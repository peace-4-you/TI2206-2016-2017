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
public class BubbleDropTest {
    private Bubble bubble;
    private Bubble.State fState;
    private Bubble.State fExpectedState;
    private String fMessage;

    @Before
    public void setUp() throws Exception {
        bubble = new Bubble(200, 300, Bubble.ColorChoice.BLUE, true);

        bubble.setState(fState);
    }

    //Inject via constructor
    public BubbleDropTest(Bubble.State state, Bubble.State expectedState,
                          String message) {
        this.fState = state;
        this.fExpectedState = expectedState;
        this.fMessage = message;
    }

    // Assumes Bubble.Speed = 3
    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
                {Bubble.State.LANDED, Bubble.State.DROPPING, "Should return state = DROPPING"},
                /* FUTURE TESTS
                {Bubble.State.POPPING, Bubble.State.POPPING, "Should not be able to drop"},
                {Bubble.State.DROPPING, Bubble.State.DROPPING, "Should be dropping already"},
                {Bubble.State.FIRING, Bubble.State.FIRING,"Should not be able to drop"},
                {Bubble.State.NEW, Bubble.State.NEW, "Should not be able to drop"},
                */
        });
    }

    @Test
    public void dropBubbleTest() throws Exception {
        double prevYSpeed = bubble.getYSpeed();
        double prevXSpeed = bubble.getXSpeed();

        bubble.drop();

        assertEquals(fMessage, fExpectedState, bubble.getState());
        if(fState == Bubble.State.LANDED) {
            assertEquals("Should return ySpeed = 3", 3, bubble.getYSpeed(), 0.0001);
            assertEquals("Should return xSpeed = 0", 0, bubble.getXSpeed(), 0.0001);
        } else {
            assertEquals("Should not change the ySpeed", prevYSpeed, bubble.getYSpeed(), 0.0001);
            assertEquals("Should not change the xSpeed", prevXSpeed, bubble.getXSpeed(), 0.0001);
        }
    }
}