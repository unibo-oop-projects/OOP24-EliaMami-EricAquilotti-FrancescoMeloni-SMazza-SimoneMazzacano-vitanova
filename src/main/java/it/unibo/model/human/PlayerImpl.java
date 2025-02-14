package it.unibo.model.human;

import java.util.Arrays;
import java.util.List;

import it.unibo.common.Direction;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a player that is moved by the user.
 */
public final class PlayerImpl extends BasicHuman implements Player {
    private static final List<Sprite> VALID_SPRITES =
        Arrays.stream(Sprite.values())
                .filter(s -> s.name().startsWith("PLAYER_"))
                .toList();

    /**
     * 
     * @param x the starting x coordinate.
     * @param y the starting y coordinate.
     */
    public PlayerImpl(final int x, final int y) {
        super(x, y, Sprite.PLAYER_DOWN_1);
    }

    @Override
    public void move() {
        super.move();
        updateSprite(VALID_SPRITES);
    }

    @Override
    public void setDirection(final Direction newDirection) {
        super.setDirection(newDirection);
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
