package it.unibo.model.human;

import java.util.Random;

import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
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
     * @param startingPosition the initial position.
     * @param startingSprite the fist sprite to show.
     * @param map the chapter's map
     */
    protected BasicNPC(final Position startingPosition, final Sprite startingSprite, final Map map) {
        super(startingPosition, startingSprite, map);
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
