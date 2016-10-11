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


package bustamove.util;

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
    T next();
    /**
     * Returns if there is a next element.
     * @return  true if there is a next element, false otherwise.
     */
    boolean hasNext();
}
