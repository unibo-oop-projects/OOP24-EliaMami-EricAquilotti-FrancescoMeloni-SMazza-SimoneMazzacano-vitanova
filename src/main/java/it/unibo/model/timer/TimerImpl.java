package it.unibo.model.timer;

import java.time.Duration;

/**
 * Timer implementation.
 * It is used to manage the time in the game.
 */
public final class TimerImpl implements Timer {
    private final Duration targetTime;
    private Duration remainingTime;
    /**
     * Constructor for the timer.
     * @param targetTime the time to count down from.
     */
    public TimerImpl(final Duration targetTime) {
        this.targetTime = targetTime;
        reset();
    }

    @Override
    public void update(final Duration deltaTime) {
        this.remainingTime = this.remainingTime.minus(deltaTime);
    }

    @Override
    public boolean isOver() {
        return this.remainingTime.isZero() || this.remainingTime.isNegative();
    }

    @Override
    public void reset() {
        this.remainingTime = targetTime;
    }

    @Override
    public Duration getRemainingTime() {
        return this.remainingTime;
    }

}
