package it.unibo.model.tile;

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
}
