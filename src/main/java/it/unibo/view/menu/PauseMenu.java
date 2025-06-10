package it.unibo.view.menu;

import java.util.List;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;


/**
 * Class that handles the menu options during the gameplay.
 */
public final class PauseMenu extends AbstractMenu {

    /**
     * Constructor for the PauseMenu class.
     * @param input the input handler
     * @param game the game controller
     */
    public PauseMenu(final InputHandler input, final Game game) {
        super(input, game, List.of(
        MenuOption.emptyAction("Resume"),
        MenuOption.of("Restart", Game::restartCurrentChapter), 
        MenuOption.of("Stats", g -> g.setMenu(new StatsMenu(input, g))),
        MenuOption.home(input),
        MenuOption.of("New Map", g -> {
            g.setNewChapter();
            g.startGameplay();
        }),
        MenuOption.quit()),
        false, "", "Current chapter: " + game.getChapter().getChapterNumber());
    }

    @Override
    protected void toggleMenu() {
        toggleVisibility();
        getGame().setGameplayState(visibility());
    }
}
