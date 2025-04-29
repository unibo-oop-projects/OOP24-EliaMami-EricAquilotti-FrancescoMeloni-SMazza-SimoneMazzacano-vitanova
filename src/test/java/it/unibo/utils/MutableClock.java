package it.unibo.utils;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Implementation of a clock that can advance in time.
 */
public final class MutableClock extends Clock {
    private Instant currentTime;
    private final ZoneId zone;

    /**
     * 
     * @param initialTime the initial instant
     * @param zone 
     */
    public MutableClock(final Instant initialTime, final ZoneId zone) {
        this.currentTime = initialTime;
        this.zone = zone;
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(final ZoneId zone) {
        return new MutableClock(currentTime, zone);
    }

    @Override
    public Instant instant() {
        return currentTime;
    }

    /**
     * 
     * @param duration the time to skip.
     */
    public void advance(final Duration duration) {
        // don't know what to put here
        currentTime = currentTime.plus(duration);
    }
}
