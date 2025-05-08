package it.unibo.model.tile;

import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.common.DirectionEnum;

/**
 * Class representing a Tile used by the Wave function collapse algorithm.
 * @see Tile
 */
public interface WaveFunctionTile extends Tile {

    /**
     * Returns the entropy of the tile.
     * @return the number of possible tile types.
     */
    int getEntropy();

    /**
     * Returns whether a tile has been set.
     * @return true if the tile type has been set.
     */
    boolean hasType();

    /**
     * Adds the {@code tile} as a neighbour, if is already present an entry with {@code direction}
     * as a key, then the {@code value} become the {@code tile}.
     * @param tile the tile that hash to be added as the neighbour.
     * @param direction where the {@code tile} is.
     */
    void addNeighbour(WaveFunctionTile tile, DirectionEnum direction);

    /**
     * Returns a {@code Map} containing the neighbours, a neighbour is associated with the direction where it is relative to the
     * current tile.
     * @return the neigbours of the tile.
     */
    Map<DirectionEnum, WaveFunctionTile> getNeighbours();

    /**
     * Return a {@code List} of all the possible types of tile that the current tile can become.
     * @return all the possible types of tile.
     */
    List<TileType> getPossibleTiles();

    /**
     * Set the tile type by choosing randomly from all the possible tiles remaining.
     * @param rand random used to chose a tile type.
     */
    void collapse(Random rand);

    /**
     * If it can, reduces the possible tiles.
     * @param direction of where the current tile is relative to the neighbouring one.
     * @return true if the possible tiles have been reduced.
     */
    boolean costrain(DirectionEnum direction);
}
