package it.unibo.model.human.strategies.reproduction;

import java.time.Clock;
import java.time.Duration;
import java.util.function.Predicate;
import java.util.function.Supplier;

import it.unibo.common.CooldownGate;
import it.unibo.model.human.Human;

/**
 * Implements the cooldown logic for the reproduction strategies that need it.
 * After reproduction there's a cooldown before the next one.
 */
public final class CooldownReproductionPredicate implements Predicate<Human> {
    private final Predicate<Human> canReproduceWith;
    private final CooldownGate gate;

    /**
     * Creates a new cooldown reproduction predicate initializing the fields.
     * 
     * @param canReproduceWith tells if the current human can reproduce with another human.
     * @param cooldown the time to wait between reproductions.
     * @param clock the clock used to get the time that can be paused. (useful for testing).
     */
    public CooldownReproductionPredicate(
        final Predicate<Human> canReproduceWith,
        final Supplier<Duration> cooldown,
        final Clock clock
    ) {
        this.canReproduceWith = canReproduceWith;
        this.gate = new CooldownGate(cooldown, clock);
    }

    @Override
    public boolean test(final Human other) {
        return canReproduceWith.test(other) && gate.tryActivate();
    }

    /**
     * Method that tells if the reproduction is on cooldown.
     * @return if the reproduction is on Cooldown.
     */
    public boolean isOnCooldown() {
        return !this.gate.checkStatus();
    }
}
