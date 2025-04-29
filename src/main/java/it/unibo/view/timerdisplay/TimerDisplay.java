package it.unibo.view.timerdisplay;

import java.awt.Color;
import java.time.Duration;

import it.unibo.common.Position;
import it.unibo.common.Text;

/**
 * Class that handles the timer display.
 */
public final class TimerDisplay {
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final int TEXT_SIZE = 60;

    private TimerDisplay() { }

    private static String format(final Duration duration) {
        return String.format("%02d:%02d", duration.toMinutesPart(),
        duration.toSecondsPart());
    }

    /**
     * Creates a Text object that represents the timer.
     * @param timerValue the duration of the timer
     * @return a Text object that represents the timer
     */
    public static Text text(final Duration timerValue) {
        final String time = format(timerValue);
        return new Text(time, new Position(0, 0), TEXT_COLOR, TEXT_SIZE);
    }
}
