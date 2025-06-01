package it.unibo.view.timerdisplay;

import java.awt.Color;
import java.awt.Font;
import java.time.Duration;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Class that handles the timer display.
 */
public final class TimerDisplay extends JLabel {
    private static final long serialVersionUID = 2631287L;

    /**
     * The size of the text in the timer display.
     */
    public static final int TEXT_SIZE = 60;
    private static final Color TEXT_COLOR = Color.WHITE;

    /**
     * Constructor for the timer display.
     */
    public TimerDisplay() {
        final Font font = new Font("Verdana", Font.BOLD, TEXT_SIZE);
        this.setFont(font);
        this.setForeground(TEXT_COLOR);
    }

    private String format(final Duration duration) {
        return String.format("%02d:%02d", duration.toMinutesPart(),
        duration.toSecondsPart());
    }

    /**
     * Sets the duration of the timer display.
     *
     * @param duration the timer duration to set
     */
    public void update(final Duration duration) {
        SwingUtilities.invokeLater(() -> {
            this.setText(format(duration));
        });
    }

    /**
     * Clears the timer display.
     */
    public void clear() {
        SwingUtilities.invokeLater(() -> {
            this.setText("");
        });
    }
}
