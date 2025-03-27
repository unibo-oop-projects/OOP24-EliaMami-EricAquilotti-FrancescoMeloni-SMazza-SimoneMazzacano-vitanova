package it.unibo.model.human;

import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a player that is moved by the user.
 */
public final class PlayerImpl extends BasicHuman implements Player {

    /**
     * 
     * @param startingPosition the initial position.
     * @param map the chapter's map
     */
    public PlayerImpl(final Position startingPosition, final Map map) {
        super(startingPosition, Sprite.PLAYER_DOWN_1, map, HumanType.PLAYER);
    }

    @Override
    public void move() {
        super.move();
        updateSprite();
    }

    @Override
    public void setSpeedMultiplier(final float speedMult) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSpeedMultiplier'");
    }

    @Override
    public void setHitRadiousMultiplier(final float hitRadiousMult) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHitRadiousMultiplier'");
    }

}
