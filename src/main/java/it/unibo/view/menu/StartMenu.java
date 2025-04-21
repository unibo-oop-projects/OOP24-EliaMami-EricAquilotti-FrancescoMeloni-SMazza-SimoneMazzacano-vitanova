package it.unibo.view.menu;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;


/**
 * Class that handles the menu options, text and input.
 */
public final class StartMenu extends AbstractMenu {

    public StartMenu(InputHandler input, Game game) {
        super(input, game, false);
    }

    @Override
    protected void play() {
        game.setMenu(new GamePlayMenu(input, game));
        game.startGameplay();
    }
   
}
