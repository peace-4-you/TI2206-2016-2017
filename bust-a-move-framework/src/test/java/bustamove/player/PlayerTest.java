package bustamove.player;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Before;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Score Tester
 * @author Maurice Willemsen
 * @version 17.10.2016
 */
@RunWith(Parameterized.class)
public class PlayerTest {

	private Player player;
	private TestObserver tester;
	
    @Parameterized.Parameters
    public static Object[] input() {
        return new Object[][]{{new Player("TestPlayer",0,1)}};
    }

    public PlayerTest(Object expected) {
    	player = (Player) expected;
    }

    @Before
    public void setUp() {
    	tester = new TestObserver();
    }    
    
    @Test
    public void setName() {
    	player.setName("NewTestPlayer");
    	assertThat("Score should be added", player.getName(), equalTo("NewTestPlayer"));		
    }
        
    @Test
    public void setCombo() {
    	player.setCombo(2);
    	assertThat("Score should be added", player.getCombo(), equalTo(2));		
    }
    
    @Test
    public void reset() {
    	player.reset();
    	assertThat("Combo should be 1", player.getCombo(), equalTo(1));		
    	assertThat("Score should be 0", player.getScore().getScore(), equalTo(0));	
    }
    
    @Test
    public void addObserver() {
    	player.registerObserver(tester);
    	assertTrue("Observer should be registered", player.getObservers().contains(tester));		
    }
    
    @Test
    public void removeObserver() {
    	player.registerObserver(tester);
    	player.removeObserver(tester);
    	assertFalse("Observer should be registered", player.getObservers().contains(tester));		
    }
    
    @Test
    public void notifyObserver() {
    	player.registerObserver(tester);
    	player.setName("tester");
    	player.notifyObserver();    	
    	assertThat("Observer should be registered",tester.name, equalTo("tester"));		
    }
    @Test
    public void notifyAmountObserver() {
    	player.registerObserver(tester);
    	player.notifyAmountObserver(2);    	
    	assertThat("Observer should be registered",tester.amount, equalTo(2));		
    }
}
