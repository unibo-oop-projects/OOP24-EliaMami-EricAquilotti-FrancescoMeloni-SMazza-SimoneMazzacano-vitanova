package it.unibo.model.human.strategies.reproduction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.Circle;
import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.model.human.Human;
import it.unibo.model.human.HumanStats;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

class ReproStrategyFactoryTest {
    private ReproStrategyFactory factory;

    @BeforeEach
    void setup() {
        final Clock clock = Clock.fixed(Instant.parse("2025-04-19T10:00:00Z"), ZoneOffset.UTC);
        factory = new ReproStrategyFactoryImpl(clock);
    }

    private Human createHuman(final HumanType type, final Circle area) {
        return new Human() {
            @Override public HumanType getType() {
                return type;
            }
            @Override
            public void move() {
            }
            @Override
            public Position getPosition() {
                return null;
            }
            @Override
            public Sprite getSprite() {
                return null;
            }
            @Override
            public Direction getDirection() {
                return null;
            }
            @Override
            public boolean collide(final Human other) {
                return false;
            }
            @Override
            public HumanStats getStats() {
                return new HumanStats() {

                    @Override
                    public double getSpeed() {
                        return 0;
                    }

                    @Override
                    public void increaseSpeed() {
                    }

                    @Override
                    public ReproStrategy getReproStrategy() {
                        return null;
                    }

                    @Override
                    public Circle getReproductionAreaRadius() {
                        return area;
                    }

                    @Override
                    public void increaseReproductionAreaRadius() {
                    }

                    @Override
                    public double getSicknessResistence() {
                        return 0;
                    }

                    @Override
                    public void increaseSicknessResistence() {
                    }

                    @Override
                    public double getFertility() {
                        return 0;
                    }

                    @Override
                    public void increaseFertility() {
                    }

                    @Override
                    public void applySpeedModifier(final double multiplyValue) {
                    }

                    @Override
                    public void applyReproductionRangeModifier(final double multiplyValue) {
                    }

                    @Override
                    public void applySicknessResistenceModifier(final double multiplyValue) {
                    }

                    @Override
                    public void resetToBaseSpeed() {
                    }

                    @Override
                    public void resetToBaseSicknessResistence() {
                    }

                    @Override
                    public void resetToBaseReproductionRange() {
                    }
                };
            }
        };
    }

    @Test
    void testMaleReproductionNeverCollides() {
        final ReproStrategy male = factory.maleReproStrategy(new Position(0, 0));
        final Human other = createHuman(HumanType.FEMALE, male.getReproductionArea());
        assertFalse(male.collide(other));
    }

    @Test
    void testFemaleReproductionCollidesWithMaleOnce() {
        final Position pos = new Position(0, 0);
        final ReproStrategy female = factory.femaleReproStrategy(pos);
        final Circle area = female.getReproductionArea();
        final Human male = createHuman(HumanType.MALE, area);

        assertTrue(female.collide(male));
        assertFalse(female.collide(male), "Should be on cooldown after first reproduction");
    }

    @Test
    void testReproductionAreaMovesCorrectly() {
        final Position start = new Position(0, 0);
        final ReproStrategy strategy = factory.maleReproStrategy(start);
        final Circle initial = strategy.getReproductionArea();

        final Position updatedPos = new Position(10, 10);
        strategy.update(updatedPos);
        final Circle moved = strategy.getReproductionArea();

        assertNotEquals(initial.getCenter(), moved.getCenter());
    }
}
