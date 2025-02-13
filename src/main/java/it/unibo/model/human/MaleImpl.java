package it.unibo.model.human;

import java.util.Arrays;
import java.util.Random;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a male human that moves randomly on the map.
 */
public final class MaleImpl implements Male {

    private static final int CHANGE_SPRITE_THRESHOLD = 20;
    private static final int CHANGE_DIRECTION_THRESHOLD = 40;
    // I want the center to be around the legs of the human.
    private static final int CIRCLE_X_OFFSET = 8;
    private static final int CIRCLE_Y_OFFSET = 12;
    private static final int CIRCLE_RADIOUS = 6;
    private final Circle reproductionArea;
    private final Random random = new Random();
    private int x;
    private int y;
    private Direction direction = new Direction(false, false, false, false);
    private static final double SPEED = 1.0;
    private Sprite sprite = Sprite.MALE_DOWN_1;
    private int directionCounter;
    private int spriteCounter;
    private int numSprite = 1;
    private boolean canReproduce = true;

    /**
     * 
     * @param x the starting x coordinate.
     * @param y the starting y coordinate.
     */
    public MaleImpl(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.reproductionArea = new CircleImpl(x + CIRCLE_X_OFFSET, y + CIRCLE_Y_OFFSET, CIRCLE_RADIOUS);
    }

    @Override
    public void move() {
        directionCounter++;
        if (directionCounter > CHANGE_DIRECTION_THRESHOLD) {
            directionCounter = 0;
            direction = randomDirection();
        }
        if (direction.up() && !direction.down()) {
            sprite = getSpriteFromDirection("UP");
            y -= SPEED;
        } else if (direction.down()) {
            sprite = getSpriteFromDirection("DOWN");
            y += SPEED;
        }
        if (direction.left() && !direction.right()) {
            sprite = getSpriteFromDirection("LEFT");
            x -= SPEED;
        } else if (direction.right()) {
            sprite = getSpriteFromDirection("RIGHT");
            x += SPEED;
        }
        reproductionArea.setCenter(x + CIRCLE_X_OFFSET, y + CIRCLE_Y_OFFSET);

        spriteCounter++;
        if (spriteCounter > CHANGE_SPRITE_THRESHOLD) {
            spriteCounter = 0;
            numSprite = numSprite == 1 ? 2 : 1;
        }
    }

    @Override
    public Position getPosition() {
        return new Position(x, y);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    private Sprite getSpriteFromDirection(final String direction) {
        return Arrays.stream(Sprite.values())
                .filter(s -> s.name().startsWith("MALE_"))
                .filter(s -> s.name().endsWith(direction + "_" + numSprite))
                .findFirst()
                .orElse(null);
    }

    private Direction randomDirection() {
        return new Direction(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
    }

    @Override
    public boolean collide(final Human other) {
        if (this.canReproduce && this.reproductionArea.intersects(other.getReproductionArea())) {
            this.canReproduce = false;
            return true;
        }
        return false;
    }

    @Override
    public Circle getReproductionArea() {
        return new CircleImpl(this.reproductionArea);
    }
}
