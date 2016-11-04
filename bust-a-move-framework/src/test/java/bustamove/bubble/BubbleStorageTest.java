package bustamove.bubble;

import bustamove.player.Player;
import bustamove.player.Score;
import bustamove.screen.config.GameConfig;
import bustamove.game.GameData.GameDifficulty;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import bustamove.bubble.Bubble.ColorChoice;

import java.util.LinkedList;

/**
 * Created by Jason Xie on 13/10/2016.
 */
public class BubbleStorageTest {

    private BubbleStorage bubbleStorage;
    private Collision collision;

    @Before
    public void setUp() {
        collision = new Collision(0, 200, 0);
        bubbleStorage = new BubbleStorage(200, 200, GameDifficulty.NORMAL);
        bubbleStorage.initRows(5);
        collision.setBubbleStorage(bubbleStorage);
    }

    @Test
    public void getColors() {
        ColorChoice[] colors = ColorChoice.values();
        LinkedList<ColorChoice> arenaColors = bubbleStorage.getColorsOnArena();
        for (int i = 0; i < GameConfig.NUM_COLORS_NORMAL; i++) {
            assertTrue(arenaColors.contains(ColorChoice.values()[i]));
        }
        assertEquals(GameConfig.NUM_COLORS_NORMAL, arenaColors.size());
    }

    @Test
    public void getAtIndex() {
        assertNotNull(bubbleStorage.get(2));
        assertNull(bubbleStorage.get(5));
    }

    @Test
    public void notFullOrFull() {
        assertFalse(bubbleStorage.isFull());
        assertFalse(bubbleStorage.isEmpty());
    }

    @Test
    public void isFull() {
        for (int i = 0; i < 7; i++) {
            bubbleStorage.addEmptyBubbleRowBelow();
        }
        assertTrue(bubbleStorage.isFull());
    }

    @Test
    public void storageDimensions() {
        assertEquals(12, bubbleStorage.getHeight());
        assertEquals(8, bubbleStorage.getWidth());
    }

    @Test
    public void addEmptyRowsBelow() {
        bubbleStorage.addEmptyBubbleRowBelow();
        assertEquals(6, bubbleStorage.size());
        assertEquals(7, bubbleStorage.get(5).length);
        bubbleStorage.addEmptyBubbleRowBelow();
        assertEquals(7, bubbleStorage.size());
        assertEquals(8, bubbleStorage.get(6).length);
    }

    @Test
    public void addBubbleRow() {
        assertEquals(8, bubbleStorage.get(0).length);
        bubbleStorage.addBubbleRow();
        assertEquals(6, bubbleStorage.size());
        assertEquals(7, bubbleStorage.get(0).length);
        bubbleStorage.addEmptyBubbleRowBelow();
        bubbleStorage.addBubbleRow();
        assertEquals(8, bubbleStorage.size());
        assertEquals(8, bubbleStorage.get(0).length);
    }

    @Test
    public void getNeighbours() {
        // Row width 7
        Bubble[] neighbours = bubbleStorage.getNeighbors(3, 4);
        for (Bubble b : neighbours) {
            assertTrue(b instanceof Bubble);
        }
        // Row width 8
        neighbours = bubbleStorage.getNeighbors(2, 4);
        for (Bubble b : neighbours) {
            assertTrue(b instanceof Bubble);
        }
        // Invalid case
        neighbours = bubbleStorage.getNeighbors(5, 4);
        for (Bubble b : neighbours) {
            assertEquals(null, b);
        }
    }

    @Test
    public void getRow() {
        assertEquals(0, bubbleStorage.getRow(200));
        assertEquals(1, bubbleStorage.getRow(220));
        assertEquals(3, bubbleStorage.getRow(200 + 3 * GameConfig.ROW_OFFSET));
        assertEquals(42, bubbleStorage.getRow(200 + 42 * GameConfig.ROW_OFFSET));
    }

    @Test
    public void getColumn() {
        Bubble b = bubbleStorage.get(2)[3];
        assertEquals(3, bubbleStorage.getColumn(b.getX(), b.getY()));

        b = bubbleStorage.get(1)[4];
        assertEquals(4, bubbleStorage.getColumn(b.getX(), b.getY()));
    }

    @Test
    public void removeBubble() {
        Bubble b = bubbleStorage.get(2)[3];
        bubbleStorage.removeBubble(b);
        assertNull(bubbleStorage.get(2)[3]);
    }

    @Test
    public void emptyStorage() {
        for (int i = 0; i < bubbleStorage.size(); i++) {
            Bubble[] row = bubbleStorage.get(i);
            for (int j = 0; j < row.length; j++) {
                bubbleStorage.removeBubble(row[j]);
            }
        }
        assertTrue(bubbleStorage.isEmpty());
    }
}
