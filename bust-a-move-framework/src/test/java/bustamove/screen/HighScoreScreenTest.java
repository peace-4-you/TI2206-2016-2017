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

import bustamove.game.Highscore;
import bustamove.screen.config.GameState;
/**
 * Screen Tests
 * @author Maurice Willemsen
 * @version 03.11.2016
 */
public class HighScoreScreenTest {
	@Mock 
	private GameContainer game;
	@Mock
	private StateBasedGame sbg;
	@Mock 
	private TrueTypeFont font;
	
	private Screen screen;
	
	private Highscore highscore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Screen.setFonts(font);
        highscore = new Highscore();
    }
    

    @Test
    public void testHighScoreScreenInit() throws SlickException {
    	when(game.getWidth()).thenReturn(500);
    	when(font.getWidth("")).thenReturn(50);
    	screen = new HighscoreScreen(highscore);    	
    	screen.init(game, sbg);
    	assertThat("Amount of texts should be like expected", screen.getTexts().size(), equalTo(0));
    	assertThat("Amount of buttons should be like expected", screen.getButtons().size(), equalTo(1));
    	assertThat("Amount of textfields should be like expected", screen.getTextFields().size(), equalTo(0));
    }
    
    
    @Test
    public void testHighScoreScreenID() throws SlickException {
    	screen = new HighscoreScreen(highscore);   	
    	int id = screen.getID();
    	assertThat("ID should be as expected", id, equalTo(GameState.HIGHSCORES_SCREEN));
    }
}