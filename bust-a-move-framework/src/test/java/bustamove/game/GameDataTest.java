package bustamove.game;

import bustamove.player.Player;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

/**
 * Created by Jason Xie on 27/10/2016.
 */
public class GameDataTest {

    private GameData gameData;
    private double epsilon = 1e-6;

    @Before
    public void setUp() {
        gameData = new GameData(0, 0);
    }

    @Test
    public void getPlayer() {
        assertNotNull(gameData.getPlayer());
        assertTrue(gameData.getPlayer() instanceof Player);
    }

    @Test
    public void getCannon() {
        assertNotNull(gameData.getCannon());
        assertTrue(gameData.getCannon() instanceof Cannon);
    }

    @Test
    public void getState() {
        assertNotNull(gameData.getState());
        assertTrue(gameData.getState() instanceof GameData.GameDataState);
    }

    @Test
    public void getBubbleSpeed() {
        assertNotNull(gameData.getBubbleSpeed());
        assertEquals(GameConfig.DEFAULT_BUBBLE_SPEED, gameData.getBubbleSpeed(), epsilon);
    }

    @Test
    public void setBubbleSpeed() {
        double speed = 20;
        gameData.setBubbleSpeed(speed);
        assertEquals(speed, gameData.getBubbleSpeed(), epsilon);
    }

    @Test
    public void getBubbles() {
        assertEquals(2, gameData.getBubbles().size());
    }

    @Test
    public void update() {
        int newscore = 100;
        String newname = "Jason";
        int score = Whitebox.getInternalState(gameData, "scorePlayer");
        String name = Whitebox.getInternalState(gameData, "namePlayer");
        gameData.update(1, newname, newscore);
        assertNotEquals(newscore, score);
        assertNotEquals(newname, name);

        gameData.update(0, newname, newscore);
        score = Whitebox.getInternalState(gameData, "scorePlayer");
        name = Whitebox.getInternalState(gameData, "namePlayer");

        assertEquals(newscore, score);
        assertEquals(newname, name);
    }
}