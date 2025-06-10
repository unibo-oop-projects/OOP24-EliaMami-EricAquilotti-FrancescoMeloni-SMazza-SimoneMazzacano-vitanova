package it.unibo.model.human.strategies.reproduction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import it.unibo.common.Circle;
import it.unibo.common.Position;
import it.unibo.model.human.Human;
import it.unibo.utils.HumanMockup;
import it.unibo.utils.MutableClock;
import it.unibo.view.sprite.HumanType;

class ReproStrategyFactoryTest {
    private final MutableClock mutableClock = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private final ReproStrategyFactory factory = new ReproStrategyFactoryImpl(mutableClock);

    @Test
    void testMaleReproductionNeverCollides() {
        final ReproStrategy male = factory.maleReproStrategy(new Position(0, 0));
        final Human other = HumanMockup.createEmptyHuman(HumanType.FEMALE, male.getReproductionArea());
        assertFalse(male.collide(other));
    }

    @Test
    void testFemaleReproductionCollidesWithMaleOnce() {
        final Position pos = new Position(0, 0);
        final ReproStrategy female = factory.femaleReproStrategy(pos);
        final Circle area = female.getReproductionArea();
        final Human male = HumanMockup.createEmptyHuman(HumanType.MALE, area);

        assertFalse(female.collide(male), "Cooldown has to pass");
        mutableClock.advance(Duration.ofSeconds(2));
        assertTrue(female.collide(male), "Should reproduce after cooldown");
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
