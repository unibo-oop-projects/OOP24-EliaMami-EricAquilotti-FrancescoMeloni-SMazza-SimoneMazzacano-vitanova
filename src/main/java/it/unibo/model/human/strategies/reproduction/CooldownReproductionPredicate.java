package it.unibo.model.human.strategies.reproduction;

import java.time.Clock;
import java.time.Duration;
import java.util.function.Predicate;

import it.unibo.model.human.Human;
import it.unibo.model.human.strategies.CooldownGate;

/**
 * Implements the cooldown logic for the reproduction strategies that need it.
 * After reproduction there's a cooldown before the next one.
 */
public final class CooldownReproductionPredicate implements Predicate<Human> {
    private final Predicate<Human> canReproduceWith;
    private final CooldownGate gate;
    private boolean canReproduce = true;

    /**
     * @param canReproduceWith tells if the current human can reproduce with another human.
     * @param cooldown the time to wait between reproductions.
     * @param clock the clock used to get the time that can be paused. (useful for testing).
     */
    public CooldownReproductionPredicate(final Predicate<Human> canReproduceWith, final Duration cooldown, final Clock clock) {
        this.canReproduceWith = canReproduceWith;
        this.gate = new CooldownGate(cooldown, clock);
    }

    @Override
    public boolean test(final Human other) {
        if (!canReproduce && gate.tryActivate()) {
            canReproduce = true;
        }
        if (canReproduce && canReproduceWith.test(other)) {
            canReproduce = false;
            return true;
        }
        return false;
    }
}
