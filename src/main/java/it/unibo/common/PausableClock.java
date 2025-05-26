package it.unibo.common;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Implementation of a clock that can be pause and unpaused. This class can be
 * extended for testing purposes.
 */
public final class PausableClock extends Clock {
    private final Clock baseClock;
    private boolean paused;
    private Instant pauseStart;
    private Duration pausedDuration = Duration.ZERO;

    /**
     * 
     * @param baseClock the base clock used to create a pausable one.
     */
    public PausableClock(final Clock baseClock) {
        this.baseClock = baseClock;
    }

    @Override
    public ZoneId getZone() {
        return baseClock.getZone();
    }

    @Override
    public Instant instant() {
        final Instant now = baseClock.instant().minus(pausedDuration);
        return paused
            ? now.minus(Duration.between(pauseStart, baseClock.instant()))
            : now;
    }

    @Override
    public Clock withZone(final ZoneId zone) {
        return baseClock.withZone(zone);
    }

    /**
     * Pauses the clock, it won't tick while paused.
     */
    public void pause() {
        if (!paused) {
            paused = true;
            pauseStart = baseClock.instant();
        }
    }

    /**
     * Unpauses the clock, it will start ticking again.
     */
    public void unpause() {
        if (paused) {
            paused = false;
            pausedDuration = pausedDuration.plus(Duration.between(pauseStart, baseClock.instant()));
        }
    }
}
