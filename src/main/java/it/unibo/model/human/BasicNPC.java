package it.unibo.model.human;

import java.util.Random;

import it.unibo.common.Direction;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a basic NPC that moves randomly around the map.
 */
public abstract class BasicNPC extends BasicHuman {
    private static final int CHANGE_DIRECTION_THRESHOLD = 40;
    private final Random random = new Random();
    private int directionCounter;

    /**
     * 
     * @param x the x position of the human.
     * @param y the y position of the human.
     * @param startingSprite the fist sprite to show.
     */
    protected BasicNPC(final int x, final int y, final Sprite startingSprite) {
        super(x, y, startingSprite);
    }

    /**
     * Moves the NPC in the currently facing direction.
     * Subclasses can override this method to modify movement behavior,
     * but they should ensure to update the reproduction area and sprite state.
     */
    @Override
    public void move() {
        directionCounter++;
        if (directionCounter > CHANGE_DIRECTION_THRESHOLD) {
            directionCounter = 0;
            setDirection(randomDirection());
        }
        super.move();
    }

    private Direction randomDirection() {
        return new Direction(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
    }
}
