package it.unibo.model.tile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.common.DirectionEnum;

/**
 * Class that tests the Tile class.
 */
class TileTest {

    private Tile tile;

    @BeforeEach
    void initialize() {
        tile = new TileImpl();
    }

    @Test
    void neigboursTest() {
        assertTrue(tile.getNeighbours().isEmpty());
        final var neighbour = new TileImpl();
        assertNotEquals(tile, neighbour);
        tile.addNeighbour(neighbour, DirectionEnum.UP);
        assertTrue(tile.getNeighbour(DirectionEnum.UP).isPresent());
        assertEquals(neighbour, tile.getNeighbour(DirectionEnum.UP).get());
        assertFalse(tile.getNeighbour(DirectionEnum.RIGHT).isPresent());
        final var neighbours = tile.getNeighbours();
        assertEquals(1, neighbours.size());
        assertEquals(neighbour, neighbours.get(DirectionEnum.UP));
        tile.addNeighbour(neighbour, DirectionEnum.UP);
        assertEquals(1, tile.getNeighbours().size());
        final var neighbour2 = new TileImpl();
        tile.addNeighbour(neighbour2, DirectionEnum.RIGHT);
        assertEquals(2, tile.getNeighbours().size());
    }

    @Test
    void collapseTest() {
        assertNotEquals(1, tile.getPossibleTiles().size());
        tile.collapse();
        assertEquals(1, tile.getPossibleTiles().size());
    }

    @Test
    void isWalkableTest() {
        assertThrowsExactly(IllegalStateException.class, new Executable() {
            public void execute() throws Throwable {
                tile.isWalkable();
            }       
        });
        tile.collapse();
        assertDoesNotThrow(new Executable() {
            public void execute() throws Throwable {
                tile.isWalkable();
            }       
        });
        assertNotNull(tile.isWalkable());
    }

    @Test
    void getSpriteTest() {
        assertThrowsExactly(IllegalStateException.class, new Executable() {
            public void execute() throws Throwable {
                tile.getSprite();
            }       
        });
        tile.collapse();
        assertDoesNotThrow(new Executable() {
            public void execute() throws Throwable {
                tile.getSprite();
            }       
        });
        var a = tile.getSprite();
        assertNotNull(a);
    }

    @Test
    void getEntropyTest() {
        assertEquals(TileType.values().length, tile.getEntropy());
        tile.collapse();
        assertEquals(0, tile.getEntropy());
    }

}
