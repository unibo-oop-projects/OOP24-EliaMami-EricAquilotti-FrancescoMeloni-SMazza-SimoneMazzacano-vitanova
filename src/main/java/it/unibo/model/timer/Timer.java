package it.unibo.model.timer;

import java.time.Duration;

/**
 * Timer interface.
 * It is used to manage the time in the game.
 */
public interface Timer {
    void update(Duration deltaTime);
    void reset();
    Duration getRemainingTime();
    boolean isOver();
}
