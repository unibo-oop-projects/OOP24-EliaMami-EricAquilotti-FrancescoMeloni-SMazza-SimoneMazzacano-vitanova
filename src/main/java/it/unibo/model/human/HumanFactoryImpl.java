package it.unibo.model.human;

import java.time.Clock;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Direction;
import it.unibo.common.DirectionEnum;
import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.strategies.movement.MovStrategyFactory;
import it.unibo.model.human.strategies.movement.MovStrategyFactoryImpl;
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

    private Human generalised(final Position startingPosition, final Map map,
                                final HumanType humanType, final MovStrategy movementStrategy,
                                final ReproStrategy reproductionStrategy) {
        return new Human() {
            private static final int CHANGE_SPRITE_THRESHOLD = 20;
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
                direction = movementStrategy.nextDirection();
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
            public Stats getStats(){
                return new Stats() {
                    private double speed = 4.0;
                    private double sicknessResistence = .1;
                    private double fertility = .1;
                    private Circle reproductionAreaRadius = new CircleImpl(reproductionStrategy.getReproductionArea());;
                    private static final double SPEED_UPGRADE_VALUE = .5;
                    private static final double SICKNESS_RESISTENCE_UPGRADE_VALUE = .05;
                    private static final double FERTILITY_UPGRADE_VALUE = .05;
                    //private static final int RADIUS_UPGRADE_VALUE = 5;

                    @Override
                    public double getSpeed() {
                        return this.speed;
                    }

                    private void setSpeed(double newSpeed){
                        this.speed = newSpeed;
                    }

                    @Override
                    public void increaseSpeed() {
                        setSpeed(speed+SPEED_UPGRADE_VALUE);
                    }

                    @Override
                    public Circle getReproductionAreaRadius() {
                        return this.reproductionAreaRadius;
                    }

                    @Override
                    public void increaseReproductionAreaRadius() {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException("Unimplemented method 'increaseReproductionAreaRadius'");
                    }

                    @Override
                    public double getSicknessResistence() {
                        return this.sicknessResistence;
                    }

                    private void setSicknessResistence(double newSicknessResistence) {
                        this.sicknessResistence = newSicknessResistence;
                    }

                    @Override
                    public void increaseSicknessResistence() {
                        setSicknessResistence(this.sicknessResistence+SICKNESS_RESISTENCE_UPGRADE_VALUE);
                    }

                    @Override
                    public double getFertility() {
                        return this.fertility;
                    }

                    private void setFertility(double newFertility) {
                        this.fertility = newFertility;
                    }

                    @Override
                    public void increaseFertility() {
                        setFertility(fertility+FERTILITY_UPGRADE_VALUE);
                    }
                    
                };
            }

            @Override
            public boolean collide(final Human other) {
                return reproductionStrategy.collide(other);
            }
        };
    }
}
