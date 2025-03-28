package it.unibo.model.human.strategies;

import java.util.Random;

import it.unibo.common.Direction;
import it.unibo.model.human.Human;

/**
 * Implementation of a random movement that changes the direction randomly.
 */
public final class RandomMovementStrategy implements MovementStrategy {
    private static final int CHANGE_DIRECTION_THRESHOLD = 40;
    private final Random random = new Random();
    private int directionCounter;

    @Override
    public Direction nextDirection(final Human human) {
        directionCounter++;
        if (directionCounter > CHANGE_DIRECTION_THRESHOLD) {
            directionCounter = 0;
            return randomDirection();
        }
        return human.getDirection();
    }

    private Direction randomDirection() {
        return new Direction(random.nextBoolean(), random.nextBoolean(),
                            random.nextBoolean(), random.nextBoolean());
    }
}
