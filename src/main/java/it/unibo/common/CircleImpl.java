package it.unibo.common;

/**
 * Implementation of a circle that has a center and a radious and can intersects
 * with other circles.
 */
public final class CircleImpl implements Circle {
    private int centerX;
    private int centerY;
    private double radious;

    /**
     * 
     * @param centerX x coordinate of the center.
     * @param centerY y coordinate of the center.
     * @param radious radious of the circle.
     */
    public CircleImpl(final int centerX, final int centerY, final double radious) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radious = radious;
    }

    /**
     * Copy constructor.
     * @param circle the circle to copy.
     */
    public CircleImpl(final Circle circle) {
        this.centerX = circle.getCenter().x();
        this.centerY = circle.getCenter().y();
        this.radious = circle.getRadious();
    }

    @Override
    public boolean intersects(final Circle other) {
        final Position otherCenter = other.getCenter();
        final int xDiff = this.centerX - otherCenter.x();
        final int yDiff = this.centerY - otherCenter.y();
        return xDiff * xDiff + yDiff * yDiff <= 4 * radious * radious;
    }

    @Override
    public Position getCenter() {
        return new Position(centerX, centerY);
    }

    @Override
    public double getRadious() {
        return radious;
    }

    @Override
    public void setCenter(final int newCenterX, final int newCenterY) {
        centerX = newCenterX;
        centerY = newCenterY;
    }

    @Override
    public void setRadious(final double newRadious) {
        radious = newRadious;
    }

}
