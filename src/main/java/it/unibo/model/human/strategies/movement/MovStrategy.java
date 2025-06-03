package it.unibo.model.human.strategies.movement;

import it.unibo.common.Direction;

/**
 * Strategy to model all the movements of the humans.
 */
public interface MovStrategy {
    /**
     * Retrieves the next direction the human will face.
     * 
     * @return the next direction the human will face.
     */
    Direction nextDirection();
}
