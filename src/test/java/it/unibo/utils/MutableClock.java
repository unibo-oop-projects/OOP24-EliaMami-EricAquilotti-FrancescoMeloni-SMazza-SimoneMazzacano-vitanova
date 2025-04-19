package it.unibo.utils;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Implementation of a clock that can advance in time.
 */
public final class MutableClock extends Clock {
    private Instant instant;
    private final ZoneId zone;

    /**
     * 
     * @param initial the starting instant.
     * @param zone the time zone.
     */
    public MutableClock(final Instant initial, final ZoneId zone) {
        this.instant = initial;
        this.zone = zone;
    }

    /**
     * 
     * @param duration the time to skip.
     */
    public void advance(final Duration duration) {
        this.instant = this.instant.plus(duration);
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(final ZoneId zone) {
        return new MutableClock(instant, zone);
    }

    @Override
    public Instant instant() {
        return instant;
    }
}
