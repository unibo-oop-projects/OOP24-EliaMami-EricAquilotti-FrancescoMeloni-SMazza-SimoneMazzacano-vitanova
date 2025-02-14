package it.unibo.model.human;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import it.unibo.common.Direction;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a female human that moves randomly around the map and
 * produces new humans.
 */
public final class FemaleImpl extends BasicHuman implements Female {

    private static final List<Sprite> VALID_SPRITES =
        Arrays.stream(Sprite.values())
                .filter(s -> s.name().startsWith("FEMALE_"))
                .toList();
    private static final int CHANGE_DIRECTION_THRESHOLD = 40;
    private static final int REPRODUCTION_COOLDOWN = 100;
    private final Random random = new Random();
    private int directionCounter;
    private int reproductionCounter;

    /**
     * 
     * @param x the starting x coordinate.
     * @param y the starting y coordinate.
     */
    public FemaleImpl(final int x, final int y) {
        super(x, y, Sprite.FEMALE_DOWN_1);
        setCanReproduce(true);
    }

    @Override
    public void move() {
        directionCounter++;
        if (directionCounter > CHANGE_DIRECTION_THRESHOLD) {
            directionCounter = 0;
            setDirection(randomDirection());
        }
        if (!canReproduce()) {
            reproductionCounter++;
            if (reproductionCounter > REPRODUCTION_COOLDOWN) {
                reproductionCounter = 0;
                setCanReproduce(true);
            }
        }
        super.move();
        updateSprite(VALID_SPRITES);
    }


    private Direction randomDirection() {
        return new Direction(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
    }

    @Override
    public boolean collide(final Male other) {
        if (canReproduce() && reproductionArea().intersects(other.reproductionArea())) {
            setCanReproduce(false);
            return true;
        }
        return false;
    }
}
