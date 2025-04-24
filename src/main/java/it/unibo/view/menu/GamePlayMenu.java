package it.unibo.view.menu;

import java.util.List;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;


/**
 * Class that handles the menu options, text and input.
 */
public final class GamePlayMenu extends AbstractMenu {

    /**
     * Constructor for the GamePlayMenu class.
     * @param input the input handler
     * @param game the game controller
     */
    public GamePlayMenu(final InputHandler input, final Game game) {
        super(input, game, List.of("Play", "Quit"), List.of(g -> { }, Game::exit), true, "", "Game paused");
    }

    @Override
    protected void toggleMenu() {
        super.toggleMenu();
        getGame().toogleGameplayState();
    }
}
