package it.unibo.model.tile;

import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a walkable grass tile.
 */
public final class TileGrass implements Tile {

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public Sprite getSprite() {
        return Sprite.TILE_GRASS;
    }

}
