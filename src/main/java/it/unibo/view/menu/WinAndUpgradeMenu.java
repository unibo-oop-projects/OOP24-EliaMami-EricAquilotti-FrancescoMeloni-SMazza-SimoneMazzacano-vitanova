package it.unibo.view.menu;

import java.util.List;
import java.util.function.Consumer;

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
            checkAndUpdateSkillPoint(k -> k.getPlayerStats().increaseSpeed(), g);
        }),
        MenuOption.of("Resistence: " + updateSicknessResistenceText(game), g -> {
            checkAndUpdateSkillPoint(k -> k.getPlayerStats().increaseSicknessResistence(), g);
        }),
        MenuOption.of("Area: " + updateReproductionAreaText(game), g -> {
            checkAndUpdateSkillPoint(k -> k.getPlayerStats().increaseReproductionAreaRadius(), g);
        }), 
        MenuOption.of("Fertility: " + updateFertilityText(game), g -> {
            checkAndUpdateSkillPoint(k -> k.getPlayerStats().increaseFertility(), g);
        }),
        MenuOption.home(input),
        MenuOption.quit()), 
        true, "You have " + game.getSkillPoint() + " skill point.", "You won the Chapter! Upgrade your skills.", AbstractMenu.getSelectedOptionIndex());
    }

    private static void checkAndUpdateSkillPoint(final Consumer<Game> c, final Game g){
        if (g.getSkillPoint() > 0) {
            c.accept(g);    
        }
        g.updateSkillPoint();
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
