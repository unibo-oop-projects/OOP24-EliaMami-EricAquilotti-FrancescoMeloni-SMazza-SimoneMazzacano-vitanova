package it.unibo.common;

/**
 * Implementation of a rectangle defined by the top-left corner, width and
 * height.
 */
public final class RectangleImpl implements Rectangle {
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    /**
     * Initialize fields.
     * @param topLeftCorner
     * @param width
     * @param height
     */
    public RectangleImpl(final Position topLeftCorner, final double width, final double height) {
        this.x = topLeftCorner.x();
        this.y = topLeftCorner.y();
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean contains(final Position point) {
        return point.x() >= this.x - this.width
            && point.x() <= this.x + this.width
            && point.y() >= this.y - this.height
            && point.y() <= this.y + this.height;
    }

    @Override
    public Position topLeftCorner() {
        return new Position(x, y);
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public double height() {
        return height;
    }

}
