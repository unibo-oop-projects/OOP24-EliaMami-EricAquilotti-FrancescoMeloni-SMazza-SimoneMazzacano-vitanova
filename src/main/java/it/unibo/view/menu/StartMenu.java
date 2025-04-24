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
        super(input, game, List.of("Play", "Help", "Quit"), List.of(g -> {
            g.setMenu(new GamePlayMenu(input, g));
            g.startGameplay();
        }, g -> g.setMenu(new HelpMenu(input, game)), Game::exit), false, "Welcome to the game", "Vitanova");
    }

    @Override
    protected void toggleMenu() { }
}
