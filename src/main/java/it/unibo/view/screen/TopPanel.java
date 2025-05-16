package it.unibo.view.screen;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import it.unibo.view.population.PopulationCounterDisplay;
import it.unibo.view.timerdisplay.TimerDisplay;

/**
 * Models the top panel of the screen.
 * It contains the timer and population counter displays and
 * handles their resizing.
 */
public final class TopPanel extends JPanel {
    private static final long serialVersionUID = 6L;
    private static final int TOP_MARGIN = 150;
    private static final int TEXT_LATERAL_MARGIN = 200;
    /**
     * Constructor for the TopPanel class.
     * @param timerDisplay the timer display
     * @param populationCounterDisplay the population counter display
     */
    public TopPanel(final TimerDisplay timerDisplay, final PopulationCounterDisplay populationCounterDisplay) {
        this.setLayout(new SpringLayout());
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(TOP_MARGIN, 0, 0, 0));
        this.add(timerDisplay);
        this.add(populationCounterDisplay);
        addLayoutConstraints(timerDisplay, populationCounterDisplay);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                adjustInnerLabelsFontSize(timerDisplay, populationCounterDisplay);
            }
        });
    }

    private void addLayoutConstraints(final TimerDisplay timerDisplay, final PopulationCounterDisplay populationCounterDisplay) {
        final SpringLayout layout = (SpringLayout) this.getLayout();
        layout.putConstraint(
            SpringLayout.HORIZONTAL_CENTER,
            timerDisplay,
            0,
            SpringLayout.HORIZONTAL_CENTER,
            this
        );
        layout.putConstraint(
            SpringLayout.EAST,
            populationCounterDisplay,
            -TEXT_LATERAL_MARGIN,
            SpringLayout.EAST,
            this
        );
        layout.putConstraint(
            SpringLayout.SOUTH,
            timerDisplay,
            0,
            SpringLayout.SOUTH,
            this
        );
        layout.putConstraint(
            SpringLayout.SOUTH,
            populationCounterDisplay,
            0,
            SpringLayout.SOUTH,
            this
        );
    }

    private float getAdjustedFontSize(final int originalTextSize) {
        return (float) originalTextSize * this.getWidth() / ScreenImpl.BASE_WINDOW_WIDTH;
    }

    private void adjustInnerLabelsFontSize(final TimerDisplay timerDisplay,
    final PopulationCounterDisplay populationCounterDisplay) {
        final float timerFontSize = getAdjustedFontSize(TimerDisplay.TEXT_SIZE);
        timerDisplay.setFont(timerDisplay.getFont().deriveFont(timerFontSize));

        final float populationFontSize = getAdjustedFontSize(PopulationCounterDisplay.TEXT_SIZE);
        populationCounterDisplay.setFont(
            populationCounterDisplay.getFont().deriveFont(populationFontSize)
        );
    }
}
