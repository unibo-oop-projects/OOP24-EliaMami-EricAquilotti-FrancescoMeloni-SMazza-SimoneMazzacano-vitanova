package it.unibo.model.human.strategies;

import it.unibo.common.Direction;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.Human;

/**
 * Implementation of the player movement strategy that uses the default move of
 * the human.
 */
public final class PlayerMovementStrategy implements MovementStrategy {

    private final InputHandler inputHandler;

    /**
     * 
     * @param inputHandler the input to get the direction from.
     */
    public PlayerMovementStrategy(final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public Direction nextDirection(final Human human) {
        return inputHandler.getDirection();
    }

}
