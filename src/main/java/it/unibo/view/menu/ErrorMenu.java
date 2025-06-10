package it.unibo.view.menu;

import java.util.List;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;

/**
 * Class that handles the errors during the gameplay.
 */
public class ErrorMenu extends AbstractMenu {
    /**
     * Constructor for error menu.
     * @param input the input handler
     * @param game the game controller
     * @param subtitle the error's info.
     */ 
    public ErrorMenu(final InputHandler input, final Game game, final String subtitle) {
        super(input, game, List.of(MenuOption.of("New game", g -> {
            g.setFirstChapter();
            g.setMenu(new PauseMenu(input, g));
            g.startGameplay();
        })), true, subtitle, "Error:");
    }
}
