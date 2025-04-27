package it.unibo.model.timer;

import java.time.Duration;

/**
 * Timer interface.
 * It is used to manage the time in the game.
 */
public interface Timer {
    /**
     * Updates the timer with the given delta time from the last update.
     * @param deltaTime the time to update the timer with.
     */
    void update(Duration deltaTime);

    /**
     * Resets the timer to the initial value.
     */
    void reset();

    /**
     * @return the remaining time.
     */
    Duration getRemainingTime();

    /**
     * @return true if the timer is over.
     */
    boolean isOver();
}
