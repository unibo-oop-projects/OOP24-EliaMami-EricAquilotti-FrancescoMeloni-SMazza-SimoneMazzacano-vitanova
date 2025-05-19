package it.unibo.view.menu;

import java.util.Collections;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.HumanStats;

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
        return "Speed: " + playerStats.getSpeed() + "\n" 
        + "Reproduction range: " + playerStats.getReproductionAreaRadius().getRadius() + "\n" 
        + "Sickness resistence: " + playerStats.getSicknessResistence() + "\n" 
        + "Fertility: " + playerStats.getFertility();
    }
}
