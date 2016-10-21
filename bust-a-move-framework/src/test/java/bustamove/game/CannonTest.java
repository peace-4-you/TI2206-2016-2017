package bustamove.game;

import bustamove.bubble.Bubble;
import bustamove.bubble.Bubble.ColorChoice;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jason Xie on 18/10/2016.
 */
public class CannonTest {

    private GameData game;
    private Cannon cannon;
    private double epsilon = 1e-6;

    @Before
    public void setUp() {
        game = new GameData(0, 0);
        cannon = new Cannon(game, 0, 0);
    }

    @Test
    public void cannonLength() {
        int length = 20;
        cannon.setLength(length);
        assertEquals(length, cannon.getLength());
    }

    @Test
    public void fireStraight() {
        cannon.fire();
        Bubble loaded = game.getBubbles().get(2);
        assertEquals(Bubble.State.FIRING, loaded.getState());
        assertEquals(0, loaded.getXSpeed(), epsilon);
        assertEquals(-3, loaded.getYSpeed(), epsilon);
    }

    @Test
    public void fireMaxRighttAngle() {
        for (int i = 0; i < 60; i++) {
            cannon.stepUp();
        }
        cannon.fire();
        Bubble loaded = game.getBubbles().get(2);
        double xspeed = Math.cos(Math.toRadians(60 + 90)) * 3;
        double yspeed = -Math.sin(Math.toRadians(60 + 90)) * 3;
        assertEquals(xspeed, loaded.getXSpeed(), epsilon);
        assertEquals(yspeed, loaded.getYSpeed(), epsilon);
    }

    @Test
    public void firePastMaxRighttAngle() {
        for (int i = 0; i < 70; i++) {
            cannon.stepUp();
        }
        cannon.fire();
        Bubble loaded = game.getBubbles().get(2);
        double xspeed = Math.cos(Math.toRadians(60 + 90)) * 3;
        double yspeed = -Math.sin(Math.toRadians(60 + 90)) * 3;
        assertEquals(xspeed, loaded.getXSpeed(), epsilon);
        assertEquals(yspeed, loaded.getYSpeed(), epsilon);
    }

    @Test
    public void fireMaxLeftAngle() {
        for (int i = 0; i < 60; i++) {
            cannon.stepDown();
        }
        cannon.fire();
        Bubble loaded = game.getBubbles().get(2);
        double xspeed = Math.cos(Math.toRadians(-60 + 90)) * 3;
        double yspeed = -Math.sin(Math.toRadians(-60 + 90)) * 3;
        assertEquals(xspeed, loaded.getXSpeed(), epsilon);
        assertEquals(yspeed, loaded.getYSpeed(), epsilon);
    }

    @Test
    public void firePastMaxLeftAngle() {
        for (int i = 0; i < 70; i++) {
            cannon.stepDown();
        }
        cannon.fire();
        Bubble loaded = game.getBubbles().get(2);
        double xspeed = Math.cos(Math.toRadians(-60 + 90)) * 3;
        double yspeed = -Math.sin(Math.toRadians(-60 + 90)) * 3;
        assertEquals(xspeed, loaded.getXSpeed(), epsilon);
        assertEquals(yspeed, loaded.getYSpeed(), epsilon);
    }

}