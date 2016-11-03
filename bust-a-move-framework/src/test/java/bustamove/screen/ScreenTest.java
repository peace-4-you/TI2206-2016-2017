package bustamove.screen;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.powermock.reflect.Whitebox;

import bustamove.screen.attributes.Button;
import bustamove.screen.attributes.Text;
/**
 * Screen Test
 * @author Maurice Willemsen
 * @version 17.10.2016
 */
public class ScreenTest {
	@Mock 
	private GameContainer game;
	@Mock
	private StateBasedGame sbg;
	@Mock 
	private TrueTypeFont font;
	
	private Screen screen;

    @Before
    public void setUp() {

    }

    @Test
    public void testObjects() throws SlickException {
    	//Screen.setFonts();
    	//when(screen.center()).thenReturn();
    	screen = new CreditsScreen();    	
    	screen.init(game, sbg);
    	ArrayList<Text> texts = Whitebox.getInternalState(screen, "texts");
    	ArrayList<Button> buttons = Whitebox.getInternalState(screen, "buttons");
    	assertThat("Amount of buttons should be like expected", texts.size(), equalTo(11));
    	assertThat("Amount of buttons should be like expected", buttons.size(), equalTo(1));
    }
}