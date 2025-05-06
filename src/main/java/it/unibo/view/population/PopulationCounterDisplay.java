package it.unibo.view.population;

import java.awt.Color;

import it.unibo.common.Position;
import it.unibo.common.Text;
import it.unibo.model.chapter.PopulationCounter;

/**
 * Models a population counter display for the view.
 */
public final class PopulationCounterDisplay {
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final int TEXT_SIZE = 60;

    private PopulationCounterDisplay() { }

    /**
     * Creates a Text object representing the population counter.
     * @param populationCounter the population counter to display.
     * @return a Text object representing the population counter.
     */
    public static Text text(final PopulationCounter populationCounter) {
        final String counterString = populationCounter.population() + "/" + populationCounter.populationGoal();
        return new Text(counterString, new Position(0, 0), TEXT_COLOR, TEXT_SIZE);
    }
}
