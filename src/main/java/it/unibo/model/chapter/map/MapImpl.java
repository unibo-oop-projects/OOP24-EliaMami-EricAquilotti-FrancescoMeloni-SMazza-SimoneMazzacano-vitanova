package it.unibo.model.chapter.map;

import java.util.Arrays;

import it.unibo.model.chapter.map.generator.MapGenerator;
import it.unibo.model.chapter.map.generator.WaveFunctionCollapse;
import it.unibo.model.tile.Tile;

/**
 * Implementation of a game map.
 */
public final class MapImpl implements Map {

    /**
     * The size of a tile.
     */
    public static final int TILE_SIZE = 16;
    private final int rows;
    private final int coloumns;
    private final Tile[][] tiles;

    /**
     * Give an instance of {@code MapImpl} with {@code rows}, {@code coloums} and generated {@code tiles}.
     * @see MapGenerator
     * @see WaveFunctionCollapse
     * @param rows number of the rows of the map
     * @param coloumns number of the coloumns of the map
     */
    public MapImpl(final int rows, final int coloumns) {
        this.rows = rows;
        this.coloumns = coloumns;
        final MapGenerator mapGenerator = new WaveFunctionCollapse(this.rows, this.coloumns);
        this.tiles = mapGenerator.generateMap();
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
        return Arrays.copyOf(this.tiles, this.coloumns);
    }

    @Override
    public Tile getTileFromPixel(final double x, final double y) {
        final int newX = (int) (x + TILE_SIZE / 2) / TILE_SIZE;
        final int newY = (int) (y + TILE_SIZE  - TILE_SIZE / 8)  / TILE_SIZE;
        if (newX <= this.coloumns && newY <= this.rows) {
            return tiles[newX][newY];
        }
        throw new IllegalArgumentException("The coordinates are not in the map.");
    }

}
