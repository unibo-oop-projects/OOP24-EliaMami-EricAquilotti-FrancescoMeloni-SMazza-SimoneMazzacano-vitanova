package it.unibo.model.human.strategies.reproduction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.Circle;
import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.model.human.Human;
import it.unibo.utils.MutableClock;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

class CooldownPredicateTest {

    private static final Duration COOLDOWN = Duration.ofSeconds(2);
    private Clock baseClock;
    private Human createHuman(final HumanType type) {
        return new Human() {
            @Override public HumanType getType() {
                return type;
            }
            @Override public Circle reproductionArea() {
                return null;
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

    private CooldownReproductionPredicate noFemalePredicate(final Clock clock) {
        return new CooldownReproductionPredicate(h -> h.getType() != HumanType.FEMALE, COOLDOWN, clock);
    }

    @BeforeEach
    void setup() {
        baseClock = Clock.fixed(Instant.parse("2025-04-19T10:00:00Z"), ZoneOffset.UTC);
    }

    @Test
    void testReproductionAndCooldown() {
        final Human male = createHuman(HumanType.MALE);
        final CooldownReproductionPredicate predicate = noFemalePredicate(baseClock);
        assertTrue(predicate.test(male), "Should reproduce initially");

        assertFalse(predicate.test(male), "Should be on cooldown");
    }

    @Test
    void testCooldownExpiresAndAllowsReproductionAgain() {
        final MutableClock mutableClock = new MutableClock(baseClock.instant(), baseClock.getZone());
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
        final CooldownReproductionPredicate predicate = noFemalePredicate(baseClock);
        assertFalse(predicate.test(female));
    }
}
