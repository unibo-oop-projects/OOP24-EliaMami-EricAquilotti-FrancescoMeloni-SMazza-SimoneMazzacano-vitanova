package it.unibo.common;

import java.util.Random;

import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;

/**
 * 
 * @param x the position on the x axes.
 * @param y the position on the y axes.
 */
public record Position(double x, double y) {
    private static final Random RANDOM = new Random();

    /**
     * Returns a walkable position given a map.
     * @param map the map we want to have a walkable position.
     * @return a walkable position within the map.
     */
    public static Position getRandomWalkablePosition(final Map map) {
        final Position p = randomPosition(map);
        return isWalkable(p, map) ? p : getRandomWalkablePosition(map);
    }

    /**
     * Returns the walkable position closest to the center of the map.
     * @param map the map we want to have the walkable position.
     * @return the walkable position closest to the center within the map.
     */
    public static Position getRandomCentralWalkablePosition(final Map map) {
        final var mapSize = new Position(map.getColoumns() * MapImpl.TILE_SIZE, map.getRows() * MapImpl.TILE_SIZE);
        final var center = new Position(mapSize.x / 2, mapSize.y / 2);
        final var offset = new Position(mapSize.x / 10, mapSize.y / 10);
        var pos = center;
        while (!isWalkable(pos, map) || !pos.withinOffset(center, offset)) {
            pos = randomPosition(map);
        }
        return pos;
    }

    private boolean withinOffset(final Position center, final Position offset) {
        return x > center.x - offset.x && x < center.x + offset.x 
                && y > center.y - offset.y && y < center.y + offset.y;
    }

    /**
     * Returns a random walkable position near a reference.
     * @param reference the position we want to stay close.
     * @param map the map we want to have a walkable position.
     * @return a walkable position near reference within the map.
     */
    public static Position getRandomWalkableReferencePosition(final Position reference, final Map map) {
        final Position r = new Position(
            reference.x() + (RANDOM.nextBoolean() ? 1 : -1) * MapImpl.TILE_SIZE * 2 * RANDOM.nextDouble(),
            reference.y() + (RANDOM.nextBoolean() ? 1 : -1) * MapImpl.TILE_SIZE * 2 * RANDOM.nextDouble()
        );
        return isWalkable(r, map) ? r : getRandomWalkableReferencePosition(reference, map);
    }

    private static boolean isWalkable(final Position p, final Map map) {
        return map.getTileFromPixel(p.x, p.y).isWalkable();
    }

    private static Position randomPosition(final Map map) {
        return new Position(
            RANDOM.nextInt(0, map.getColoumns()) * MapImpl.TILE_SIZE,
            RANDOM.nextInt(0, map.getRows()) * MapImpl.TILE_SIZE
        );
    }
}
