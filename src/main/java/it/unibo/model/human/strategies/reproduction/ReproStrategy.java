package it.unibo.model.human.strategies.reproduction;

import it.unibo.common.Circle;
import it.unibo.common.Position;
import it.unibo.model.human.Human;

/**
 * Models the reproduction of a human.
 */
public interface ReproStrategy {

    /**
     * Updates the state of reproduction.
     * @param humanPosition the current position of the human.
     */
    void update(Position humanPosition);

    /**
     * Check if the collision with the human happens.
     * 
     * @param other the human to collide with.
     * @return true if collision happened.
     */
    boolean collide(Human other);

    /**
     * Retrieves the current reproduction area.
     * 
     * @return the current circle representing the reproduction area.
     */
    Circle getReproductionArea();

    /**
     * This method changes the radius of the reproduction area.
     * @param changeValue is the new radius value.
     */
    void changeReproductionArea(double changeValue);

    /**
     * Method that returns if the reproduction is on cooldown.
     * @return true if the reproduction is on cooldown.
     */
    boolean isOnCooldown();
}
