package it.unibo.model.human.strategies.reproduction;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Predicate;

import it.unibo.model.human.Human;

/**
 * Implements the cooldown logic for the reproduction strategies that need it.
 * After reproduction there's a cooldown before the next one.
 */
public final class CooldownPredicate implements Predicate<Human> {
    private final Predicate<Human> canReproduceWith;
    private final Duration cooldown;
    private final Clock clock;
    private boolean canReproduce = true;
    private Instant lastReproduction = Instant.MIN;

    /**
     * @param canReproduceWith tells if the current human can reproduce with another human.
     * @param cooldown the time to wait between reproductions.
     * @param clock the clock used to get the time. (useful for testing).
     */
    public CooldownPredicate(final Predicate<Human> canReproduceWith, final Duration cooldown, final Clock clock) {
        this.canReproduceWith = canReproduceWith;
        this.cooldown = cooldown;
        this.clock = clock;
    }

    @Override
    public boolean test(final Human other) {
        final Instant now = clock.instant();
        if (!canReproduce && Duration.between(lastReproduction, now).compareTo(cooldown) >= 0) {
            canReproduce = true;
        }
        if (canReproduce && canReproduceWith.test(other)) {
            canReproduce = false;
            lastReproduction = now;
            return true;
        }
        return false;
    }
}
