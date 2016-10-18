/*
 * File: BubbleStorageIterator.java
 *
 * Class: BubbleStorageIterator
 *
 * Version: 0.0.1
 *
 * Date: October 11th, 2016
 *
 */


package bustamove.bubble;

import java.util.LinkedList;

import bustamove.util.Iterator;
import bustamove.util.ArrayIterator;
import bustamove.util.LinkedListIterator;

/**
 * BubbleStorageIterator is an iterator realization for BubbleStorage.
 *
 * @author Calvin Nhieu
 *
 */
public class BubbleStorageIterator implements Iterator<AbstractBubble> {
    private LinkedList<AbstractBubble[]> bubbles;
    private LinkedListIterator<AbstractBubble[]> listIt;
    private ArrayIterator<AbstractBubble> rowIt;

    /**
     * Creates BubbleStorageIterator instance.
     * @param list  LinkedList of arrays to iterate over.
     */
    public BubbleStorageIterator(final LinkedList<AbstractBubble[]> list) {
        this.bubbles = list;
        listIt = new LinkedListIterator<AbstractBubble[]>(bubbles);
        rowIt = null;
        if (listIt.hasNext()) {
            rowIt = new ArrayIterator<AbstractBubble>(listIt.next());
            while (!rowIt.hasNext() && listIt.hasNext()) {
                rowIt = new ArrayIterator<AbstractBubble>(listIt.next());
            }
        }
    }
    /**
     * Returns the next AbstractBubble in the iteration.
     * @return AbstractBubble  next AbstractBubble.
     */
    public final AbstractBubble next() {
        AbstractBubble bubble = rowIt.next();
        if (!rowIt.hasNext() && listIt.hasNext()) {
            rowIt = new ArrayIterator<AbstractBubble>(listIt.next());
        }
        return bubble;
    }
    /**
     * Returns if there is a next AbstractBubble in the iteration.
     * @return boolean  true if there is a next AbstractBubble, false otherwise.
     */
    public final boolean hasNext() {
        if (rowIt != null && rowIt.hasNext()) {
          return true;
        }
        return false;
    }
}
