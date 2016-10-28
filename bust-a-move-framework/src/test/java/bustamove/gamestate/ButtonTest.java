package bustamove.gamestate;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

import bustamove.screen.attributes.Button;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Jason Xie on 20/10/2016.
 */
@RunWith(Parameterized.class)
public class ButtonTest {

    private Button button;
    private Object param1;
    private Object param2;
    private Object expectedResult;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private Input mouse;
    @Mock
    private TrueTypeFont font;
    @Mock
    private GameContainer game;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        exception = ExpectedException.none();
    }


    public ButtonTest(float x, float y, Object parameter1, Object parameter2, Object expected) {
        button = new Button("Button", x, y, 100, 30);
        param1 = parameter1;
        param2 = parameter2;
        expectedResult = expected;
    }

    @Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][]{
                {0, 0, 10, 10, true},
                {100, 100, 120, 110, true},
                {0, 0, 110, 40, false},
                {100, 100, 90, 120, false},
                {100, 100, 100, 90, false},
                {100, 100, 100, 140, false},
        });
    }

    @Test
    public void ButtonAsserts() {
//        try {
//            Assume.assumeTrue(type == Type.ASSERTTEXT);
//            exception.expect(AssertionError.class);
//            Button button1 = new Button((String) param1, 0, 0, 0, 0);
//        } catch (AssumptionViolatedException e) {
//            // Expected exception.
//        }
    }

    @Test
    public void isInBounds() {
        try {
            when(mouse.getMouseX()).thenReturn((Integer) param1);
            when(mouse.getMouseY()).thenReturn((Integer) param2);
            assertEquals(expectedResult, button.isInBounds(mouse));
        } catch (AssumptionViolatedException e) {
            // Expected exception.
        }
    }

    @Test
    public void centerButton() {
        button = new Button("Button", 100, 100, 30);
        when(mouse.getMouseX()).thenReturn(150);
        when(mouse.getMouseY()).thenReturn(100);
        assertFalse(button.isInBounds(mouse));
        when(game.getWidth()).thenReturn(200);
        button.centerButton(game);
        assertTrue(button.isInBounds(mouse));
    }

    @Test
    public void ButtonFont() {
        button.setFont(font);
        assertEquals(font, button.getFont());
    }
}