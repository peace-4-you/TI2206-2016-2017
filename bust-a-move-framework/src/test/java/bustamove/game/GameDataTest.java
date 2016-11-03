package bustamove.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import bustamove.bubble.Collision;
import bustamove.bubble.PopBehaviour;
import bustamove.game.GameData.GameDifficulty;
import bustamove.player.Player;
import bustamove.screen.config.GameConfig;
import bustamove.util.PlayerObserver;

/**
 * Created by Jason Xie on 27/10/2016.
 */
public class GameDataTest {

    private GameData gameData;
    private double epsilon = 1e-6;

    @Before
    public void setUp() {
        gameData = new GameData(0, 0, GameDifficulty.NORMAL);
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
    public void getCollision() {
        assertTrue(gameData.getCollision() instanceof Collision);
    }

    @Test
    public void getPopBehaviour() {
        assertTrue(gameData.getPopBehaviour() instanceof PopBehaviour);
    }

    @Test
    public void registerPlayerObserver() {
        Player player = gameData.getPlayer();
        Vector<PlayerObserver> observers = Whitebox.getInternalState(player, "observers");
        PlayerObserver observer = Mockito.mock(PlayerObserver.class);
        gameData.registerPlayerObservers(observer);
        assertTrue(observers.contains(observer));
    }

    @Test
    public void update() {
        int newscore = 100;
        String newname = "Jason";
        int newpopped = 1;
        int newdropped = 2;
        int score = Whitebox.getInternalState(gameData, "scorePlayer");
        String name = Whitebox.getInternalState(gameData, "namePlayer");
        int dropped = Whitebox.getInternalState(gameData, "droppedBubbles");
        int popped = Whitebox.getInternalState(gameData, "poppedBubbles");

        gameData.update(1, newname, newscore, newdropped, newpopped);
        assertNotEquals(newscore, score);
        assertNotEquals(newname, name);
        assertNotEquals(newpopped, popped);
        assertNotEquals(newdropped, dropped);

        gameData.update(0, newname, newscore, newdropped, newpopped);
        score = Whitebox.getInternalState(gameData, "scorePlayer");
        name = Whitebox.getInternalState(gameData, "namePlayer");
        dropped = Whitebox.getInternalState(gameData, "droppedBubbles");
        popped = Whitebox.getInternalState(gameData, "poppedBubbles");

        assertEquals(newscore, score);
        assertEquals(newname, name);
        assertEquals(newpopped, popped);
        assertEquals(newdropped, dropped);
    }
}
