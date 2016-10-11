/*
 * File: ArrayIterator.java
 *
 * Class: ArrayIterator
 *
 * Version: 0.0.1
 *
 * Date: October 11th, 2016
 *
 */


package bust_a_move.util;

/**
 * ArrayIterator is an Iterator realized for arrays
 * with null element support.
 *
 * @author Calvin Nhieu
 *
 */
public class ArrayIterator<T> implements Iterator<T> {
    private T[] items;
    private int position;

    /**
     * Creates ArrayIterator instance.
     * @param array  array to iterate over.
     */
    public ArrayIterator(T[] array) {
        this.items = array;
        this.position = 0;
        advanceToNext();
    }
    /**
     * Advances iterator to next non null item.
     */
    private void advanceToNext() {
        if (position >= items.length) {
            return;
        }

        while (items[position] == null) {
            if (position >= items.length) {
                return;
            }
            position++;
        }
    }
    /**
     * Returns the next item in the iteration.
     * @return T  next item.
     */
    public T next() {
        T item = items[position];
        position++;
        advanceToNext();
        return item;
    }
    /**
     * Returns if there is a next item in the iteration.
     * @return boolean  true if there is a next item.
     */
    public boolean hasNext() {
        if (position >= items.length) {
            return false;
        }
        return true;
    }
}
