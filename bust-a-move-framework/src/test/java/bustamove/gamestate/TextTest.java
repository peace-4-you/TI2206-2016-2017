package bustamove.gamestate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.newdawn.slick.TrueTypeFont;

import static org.junit.Assert.*;

/**
 * Created by Jason Xie on 19/10/2016.
 */
public class TextTest {

    @Mock
    private TrueTypeFont font;
    @InjectMocks
    private Text text;

    @Before
    public void setUp() {
        text = new Text("Asd", 20);
    }

    @Test
    public void test() {

    }
}