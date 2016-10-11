/*
 * File: Iterator.java
 *
 * Interface: Iterator
 *
 * Version: 0.0.1
 *
 * Date: October 11th, 2016
 *
 */


package bust_a_move.util;

/**
 * Iterator interface makes an object iterable.
 *
 * @author Calvin Nhieu
 *
 */
public interface Iterator<T> {
    /**
     * Returns next element in iteration.
     * @return  next Object.
     */
    public T next();
    /**
     * Returns if there is a next element.
     * @return  true if there is a next element, false otherwise.
     */
    public boolean hasNext();
}
