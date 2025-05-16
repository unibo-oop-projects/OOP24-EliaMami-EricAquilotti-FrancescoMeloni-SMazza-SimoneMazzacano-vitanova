package it.unibo.common;

import java.util.Random;

import it.unibo.model.chapter.map.Map;
import it.unibo.view.screen.ScreenImpl;

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
     * Returns a random walkable position near a reference.
     * @param reference the position we want to stay close.
     * @param map the map we want to have a walkable position.
     * @return a walkable position near reference within the map.
     */
    public static Position getRandomWalkableReferencePosition(final Position reference, final Map map) {
        final Position r = new Position(
            reference.x() + (RANDOM.nextBoolean() ? 1 : -1) * ScreenImpl.TILE_SIZE * 2 * RANDOM.nextDouble(),
            reference.y() + (RANDOM.nextBoolean() ? 1 : -1) * ScreenImpl.TILE_SIZE * 2 * RANDOM.nextDouble()
        );
        return isWalkable(r, map) ? r : getRandomWalkableReferencePosition(reference, map);
    }

    private static boolean isWalkable(final Position p, final Map map) {
        return map.getTileFromPixel(p.x, p.y).isWalkable();
    }

    private static Position randomPosition(final Map map) {
        return new Position(
            RANDOM.nextInt(0, map.getColoumns()) * ScreenImpl.TILE_SIZE,
            RANDOM.nextInt(0, map.getRows()) * ScreenImpl.TILE_SIZE
        );
    }
}
