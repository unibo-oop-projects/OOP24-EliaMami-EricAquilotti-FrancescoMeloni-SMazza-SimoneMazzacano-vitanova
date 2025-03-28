package it.unibo.model.human.strategies.reproduction;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Position;
import it.unibo.model.human.Human;

/**
 * Implementation of the reproduction for males that only moves the reproduction
 * area based on the human position.
 */
public final class MaleReproductionStrategy implements ReproductionStrategy {
    // I want the center to be around the legs of the human.
    private static final int CIRCLE_X_OFFSET = 16;
    private static final int CIRCLE_Y_OFFSET = 24;
    private static final int CIRCLE_RADIOUS = 12;
    private final Circle reproductionArea;

    /**
     * 
     * @param startingPosition the starting position of the human used to center
     * the reproduction area.
     */
    public MaleReproductionStrategy(final Position startingPosition) {
        reproductionArea = new CircleImpl(
            startingPosition.x() + CIRCLE_X_OFFSET,
            startingPosition.y() + CIRCLE_Y_OFFSET,
            CIRCLE_RADIOUS
        );
    }

    @Override
    public void update(final Position humanPosition) {
        centerReproductionArea(humanPosition);
    }

    @Override
    public boolean collide(final Human other) {
        // male do not collide.
        return false;
    }

    @Override
    public Circle getReproductionArea() {
        return new CircleImpl(reproductionArea);
    }

    private void centerReproductionArea(final Position humanPosition) {
        reproductionArea.setCenter(humanPosition.x() + CIRCLE_X_OFFSET, humanPosition.y() + CIRCLE_Y_OFFSET);
    }
}
