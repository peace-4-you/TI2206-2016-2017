package bustamove.bubble;

import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

import bustamove.bubble.Bubble.ColorChoice;
import bustamove.bubble.Bubble.State;
import bustamove.game.GameData;
import bustamove.screen.config.GameConfig;

/**
 * Created by Jason Xie on 12/10/2016.
 */
@RunWith(Parameterized.class)
public class BubbleTest {


    private enum Type {
        CONSTRUCTOR, COLOR, XPOS, YPOS, XSPEED, YSPEED, DROPSPEED
    }

    private SimpleBubble parameterizedBubble;
    private Type type;

    private float xPos;
    private float yPos;
    private ColorChoice currColor;
    private boolean forCannon;
    private Object expectedResult;

    public BubbleTest(Type t, float xpos, float ypos, ColorChoice color, boolean cannon, Object expected) {
        type = t;
        xPos = xpos;
        yPos = ypos;
        currColor = color;
        forCannon = cannon;
        expectedResult = expected;
        parameterizedBubble = new SimpleBubble(xPos, yPos, currColor, forCannon);
    }

    @Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][]{
                {Type.CONSTRUCTOR, 200, 200, ColorChoice.BLUE, true, State.NEW},
                {Type.CONSTRUCTOR, 5, 500, ColorChoice.YELLOW, false, State.LANDED},
                {Type.COLOR, 200, 200, ColorChoice.BLUE, true, ColorChoice.BLUE},
                {Type.COLOR, 5, 500, ColorChoice.YELLOW, false, ColorChoice.YELLOW},
                {Type.COLOR, 0, 0, ColorChoice.RED, true, ColorChoice.RED},
                {Type.COLOR, 325, 951, ColorChoice.GREEN, false, ColorChoice.GREEN},
                {Type.XPOS, 200, 200, ColorChoice.BLUE, true, 200.f},
                {Type.XPOS, 5, 500, ColorChoice.YELLOW, false, 5.f},
                {Type.XPOS, 0, 0, ColorChoice.RED, true, 0.f},
                {Type.XPOS, 325, 951, ColorChoice.GREEN, false, 325.f},
                {Type.YPOS, 200, 200, ColorChoice.BLUE, true, 200.f},
                {Type.YPOS, 5, 500, ColorChoice.YELLOW, false, 500.f},
                {Type.YPOS, 0, 0, ColorChoice.RED, true, 0.f},
                {Type.YPOS, 325, 951, ColorChoice.GREEN, false, 951.f},
                {Type.XSPEED, 200, 200, ColorChoice.GREEN, true, 100.d},
                {Type.XSPEED, 200, 200, ColorChoice.GREEN, true, 59.d},
                {Type.XSPEED, 200, 200, ColorChoice.GREEN, true, 503.d},
                {Type.XSPEED, 200, 200, ColorChoice.GREEN, true, 0.d},
                {Type.YSPEED, 200, 200, ColorChoice.GREEN, true, 100.d},
                {Type.YSPEED, 200, 200, ColorChoice.GREEN, true, 59.d},
                {Type.YSPEED, 200, 200, ColorChoice.GREEN, true, 503.d},
                {Type.YSPEED, 200, 200, ColorChoice.GREEN, true, 0.d},
                {Type.DROPSPEED, 200, 200, ColorChoice.BLUE, true, GameConfig.DEFAULT_BUBBLE_SPEED}
        });
    }

    @Test
    public void bubbleState() {
        try {
            Assume.assumeTrue(type == Type.CONSTRUCTOR);
            assertEquals(expectedResult, parameterizedBubble.getState());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

    @Test
    public void bubbleColor() {
        try {
            Assume.assumeTrue(type == Type.COLOR);
            assertEquals(expectedResult, parameterizedBubble.getColor());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

    @Test
    public void bubbleXPos() {
        try {
            Assume.assumeTrue(type == Type.XPOS);
            assertEquals(expectedResult, parameterizedBubble.getX());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

    @Test
    public void bubbleYPos() {
        try {
            Assume.assumeTrue(type == Type.YPOS);
            assertEquals(expectedResult, parameterizedBubble.getY());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

    @Test
    public void bubbleXSpeed() {
        try {
            Assume.assumeTrue(type == Type.XSPEED);
            parameterizedBubble.setXSpeed((Double) expectedResult);
            assertEquals(expectedResult, parameterizedBubble.getXSpeed());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

    @Test
    public void bubbleYSpeed() {
        try {
            Assume.assumeTrue(type == Type.YSPEED);
            parameterizedBubble.setYSpeed((Double) expectedResult);
            assertEquals(expectedResult, parameterizedBubble.getYSpeed());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

    @Test
    public void bubbleDropSpeed() {
        try {
            Assume.assumeTrue(type == Type.DROPSPEED);
            assertEquals(expectedResult, Bubble.DROP_SPEED);
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

    // Here starts the manual testing.
    private double epsilon = 1e-6;
    private SimpleBubble bubble1;
    private SimpleBubble bubble2;

    @Before
    public void setUp() {
        bubble1 = new SimpleBubble(200, 200, ColorChoice.BLUE, true);
        bubble2 = new SimpleBubble(123, 465, ColorChoice.GREEN, false);
    }

    @Test
    public void bubbleBoundingBox() {
        assertEquals(bubble1.getX() + SimpleBubble.DIAMETER / 2, bubble1.getBoundingBox().getCenterX(), epsilon);
        assertEquals(bubble1.getY() + SimpleBubble.DIAMETER / 2, bubble1.getBoundingBox().getCenterY(), epsilon);

        assertEquals(bubble2.getX() + SimpleBubble.DIAMETER / 2, bubble2.getBoundingBox().getCenterX(), epsilon);
        assertEquals(bubble2.getY() + SimpleBubble.DIAMETER / 2, bubble2.getBoundingBox().getCenterY(), epsilon);
    }

    @Test
    public void bubbleNotMovingNoSpeed() {
        double xpos = bubble1.getX();
        double ypos = bubble1.getY();
        bubble1.move();
        assertEquals(xpos, bubble1.getX(), epsilon);
        assertEquals(ypos, bubble1.getY(), epsilon);

        xpos = bubble2.getX();
        ypos = bubble2.getY();
        bubble2.move();
        assertEquals(xpos, bubble2.getX(), epsilon);
        assertEquals(ypos, bubble2.getY(), epsilon);
    }

    @Test
    public void bubbleNotMovingSpeed() {
        float xspeed1 = 5;
        float xspeed2 = 3.567f;
        float xpos = bubble1.getX();

        bubble1.setXSpeed(xspeed1);
        bubble2.setXSpeed(xspeed2);
        bubble1.move();
        bubble2.move();

        assertEquals(xpos, bubble1.getX(), epsilon);
        xpos = bubble2.getX();
        assertEquals(xpos, bubble2.getX(), epsilon);
    }

    @Test
    public void bubbleMovingSpeed() {
        bubble1.setState(State.FIRING);
        bubble2.setState(State.FIRING);
        float xspeed1 = 5;
        float xspeed2 = 3.567f;
        float xpos = bubble1.getX();

        bubble1.setXSpeed(xspeed1);
        bubble2.setXSpeed(xspeed2);
        bubble1.move();
        assertEquals(xpos + xspeed1, bubble1.getX(), epsilon);
        xpos = bubble2.getX();
        bubble2.move();
        assertEquals(xpos + xspeed2, bubble2.getX(), epsilon);
    }

    @Test
    public void bubbleHitWallSpeed() {
        bubble1.setState(State.FIRING);
        bubble2.setState(State.FIRING);
        float xspeed1 = 5;
        float xspeed2 = 3.567f;
        bubble1.setXSpeed(xspeed1);
        bubble2.setXSpeed(xspeed2);
        bubble1.hitWall();
        bubble2.hitWall();
        assertEquals(-xspeed1, bubble1.getXSpeed(), epsilon);
        assertEquals(-xspeed2, bubble2.getXSpeed(), epsilon);
    }

    @Test
    public void bubbleGameHead() {
        GameData gameData = new GameData(0, 1);
        bubble1.setGameHead(gameData);
        assertEquals(gameData, bubble1.getGameHead());
    }

    @Test
    public void bubbleDrop() {
        bubble1.drop();
        assertEquals(State.DROPPING, bubble1.getState());
        assertEquals(0, bubble1.getXSpeed(), epsilon);
        assertEquals(GameConfig.DEFAULT_BUBBLE_SPEED, Bubble.DROP_SPEED, epsilon);

        double xpos = bubble1.getX();
        double ypos = bubble1.getY();
        bubble1.move();
        assertEquals(xpos, bubble1.getX(), epsilon);
        assertEquals(ypos + bubble1.getYSpeed(), bubble1.getY(), epsilon);
    }

    @Test
    public void bubbleLand() {
        float xpos = 123;
        float ypos = 42.0f;
        bubble1.land(xpos, ypos);
        assertEquals(State.LANDED, bubble1.getState());
        assertEquals(xpos, bubble1.getX(), epsilon);
        assertEquals(ypos, bubble1.getY(), epsilon);
    }
}
