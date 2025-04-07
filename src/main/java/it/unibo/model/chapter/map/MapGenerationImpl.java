package it.unibo.model.chapter.map;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import it.unibo.common.DirectionEnum;
import it.unibo.model.tile.Tile;
import it.unibo.model.tile.TileImpl;
import it.unibo.model.tile.TileType;

/**
 * Implementation of {@code MapGeneration}.
 * @see MapGeneration
 */
public final class MapGenerationImpl implements MapGeneration {

    private final int rows;
    private final int coloumns;
    private final Tile[][] tiles;
    private final Random rand = new Random(System.currentTimeMillis());

    /**
     * Initialize tileIds and loads the map from a file.
     * @param rows number of the rows of the map
     * @param coloumns number of the coloumns of the map
     */
    public MapGenerationImpl(final int rows, final int coloumns) {
        this.rows = rows;
        this.coloumns = coloumns;
        this.tiles = new TileImpl[coloumns][rows];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < coloumns; x++) {
                this.tiles[x][y] = newTile(x, y);
            }
        }
        addNeighbours();
    }

    private void addNeighbours() {
        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.coloumns; x++) {
                if (y > 0) {
                    tiles[x][y].addNeighbour(tiles[x][y - 1], DirectionEnum.UP);
                }
                if (x < this.coloumns - 1) {
                    tiles[x][y].addNeighbour(tiles[x + 1][y], DirectionEnum.RIGHT);
                }
                if (y < this.rows - 1) {
                    tiles[x][y].addNeighbour(tiles[x][y + 1], DirectionEnum.DOWN);
                }
                if (x > 0) {
                    tiles[x][y].addNeighbour(tiles[x - 1][y], DirectionEnum.LEFT);
                }
            }
        }
    }

    private Tile newTile(final int x, final int y) {
        if (y < MapImpl.MARGIN_COLOUMNS || y > coloumns - MapImpl.MARGIN_COLOUMNS
            || x < MapImpl.MARGIN_ROWS || x > rows - MapImpl.MARGIN_ROWS) {
            return new TileImpl(new LinkedList<>(List.of(TileType.TILE_WATER)));
        }
        return new TileImpl();
    }

    @Override
    public Tile[][] generateMap() {
        boolean finished = false;
        while (!finished) {
            finished = waveFunctionCollapse();
        }
        return Arrays.copyOf(this.tiles, coloumns);
    }

    private boolean waveFunctionCollapse() {
        final var tilesLowestEntropy = getTilesLowestEntropy();
        if (tilesLowestEntropy.isEmpty()) {
            return true;
        }
        final int index = Math.abs(rand.nextInt(0, tilesLowestEntropy.size()));
        final var tileCollapsed = tilesLowestEntropy.get(index);
        tileCollapsed.collapse(rand);
        final List<Tile> stack = new Stack<>();
        stack.addFirst(tileCollapsed);
        while (!stack.isEmpty()) {
            final var currentTile = stack.removeLast();
            currentTile.getNeighbours().forEach((d, t) -> {
                if (!t.hasType() && t.costrain(d)) {
                    stack.addFirst(t);
                }
            });
        }
        return false;
    }

    private List<Tile> getTilesLowestEntropy() {
        var lowestEntropy = TileType.values().length;
        final List<Tile> tilesLowestEntropy = new LinkedList<>();
        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.coloumns; x++) {
                final var currentTile = tiles[x][y];
                final var tileEntropy = currentTile.getEntropy();
                if (!currentTile.hasType() && tileEntropy <= lowestEntropy) {
                    if (tileEntropy < lowestEntropy) {
                        lowestEntropy = tileEntropy;
                        tilesLowestEntropy.clear();
                    }
                    tilesLowestEntropy.add(currentTile);
                }
            }
        }
        return tilesLowestEntropy;
    }

}
