package it.unibo.common;

/**
 * Models a rectangle that can intersect with a circle. This intersection is
 * needed by the QuadTree in order to answer queries.
 */
public interface Rectangle {
    /**
     * Retrieves the position of the top left corner.
     * 
     * @return the position of the top-left corner.
     */
    Position topLeftCorner();

    /**
     * Checks if a point is contained in the rectangle.
     * 
     * @param point the point to check.
     * @return if the point is inside the rectangle.
     */
    boolean contains(Position point);

    /**
     * Retrieves the width of the rectangle.
     * 
     * @return the width of the rectangle.
     */
    double width();

    /**
     * Retrieves the height of the rectangle.
     * 
     * @return the height of the rectangle.
     */
    double height();
}
