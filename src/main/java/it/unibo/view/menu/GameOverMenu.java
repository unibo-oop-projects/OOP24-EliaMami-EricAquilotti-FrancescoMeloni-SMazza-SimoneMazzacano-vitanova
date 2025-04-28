package it.unibo.view.menu;

import java.util.List;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;

/**
 * Class that handles the game over menu options.
 */
public final class GameOverMenu extends AbstractMenu {

    /**
     * Constructor for the GameOverMenu class.
     * @param input the input handler
     * @param game the game controller
     */
    public GameOverMenu(final InputHandler input, final Game game) {
        super(input, game, List.of(
            MenuOption.of("Retry", g -> {
                g.restartCurrentChapter();
                g.setMenu(new GamePlayMenu(input, g));
            }),
            MenuOption.home(input),
            MenuOption.quit()
            ), false, "", "Game Over");
        getGame().toggleGameplayState();
    }

    @Override
    protected void toggleMenu() { 
        getGame().toggleGameplayState();
    }
}
