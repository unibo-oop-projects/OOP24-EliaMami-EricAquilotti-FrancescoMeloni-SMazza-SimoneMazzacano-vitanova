package it.unibo.view.menu;

import java.util.List;
import java.util.function.Function;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.stats.StatType;

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
                + updateUpgradeText(k -> k.getPlayerStats().getSpeedUpgrade(), game),
                g -> g.checkAndIncreaseStats(StatType.SPEED)),
            MenuOption.of(() -> "Resistence: " 
                + updateUpgradeText(k -> k.getPlayerStats().getSicknessResistenceUpgrade(), game),
                g -> g.checkAndIncreaseStats(StatType.SICKNESS_RESISTENCE)),
            MenuOption.of(() -> "Area: " 
                + updateUpgradeText(k -> k.getPlayerStats().getReproductionRangeUpgrade(), game),
                g -> g.checkAndIncreaseStats(StatType.REPRODUCTION_RANGE)), 
            MenuOption.of(() -> "Fertility: " 
                + updateUpgradeText(k -> k.getPlayerStats().getFertilityUpgrade(), game),
                g -> g.checkAndIncreaseStats(StatType.FERTILITY)),
            MenuOption.nextChapter(input),
            MenuOption.of("Home", g -> {
                g.setNextChapter();
                g.setMenu(new StartMenu(input, g));
            })
        ), 
        true,
        () -> "You have " + game.getSkillPoint().getValue() + " skill point.",
        "You won the " + game.getChapter().getChapterNumber() + " Chapter! Upgrade your skills."
        );
    }

    private static int updateUpgradeText(final Function<Game, Integer> c, final Game g) {
        return c.apply(g);
    }

    @Override
    protected void onExit() { }

    @Override
    public MenuContent getContent() {
        return super.getContent().withTextSize(TEXT_SIZE);
    }
}
