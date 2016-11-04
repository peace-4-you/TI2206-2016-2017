package bustamove.screen;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.screen.config.GameState;
/**
 * Screen Tests
 * @author Maurice Willemsen
 * @version 03.11.2016
 */
public class CreditsScreenTest {
	@Mock 
	private GameContainer game;
	@Mock
	private StateBasedGame sbg;
	@Mock 
	private TrueTypeFont font;
	
	private Screen screen;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Screen.setFonts(font);
    }

    @Test
    public void testCreditScreenInit() throws SlickException {
    	when(game.getWidth()).thenReturn(500);
    	when(font.getWidth("")).thenReturn(50);
    	screen = new CreditsScreen();    	
    	screen.init(game, sbg);
    	assertThat("Amount of texts should be like expected", screen.getTexts().size(), equalTo(11));
    	assertThat("Amount of buttons should be like expected", screen.getButtons().size(), equalTo(1));
    	assertThat("Amount of textfields should be like expected", screen.getTextFields().size(), equalTo(0));
    }
    
    @Test
    public void testCreditScreenID() throws SlickException {
    	screen = new CreditsScreen();    	
    	int id = screen.getID();
    	assertThat("ID should be as expected", id, equalTo(GameState.CREDITS_SCREEN));
    }
}