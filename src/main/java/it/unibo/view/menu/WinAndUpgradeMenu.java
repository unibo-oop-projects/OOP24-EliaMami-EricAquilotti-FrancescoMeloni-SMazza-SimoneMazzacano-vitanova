package it.unibo.view.menu;

import java.util.List;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;

/**
 * Class that handles the win menu options.
 */
public final class WinAndUpgradeMenu extends AbstractMenu {
    /**
     * Constructor for the WinMenu class.
     * @param input the input handler
     * @param game the game controller
     */
    public WinAndUpgradeMenu(final InputHandler input, final Game game) {
        super(input, game, List.of(
        MenuOption.of("Speed: " + updateSpeedText(game), g -> {
            if (g.getSkillPoint() > 0) {
                g.getPlayerStats().increaseSpeed();
            }
            g.updateSkillPoint();
        }),
        MenuOption.of("Resistence: " + updateSicknessResistenceText(game), g -> {
            if (g.getSkillPoint() > 0) {
                g.getPlayerStats().increaseSicknessResistence();
            }
            g.updateSkillPoint();
        }),
        MenuOption.of("Area: " + updateReproductionAreaText(game), g -> {
            if (g.getSkillPoint() > 0) {
                g.getPlayerStats().increaseReproductionAreaRadius();
            }
            g.updateSkillPoint();
        }), 
        MenuOption.of("Fertility: " + updateFertilityText(game), g -> {
            if (g.getSkillPoint() > 0) {
                g.getPlayerStats().increaseFertility();
            }
            g.updateSkillPoint();
        }),
        MenuOption.home(input),
        MenuOption.quit()), 
        true, "You have " + game.getSkillPoint() + " skill point.", "You won the Chapter! Upgrade your skills.", AbstractMenu.getSelectedOptionIndex());
    }

    private static int updateSpeedText(Game game){
        return game.getPlayerStats().getActualSpeedUpgrade();
    }

    private static int updateSicknessResistenceText(Game game){
        return game.getPlayerStats().getActualSicknessResistenceUpgrade();
    }

    private static int updateReproductionAreaText(Game game){
        return game.getPlayerStats().getActualReproductionRangeUpgrade();
    }

    private static int updateFertilityText(Game game){
        return game.getPlayerStats().getActualFertilityUpgrade();
    }
}
