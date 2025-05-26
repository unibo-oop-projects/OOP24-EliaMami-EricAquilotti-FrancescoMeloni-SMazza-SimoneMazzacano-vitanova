package it.unibo.view.menu;

import java.util.List;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;


/**
 * Class that handles the menu options, text and input.
 */
public final class StartMenu extends AbstractMenu {
    /**
     * Constructor for the StartMenu class.
     * @param input the input handler
     * @param game the game controller
     */
    public StartMenu(final InputHandler input, final Game game) {
        super(input, game, List.of(
        MenuOption.of("Play/Continue", g -> {
            g.setMenu(new PauseMenu(input, g));
            g.startGameplay();
        }),
        MenuOption.of("New game", g -> {
            g.setMenu(new PauseMenu(input, g));
            g.setFirstChapter();
            g.startGameplay();
        }),
        MenuOption.of("Help", g -> g.setMenu(new HelpMenu(input, game))),
        MenuOption.quit()),
        true, "Welcome to the game", "Vitanova");
    }
}
