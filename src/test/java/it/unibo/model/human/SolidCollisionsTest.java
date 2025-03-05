package it.unibo.model.human;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.view.screen.ScreenImpl;

/**
 * Class that tests the solid collisions.
 */
class SolidCollisionsTest {

    private Map map;
    private Player human;
    private Position initialPosition;

    /**
     * Initialize map, human and initialPosition.
     */
    @BeforeEach
    void initialize() {
        this.map = new MapImpl();
        final var tiles = map.getTiles();
        boolean positionAssigned = false;
        for (int r = 0; r < tiles.length && !positionAssigned; r++) {
            for (int c = 0; c < tiles[r].length && !positionAssigned; c++) {
                if (tiles[r][c].isWalkable()) {
                    initialPosition = new Position(r * ScreenImpl.TILE_SIZE, c * ScreenImpl.TILE_SIZE);
                    positionAssigned = true;
                }
            }
        }
        this.human = new PlayerImpl(initialPosition, map);
    }

    @Test
    void testCanMove() {
        final Position pos = human.getPosition();
        assertTrue(map.getTileFromPixel(pos.x(), pos.y()).isWalkable());
        canMove(new Direction(false, true, false, false));
        canMove(new Direction(false, false, true, false));
    }

    private void canMove(final Direction dir) {
        human.setDirection(dir);
        human.move();
        assertNotEquals(initialPosition, human.getPosition());
    }

    @Test
    void testStayStill() {
        final Position pos = human.getPosition();
        assertTrue(map.getTileFromPixel(pos.x(), pos.y()).isWalkable());
        stayStill(new Direction(false, false, false, true));
        stayStill(new Direction(true, false, false, true));
    }

    private void stayStill(final Direction dir) {
        human.setDirection(dir);
        final Position pos = human.getPosition();
        for (int i = 0; i < 10; i++) {
            human.move();
        }
        assertTrue(map.getTileFromPixel(pos.x(), pos.y()).isWalkable());
    }

}
