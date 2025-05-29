package it.unibo.view.menu;

import java.text.DecimalFormat;
import java.util.Collections;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.stats.HumanStats;

/**
 * 
 */
public class StatsMenu extends AbstractMenu {

    /**
     * 
     * @param input
     * @param game
     */
    protected StatsMenu(final InputHandler input, final Game game) {
        super(input, game, Collections.singletonList(
            MenuOption.of("Back", g -> {
                final PauseMenu newPause = new PauseMenu(input, game);
                newPause.toggleMenu();
                g.setMenu(newPause);
            })),
         true, getStatsText(game.getPlayerStats()), "Stats");
    }

    private static String getStatsText(final HumanStats playerStats) {
        return "Speed: " + decimalFormat(playerStats.getSpeed()) + "\n"
        + "Sickness resistence: " + decimalFormat(playerStats.getSicknessResistence()) + "\n"
        + "Reproduction range: " + decimalFormat(playerStats.getReproductionAreaRadius().getRadius()) + "\n"
        + "Fertility: " + decimalFormat(playerStats.getFertility());
    }

    private static String decimalFormat(final Double value) {
        return DecimalFormat.getNumberInstance().format(value);
    }
}
