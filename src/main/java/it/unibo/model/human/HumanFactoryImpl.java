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
import it.unibo.model.human.strategies.reproduction.FemaleReproductionStrategy;
import it.unibo.model.human.strategies.reproduction.MaleReproductionStrategy;
import it.unibo.model.human.strategies.reproduction.ReproductionStrategy;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of an NPC Factory that produces NPCs.
 */
public final class HumanFactoryImpl implements HumanFactory {

    @Override
    public Human male(final Position startingPosition, final Map map) {
        return generalised(
            startingPosition,
            map,
            HumanType.MALE,
            new RandomMovementStrategy(),
            new MaleReproductionStrategy(startingPosition)
        );
    }

    @Override
    public Human female(final Position startingPosition, final Map map) {
        return generalised(
            startingPosition,
            map,
            HumanType.FEMALE,
            new RandomMovementStrategy(),
            new FemaleReproductionStrategy(startingPosition)
        );
    }

    @Override
    public Human player(final Position startingPosition, final Map map, final InputHandler inputHandler) {
        return generalised(
            startingPosition,
            map,
            HumanType.PLAYER,
            new PlayerMovementStrategy(inputHandler),
            new MaleReproductionStrategy(startingPosition)
        );
    }

    private Human generalised(final Position startingPosition, final Map map,
                                final HumanType humanType, final MovementStrategy movementStrategy,
                                final ReproductionStrategy reproductionStrategy) {
        return new Human() {
            private static final int CHANGE_SPRITE_THRESHOLD = 20;
            private static final double SPEED = 4.0;
            // private boolean canReproduce = true;
            private double x = startingPosition.x();
            private double y = startingPosition.y();
            // Initially the human is facing down.
            private Direction direction = new Direction(false, false, true, false);
            private int numSprite = 1;
            private int spriteCounter;
            private Sprite sprite = nextSprite();

            @Override
            public void move() {
                sprite = nextSprite();
                direction = movementStrategy.nextDirection(this);
                final Position nextPosition = nextPosition();
                if (validPosition(nextPosition)) {
                    updateSpriteCounter();
                    this.x = nextPosition.x();
                    this.y = nextPosition.y();
                }
                reproductionStrategy.update(new Position(x, y));
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
                return new CircleImpl(reproductionStrategy.getReproductionArea());
            }

            private Sprite nextSprite() {
                return Sprite.getSprite(
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

            @Override
            public boolean collide(final Human other) {
                return reproductionStrategy.collide(other);
            }
        };
    }
}
