package it.unibo.common;

/**
 * Implementation of a circle that has a center and a radious and can intersects
 * with other circles.
 */
public final class CircleImpl implements Circle {
    private double centerX;
    private double centerY;
    private double radius;

    /**
     * 
     * @param centerX x coordinate of the center.
     * @param centerY y coordinate of the center.
     * @param radius radius of the circle.
     */
    public CircleImpl(final double centerX, final double centerY, final double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    /**
     * Copy constructor.
     * @param circle the circle to copy.
     */
    public CircleImpl(final Circle circle) {
        this.centerX = circle.getCenter().x();
        this.centerY = circle.getCenter().y();
        this.radius = circle.getRadius();
    }

    @Override
    public boolean intersects(final Circle other) {
        final Position otherCenter = other.getCenter();
        final double xDiff = this.centerX - otherCenter.x();
        final double yDiff = this.centerY - otherCenter.y();
        return xDiff * xDiff + yDiff * yDiff <= 4 * radius * radius;
    }

    @Override
    public boolean intersects(final Rectangle other) {
        final double rectX = other.topLeftCorner().x();
        final double rectY = other.topLeftCorner().y();
        final double width = other.width();
        final double height = other.height();

        final double closestX = Math.max(rectX, Math.min(this.centerX, rectX + width));
        final double closestY = Math.max(rectY, Math.min(this.centerY, rectY + height));
        final double dx = centerX - closestX;
        final double dy = centerY - closestY;
        final double distanceSquared = dx * dx + dy * dy;
        // Circle intersects the rectangle.
        if (distanceSquared <= (this.radius * this.radius)) {
            return true;
        }

        // Circle inside the rectangle.
        return (this.centerX - this.radius >= rectX)
            && (this.centerX + this.radius <= rectX + width)
            && (this.centerY - this.radius >= rectY)
            && (this.centerY + this.radius <= rectY + height);
    }

    @Override
    public Position getCenter() {
        return new Position(centerX, centerY);
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public void setCenter(final double newCenterX, final double newCenterY) {
        centerX = newCenterX;
        centerY = newCenterY;
    }

    @Override
    public void setRadius(final double newRadius) {
        radius = newRadius;
    }

}
