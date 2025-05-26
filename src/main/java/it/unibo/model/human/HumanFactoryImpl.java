package it.unibo.model.human;

import java.time.Clock;

import it.unibo.common.Direction;
import it.unibo.common.DirectionEnum;
import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.strategies.movement.MovStrategyFactory;
import it.unibo.model.human.strategies.movement.MovStrategyFactoryImpl;
import it.unibo.model.human.solidcollisions.SimpleSolidCollisions;
import it.unibo.model.human.solidcollisions.SolidCollisions;
import it.unibo.model.human.strategies.movement.MovStrategy;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;
import it.unibo.model.human.strategies.reproduction.ReproStrategyFactory;
import it.unibo.model.human.strategies.reproduction.ReproStrategyFactoryImpl;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of an NPC Factory that produces all kinds of humans.
 */
public final class HumanFactoryImpl implements HumanFactory {
    private final ReproStrategyFactory reproductionStrategyFactory;
    private final MovStrategyFactory movementStrategyFactory;
    private static final double BASE_SPEED = 4.5;
    private static final double BASE_SICKNESS_RESISTENCE = .1;
    private static final double BASE_FERTILITY = .1;

    /**
     * 
     * @param baseClock the clock to give to the strategies that may need cooldowns.
     */
    public HumanFactoryImpl(final Clock baseClock) {
        reproductionStrategyFactory = new ReproStrategyFactoryImpl(baseClock);
        movementStrategyFactory = new MovStrategyFactoryImpl(baseClock);
    }

    @Override
    public Human male(final Position startingPosition, final Map map) {
        return generalised(
            startingPosition,
            map,
            HumanType.MALE,
            movementStrategyFactory.randomMovement(),
            reproductionStrategyFactory.maleReproStrategy(startingPosition)
        );
    }

    @Override
    public Human female(final Position startingPosition, final Map map) {
        return generalised(
            startingPosition,
            map,
            HumanType.FEMALE,
            movementStrategyFactory.randomMovement(),
            reproductionStrategyFactory.femaleReproStrategy(startingPosition)
        );
    }

    @Override
    public Human player(final Position startingPosition, final Map map, final InputHandler inputHandler) {
        return generalised(
            startingPosition,
            map,
            HumanType.PLAYER,
            movementStrategyFactory.userInputMovement(inputHandler),
            reproductionStrategyFactory.maleReproStrategy(startingPosition)
        );
    }

    @Override
    public Human player(
        final Position startingPosition, final Map map, final InputHandler inputHandler, final HumanStats playerStats) {
        return generalised(
            startingPosition,
            map,
            HumanType.PLAYER,
            movementStrategyFactory.userInputMovement(inputHandler),
            playerStats.getReproStrategy(),
            playerStats
        );
    }

    @Override 
    public Human player(final Position startingPosition, final Map map, final InputHandler inputHandler, final HumanStats playerStats, final double areaRadius){
        ReproStrategy rs = reproductionStrategyFactory.maleReproStrategy(startingPosition);
        rs.changeReproductionArea(areaRadius);
        playerStats.setReproStrategy(rs);
        return generalised(
            startingPosition,
            map,
            HumanType.PLAYER,
            movementStrategyFactory.userInputMovement(inputHandler),
            rs,
            playerStats
        );
    }

    private Human generalised(final Position startingPosition, final Map map,
                                final HumanType humanType, final MovStrategy movementStrategy,
                                final ReproStrategy reproductionStrategy, final HumanStats stats) {
        return new Human() {
            private static final int CHANGE_SPRITE_THRESHOLD = 20;
            // private boolean canReproduce = true;
            private double x = startingPosition.x();
            private double y = startingPosition.y();
            // Initially the human is facing down.
            private Direction direction = new Direction(false, false, true, false);
            private int numSprite = 1;
            private int spriteCounter;
            private final HumanStats humanStats = stats;
            private Sprite sprite = nextSprite();
            private final SolidCollisions solidCollisions = new SimpleSolidCollisions(map);

            @Override
            public void move() {
                sprite = nextSprite();
                direction = movementStrategy.nextDirection();
                final Position nextPosition = nextPosition();
                if (solidCollisions.isWalkable(nextPosition)) {
                    updateSpriteCounter();
                    this.x = nextPosition.x();
                    this.y = nextPosition.y();
                }
                humanStats.getReproStrategy().update(new Position(x, y));
            }

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public Sprite getSprite() {
                return sprite;
            }

            private Sprite nextSprite() {
                return Sprite.getSprite(
                    humanType,
                    DirectionEnum.getDirectionEnum(direction), humanStats.isSick(), numSprite
                ).orElse(sprite);
            }

            private void updateSpriteCounter() {
                spriteCounter++;
                if (spriteCounter > CHANGE_SPRITE_THRESHOLD) {
                    spriteCounter = 0;
                    numSprite = numSprite == 1 ? 2 : 1;
                }
            }

            private Position nextPosition() {
                return new Position(
                    this.x + getStats().getSpeed() * direction.getDx(),
                    this.y + getStats().getSpeed() * direction.getDy()
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
            public HumanStats getStats() {
                return this.humanStats;
            }

            @Override
            public boolean collide(final Human other) {
                return reproductionStrategy.collide(other);
            }
        };
    }

    private Human generalised(final Position startingPosition, final Map map,
                                final HumanType humanType, final MovStrategy movementStrategy,
                                final ReproStrategy reproductionStrategy) {
        return generalised(startingPosition, map, humanType, movementStrategy, reproductionStrategy, 
            new HumanStatsImpl(BASE_SPEED, BASE_SICKNESS_RESISTENCE, BASE_FERTILITY, reproductionStrategy)
        );
    }
}
