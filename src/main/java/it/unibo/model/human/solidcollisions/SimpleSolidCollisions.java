package it.unibo.model.human.solidcollisions;

import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;

/**
 * Solid collisions using only the Tile where the human is on.
 */
public final class SimpleSolidCollisions implements SolidCollisions {

    private final Map map;

    /**
     * Give an instance of {@code SimpleSolidCollisions} where the collision will be used in the {@code map}.
     * @param map
     */
    public SimpleSolidCollisions(final Map map) {
        this.map = map;
    }

    @Override
    public boolean isWalkable(final Position pos) {
        return this.map.getTileFromPixel(pos.x(), pos.y()).isWalkable();
    }

}
