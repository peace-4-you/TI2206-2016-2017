package bustamove;

import bustamove.game.Game;
import bustamove.screen.Screen;
import bustamove.screen.config.GameConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;

/**
 * Created by Jason Xie on 27/10/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Screen.class)
public class AppTest {

    private App app;
    @Mock
    private GameContainer container;

    @Before
    public void setUp() {
        app = new App("Game title");
    }

    @Test
    public void setgetGame() {
        Game game = new Game();
        assertNull(app.getGame());
        app.setGame(game);
        assertEquals(game, app.getGame());
    }

    @Test
    public void getGame() {
        assertNull(app.getGame());
    }

    @Test
    public void getPauseScreen() {
        assertNull(app.getPauseScreen());
    }

    @Test
    public void getDefeatScreen() {
        assertNull(app.getDefeatScreen());
    }

    @Test
    public void getVictoryScreen() {
        assertNull(app.getVictoryScreen());
    }


    @Test
    public void gameHeight() {
        assertEquals(580, app.getGameHeight());
    }

    @Test
    public void gameWidth() {
        assertEquals(640, app.getGameWidth());
    }
}