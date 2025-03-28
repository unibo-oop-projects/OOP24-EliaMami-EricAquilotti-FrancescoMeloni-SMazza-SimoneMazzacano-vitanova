package it.unibo.model.human.strategies.reproduction;

import it.unibo.common.Position;
import it.unibo.model.human.Human;
import it.unibo.view.sprite.HumanType;

/**
 * Implementation of the female reproduction that can collide with males or with
 * the player and has a cooldown for new reproductions. It also moves the center
 * of the reproduction area based on the human position.
 */
public final class FemaleReproductionStrategy extends AbstractReproductionStrategy {
    private static final int REPRODUCTION_COOLDOWN = 100;
    private boolean canReproduce = true;
    private int reproductionCounter;

    /**
     * 
     * @param startingPosition the starting position of the human used to center
     * the reproduction area.
     */
    public FemaleReproductionStrategy(final Position startingPosition) {
        super(startingPosition);
    }

    @Override
    public void update(final Position humanPosition) {
        super.update(humanPosition);
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
            && canReproduce && getReproductionArea().intersects(other.reproductionArea())) {
            canReproduce = false;
            return true;
        }
        return false;
    }
}
