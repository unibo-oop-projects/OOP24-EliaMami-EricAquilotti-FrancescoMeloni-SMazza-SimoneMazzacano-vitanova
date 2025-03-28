package it.unibo.model.human.strategies.reproduction;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Position;
import it.unibo.model.human.Human;
import it.unibo.view.sprite.HumanType;

/**
 * Implementation of the female reproduction that can collide with males or with
 * the player and has a cooldown for new reproductions. It also moves the center
 * of the reproduction area based on the human position.
 */
public final class FemaleReproductionStrategy implements ReproductionStrategy {
    // I want the center to be around the legs of the human.
    private static final int CIRCLE_X_OFFSET = 16;
    private static final int CIRCLE_Y_OFFSET = 24;
    private static final int CIRCLE_RADIOUS = 12;
    private static final int REPRODUCTION_COOLDOWN = 100;
    private final Circle reproductionArea;
    private boolean canReproduce = true;
    private int reproductionCounter;

    /**
     * 
     * @param startingPosition the starting position of the human used to center
     * the reproduction area.
     */
    public FemaleReproductionStrategy(final Position startingPosition) {
        reproductionArea = new CircleImpl(
            startingPosition.x() + CIRCLE_X_OFFSET,
            startingPosition.y() + CIRCLE_Y_OFFSET,
            CIRCLE_RADIOUS
        );
    }

    @Override
    public void update(final Position humanPosition) {
        centerReproductionArea(humanPosition);
        if (!canReproduce) {
            reproductionCounter++;
            if (reproductionCounter > REPRODUCTION_COOLDOWN) {
                reproductionCounter = 0;
                canReproduce = true;
            }
        }
    }

    @Override
    public boolean collide(final Human other) {
        if ((other.getType() == HumanType.MALE || other.getType() == HumanType.PLAYER)
            && canReproduce && reproductionArea.intersects(other.reproductionArea())) {
            canReproduce = false;
            return true;
        }
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
