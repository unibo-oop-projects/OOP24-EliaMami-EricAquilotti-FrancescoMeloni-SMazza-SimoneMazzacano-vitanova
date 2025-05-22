package it.unibo.view.menu;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;

/**
 * Class that handles the win menu options.
 */
public final class WinAndUpgradeMenu extends AbstractMenu {

    private static final int TEXT_SIZE = 45;

    /**
     * Constructor for the WinMenu class.
     * @param input the input handler
     * @param game the game controller
     */
    public WinAndUpgradeMenu(final InputHandler input, final Game game) {
        super(input, game, List.of(
            MenuOption.of(() -> "Speed: " 
            + updateUpgradeText(k -> k.getPlayerStats().getActualSpeedUpgrade(), game), g -> {
                checkAndUpdateSkillPoint(k -> k.getPlayerStats().increaseSpeed(), g);
            }),
            MenuOption.of(() -> "Resistence: " 
            + updateUpgradeText(k -> k.getPlayerStats().getActualSicknessResistenceUpgrade(), game), g -> {
                checkAndUpdateSkillPoint(k -> k.getPlayerStats().increaseSicknessResistence(), g);
            }),
            MenuOption.of(() -> "Area: " 
            + updateUpgradeText(k -> k.getPlayerStats().getActualReproductionRangeUpgrade(), game), g -> {
                checkAndUpdateSkillPoint(k -> k.getPlayerStats().increaseReproductionAreaRadius(), g);
            }), 
            MenuOption.of(() -> "Fertility: " 
            + updateUpgradeText(k -> k.getPlayerStats().getActualFertilityUpgrade(), game), g -> {
                checkAndUpdateSkillPoint(k -> k.getPlayerStats().increaseFertility(), g);
            }),
            MenuOption.nextChapter(input),
            MenuOption.home(input),
            MenuOption.quit()
        ), 
        true,
        () -> "You have " + game.getSkillPoint() + " skill point.",
        "You won the Chapter! Upgrade your skills."
        );
    }

    private static int updateUpgradeText(final Function<Game, Integer> c, final Game g) {
        return c.apply(g);
    }

    private static void checkAndUpdateSkillPoint(final Consumer<Game> c, final Game g) {
        if (g.getSkillPoint() > 0) {
            c.accept(g);
        }
        g.updateSkillPoint();
    }

    @Override
    public MenuContent getContent() {
        return super.getContent().withTextSize(TEXT_SIZE);
    }
}
