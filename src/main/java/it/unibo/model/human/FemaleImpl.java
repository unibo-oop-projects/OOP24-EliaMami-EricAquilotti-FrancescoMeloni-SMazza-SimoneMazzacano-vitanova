package it.unibo.model.human;

import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a female human that moves randomly around the map and
 * produces new humans.
 */
public final class FemaleImpl extends BasicNPC implements Female {
    private static final int REPRODUCTION_COOLDOWN = 100;
    private int reproductionCounter;

    /**
     * 
     * @param startingPosition the initial position.
     * @param map the chapter's map
     */
    public FemaleImpl(final Position startingPosition, final Map map) {
        super(startingPosition, Sprite.FEMALE_DOWN_1, map, HumanType.FEMALE);
        setCanReproduce(true);
    }

    @Override
    public void move() {
        if (!canReproduce()) {
            reproductionCounter++;
            if (reproductionCounter > REPRODUCTION_COOLDOWN) {
                reproductionCounter = 0;
                setCanReproduce(true);
            }
        }
        super.move();
        updateSprite();
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
