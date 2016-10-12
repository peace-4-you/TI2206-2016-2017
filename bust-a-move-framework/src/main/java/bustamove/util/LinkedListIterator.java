/*
 * File: LinkedListIterator.java
 *
 * Class: LinkedListIterator
 *
 * Version: 0.0.1
 *
 * Date: October 11th, 2016
 *
 */


package bustamove.util;

import java.util.LinkedList;

/**
 * LinkedListIterator is an Iterator realized for LinkedLists.
 *
 * @param <T> type of object to be iterated over
 * @author Calvin Nhieu
 */
public class LinkedListIterator<T> implements Iterator<T> {
    private LinkedList<T> items;
    private int position;

    /**
     * Creates LinkedListIterator instance.
     *
     * @param list linked list to iterate over.
     */
    public LinkedListIterator(final LinkedList<T> list) {
        this.items = list;
        this.position = 0;
    }

    /**
     * Returns the next item in the iteration.
     *
     * @return T  next item.
     */
    public final T next() {
        T item = items.get(position);
        position++;
        return item;
    }

    /**
     * Returns if there is a next item in the iteration.
     *
     * @return boolean  true if there is a next item.
     */
    public final boolean hasNext() {
        if (position >= items.size()) {
            return false;
        }
        return true;
    }
}
