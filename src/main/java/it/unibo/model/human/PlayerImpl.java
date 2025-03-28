package it.unibo.model.human;

import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.strategies.MovementStrategy;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a player that is moved by the user.
 */
public final class PlayerImpl extends BasicHuman implements Player {

    /**
     * 
     * @param startingPosition the initial position.
     * @param map the chapter's map.
     * @param movementStrategy the movement brhavior of the player.
     */
    public PlayerImpl(final Position startingPosition, final Map map, final MovementStrategy movementStrategy) {
        super(startingPosition, Sprite.PLAYER_DOWN_1, map, HumanType.PLAYER, movementStrategy);
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
