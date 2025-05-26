package it.unibo.model.human.strategies.reproduction;

import java.io.Serializable;

import it.unibo.common.Circle;
import it.unibo.common.Position;
import it.unibo.model.human.Human;

/**
 * Models the reproduction of a human.
 */
public interface ReproStrategy extends Serializable{

    /**
     * Updates the state of reproduction.
     * @param humanPosition the current position of the human.
     */
    void update(Position humanPosition);

    /**
     * 
     * @param other the human to collide with.
     * @return true if collision happened.
     */
    boolean collide(Human other);

    /**
     * 
     * @return the current circle representing the reproduction area.
     */
    Circle getReproductionArea();

    /**
     * @param changeValue is the new radius value.
     */
    void changeReproductionArea(double changeValue);
}
