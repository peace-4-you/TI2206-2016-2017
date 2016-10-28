package bustamove.bubble;

import bustamove.player.Player;
import bustamove.player.Score;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Jason Xie on 28/10/2016.
 */
@RunWith(Parameterized.class)
public class PopBehaviourTest {
    private BubbleStorage bubbleStorage;
    private PopBehaviour popBehaviour;
    private Score score;

    @Before
    public void setUp() {
        bubbleStorage = new BubbleStorage(0, 0);
        score = new Score(new Player("name", 0, 0));
        popBehaviour = new PopBehaviour(bubbleStorage, score);
    }

    @Test
    public void popBubble() {
        Bubble bubble = bubbleStorage.iterator().next();
        popBehaviour.popBubble(bubble);
        assertNotEquals(bubble, bubbleStorage.iterator().next());
    }

    @Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][]{
                {Type.ROWBOMB, 1, -1, -1, 0},
                {Type.ROWBOMB, 8, -1, -1, 1},
                {Type.ROWBOMB, 15, -1, -1, 2},
                {Type.OBOMB, 0, 0, 0, null},
                {Type.OBOMB, 7, 0, 7, null},
                {Type.OBOMB, 12, 1, 4, null}
        });
    }

    private enum Type {
        ROWBOMB, OBOMB
    }

    private Type type;
    private int iterations;
    private int row;
    private int column;
    private Object expectedValue;

    public PopBehaviourTest(Type t, int ite, int r, int col, Object expected) {
        type = t;
        iterations = ite;
        row = r;
        column = col;
        expectedValue = expected;
    }

    @Test
    public void popRowBomb() {
        try {
            Assume.assumeTrue(type == Type.ROWBOMB);
            BubbleStorageIterator ite = bubbleStorage.iterator();
            int count = 0;
            while (ite.hasNext() && count < iterations) {
                ite.next();
                count++;
            }
            popBehaviour.popRowBomb(ite.next());
            assertEquals(expectedValue, bubbleStorage.size());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

    @Test
    public void popOBomb() {
        try {
            Assume.assumeTrue(type == Type.OBOMB);
            BubbleStorageIterator ite = bubbleStorage.iterator();
            int count = 0;
            while (ite.hasNext() && count < iterations) {
                ite.next();
                count++;
            }
            popBehaviour.popOBomb(ite.next());
            Bubble[] neighbours = bubbleStorage.getNeighbors(row, column);
            for (Bubble b : neighbours) {
                assertNull(b);
            }
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

}