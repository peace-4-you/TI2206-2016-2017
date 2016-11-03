package bustamove.bubble;

import bustamove.player.Player;
import bustamove.player.Score;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by Winer Bao on 1/11/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Collision.class)
public class CollisionTest {
    private Collision collision;
    private BubbleStorage bubbleStorage;
    private Bubble bubble;
    private PopBehaviour popBehaviour;
    private Score score;

    @Before
    public void setUp() {
        collision = new Collision(0, 200, 0);
        bubbleStorage = new BubbleStorage(0, 0);
        bubble = new SimpleBubble(0, 170, Bubble.ColorChoice.BLUE, true);
        score = new Score(new Player("name", 0, 0));
        popBehaviour = new PopBehaviour(score);
    }

    @Test
    public void setBubbleStorageTest() {
        collision.setBubbleStorage(bubbleStorage);

        BubbleStorage bubbleStorage_t = Whitebox.getInternalState(collision,
                "bubbleStorage");

        assertEquals(bubbleStorage, bubbleStorage_t);
    }

    @Test
    public void setPopBehaviourTest() {
        collision.setPopBehaviour(popBehaviour);

        PopBehaviour popBehaviour_t = Whitebox.getInternalState(collision, "popBehaviour");

        assertEquals(popBehaviour, popBehaviour_t);
    }

    @Test
    public void checkCollisionTest() {
        Collision spyCollision = PowerMockito.spy(new Collision(0, 200, 0));
        when(spyCollision, method(Collision.class, "checkCollision", Bubble.class)).withArguments(Bubble.class);
        collision.checkCollision(bubble);

    }
}
