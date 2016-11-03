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

import bustamove.screen.attributes.Text;
import bustamove.screen.config.GameState;
/**
 * Screen Tests
 * @author Maurice Willemsen
 * @version 03.11.2016
 */
public class ScoreScreenTest {
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
    public void testUpdater() throws SlickException {
    	when(game.getWidth()).thenReturn(500);
    	when(font.getWidth("")).thenReturn(50);
    	screen = new PausedScreen();    	
    	screen.init(game, sbg);
    	((ScoreScreen)screen).update(1,"Henk",2,3,4);
    	Text[] name = Whitebox.getInternalState(screen, "playername");
    	String nameS = Whitebox.getInternalState(name[0], "text");
    	Text[] score = Whitebox.getInternalState(screen, "playerscore");
    	String scoreS = Whitebox.getInternalState(score[0], "text");
    	Text[] dropped = Whitebox.getInternalState(screen, "droppedbubbles");
    	String droppedS = Whitebox.getInternalState(dropped[0], "text");
    	Text[] popped = Whitebox.getInternalState(screen, "poppedbubbles");
    	String poppedS = Whitebox.getInternalState(popped[0], "text");
    	assertThat("Name Should be updated", nameS, equalTo("Player: Henk"));
    	assertThat("Score Should be updated", scoreS, equalTo("Score: 2"));;
    	assertThat("Dropped Should be updated", droppedS, equalTo("Dropped: 3"));
    	assertThat("Popped Should be updated", poppedS, equalTo("Popped: 4"));;
    }
    
    @Test
    public void testNoUpdater() throws SlickException {
    	when(game.getWidth()).thenReturn(500);
    	when(font.getWidth("")).thenReturn(50);
    	screen = new PausedScreen();    	
    	screen.init(game, sbg);
    	((ScoreScreen)screen).update(3,"Henk",2,3,4);
    	Text[] name = Whitebox.getInternalState(screen, "playername");
    	String nameS = Whitebox.getInternalState(name[0], "text");
    	assertFalse("Name Should be updated", nameS.equals("Player: Henk"));
    }
    
    @Test
    public void testGetNames() throws SlickException {
    	when(game.getWidth()).thenReturn(500);
    	when(font.getWidth("")).thenReturn(50);
    	screen = new PausedScreen();    	
    	screen.init(game, sbg);    	
    	((ScoreScreen)screen).update(1,"Piet",2,3,4);
    	((ScoreScreen)screen).update(2,"Henk",2,3,4);
    	String[] nameS = ((ScoreScreen)screen).getPlayerNames();    	 
    	assertThat("Name Should be updated", nameS[0], equalTo("Piet"));
    	assertThat("Name Should be updated", nameS[1], equalTo("Henk"));
    }
    
    @Test
    public void testAmountUpdater1Player() throws SlickException {
    	when(game.getWidth()).thenReturn(500);
    	when(font.getWidth("")).thenReturn(50);
    	screen = new PausedScreen();    	
    	screen.init(game, sbg);   
    	((ScoreScreen)screen).update(2);
    	((ScoreScreen)screen).update(1);
    	int amount = ((ScoreScreen)screen).getPlayerAmount();    	 
    	assertThat("Name Should be updated", amount, equalTo(1));
    	assertThat("Amount of texts should be like expected", screen.getTexts().size(), equalTo(5));
    }
    
    @Test
    public void testAmountUpdater2Player() throws SlickException {
    	when(game.getWidth()).thenReturn(500);
    	when(font.getWidth("")).thenReturn(50);
    	screen = new PausedScreen();    	
    	screen.init(game, sbg);    	
    	((ScoreScreen)screen).update(1);
    	((ScoreScreen)screen).update(2);
    	int amount = ((ScoreScreen)screen).getPlayerAmount();    	 
    	assertThat("Name Should be updated", amount, equalTo(2));
    	assertThat("Amount of texts should be like expected", screen.getTexts().size(), equalTo(9));
    }
    @Test
    public void testAmountUpdater3Player() throws SlickException {
    	when(game.getWidth()).thenReturn(500);
    	when(font.getWidth("")).thenReturn(50);
    	screen = new PausedScreen();    	
    	screen.init(game, sbg);   
    	((ScoreScreen)screen).update(1);
    	((ScoreScreen)screen).update(1);
    	((ScoreScreen)screen).update(3);
    	int amount = ((ScoreScreen)screen).getPlayerAmount();    	 
    	assertThat("3 not allowed", amount, equalTo(1));
    }
}