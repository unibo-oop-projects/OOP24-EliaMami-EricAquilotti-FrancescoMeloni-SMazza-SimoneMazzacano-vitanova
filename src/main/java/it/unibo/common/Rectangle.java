package it.unibo.common;

/**
 * Models a rectangle that can intersect with a circle. This intersection is
 * needed by the QuadTree in order to answer queries.
 */
public interface Rectangle {
    /**
     * 
     * @return the position of the top-left corner.
     */
    Position topLeftCorner();

    /**
     * 
     * @param point
     * @return if the point is inside the rectangle.
     */
    boolean contains(Position point);

    /**
     * 
     * @return the width of the rectangle.
     */
    double width();

    /**
     * 
     * @return the height of the rectangle.
     */
    double height();
}
