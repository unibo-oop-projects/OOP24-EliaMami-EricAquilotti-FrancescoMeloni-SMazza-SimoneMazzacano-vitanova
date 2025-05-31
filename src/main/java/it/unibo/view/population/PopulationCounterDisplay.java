package it.unibo.view.population;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import it.unibo.model.chapter.PopulationCounter;

/**
 * Models a population counter display for the view.
 */
public final class PopulationCounterDisplay extends JLabel {
    private static final long serialVersionUID = 2783719L;

    /**
     * The size of the text in the population counter display.
     */
    public static final int TEXT_SIZE = 60;
    private static final Color TEXT_COLOR = Color.WHITE;

    /**
     * Constructor for the population counter display.
     */
    public PopulationCounterDisplay() {
        final Font font = new Font("Verdana", Font.BOLD, TEXT_SIZE);
        this.setFont(font);
        this.setForeground(TEXT_COLOR);
    }

    /**
     * Sets the label text to show population counter display.
     *
     * @param populationCounter the population counter to set
     */
    public void update(final PopulationCounter populationCounter) {
        final String counterString = populationCounter.population() + "/" + populationCounter.populationGoal();
        SwingUtilities.invokeLater(() -> {
            this.setText(counterString);
        });
    }

    /**
     * Clears the population counter display.
     */
    public void clear() {
        SwingUtilities.invokeLater(() -> {
            this.setText("");
        });
    }
}
