package it.unibo.model.human.strategies;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import it.unibo.common.PausableClock;
import it.unibo.utils.MutableClock;

class CooldownGateTest {

    private final MutableClock mutableClock = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private final PausableClock pausableClock = new PausableClock(mutableClock);

    @Test
    void testInitialCooldownNotExpired() {
        final CooldownGate gate = new CooldownGate(Duration.ofSeconds(3), pausableClock);
        assertFalse(gate.tryActivate(), "Should not activate immediately after creation");
    }

    @Test
    void testCooldownExpiredReturnsTrue() {
        final CooldownGate gate = new CooldownGate(Duration.ofSeconds(3), pausableClock);
        mutableClock.advance(Duration.ofSeconds(3));
        assertTrue(gate.tryActivate(), "Should activate after cooldown has passed");
    }

    @Test
    void testCooldownNotExpiredReturnsFalse() {
        final CooldownGate gate = new CooldownGate(Duration.ofSeconds(3), pausableClock);
        mutableClock.advance(Duration.ofSeconds(2));
        assertFalse(gate.tryActivate(), "Should not activate before cooldown");
    }

    @Test
    void testCooldownResetsAfterActivation() {
        final CooldownGate gate = new CooldownGate(Duration.ofSeconds(3), pausableClock);
        mutableClock.advance(Duration.ofSeconds(3));
        assertTrue(gate.tryActivate());
        mutableClock.advance(Duration.ofSeconds(2));
        assertFalse(gate.tryActivate(), "Should wait again after reset");
        mutableClock.advance(Duration.ofSeconds(1));
        assertTrue(gate.tryActivate(), "Should work again after full cooldown");
    }

    @Test
    void testDynamicCooldownChanges() {
        final AtomicInteger callCount = new AtomicInteger();
        final Supplier<Duration> dynamicCooldown = () -> Duration.ofSeconds(2 + callCount.getAndIncrement());
        final CooldownGate gate = new CooldownGate(dynamicCooldown, pausableClock);

        // First: needs 2s
        mutableClock.advance(Duration.ofSeconds(2));
        assertTrue(gate.tryActivate());

        // Second: needs 3s
        mutableClock.advance(Duration.ofSeconds(2));
        assertFalse(gate.tryActivate());
        // Third: needs 2s
        mutableClock.advance(Duration.ofSeconds(2));
        assertTrue(gate.tryActivate());

        // Fourth: needs 5s
        mutableClock.advance(Duration.ofSeconds(3));
        assertFalse(gate.tryActivate());
        // Fifth: needs 3s
        mutableClock.advance(Duration.ofSeconds(3));
        assertTrue(gate.tryActivate());
    }

    @Test
    void testZeroCooldownAlwaysActivates() {
        final CooldownGate gate = new CooldownGate(Duration.ZERO, pausableClock);
        assertTrue(gate.tryActivate());
        assertTrue(gate.tryActivate());
        assertTrue(gate.tryActivate());
    }

    @Test
    void testPauseCooldownNotExpires() {
        final CooldownGate gate = new CooldownGate(Duration.ofSeconds(2), pausableClock);
        assertFalse(gate.tryActivate(), "Should wait for cooldown");
        pausableClock.pause();
        mutableClock.advance(Duration.ofSeconds(3));
        assertFalse(gate.tryActivate(), "No activation because it's paused");
        pausableClock.unpause();
        assertFalse(gate.tryActivate(), "Still no activation because advanced but in pause");
        mutableClock.advance(Duration.ofSeconds(2));
        assertTrue(gate.tryActivate(), "Should activate, cooldown has passed");
    }
}
