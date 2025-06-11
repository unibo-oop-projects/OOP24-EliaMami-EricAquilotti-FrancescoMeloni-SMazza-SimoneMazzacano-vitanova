package it.unibo.model.human;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.human.solidcollisions.SimpleSolidCollisions;
import it.unibo.model.human.solidcollisions.SolidCollisions;
import it.unibo.model.tile.Tile;

/**
 * Class that tests the solid collisions.
 */
class SolidCollisionsTest {

    private final Map map = new MapImpl(32, 32);
    private final SolidCollisions solidCollisions = new SimpleSolidCollisions(map);
    private final Tile[][] tiles =  map.getTiles();

    @Test
    void testCanMove() {
        final var pos = getWalkablePosition((x, y) -> tiles[x][y].isWalkable());
        assertTrue(pos.isPresent()); // Se e' false, allora c'e' un problema con la mappa 
        assertTrue(solidCollisions.isWalkable(pos.get()));
    }

    @Test
    void testStayStill() {
        final var pos = getWalkablePosition((x, y) -> !tiles[x][y].isWalkable());
        assertTrue(pos.isPresent()); // Se e' false, allora c'e' un problema con la mappa 
        assertFalse(solidCollisions.isWalkable(pos.get()));
    }

    private Optional<Position> getWalkablePosition(final BiPredicate<Integer, Integer> biPredicate) {
        return IntStream.range(0, map.getRows())
            .boxed()
            .flatMap(y -> IntStream.range(0, map.getColoumns())
                .filter(x -> biPredicate.test(x, y))
                .mapToObj(x -> new Position(x * MapImpl.TILE_SIZE,
                                            y * MapImpl.TILE_SIZE)))
            .findAny();
    }

}
