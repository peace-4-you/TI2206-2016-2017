package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by APPLE on 9/30/2016.
 */
public class BubbleBoundingBoxTest {

    @Test
    public void getBoundingBoxReturnsCircleObjectWithXPosInTheCenterTest() throws Exception {
        Bubble bubble = new Bubble(200, 300, Bubble.ColorChoice.BLUE, true);

        assertEquals((200 + (Bubble.DIAMETER / 2)), bubble.getBoundingBox().getCenterX(), 0);
    }

    @Test
    public void getBoundingBoxReturnsCircleObjectWithYPosInTheCenterTest() throws Exception {
        Bubble bubble = new Bubble(200, 300, Bubble.ColorChoice.BLUE, true);

        assertEquals((300 + (Bubble.DIAMETER / 2)), bubble.getBoundingBox().getCenterY(), 0);
    }

    @Test
    public void getBoundingBoxReturnsCircleObjectWithCorrectRadiusTest() throws Exception {
        Bubble bubble = new Bubble(200, 300, Bubble.ColorChoice.BLUE, true);

        assertEquals((Bubble.DIAMETER / 2), bubble.getBoundingBox().getRadius(), 0);
    }
}
