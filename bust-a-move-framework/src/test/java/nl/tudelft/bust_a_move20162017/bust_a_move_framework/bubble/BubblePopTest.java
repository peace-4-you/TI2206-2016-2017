package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Winer Bao on 9/30/2016.
 */
@RunWith(Parameterized.class)
public class BubblePopTest {
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
    public BubblePopTest(Bubble.State state, Bubble.State expectedState,
                          String message) {
        this.fState = state;
        this.fExpectedState = expectedState;
        this.fMessage = message;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
                {Bubble.State.LANDED, Bubble.State.POPPING, "Should return state = POPPING"},
                {Bubble.State.POPPING, Bubble.State.POPPING, "Should be popping already"},
                /* FUTURE TESTS
                {Bubble.State.DROPPING, Bubble.State.DROPPING, "Should not be able to pop"},
                {Bubble.State.FIRING, Bubble.State.FIRING, "Should not be able to pop"},
                {Bubble.State.NEW, Bubble.State.NEW, "Should not be able to pop"},
                */
        });
    }

    @Test
    public void popBubbleTest() throws Exception {
        bubble.pop();

        assertEquals(fMessage, fExpectedState, bubble.getState());
    }
}