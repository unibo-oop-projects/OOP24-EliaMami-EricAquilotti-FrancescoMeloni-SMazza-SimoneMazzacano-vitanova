package it.unibo.model.chapter.map;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;

import it.unibo.model.tile.Tile;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of a game map.
 */
public final class MapImpl implements Map {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * The vertical margin, used to not display the borders of the map.
     */
    public static final int MARGIN_ROWS = (int) SCREEN_SIZE.getHeight() / ScreenImpl.TILE_SIZE;
    /**
     * The horizontal margin, used to not display the borders of the map.
     */
    public static final int MARGIN_COLOUMNS = (int) SCREEN_SIZE.getWidth() / ScreenImpl.TILE_SIZE;

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
        this.rows = rows + MARGIN_ROWS;
        this.coloumns = coloumns + MARGIN_COLOUMNS;
        this.tiles = new MapGenerationImpl(this.rows, this.coloumns).generateMap();
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
