package it.unibo.model.tile;

import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.common.DirectionEnum;
import it.unibo.view.sprite.Sprite;

/**
 * Class representing a Tile that has a sprite and can be walkable or not.
 */
public interface Tile {
    /**
     * Returns a boolean representing if the tile can be walked on.
     * @throws IllegalStateException when the tile has not been set.
     * @return true if a human can walk on the tile.
     */
    boolean isWalkable();

    /**
     * if possible, returns the {@code Sprite} of the relative tile.
     * @throws IllegalStateException when the tile has not been set.
     * @return the relative sprite.
     */
    Sprite getSprite();

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
    void addNeighbour(Tile tile, DirectionEnum direction);

    /**
     * Returns a {@code Map} containing the neighbours, a neighbour is associated with the direction where it is relative to the
     * current tile.
     * @return the neigbours of the tile.
     */
    Map<DirectionEnum, Tile> getNeighbours();

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
