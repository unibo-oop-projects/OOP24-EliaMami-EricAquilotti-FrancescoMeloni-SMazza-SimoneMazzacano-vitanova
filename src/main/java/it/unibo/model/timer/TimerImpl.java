package it.unibo.model.timer;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

/**
 * Timer implementation.
 * It is used to manage the time in the game.
 */
public final class TimerImpl implements Timer {
    private final Clock clock;
    private final Duration targetTime;
    private Instant startingTime;
    /**
     * Constructor for the timer.
     * @param targetTime the time to count down from.
     * @param clock the clock used to tell the time.
     * @throws IllegalArgumentException if the target time is negative.
     */
    public TimerImpl(final Duration targetTime, final Clock clock) {
        if (targetTime.isNegative()) {
            throw new IllegalArgumentException("Target time cannot be negative");
        }
        this.targetTime = targetTime;
        this.clock = clock;
        this.startingTime = clock.instant();
        reset();
    }

    @Override
    public boolean isOver() {
        return getRemainingTime().isZero() || getRemainingTime().isNegative();
    }

    @Override
    public void reset() {
        this.startingTime = clock.instant();
    }

    @Override
    public Duration getRemainingTime() {
        return targetTime.minus(Duration.between(startingTime, clock.instant()));
    }

}
