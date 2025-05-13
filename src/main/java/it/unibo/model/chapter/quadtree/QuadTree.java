package it.unibo.model.chapter.quadtree;

import java.util.List;

import it.unibo.common.Circle;

/**
 * Models a data structure that allows the retrieval of points inside a
 * given range in O(logn) time complexity.
 * @param <T> the type of elements contained by the tree.
 */
public interface QuadTree<T> {
    /**
     * 
     * @param point the point to insert in the tree.
     * @return true if the point was inserted.
     */
    boolean insert(Point<T> point);

    /**
     * 
     * @param range the range to check.
     * @return the list of points that are inside the range.
     */
    List<Point<T>> query(Circle range);
}
