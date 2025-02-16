package it.unibo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Position;
import it.unibo.common.RectangleImpl;

/**
 * Class that tests the functionalities of the Circle class.
 */
class CircleTest {

    @Test
    void intersectionWithCircleTest() {
        final Circle circle = new CircleImpl(0, 0, 2);
        // basic intersection
        assertTrue(circle.intersects(new CircleImpl(0, 3, 2)));
        // one point intersection
        assertTrue(circle.intersects(new CircleImpl(0, 3, 1)));
        // full contained circle
        assertTrue(circle.intersects(new CircleImpl(0, 0, 1)));
        // no intersection
        assertFalse(circle.intersects(new CircleImpl(0, 4, 1)));
    }

    @Test
    void containsPointTest() {
        final Circle circle = new CircleImpl(0, 0, 2);
        // inside
        assertTrue(circle.contains(new Position(1, 0)));
        // edge
        assertTrue(circle.contains(new Position(0, 2)));
        // outside
        assertFalse(circle.contains(new Position(1, 3)));
    }

    @Test
    void intersectionWithRectangleTest() {
        final Circle circle = new CircleImpl(0, 0, 2);
        // basic intersection
        assertTrue(circle.intersects(new RectangleImpl(new Position(0, 0), 3, 3)));
        // rectangle full inside
        assertTrue(circle.intersects(new RectangleImpl(new Position(-1, -1), 2, 2)));
        // no intersection
        assertFalse(circle.intersects(new RectangleImpl(new Position(2, 3), 3, 3)));
    }
}
