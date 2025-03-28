package it.unibo.model.human;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.controller.InputHandlerImpl;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.human.strategies.movement.PlayerMovementStrategy;
import it.unibo.view.screen.ScreenImpl;

/**
 * Class that tests the solid collisions.
 */
class SolidCollisionsTest {

    private final InputHandler inputHandler = new InputHandlerImpl();
    private final JPanel dummyPanel = new JPanel();
    private final Map map = new MapImpl();
    private Player human;
    private Position initialPosition;

    /**
     * Initialize map, human and initialPosition.
     */
    @BeforeEach
    void initialize() {
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
        this.human = new PlayerImpl(initialPosition, map, new PlayerMovementStrategy(inputHandler));
    }

    @Test
    void testCanMove() {
        final Position pos = human.getPosition();
        assertTrue(map.getTileFromPixel(pos.x(), pos.y()).isWalkable());
        inputHandler.keyPressed(getKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_UP));
        canMove();
        inputHandler.keyReleased(getKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_UP));
        inputHandler.keyPressed(getKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT));
        canMove();
        inputHandler.keyReleased(getKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_LEFT));
    }

    private void canMove() {
        human.move();
        assertNotEquals(initialPosition, human.getPosition());
    }

    @Test
    void testStayStill() {
        final Position pos = human.getPosition();
        assertTrue(map.getTileFromPixel(pos.x(), pos.y()).isWalkable());
        inputHandler.keyPressed(getKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_UP));
        inputHandler.keyPressed(getKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN));
        stayStill();
        inputHandler.keyReleased(getKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_UP));
        inputHandler.keyReleased(getKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_DOWN));

        inputHandler.keyPressed(getKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN));
        stayStill();
        inputHandler.keyReleased(getKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_DOWN));
    }

    private void stayStill() {
        final Position pos = human.getPosition();
        for (int i = 0; i < 10; i++) {
            human.move();
        }
        assertTrue(map.getTileFromPixel(pos.x(), pos.y()).isWalkable());
    }

    private KeyEvent getKeyEvent(final int id, final int keycode) {
        return new KeyEvent(dummyPanel, id, System.currentTimeMillis(), 0,
                            keycode, KeyEvent.CHAR_UNDEFINED);
    }
}
