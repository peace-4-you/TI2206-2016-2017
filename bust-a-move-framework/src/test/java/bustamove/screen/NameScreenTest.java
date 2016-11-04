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
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.powermock.reflect.Whitebox;

import bustamove.screen.config.GameState;
/**
 * Screen Tests
 * @author Maurice Willemsen
 * @version 03.11.2016
 */
public class NameScreenTest {
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
    public void testSingleNameScreenID() throws SlickException {
    	screen = new SingleNameScreen();    	
    	int id = screen.getID();
    	assertThat("ID should be as expected", id, equalTo(GameState.NAME_SCREEN));
    }
    
    @Test
    public void testDoubleNameScreenID() throws SlickException {
    	screen = new DoubleNameScreen();    	
    	int id = screen.getID();
    	assertThat("ID should be as expected", id, equalTo(GameState.NAMES_SCREEN));
    }
    
    @Test
    public void testTextFieldGetter() throws SlickException {
    	screen = new SingleNameScreen(); 
    	((NameScreen)screen).initNameScreen(game, sbg);
    	TextField[] fields = Whitebox.getInternalState(screen, "namefields");
    	assertThat("Should be ready for 2 fields.", fields.length, equalTo(2));
    }
}