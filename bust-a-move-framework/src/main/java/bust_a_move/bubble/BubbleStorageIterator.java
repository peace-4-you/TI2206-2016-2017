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


package bust_a_move.bubble;

import java.util.LinkedList;

import bust_a_move.util.Iterator;
import bust_a_move.util.ArrayIterator;
import bust_a_move.util.LinkedListIterator;

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
     * @param elements  LinkedList of arrays to iterate over.
     */
    public BubbleStorageIterator(LinkedList<Bubble[]> list) {
        this.bubbles = list;
        listIt = new LinkedListIterator<Bubble[]>(bubbles);
        rowIt = new ArrayIterator<Bubble>(listIt.next());
    }
    /**
     * Returns the next Bubble in the iteration.
     * @return Bubble  next Bubble.
     */
    public Bubble next() {
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
    public boolean hasNext() {
        if (rowIt.hasNext()) {
          return true;
        }
        return false;
    }
}
