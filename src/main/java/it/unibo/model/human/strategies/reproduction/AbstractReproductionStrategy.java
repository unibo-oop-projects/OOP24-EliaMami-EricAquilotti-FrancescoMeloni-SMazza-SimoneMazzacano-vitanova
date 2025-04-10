package it.unibo.model.human.strategies.reproduction;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Position;

/**
 * Abstract base class for reproduction strategies, containing shared logic.
 */
public abstract class AbstractReproductionStrategy implements ReproductionStrategy {
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
    public AbstractReproductionStrategy(final Position startingPosition) {
        this.reproductionArea = new CircleImpl(
            startingPosition.x() + CIRCLE_X_OFFSET,
            startingPosition.y() + CIRCLE_Y_OFFSET,
            CIRCLE_RADIOUS
        );
    }

    /**
     * This method can be overrided but make sure to call super.update().
     */
    @Override
    public void update(final Position humanPosition) {
        centerReproductionArea(humanPosition);
    }

    @Override
    public final Circle getReproductionArea() {
        return new CircleImpl(reproductionArea);
    }

    private void centerReproductionArea(final Position humanPosition) {
        reproductionArea.setCenter(humanPosition.x() + CIRCLE_X_OFFSET, humanPosition.y() + CIRCLE_Y_OFFSET);
    }
}
