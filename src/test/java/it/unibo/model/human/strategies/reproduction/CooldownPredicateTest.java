package it.unibo.model.human.strategies.reproduction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import it.unibo.common.Direction;
import it.unibo.common.PausableClock;
import it.unibo.common.Position;
import it.unibo.model.human.Human;
import it.unibo.model.human.stats.HumanStats;
import it.unibo.utils.MutableClock;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

class CooldownPredicateTest {

    private static final Duration COOLDOWN = Duration.ofSeconds(2);
    private final MutableClock mutableClock = new MutableClock(Instant.now(), ZoneId.systemDefault());

    private Human createHuman(final HumanType type) {
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
                return null;
            }
        };
    }

    private CooldownReproductionPredicate noFemalePredicate(final Clock clock) {
        return new CooldownReproductionPredicate(h -> h.getType() != HumanType.FEMALE, COOLDOWN, clock);
    }

    @Test
    void testReproductionAndCooldown() {
        final Human male = createHuman(HumanType.MALE);
        final CooldownReproductionPredicate predicate = noFemalePredicate(mutableClock);
        assertTrue(predicate.test(male), "Should reproduce initially");

        assertFalse(predicate.test(male), "Should be on cooldown");
    }

    @Test
    void testCooldownExpiresAndAllowsReproductionAgain() {
        final CooldownReproductionPredicate predicate = noFemalePredicate(mutableClock);
        final Human male = createHuman(HumanType.MALE);

        assertTrue(predicate.test(male), "Initial reproduction allowed");

        mutableClock.advance(Duration.ofSeconds(1));
        assertFalse(predicate.test(male), "Still on cooldown after one second");

        mutableClock.advance(Duration.ofSeconds(1));
        assertTrue(predicate.test(male), "Cooldown expired, can reproduce again");
    }

    @Test
    void testNoReproduction() {
        final Human female = createHuman(HumanType.FEMALE);
        final CooldownReproductionPredicate predicate = noFemalePredicate(mutableClock);
        assertFalse(predicate.test(female));
    }

    @Test
    void testPause() {
        final PausableClock pausableClock = new PausableClock(mutableClock);
        final Human male = createHuman(HumanType.MALE);
        final CooldownReproductionPredicate predicate = noFemalePredicate(pausableClock);
        assertTrue(predicate.test(male), "Should reproduce initially");

        pausableClock.pause();
        mutableClock.advance(Duration.ofSeconds(3));
        assertFalse(predicate.test(male), "Time paused");

        pausableClock.unpause();
        assertFalse(predicate.test(male), "Cooldown");
        mutableClock.advance(Duration.ofSeconds(2));
        assertTrue(predicate.test(male), "Cooldown expired, can reproduce again");
    }
}
