package it.unibo.model.human;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import it.unibo.common.Direction;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a male human that moves randomly on the map.
 */
public final class MaleImpl extends BasicHuman implements Male {

    private static final List<Sprite> VALID_SPRITES =
        Arrays.stream(Sprite.values())
                .filter(s -> s.name().startsWith("MALE_"))
                .toList();
    private static final int CHANGE_DIRECTION_THRESHOLD = 40;
    private final Random random = new Random();
    private int directionCounter;

    /**
     * 
     * @param x the starting x coordinate.
     * @param y the starting y coordinate.
     */
    public MaleImpl(final int x, final int y) {
        super(x, y);
    }

    @Override
    public void move() {
        directionCounter++;
        if (directionCounter > CHANGE_DIRECTION_THRESHOLD) {
            directionCounter = 0;
            setDirection(randomDirection());
        }
        super.move();
        updateSprite(VALID_SPRITES);
    }

    private Direction randomDirection() {
        return new Direction(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
    }

}
