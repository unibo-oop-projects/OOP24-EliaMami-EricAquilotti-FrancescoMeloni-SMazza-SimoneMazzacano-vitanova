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
     * @return the center of the circle.
     */
    Position getCenter();

    /**
     * 
     * @return the radious of the circle.
     */
    double getRadious();

    /**
     * 
     * @param newCenterX new x coordinate of the center.
     * @param newCenterY new y coordinate of the center.
     */
    void setCenter(int newCenterX, int newCenterY);

    /**
     * 
     * @param newRadious the new radious of the circle.
     */
    void setRadious(double newRadious);
}
