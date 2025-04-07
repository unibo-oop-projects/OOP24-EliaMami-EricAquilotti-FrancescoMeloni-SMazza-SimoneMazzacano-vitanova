package it.unibo.model.chapter.map;

import java.util.Arrays;

import it.unibo.model.tile.Tile;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of a game map.
 */
public final class MapImpl implements Map {

    private final int rows;
    private final int coloumns;
    private final Tile[][] tiles;

    /**
     * Sets {@code rows}, {@code coloums} and generate {@code tiles}.
     * @see MapGeneration
     * @see MapGenerationImpl
     * @param rows number of the rows of the map
     * @param coloumns number of the coloumns of the map
     */
    public MapImpl(final int rows, final int coloumns) {
        this.rows = rows;
        this.coloumns = coloumns;
        this.tiles = new MapGenerationImpl(rows, coloumns).generateMap();
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getColoumns() {
        return this.coloumns;
    }

    @Override
    public Tile[][] getTiles() {
        return Arrays.copyOf(this.tiles, coloumns);
    }

    @Override
    public Tile getTileFromPixel(final double x, final double y) {
        final int newX = (int) (x + ScreenImpl.TILE_SIZE / 2) / ScreenImpl.TILE_SIZE;
        final int newY = (int) (y + ScreenImpl.TILE_SIZE  - ScreenImpl.TILE_SIZE / 8)  / ScreenImpl.TILE_SIZE;
        if (newX <= this.coloumns && newY <= this.rows) {
            return tiles[newX][newY];
        }
        throw new IllegalArgumentException("The coordinates are not in the map.");
    }

}
