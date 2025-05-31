package it.unibo.common;

/**
 * Models a circle and gives the possibility to check if it intersects with
 * others.
 */
public interface Circle {
    /**
     * 
     * @param other the circle to intersect.
     * @return if the circles intersects.
     */
    boolean intersects(Circle other);

    /**
     * 
     * @param other the rectangle to intersect.
     * @return if the circle intersects the rectangle.
     */
    boolean intersects(Rectangle other);

    /**
     * 
     * @param point the point to check.
     * @return if the point is inside the circle.
     */
    boolean contains(Position point);

    /**
     * 
     * @return the center of the circle.
     */
    Position getCenter();

    /**
     * 
     * @return the radius of the circle.
     */
    double getRadius();

    /**
     * 
     * @param newCenterX new x coordinate of the center.
     * @param newCenterY new y coordinate of the center.
     */
    void setCenter(double newCenterX, double newCenterY);

    /**
     * 
     * @param newRadious the new radious of the circle.
     */
    void setRadius(double newRadious);
}
