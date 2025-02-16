package it.unibo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.common.Position;
import it.unibo.common.Rectangle;
import it.unibo.common.RectangleImpl;

/**
 * Class that tests the functionalities of the Rectangle class.
 */
class RectangleTest {
    @Test
    void containsPointTest() {
        final Rectangle rectangle = new RectangleImpl(new Position(0, 0), 2, 3);
        // inside
        assertTrue(rectangle.contains(new Position(1, 1)));
        // edge
        assertTrue(rectangle.contains(new Position(2, 3)));
        // outside
        assertFalse(rectangle.contains(new Position(3, 3)));
    }
}
