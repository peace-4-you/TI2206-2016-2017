package bustamove;

import bustamove.game.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jason Xie on 27/10/2016.
 */
public class AppTest {

    private App app;

    @Before
    public void setUp() {
        app = new App("Game title");
    }

    @Test
    public void setgetGame() {
        assertNull(app.getGame());
    }

    @Test
    public void getGame() {
        assertNull(app.getGame());
    }

    @Test
    public void gameHeight() {
        assertEquals(580, app.getGameHeight());
    }
}