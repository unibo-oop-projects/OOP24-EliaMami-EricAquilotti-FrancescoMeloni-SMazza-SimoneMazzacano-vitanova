package it.unibo.view.menu;

import java.util.List;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;

/**
 * Class that handles the win menu options.
 */
public final class WinMenu extends AbstractMenu {

    /**
     * Constructor for the WinMenu class.
     * @param input the input handler
     * @param game the game controller
     */
    public WinMenu(final InputHandler input, final Game game) {
        super(input, game, List.of(
        MenuOption.of("Restart chapter", g -> {
            g.restartCurrentChapter();
            g.setMenu(new GamePlayMenu(input, game));
        }),
        MenuOption.home(input),
        MenuOption.quit()
        ), true, "", "You won the Chapter!");
    }
}
