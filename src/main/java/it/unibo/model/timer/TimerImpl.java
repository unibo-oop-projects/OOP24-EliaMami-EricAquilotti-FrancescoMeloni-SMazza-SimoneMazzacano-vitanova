package it.unibo.model.timer;

import java.time.Duration;

/**
 * Timer implementation.
 * It is used to manage the time in the game.
 */
public final class TimerImpl implements Timer {
    private final int targertTimeInSeconds;
    private Duration remainingTime;
    public TimerImpl(final int targertTimeInSeconds) {
        this.targertTimeInSeconds = targertTimeInSeconds;
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
        this.remainingTime = Duration.ofSeconds(targertTimeInSeconds);
    }

    @Override
    public Duration getRemainingTime() {
        return this.remainingTime;
    }

}
