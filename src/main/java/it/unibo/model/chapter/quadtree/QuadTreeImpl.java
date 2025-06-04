package it.unibo.model.chapter.quadtree;

import java.util.ArrayList;
import java.util.List;

import it.unibo.common.Circle;
import it.unibo.common.Position;
import it.unibo.common.Rectangle;
import it.unibo.common.RectangleImpl;

/**
 * Implementation of a quad tree that allows insertion and queries.
 * @param <T> the type of elements contained by the tree.
 */
public final class QuadTreeImpl<T> implements QuadTree<T> {
    private static final int CAPACITY = 10;
    private final Rectangle boundary;
    private final List<Point<T>> points = new ArrayList<>(CAPACITY);
    private boolean isDivided;
    private QuadTree<T> northWest;
    private QuadTree<T> northEast;
    private QuadTree<T> southWest;
    private QuadTree<T> southEast;

    /**
     * Creates a new QuadTree object with a given tree.
     * 
     * @param boundary the boundary of the node.
     */
    public QuadTreeImpl(final Rectangle boundary) {
        this.boundary = boundary;
    }

    @Override
    public boolean insert(final Point<T> point) {
        if (!boundary.contains(point.position())) {
            return false;
        }
        if (points.size() < CAPACITY && !isDivided) {
            points.add(point);
            return true;
        }
        if (!isDivided) {
            subdivide();
        }
        return northWest.insert(point)
            || northEast.insert(point)
            || southWest.insert(point)
            || southEast.insert(point);
    }

    private void subdivide() {
        final double x = this.boundary.topLeftCorner().x();
        final double y = this.boundary.topLeftCorner().y();
        final double h = this.boundary.height();
        final double w = this.boundary.width();

        final Rectangle ne = new RectangleImpl(new Position(x + w / 2, y), w / 2, h / 2);
        this.northEast = new QuadTreeImpl<>(ne);
        final Rectangle nw = new RectangleImpl(new Position(x, y), w / 2, h / 2);
        this.northWest = new QuadTreeImpl<>(nw);
        final Rectangle se = new RectangleImpl(new Position(x + w / 2, y + h / 2), w / 2, h / 2);
        this.southEast = new QuadTreeImpl<>(se);
        final Rectangle sw = new RectangleImpl(new Position(x, y + h / 2), w / 2, h / 2);
        this.southWest = new QuadTreeImpl<>(sw);
        this.isDivided = true;
    }

    @Override
    public List<Point<T>> query(final Circle range) {
        final List<Point<T>> found = new ArrayList<>();
        if (!range.intersects(this.boundary)) {
            return found;
        }
        for (final Point<T> point : this.points) {
            if (range.contains(point.position())) {
                found.add(point);
            }
        }
        if (isDivided) {
            found.addAll(northWest.query(range));
            found.addAll(northEast.query(range));
            found.addAll(southWest.query(range));
            found.addAll(southEast.query(range));
        }
        return found;
    }

}
