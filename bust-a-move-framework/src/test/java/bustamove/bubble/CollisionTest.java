package bustamove.bubble;

import bustamove.game.GameData;
import bustamove.player.Player;
import bustamove.player.Score;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.newdawn.slick.geom.Circle;
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
@PrepareForTest({Collision.class, SimpleBubble.class, PopBehaviour.class})
public class CollisionTest {
    private Collision collision;
    private BubbleStorage bubbleStorage;
    private PopBehaviour popBehaviour;
    private Score score;

    @Mock
    private Bubble bubble;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        collision = new Collision(0, 200, 0);
        bubbleStorage = new BubbleStorage(0, 0, GameData.GameDifficulty.NORMAL);
        score = new Score(new Player("name", 0));
        popBehaviour = new PopBehaviour(score);
        popBehaviour.setBubbleStorage(bubbleStorage);
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
    public void checkCollisionCalls_hitWallTest() throws Exception {
        Collision collisionSpy = PowerMockito.spy(new Collision(0, 200, 0));
        collisionSpy.setBubbleStorage(bubbleStorage);
        collisionSpy.setPopBehaviour(popBehaviour);

        // Stub bubble methods
        Bubble bubble = PowerMockito.mock(Bubble.class);
        Circle mockCircle = new Circle(0, 170, 35);
        Mockito.when(bubble.getBoundingBox()).thenReturn(mockCircle);

        // Condition 1: xPos <= wallPos && !(xPos + Bubble.DIAMETER >= xPosRightWall)
        Mockito.when(bubble.getX()).thenReturn(0.0f);
        collisionSpy.checkCollision(bubble);
        PowerMockito.verifyPrivate(collisionSpy, times(1)).invoke("checkWallCollision", bubble);
        verify(bubble, times(1)).hitWall();

        // Condition 2: !(xPos <= wallPos) && xPos + Bubble.DIAMETER >= xPosRightWall
        // times(2) because mockito remember the previous test
        Mockito.when(bubble.getX()).thenReturn(500.0f);
        collisionSpy.checkCollision(bubble);
        PowerMockito.verifyPrivate(collisionSpy, times(2)).invoke("checkWallCollision", bubble);
        verify(bubble, times(2)).hitWall();

        // Condition 3: !(xPos <= wallPos) && !(xPos + Bubble.DIAMETER >= xPosRightWall)
        // times(2) because mockito remember the previous test
        Mockito.when(bubble.getX()).thenReturn(30.0f);
        collisionSpy.checkCollision(bubble);
        PowerMockito.verifyPrivate(collisionSpy, times(3)).invoke("checkWallCollision", bubble);
        verify(bubble, times(2)).hitWall();
    }

    @Test
    public void checkCollisionCalls_landBubble_popBubbles_withYEqualsZeroTest() throws Exception {
        Collision collisionSpy = PowerMockito.spy(new Collision(0, 200, 0));
        PopBehaviour popBehaviourSpy = PowerMockito.spy(new PopBehaviour(score));
        popBehaviourSpy.setBubbleStorage(bubbleStorage);

        collisionSpy.setBubbleStorage(bubbleStorage);
        collisionSpy.setPopBehaviour(popBehaviourSpy);

        // Stub bubble methods
        Bubble bubble = PowerMockito.mock(Bubble.class);
        Circle mockCircle = new Circle(0, 200, 35);
        Mockito.when(bubble.getBoundingBox()).thenReturn(mockCircle);

        // Test 1: getY() = 0
        Mockito.when(bubble.getY()).thenReturn(0.0f);
        collisionSpy.checkCollision(bubble);
        PowerMockito.verifyPrivate(collisionSpy, times(1)).invoke("landBubble", bubble);
        verify(popBehaviourSpy, times(1)).popBubbles(bubble);
    }

    @Test
    public void checkCollisionCalls_landBubble_popBubbles_withYEqualsTwoHundredTest() throws Exception {
        Collision collisionSpy = PowerMockito.spy(new Collision(0, 200, 0));
        PopBehaviour popBehaviourSpy = PowerMockito.spy(new PopBehaviour(score));
        popBehaviourSpy.setBubbleStorage(bubbleStorage);

        collisionSpy.setBubbleStorage(bubbleStorage);
        collisionSpy.setPopBehaviour(popBehaviourSpy);

        // Stub bubble methods
        Bubble bubble = PowerMockito.mock(Bubble.class);
        Circle mockCircle = new Circle(0, 200, 35);
        Mockito.when(bubble.getBoundingBox()).thenReturn(mockCircle);

        // Test 2: getY() > 0
        Mockito.when(bubble.getY()).thenReturn(200.0f);
        collisionSpy.checkCollision(bubble);
        PowerMockito.verifyPrivate(collisionSpy, times(0)).invoke("landBubble", bubble);
        verify(popBehaviourSpy, times(0)).popBubbles(bubble);
    }

    @Test
    public void checkCollisionCalls_checkGridCollision() throws Exception {
        Collision collisionSpy = PowerMockito.spy(new Collision(0, 200, 0));
        collisionSpy.setBubbleStorage(bubbleStorage);
        collisionSpy.setPopBehaviour(popBehaviour);

        // Stub bubble methods
        Bubble bubble = PowerMockito.mock(Bubble.class);
        Circle mockCircle = new Circle(0, 170, 35);
        Mockito.when(bubble.getBoundingBox()).thenReturn(mockCircle);

        collisionSpy.checkCollision(bubble);

        PowerMockito.verifyPrivate(collisionSpy, times(1)).invoke("checkGridCollision", bubble);
    }
}
