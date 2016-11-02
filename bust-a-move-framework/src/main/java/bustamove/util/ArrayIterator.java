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
package bustamove.util;

import java.lang.reflect.Array;

/**
 * ArrayIterator is an Iterator realized for arrays
 * with null element support.
 *
 * @param <T> Type of object to be iterated over
 * @author Calvin Nhieu
 */
public class ArrayIterator<T> implements Iterator<T> {
    private T[] items;
    private int position;

    /**
     * Creates ArrayIterator instance.
     *
     * @param array array to iterate over.
     */
    public ArrayIterator(final T[] array) {
        T[] copy = (T[]) Array.newInstance(
                array.getClass().getComponentType(), array.length);
        for (int i = 0; i < array.length; i++) {
            Array.set(copy, i, array[i]);
        }
        this.items = copy;
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
            position++;
            if (position >= items.length) {
                return;
            }
        }
    }

    /**
     * Returns the next item in the iteration.
     *
     * @return T  next item.
     */
    public final T next() {
        T item = items[position];
        position++;
        advanceToNext();
        return item;
    }

    /**
     * Returns if there is a next item in the iteration.
     *
     * @return boolean  true if there is a next item.
     */
    public final boolean hasNext() {
        if (position >= items.length) {
            return false;
        }
        return true;
    }

    /**
     * Returns the index of the next element.
     *
     * @return int  index of the next element.
     */
    public final int getPosition() {
        return position;
    }
}
