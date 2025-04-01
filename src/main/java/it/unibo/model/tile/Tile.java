package it.unibo.model.tile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.common.DirectionEnum;
import it.unibo.view.sprite.Sprite;

/**
 * Class representing a Tile that has a sprite and can be walkable or not.
 */
public interface Tile {
    /**
     * 
     * @return true if a human can walk on the tile.
     */
    boolean isWalkable();

    /**
     * 
     * @return the relative sprite.
     */
    Sprite getSprite();

    /**
     * 
     * @return the number of possible tile types.
     */
    int getEntropy();

    /**
     * 
     * @return the type of the relative tile.
     */
    Optional<TileType> getTileType();

    /**
     * Adds the {@code tile} as a neighbour
     * @param direction where the {@code tile} is 
     */
    void addNeighbour(Tile tile, DirectionEnum direction);

    /**
     * @param direction 
     * @return the neigbour in a direction.
     */
    Optional<Tile> getNeighbour(DirectionEnum direction);

    /**
     * @return the neigbours of the tile.
     */
    Map<DirectionEnum, Tile> getNeighbours();

    /**
     * @return all the possible tiles that {@code this} could be.
     */
    List<TileType> getPossibleTiles();

    /**
     * Set the tile type by choosing randomly from all the possible tiles remaining.
     */
    void collapse();

    /**
     * If it can, reduces the possible tiles.
     * @param neighbourPossibilities possible tiles of the neighbour
     * @param direction where the current tile is relative to the neighbouring one
     * @return true if the possible tiles have been reduced.
     */
    boolean costrain(List<TileType> neighbourPossibleTiles, DirectionEnum direction);
    
}
