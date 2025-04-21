package it.unibo.model.human.strategies;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.CooldownGate;
import it.unibo.utils.MutableClock;

class CooldownGateTest {

    private MutableClock mutableClock;

    @BeforeEach
    void setup() {
        final Instant baseTime = Instant.parse("2025-04-20T10:00:00Z");
        mutableClock = new MutableClock(baseTime, ZoneId.of("UTC"));
    }

    @Test
    void testInitialCooldownNotExpired() {
        final CooldownGate gate = new CooldownGate(Duration.ofSeconds(3), mutableClock);
        assertFalse(gate.tryActivate(), "Should not activate immediately after creation");
    }

    @Test
    void testCooldownExpiredReturnsTrue() {
        final CooldownGate gate = new CooldownGate(Duration.ofSeconds(3), mutableClock);
        mutableClock.advance(Duration.ofSeconds(3));
        assertTrue(gate.tryActivate(), "Should activate after cooldown has passed");
    }

    @Test
    void testCooldownNotExpiredReturnsFalse() {
        final CooldownGate gate = new CooldownGate(Duration.ofSeconds(3), mutableClock);
        mutableClock.advance(Duration.ofSeconds(2));
        assertFalse(gate.tryActivate(), "Should not activate before cooldown");
    }

    @Test
    void testCooldownResetsAfterActivation() {
        final CooldownGate gate = new CooldownGate(Duration.ofSeconds(3), mutableClock);
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
        final CooldownGate gate = new CooldownGate(dynamicCooldown, mutableClock);

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
        final CooldownGate gate = new CooldownGate(Duration.ZERO, mutableClock);
        assertTrue(gate.tryActivate());
        assertTrue(gate.tryActivate());
        assertTrue(gate.tryActivate());
    }

}
