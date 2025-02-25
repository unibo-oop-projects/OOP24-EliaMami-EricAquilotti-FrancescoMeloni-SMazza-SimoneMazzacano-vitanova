package it.unibo.model.tile;

import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a tile factory that creates and returns different tiles.
 */
public final class TileFactoryImpl implements TileFactory {

    @Override
    public Tile tileGrass() {
        return generic(true, Sprite.TILE_GRASS);
    }

    @Override
    public Tile tileWater() {
        return generic(false, Sprite.TILE_WATER);
    }

    private Tile generic(final boolean isWalkable, final Sprite sprite) {
        return new Tile() {

            @Override
            public boolean isWalkable() {
                return isWalkable;
            }

            @Override
            public Sprite getSprite() {
                return sprite;
            }

        };
    }
}
