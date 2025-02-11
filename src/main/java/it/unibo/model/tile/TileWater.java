package it.unibo.model.tile;

import it.unibo.view.sprite.Sprite;

/**
 * Implementatio of a water tile that is not walkable.
 */
public final class TileWater implements Tile {

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public Sprite getSprite() {
        return Sprite.TILE_WATER;
    }

}
