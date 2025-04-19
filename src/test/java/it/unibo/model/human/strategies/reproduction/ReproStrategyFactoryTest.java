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
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

public class ReproStrategyFactoryTest {
    private ReproStrategyFactory factory;
    private Clock clock;

    @BeforeEach
    void setup() {
        clock = Clock.fixed(Instant.parse("2025-04-19T10:00:00Z"), ZoneOffset.UTC);
        factory = new ReproStrategyFactoryImpl(clock);
    }

    private Human createHuman(HumanType type, Circle area) {
        return new Human() {
            @Override public HumanType getType() {
                return type;
            }
            @Override public Circle reproductionArea() {
                return area;
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
        };
    }

    @Test
    void testMaleReproductionNeverCollides() {
        ReproStrategy male = factory.maleReproductionStrategy(new Position(0, 0));
        Human other = createHuman(HumanType.FEMALE, male.getReproductionArea());
        assertFalse(male.collide(other));
    }

    @Test
    void testFemaleReproductionCollidesWithMaleOnce() {
        Position pos = new Position(0, 0);
        ReproStrategy female = factory.femaleReproductionStrategy(pos);
        Circle area = female.getReproductionArea();
        Human male = createHuman(HumanType.MALE, area);

        assertTrue(female.collide(male));
        assertFalse(female.collide(male), "Should be on cooldown after first reproduction");
    }

    @Test
    void testReproductionAreaMovesCorrectly() {
        Position start = new Position(0, 0);
        ReproStrategy strategy = factory.maleReproductionStrategy(start);
        Circle initial = strategy.getReproductionArea();

        Position updatedPos = new Position(10, 10);
        strategy.update(updatedPos);
        Circle moved = strategy.getReproductionArea();

        assertNotEquals(initial.getCenter(), moved.getCenter());
    }
}
