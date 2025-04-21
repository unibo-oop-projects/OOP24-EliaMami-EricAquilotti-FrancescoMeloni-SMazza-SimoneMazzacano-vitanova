package it.unibo.view.menu;

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
        super(input, game, false);
    }

    @Override
    protected void play() {
        getGame().setMenu(new GamePlayMenu(getInput(), getGame()));
        getGame().startGameplay();
    }
}
