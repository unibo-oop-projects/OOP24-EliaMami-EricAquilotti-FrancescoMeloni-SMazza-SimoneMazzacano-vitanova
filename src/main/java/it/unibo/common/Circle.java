package it.unibo.common;

/**
 * Models a circle and gives the possibility to check if it intersects with
 * others.
 */
public interface Circle {
    /**
     * Checks if the circle intersects other circle.
     * 
     * @param other the circle to intersect.
     * @return if the circles intersects.
     */
    boolean intersects(Circle other);

    /**
     * Check if the circle intersects a rectangle.
     * 
     * @param other the rectangle to intersect.
     * @return if the circle intersects the rectangle.
     */
    boolean intersects(Rectangle other);

    /**
     * Check if a point is inside the circle.
     * 
     * @param point the point to check.
     * @return if the point is inside the circle.
     */
    boolean contains(Position point);

    /**
     * Retrieve the center of the circle.
     * 
     * @return the center of the circle.
     */
    Position getCenter();

    /**
     * Retrieve the radius of the circle.
     * 
     * @return the radius of the circle.
     */
    double getRadius();

    /**
     * Changes the coordinates of the center.
     * 
     * @param newCenterX new x coordinate of the center.
     * @param newCenterY new y coordinate of the center.
     */
    void setCenter(double newCenterX, double newCenterY);

    /**
     * Changes the radius value.
     * 
     * @param newRadious the new radious of the circle.
     */
    void setRadius(double newRadious);
}
