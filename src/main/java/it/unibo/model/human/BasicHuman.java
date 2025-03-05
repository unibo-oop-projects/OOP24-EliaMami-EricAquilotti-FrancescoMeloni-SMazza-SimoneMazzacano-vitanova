package it.unibo.model.human;

import java.util.List;
import java.util.stream.Stream;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.view.sprite.Sprite;

/**
 * Human implementation with functions common to all subclasses.
 */
public abstract class BasicHuman implements Human {

    // I want the center to be around the legs of the human.
    /**
     * Offset for the x coordinate of the reproduction circle center.
     */
    protected static final int CIRCLE_X_OFFSET = 16;
    /**
     * Offset for the y coordinate of the reproduction circle center.
     */
    protected static final int CIRCLE_Y_OFFSET = 24;
    /**
     * Base radious of the reproduction circle.
     */
    private static final int CIRCLE_RADIOUS = 12;
    private static final int CHANGE_SPRITE_THRESHOLD = 20;
    private static final double SPEED = 4.0;
    private boolean canReproduce = true;
    private final Circle reproductionArea;
    private double x;
    private double y;
    private Sprite sprite;
    private Direction direction = new Direction(false, false, false, false);
    private int numSprite = 1;
    private int spriteCounter;
    private final Map map;

    /**
     * 
     * @param startingPosition the initial position.
     * @param startingSprite the fist sprite to show.
     * @param map the chapter's map
     */
    protected BasicHuman(final Position startingPosition, final Sprite startingSprite, final Map map) {
        this.x = startingPosition.x();
        this.y = startingPosition.y();
        this.sprite = startingSprite;
        this.reproductionArea = new CircleImpl(x + CIRCLE_X_OFFSET, y + CIRCLE_Y_OFFSET, CIRCLE_RADIOUS);
        this.map = map;
    }

    /**
     * Moves the human in the currently facing direction.
     * Subclasses can override this method to modify movement behavior,
     * but they should ensure to update the reproduction area and sprite state.
     */
    @Override
    public void move() {
        final double oldX = this.x;
        final double oldY = this.y;
        if (direction.up() && !direction.down()) {
            this.y -= SPEED;
        } else if (direction.down() && !direction.up()) {
            this.y += SPEED;
        }
        if (direction.left() && !direction.right()) {
            this.x -= SPEED;
        } else if (direction.right() && !direction.left()) {
            this.x += SPEED;
        }
        if (map.getTileFromPixel(this.x, this.y).isWalkable()) {
            reproductionArea.setCenter(x + CIRCLE_X_OFFSET, y + CIRCLE_Y_OFFSET);
            spriteCounter++;
            if (spriteCounter > CHANGE_SPRITE_THRESHOLD) {
                spriteCounter = 0;
                numSprite = numSprite == 1 ? 2 : 1;
            }
        } else {
            this.x = oldX;
            this.y = oldY;
        }
    }

    @Override
    public final Position getPosition() {
        return new Position(x, y);
    }

    @Override
    public final Sprite getSprite() {
        return sprite;
    }

    @Override
    public final Circle reproductionArea() {
        // Put here the logic for radius multipliers and then remove this comment.
        return new CircleImpl(this.reproductionArea);
    }

    /**
     * 
     * @param canReproduce new value of canReproduce.
     */
    protected final void setCanReproduce(final boolean canReproduce) {
        this.canReproduce = canReproduce;
    }

    /**
     * 
     * @return if human can reproduce.
     */
    protected final boolean canReproduce() {
        return this.canReproduce;
    }

    /**
     * Update the sprite.
     * @param validSprites the sprites that are valid depending on the subclass.
     */
    protected final void updateSprite(final List<Sprite> validSprites) {
        if (direction.up() && !direction.down()) {
            sprite = getSpriteFromDirection(validSprites.stream(), "UP");
        } else if (direction.down() && !direction.up()) {
            sprite = getSpriteFromDirection(validSprites.stream(), "DOWN");
        }
        if (direction.left() && !direction.right()) {
            sprite = getSpriteFromDirection(validSprites.stream(), "LEFT");
        } else if (direction.right() && !direction.left()) {
            sprite = getSpriteFromDirection(validSprites.stream(), "RIGHT");
        }
    }

    private Sprite getSpriteFromDirection(final Stream<Sprite> validSprites, final String direction) {
        return validSprites.filter(s -> s.name().endsWith(direction + "_" + numSprite))
                        .findFirst()
                        .orElse(null);
    }

    /**
     *
     * @param newDirection
     */
    protected void setDirection(final Direction newDirection) {
        direction = newDirection;
    }
}
