package it.unibo.model.timer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.common.PausableClock;
import it.unibo.utils.MutableClock;

class TimerTest {
    private final MutableClock mutableClock = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private final PausableClock pausableClock = new PausableClock(mutableClock);

    @Test
    void testTimerCreation() {
        final Duration targetTime = Duration.ofSeconds(3);
        final Timer timer = new TimerImpl(targetTime, pausableClock);

        mutableClock.advance(Duration.ofSeconds(2));
        assertFalse(timer.isOver(), "Timer should not be over before target time");
        assertEquals(Duration.ofSeconds(1), timer.getRemainingTime(), "Remaining time should be 1 seconds");
    }

    @Test
    void testOverTargetTime() {
        final Duration targetTime = Duration.ofSeconds(3);
        final Timer timer = new TimerImpl(targetTime, pausableClock);

        mutableClock.advance(Duration.ofSeconds(3));
        assertTrue(timer.isOver(), "Timer should be over after all target time has passed");
        assertEquals(Duration.ZERO, timer.getRemainingTime(), "Remaining time should be 0 seconds");

        mutableClock.advance(Duration.ofSeconds(1));
        assertTrue(timer.isOver(), "Timer should be over after more than target time has passed");
        assertEquals(Duration.ZERO, timer.getRemainingTime(), "Remaining time should be 0 seconds");
    }

    @Test
    void testNegativeTargetTime() {
        final Duration negativetargetTime = Duration.ofSeconds(-3);
        assertThrowsExactly(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new TimerImpl(negativetargetTime, pausableClock);
            }
        }, "Target time cannot be negative");

        final Duration targetTime = Duration.ofSeconds(3);
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new TimerImpl(targetTime, pausableClock);
            }
        }, "Target has to be positive");
    }

    @Test 
    void testZeroTargetTime() {
        final Duration targetTime = Duration.ZERO;

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new TimerImpl(targetTime, pausableClock);
            }
        }, "Target time can be zero");

        final Timer timer = new TimerImpl(targetTime, pausableClock);
        assertTrue(timer.isOver(), "Timer should be over when target time is zero");
        assertEquals(Duration.ZERO, timer.getRemainingTime(), "Remaining time should be 0 seconds");
    }

    @Test
    void testPauseClock() {
        final Duration targetTime = Duration.ofSeconds(3);
        final Timer timer = new TimerImpl(targetTime, pausableClock);

        pausableClock.pause();
        mutableClock.advance(Duration.ofSeconds(1));
        assertFalse(timer.isOver(), "Timer should not be over while paused");
        assertEquals(Duration.ofSeconds(3), timer.getRemainingTime(), "No time should be counted while paused");
        pausableClock.unpause();
        assertEquals(Duration.ofSeconds(3), timer.getRemainingTime(),
        "Remaining time should be equal to target time after unpausing");
        mutableClock.advance(Duration.ofSeconds(2));
        assertFalse(timer.isOver(), "Timer should not be over after unpausing");
        assertEquals(Duration.ofSeconds(1), timer.getRemainingTime(), "Remaining time should be 1 second after unpausing");
        mutableClock.advance(Duration.ofSeconds(1));
        assertTrue(timer.isOver(), "Timer should be over after unpausing and advancing");
        assertEquals(Duration.ZERO, timer.getRemainingTime(), "Remaining time should be 0 seconds after unpausing and advancing");
    }

    @Test
    void testResetTimer() {
        final Duration targetTime = Duration.ofSeconds(3);
        final Timer timer = new TimerImpl(targetTime, pausableClock);

        mutableClock.advance(Duration.ofSeconds(2));
        timer.reset();
        assertFalse(timer.isOver(), "Timer should not be over after reset");
        assertEquals(targetTime, timer.getRemainingTime(),
        "Remaining time should be equal to target time after reset");
    }
}
