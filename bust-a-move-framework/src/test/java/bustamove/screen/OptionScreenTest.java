package bustamove.screen;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.powermock.reflect.Whitebox;

import bustamove.screen.attributes.Button;
import bustamove.screen.config.GameState;
import bustamove.system.SoundHandler;

/**
 * Screen Tests
 *
 * @author Maurice Willemsen
 * @version 03.11.2016
 */
public class OptionScreenTest {
    @Mock
    private GameContainer game;
    @Mock
    private StateBasedGame sbg;
    @Mock
    private TrueTypeFont font;

    private OptionScreen screen;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Screen.setFonts(font);
    }

    @Test
    public void testOptionMenuInit() throws SlickException {
        when(game.getWidth()).thenReturn(500);
        when(font.getWidth("")).thenReturn(50);
        screen = new OptionScreen();
        screen.init(game, sbg);
        assertThat("Amount of texts should be like expected", screen.getTexts().size(), equalTo(3));
        assertThat("Amount of buttons should be like expected", screen.getButtons().size(), equalTo(5));
        assertThat("Amount of textfields should be like expected", screen.getTextFields().size(), equalTo(0));
    }

    @Test
    public void testOptionMenuID() throws SlickException {
        screen = new OptionScreen();
        int id = screen.getID();
        assertThat("ID should be as expected", id, equalTo(GameState.OPTION_SCREEN));
    }


    @Test
    public void getBackgroundButton() throws Exception {
        screen = new OptionScreen();
        Button button = Whitebox.invokeMethod(screen, "getMuteUnmuteBackgroundButton");
        assertNotNull(button);
    }

    @Test
    public void getEffectsButton() throws Exception {
        screen = new OptionScreen();
        Button button = Whitebox.invokeMethod(screen, "getMuteUnmuteSoundEffectButton");
        assertNotNull(button);
    }

}