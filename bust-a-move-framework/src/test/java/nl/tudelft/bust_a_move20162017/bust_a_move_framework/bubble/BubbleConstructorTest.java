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
 * Created by Winer Bao on 9/28/2016.
 */
@RunWith(Parameterized.class)
public class BubbleConstructorTest {
    private Bubble bubble;
    private boolean fForCannon;
    private Bubble.State fExpectedState;
    private String fMessage;

    @Before
    public void setUp() throws Exception {
        bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, fForCannon);
    }

    //Inject via constructor
    public BubbleConstructorTest(boolean forCannon, Bubble.State expectedState,
                                 String message) {
        this.fForCannon = forCannon;
        this.fExpectedState = expectedState;
        this.fMessage = message;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][] {
                {true, Bubble.State.NEW, "Should return state NEW when "
                        + "forCannon is true"},
                {false, Bubble.State.LANDED, "Should return state LANDED when "
                        + "forCannon is false"}
        });
    }

    @Test
    public void ConstructorCallReturnStateTest() throws Exception {
        assertEquals(fMessage, fExpectedState, bubble.getState());
    }
}