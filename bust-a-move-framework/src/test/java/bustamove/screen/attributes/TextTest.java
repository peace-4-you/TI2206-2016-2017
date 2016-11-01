package bustamove.screen.attributes;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.powermock.reflect.Whitebox;
import bustamove.screen.attributes.Text;

/**
 * Created by Maurice Willemsen
 */
@RunWith(Parameterized.class)
public class TextTest {

    private Text text;


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private Input mouse;
    @Mock
    private TrueTypeFont font;
    @Mock
    private GameContainer game;
    

    @Before
    public void setUp() throws SlickException {
        MockitoAnnotations.initMocks(this);
        exception = ExpectedException.none();        
    }

    public TextTest(float x, float y) {    	
    	text = new Text("Test", x, y);
    }

    @Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][]{
                {0, 0},
                {100, 100},
        });
    }
    
    @Test
    public void centerText(){
    	Text.setFont(font);
    	when(font.getWidth("Test")).thenReturn(50);
    	when(game.getWidth()).thenReturn(500);
    	text.centerText(game);
    	float x = Whitebox.getInternalState(text, "xPos");
    	assertEquals(225,x,1);
    }

    @Test
    public void setTextFont() {
        Text.setFont(font);
        assertEquals(font, text.getFont());
    }
     
    @Test
    public void setText(){
    	text.setText("newTest");
    	String internText = Whitebox.getInternalState(text, "text");
    	assertEquals("newTest", internText);
    }
}