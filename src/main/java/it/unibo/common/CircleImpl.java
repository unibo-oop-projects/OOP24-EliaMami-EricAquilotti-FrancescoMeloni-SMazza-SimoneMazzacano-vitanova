package it.unibo.common;

/**
 * Implementation of a circle that has a center and a radious and can intersects
 * with other circles.
 */
public final class CircleImpl implements Circle {
    private static final long serialVersionUID = 4L;
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
        return distanceSquared(this.getCenter(), other.getCenter()) <= sumSquared(this.radius, other.getRadius());
    }

    @Override
    public boolean intersects(final Rectangle other) {
        final double rectX = other.topLeftCorner().x();
        final double rectY = other.topLeftCorner().y();
        final double width = other.width();
        final double height = other.height();

        final double closestX = Math.max(rectX, Math.min(this.centerX, rectX + width));
        final double closestY = Math.max(rectY, Math.min(this.centerY, rectY + height));
        return this.contains(new Position(closestX, closestY));
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

    @Override
    public boolean contains(final Position point) {
        return distanceSquared(this.getCenter(), point) <= (this.radius * this.radius);
    }

    private double distanceSquared(final Position a, final Position b) {
        final double dx = a.x() - b.x();
        final double dy = a.y() - b.y();
        return dx * dx + dy * dy;
    }

    private double sumSquared(final double a, final double b) {
        return a * a + 2 * a * b + b * b;
    }
}
