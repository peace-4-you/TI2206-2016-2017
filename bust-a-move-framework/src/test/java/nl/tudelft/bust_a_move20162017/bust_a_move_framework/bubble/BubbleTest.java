package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.junit.Before;
import org.junit.Test;

import java.util.IllegalFormatCodePointException;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Winer Bao on 9/28/2016.
 */
public class BubbleTest {
    @Test
    public void ConstructorCallGetXReturnsFiveTest() throws Exception {
        Bubble bubble = new Bubble(5, 0, Bubble.ColorChoice.BLUE, false);

        assertEquals(5, bubble.getX(), 0);
    }

    @Test
    public void ConstructorCallGetYReturnsThreeTest() throws Exception {
        Bubble bubble = new Bubble(0, 3, Bubble.ColorChoice.BLUE, false);

        assertEquals(3, bubble.getY(), 0);
    }

    @Test
    public void getXSpeedReturnZeroAfterConstructorIsCalledTest() throws Exception {
        Bubble bubble = new Bubble(0, 0, Bubble.ColorChoice.BLUE, false);

        assertEquals(0, bubble.getXSpeed(),0);
    }

    @Test
    public void getYSpeedReturnsZeroAfterConstructorIsCalledTest() throws Exception {
        Bubble bubble = new Bubble(0, 0, Bubble.ColorChoice.BLUE, false);

        assertEquals(0, bubble.getYSpeed(),0);
    }

    @Test
    public void ConstructorCallGetColorReturnsGreenTest() throws Exception {
        Bubble bubble = new Bubble(0, 0, Bubble.ColorChoice.GREEN, false);

        assertEquals(Bubble.ColorChoice.GREEN, bubble.getColor());
    }

    @Test
    public void ConstructorCallForCannonSetToTrueGetStateReturnsNewTest() throws Exception {
        Bubble bubble = new Bubble(0, 0, Bubble.ColorChoice.BLUE, true);

        assertEquals(Bubble.State.NEW, bubble.getState());
    }

    @Test
    public void ConstructorCallForCannonSetToFalseGetStateReturnsLandedTest() throws Exception {
        Bubble bubble = new Bubble(0, 0, Bubble.ColorChoice.BLUE, false);

        assertEquals(Bubble.State.LANDED, bubble.getState());
    }

    @Test
    public void fireBubbleCreatedForCannonHasStateFiringTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.fire(0);
        assertEquals(Bubble.State.FIRING, bubble.getState());
    }

    @Test
    public void fireBubbleNotCreatedForCannonKeepsStateNewTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, false);

        bubble.fire(0);

        assertEquals(Bubble.State.NEW, bubble.getState());
    }

    @Test
    public void moveBubbleWithStateFiringTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.fire(0);
        bubble.move();

        assertEquals(197, bubble.getY(), 0);
        assertEquals(200, bubble.getX(), 0);
    }

    @Test
    public void moveBubbleWithStateNewWillNotMoveTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.move();

        assertEquals(200, bubble.getX(), 0);
        assertEquals(200, bubble.getY(), 0);
    }

    @Test
    public void hitWallBubbleWithStateFiringWillBounceTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.fire(60);
        double speed_t = bubble.getXSpeed();
        bubble.hitWall();

        assertEquals(-speed_t, bubble.getXSpeed(), 0);
    }

    @Test
    public void hitWallBubbleWithStateLandedWillNotBounceTestTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, false);

        bubble.setXSpeed(2);
        bubble.hitWall();

        assertNotEquals(-2, bubble.getXSpeed(), 0);
    }

    @Test
    public void bubbleWithStateNewCannotLandTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.land(300, 300);

        assertNotEquals(Bubble.State.LANDED, bubble.getState());
    }

    @Test
    public void landBubbleSetsStateToLandedTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.fire(0);
        bubble.land(300, 300);

        assertEquals(Bubble.State.LANDED, bubble.getState());
    }

    @Test
    public void landBubbleSetsXSpeedToZeroTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.fire(0);
        bubble.land(300, 300);

        assertEquals(0, bubble.getXSpeed(), 0);
    }

    @Test
    public void landBubbleSetsYSpeedToZeroTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.fire(0);
        bubble.land(300, 300);

        assertEquals(0, bubble.getYSpeed(), 0);
    }

    @Test
    public void landBubbleSetsXposToThreeHundredTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.fire(0);
        bubble.land(300, 300);

        assertEquals(300, bubble.getX(), 0);
    }

    @Test
    public void landBubbleSetsYposToThreeHundredTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.fire(0);
        bubble.land(300, 300);

        assertEquals(300, bubble.getY(), 0);
    }

    @Test
    public void popLandedBubbleSetsStateToPoppingTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.pop();

        assertEquals(Bubble.State.POPPING, bubble.getState());
    }

    @Test
    public void popLandedBubbleSetsXSpeedToZeroTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.pop();

        assertEquals(0, bubble.getXSpeed(), 0);
    }

    @Test
    public void popLandedBubbleSetsYSpeedToZeroTest() throws Exception {
        Bubble bubble = new Bubble(200, 200, Bubble.ColorChoice.BLUE, true);

        bubble.pop();

        assertEquals(0, bubble.getYSpeed(), 0);
    }

    @Test
    public void drop() throws Exception {
        //TODO: add drop tests
    }

    @Test
    public void getBoundingBox() throws Exception {
        //TODO: add return Circle tests
    }

    @Test
    public void randomColor() throws Exception {
        //TODO: add return Bubble with random color tests
    }
}