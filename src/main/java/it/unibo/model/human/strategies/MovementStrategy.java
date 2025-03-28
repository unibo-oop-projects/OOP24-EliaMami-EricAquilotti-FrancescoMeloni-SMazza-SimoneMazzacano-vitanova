package it.unibo.model.human.strategies;

import it.unibo.common.Direction;
import it.unibo.model.human.Human;

/**
 * Strategy to model all the movements of the humans.
 */
public interface MovementStrategy {
    /**
     * 
     * @param human the human to move
     * @return the next direction the human will face.
     */
    Direction nextDirection(Human human);
}
