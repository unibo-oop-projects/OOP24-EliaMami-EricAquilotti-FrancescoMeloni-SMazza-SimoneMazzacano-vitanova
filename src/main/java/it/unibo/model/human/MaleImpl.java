package it.unibo.model.human;

import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.strategies.movement.RandomMovementStrategy;
import it.unibo.view.sprite.HumanType;

/**
 * Implementation of a male human that provides its sprites.
 */
public final class MaleImpl extends BasicHuman implements Male {

    /**
     * 
     * @param startingPosition the initial position.
     * @param map the chapter's map
     */
    public MaleImpl(final Position startingPosition, final Map map) {
        super(startingPosition, map, HumanType.MALE, new RandomMovementStrategy());
    }


    @Override
    public HumanType getType() {
        return HumanType.MALE;
    }
}
