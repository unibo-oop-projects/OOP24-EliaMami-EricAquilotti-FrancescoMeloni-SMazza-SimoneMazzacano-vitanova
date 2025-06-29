package it.unibo.model.chapter.map.generator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import it.unibo.common.DirectionEnum;
import it.unibo.model.tile.Tile;
import it.unibo.model.tile.wavefunction.TileType;
import it.unibo.model.tile.wavefunction.WaveFunctionTile;
import it.unibo.model.tile.wavefunction.WaveFunctionTileImpl;

/**
 * Implementation of {@code MapGeneration} with the Wave Function Collapse Algorithm (Gumin's implementation).
 * @see MapGenerator
 */
public final class WaveFunctionCollapse implements MapGenerator {

    private final int rows;
    private final int coloumns;
    private final WaveFunctionTile[][] tiles;
    private final Random rand = new Random();

    /**
     * Give an instance of {@code WaveFunctionCollapse} with {@code rows} anc {@code coloumns}.
     * @param rows number of the rows of the map
     * @param coloumns number of the coloumns of the map
     */
    public WaveFunctionCollapse(final int rows, final int coloumns) {
        this.rows = rows;
        this.coloumns = coloumns;
        this.tiles = new WaveFunctionTileImpl[coloumns][rows];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < coloumns; x++) {
                this.tiles[x][y] = newTile(x, y);
            }
        }
        addNeighbours();
    }

    @Override
    public Tile[][] generateMap() {
        boolean finished = false;
        while (!finished) {
            finished = waveFunctionCollapse();
        }
        return Arrays.copyOf(this.tiles, this.coloumns);
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

    private WaveFunctionTile newTile(final int x, final int y) {
        if (x < 1 || x > coloumns - 2 || y < 1 || y > rows - 2) {
            return new WaveFunctionTileImpl(new LinkedList<>(List.of(TileType.TILE_WATER)));
        }
        return new WaveFunctionTileImpl();
    }

    private boolean waveFunctionCollapse() {
        final var tilesLowestEntropy = getTilesLowestEntropy();
        if (tilesLowestEntropy.isEmpty()) {
            return true;
        }
        final int index = Math.abs(rand.nextInt(0, tilesLowestEntropy.size()));
        final var tileCollapsed = tilesLowestEntropy.get(index);
        tileCollapsed.collapse(rand);
        final List<WaveFunctionTile> stack = new Stack<>();
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

    private List<WaveFunctionTile> getTilesLowestEntropy() {
        var lowestEntropy = TileType.values().length;
        final List<WaveFunctionTile> tilesLowestEntropy = new LinkedList<>();
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
