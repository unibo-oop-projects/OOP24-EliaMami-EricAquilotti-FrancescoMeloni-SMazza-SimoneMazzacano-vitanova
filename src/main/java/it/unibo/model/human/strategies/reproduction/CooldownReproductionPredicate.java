package it.unibo.model.human.strategies.reproduction;

import java.time.Clock;
import java.time.Duration;

import it.unibo.common.CooldownGate;
import it.unibo.model.human.Human;

/**
 * Implements the cooldown logic for the reproduction strategies that need it.
 * After reproduction there's a cooldown before the next one.
 */
public final class CooldownReproductionPredicate implements SerializablePredicate<Human> {
    private final SerializablePredicate<Human> canReproduceWith;
    private final CooldownGate gate;
    private boolean firstReproduction = true;

    /**
     * @param canReproduceWith tells if the current human can reproduce with another human.
     * @param cooldown the time to wait between reproductions.
     * @param clock the clock used to get the time that can be paused. (useful for testing).
     */
    public CooldownReproductionPredicate(final SerializablePredicate<Human> canReproduceWith, final Duration cooldown, final Clock clock) {
        this.canReproduceWith = canReproduceWith;
        this.gate = new CooldownGate(cooldown, clock);
    }

    @Override
    public boolean test(final Human other) {
        if (firstReproduction && canReproduceWith.test(other)) {
            firstReproduction = false;
            gate.tryActivate();
            return true;
        }
        return canReproduceWith.test(other) && gate.tryActivate();
    }
}
