package it.unibo.model.human;

import java.util.Arrays;

import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a player that is moved by the user.
 */
public final class PlayerImpl implements Player {

    private static final int CHANGE_SPRITE_THRESHOLD = 20;
    private int x;
    private int y;
    private Direction direction = new Direction(false, false, false, false);
    private static final double SPEED = 1.0;
    private Sprite sprite = Sprite.PLAYER_DOWN_1;
    private int spriteCounter;
    private int numSprite = 1;

    /**
     * 
     * @param x the starting x coordinate
     * @param y the starting y coordinate
     */
    public PlayerImpl(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    private Sprite getSpriteFromDirection(final String direction) {
        return Arrays.stream(Sprite.values())
                .filter(s -> s.name().startsWith("PLAYER_"))
                .filter(s -> s.name().endsWith(direction + "_" + numSprite))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void move() {
        if (direction.up()) {
            sprite = getSpriteFromDirection("UP");
            y -= SPEED;
        }
        if (direction.down()) {
            sprite = getSpriteFromDirection("DOWN");
            y += SPEED;
        }
        if (direction.left()) {
            sprite = getSpriteFromDirection("LEFT");
            x -= SPEED;
        }
        if (direction.right()) {
            sprite = getSpriteFromDirection("RIGHT");
            x += SPEED;
        }
        spriteCounter++;
        if (spriteCounter > CHANGE_SPRITE_THRESHOLD) {
            spriteCounter = 0;
            numSprite = numSprite == 1 ? 2 : 1;
        }
    }

    @Override
    public void setDirection(final Direction newDirection) {
        this.direction = newDirection;
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

    @Override
    public Position getPosition() {
        return new Position(x, y);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

}
