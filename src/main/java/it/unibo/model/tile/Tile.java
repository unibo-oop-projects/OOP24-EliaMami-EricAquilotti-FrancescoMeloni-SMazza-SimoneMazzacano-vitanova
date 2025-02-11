package it.unibo.model.tile;

import it.unibo.view.sprite.Sprite;

/**
 * Class representing a Tile that has a sprite and can be walkable or not.
 */
public interface Tile {
    /**
     * 
     * @return true if a human can walk on it.
     */
    boolean isWalkable();

    /**
     * 
     * @return the relative sprite.
     */
    Sprite getSprite();
}
