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
public class BubbleStorageIterator implements Iterator<Bubble> {
    private LinkedList<Bubble[]> bubbles;
    private LinkedListIterator<Bubble[]> listIt;
    private ArrayIterator<Bubble> rowIt;

    /**
     * Creates BubbleStorageIterator instance.
     * @param list  LinkedList of arrays to iterate over.
     */
    public BubbleStorageIterator(final LinkedList<Bubble[]> list) {
        this.bubbles = list;
        listIt = new LinkedListIterator<Bubble[]>(bubbles);
        rowIt = null;
        if (listIt.hasNext()) {
            rowIt = new ArrayIterator<Bubble>(listIt.next());
            while (!rowIt.hasNext() && listIt.hasNext()) {
                rowIt = new ArrayIterator<Bubble>(listIt.next());
            }
        }
    }
    /**
     * Returns the next Bubble in the iteration.
     * @return Bubble  next Bubble.
     */
    public final Bubble next() {
        Bubble bubble = rowIt.next();
        if (!rowIt.hasNext() && listIt.hasNext()) {
            rowIt = new ArrayIterator<Bubble>(listIt.next());
        }
        return bubble;
    }
    /**
     * Returns if there is a next Bubble in the iteration.
     * @return boolean  true if there is a next Bubble, false otherwise.
     */
    public final boolean hasNext() {
        if (rowIt != null && rowIt.hasNext()) {
          return true;
        }
        return false;
    }
}
