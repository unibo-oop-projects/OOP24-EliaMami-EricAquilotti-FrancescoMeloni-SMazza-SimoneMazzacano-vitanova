package it.unibo.model.human.strategies;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

/**
 * A generic cooldown utility that allows an action to occur only after a
 * certain duration has passed.
 */
public final class CooldownGate {
    private final Supplier<Duration> cooldownSupplier;
    private final Clock clock;
    private Instant lastActivation;

    /**
     * Creates a gate with a fixed cooldown duration.
     * @param cooldown the duration to wait for.
     * @param clock the clock used to get the time. (useful for testing).
     */
    public CooldownGate(final Duration cooldown, final Clock clock) {
        this(() -> cooldown, clock);
    }

    /**
     * Creates a gate with a dynamic cooldown duration (can change every activation).
     * @param cooldownSupplier the supllier of durations to wait for.
     * @param clock the clock used to get the time. (useful for testing).
     */
    public CooldownGate(final Supplier<Duration> cooldownSupplier, final Clock clock) {
        this.cooldownSupplier = cooldownSupplier;
        this.clock = clock;
        this.lastActivation = clock.instant();
    }

    /**
     * @return true if cooldown expired and resets it.
     */
    public boolean tryActivate() {
        final Instant now = clock.instant();
        final Duration cooldown = cooldownSupplier.get();
        if (Duration.between(lastActivation, now).compareTo(cooldown) >= 0) {
            lastActivation = now;
            return true;
        }
        return false;
    }
}
