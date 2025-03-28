package it.unibo.model.human.strategies.reproduction;

import it.unibo.common.Position;
import it.unibo.model.human.Human;

/**
 * Implementation of the reproduction for males that only moves the reproduction
 * area based on the human position.
 */
public final class MaleReproductionStrategy extends AbstractReproductionStrategy {
    /**
     * 
     * @param startingPosition the starting position of the human used to center
     * the reproduction area.
     */
    public MaleReproductionStrategy(final Position startingPosition) {
        super(startingPosition);
    }

    @Override
    public boolean collide(final Human other) {
        // male do not collide.
        return false;
    }
}
