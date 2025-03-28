package it.unibo.model.human;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Direction;
import it.unibo.common.DirectionEnum;
import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.strategies.movement.MovementStrategy;
import it.unibo.model.human.strategies.movement.PlayerMovementStrategy;
import it.unibo.model.human.strategies.movement.RandomMovementStrategy;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of an NPC Factory that produces NPCs.
 */
public final class HumanFactoryImpl implements HumanFactory {

    @Override
    public Human male(final Position startingPosition, final Map map) {
        return generalised(startingPosition, map, HumanType.MALE, new RandomMovementStrategy());
    }

    @Override
    public Human female(final Position startingPosition, final Map map) {
        return generalised(startingPosition, map, HumanType.FEMALE, new RandomMovementStrategy());
    }

    @Override
    public Human player(final Position startingPosition, final Map map, final InputHandler inputHandler) {
        return generalised(startingPosition, map, HumanType.PLAYER, new PlayerMovementStrategy(inputHandler));
    }

    private Human generalised(final Position startingPosition, final Map map,
                                final HumanType humanType, final MovementStrategy movementStrategy) {
        return new Human() {
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
            // private boolean canReproduce = true;
            private double x = startingPosition.x();
            private double y = startingPosition.y();
            private final Circle reproductionArea = new CircleImpl(
                x + CIRCLE_X_OFFSET,
                y + CIRCLE_Y_OFFSET,
                CIRCLE_RADIOUS
            );
            private Sprite sprite;
            // Initially everyone is facing down.
            private Direction direction = new Direction(false, false, true, false);
            private int numSprite = 1;
            private int spriteCounter;

            @Override
            public void move() {
                updateSprite();
                direction = movementStrategy.nextDirection(this);
                final Position nextPosition = nextPosition();
                if (validPosition(nextPosition)) {
                    centerReproductionArea();
                    updateSpriteCounter();
                    this.x = nextPosition.x();
                    this.y = nextPosition.y();
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

            @Override
            public Circle reproductionArea() {
                // Put here the logic for radius multipliers and then remove this comment.
                return new CircleImpl(this.reproductionArea);
            }

            // private void setCanReproduce(final boolean canReproduce) {
            //     this.canReproduce = canReproduce;
            // }

            // private boolean canReproduce() {
            //     return this.canReproduce;
            // }

            private void updateSprite() {
                sprite = Sprite.getSprite(
                    humanType,
                    DirectionEnum.getDirectionEnum(direction), numSprite
                ).orElse(sprite);
            }

            private void updateSpriteCounter() {
                spriteCounter++;
                if (spriteCounter > CHANGE_SPRITE_THRESHOLD) {
                    spriteCounter = 0;
                    numSprite = numSprite == 1 ? 2 : 1;
                }
            }

            private void centerReproductionArea() {
                reproductionArea.setCenter(x + CIRCLE_X_OFFSET, y + CIRCLE_Y_OFFSET);
            }

            private boolean validPosition(final Position position) {
                return map.getTileFromPixel(position.x(), position.y()).isWalkable();
            }

            private Position nextPosition() {
                return new Position(
                    this.x + SPEED * direction.getDx(),
                    this.y + SPEED * direction.getDy()
                );
            }

            @Override
            public Direction getDirection() {
                return direction;
            }

            @Override
            public HumanType getType() {
                return humanType;
            }
        };
    }
}
