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

    /**
     * Returns a walkable position given a map.
     * @param map the map we want to have a walkable position.
     * @return a walkable position within the map.
     */
    public static Position getRandomWalkablePosition(final Map map) {
        final Random rand = new Random();
        Position p = new Position(0, 0);
        while (!map.getTileFromPixel(p.x, p.y).isWalkable()) {
            p = new Position(
                (double) Math.abs(rand.nextInt(0, map.getColoumns())) * ScreenImpl.TILE_SIZE,
                (double) Math.abs(rand.nextInt(0, map.getRows())) * ScreenImpl.TILE_SIZE
            );
        }
        return p;
    }

    /**
     * Returns a boolean representing if the tile on the current position is walkable.
     * @throws IllegalArgumentException if the coordinates are not in the map.
     * @param map the map we want to know if the current position is walkable.
     * @return true if the tile is walkable.
     */
    public boolean isWalkable(final Map map) {
        return map.getTileFromPixel(x, y).isWalkable();
    }
}
