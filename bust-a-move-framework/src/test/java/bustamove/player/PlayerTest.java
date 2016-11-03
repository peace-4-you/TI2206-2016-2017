package bustamove.player;

import bustamove.util.PlayerObserver;

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
    private PlayerObserver tester;

    private String name;
    private int playerid;

    @Parameterized.Parameters
    public static Object[] input() {
        return new Object[][]{{"TestPlayer",1}};
    }

    public PlayerTest(String s, int id) {
        name = s;
        playerid = id;
        player = new Player(name,playerid);
    }

    @Before
    public void setUp() {
        tester = new TestObserver();
    }

    @Test
    public void setName() {
        player.setName("NewTestPlayer");
        assertThat("Name should be set", player.getName(), equalTo("NewTestPlayer"));
    }

    @Test
    public void reset() {
        player.reset();
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
        assertFalse("Observer should be removed", player.getObservers().contains(tester));
    }

    @Test
    public void notifyObserver() {
        player.registerObserver(tester);
        player.setName("tester");
        player.notifyObserver();
        assertThat("Observer should be notified",((TestObserver) tester).name, equalTo("Tester"));
    }
    @Test
    public void notifyAmountObserver() {
        player.registerObserver(tester);
        player.notifyAmountObserver(2);
        assertThat("Observer should be notified",((TestObserver) tester).amount, equalTo(2));
    }
}