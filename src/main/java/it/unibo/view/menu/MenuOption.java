package it.unibo.view.menu;

import java.util.function.Consumer;
import java.util.function.Supplier;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;

/**
 * Class that handles the menu options, wraps the description and the action to be performed.
 * @param desc the description of the menu option
 * @param action the action to be performed when the option is selected by the user
 */
public record MenuOption(Supplier<String> desc, Consumer<Game> action) {
    /**
     * Static factory method for the MenuOption class.
     * @param desc the description of the menu option
     * @param action the action to be performed when the option is selected by the user
     */
    public static MenuOption of(final String desc, final Consumer<Game> action) {
        return new MenuOption(() -> desc, action);
    }

    /**
     * 
     * @return
     */
    public static MenuOption of(final Supplier<String> desc, final Consumer<Game> action) {
        return new MenuOption(desc, action);
    }

    /**
     * @return a MenuOption to quit the game
     */
    public static MenuOption quit() {
        return new MenuOption(() -> "Quit", Game::exit);
    }

    /**
     * Static factory method for an empty action.
     * @param desc the description of the menu option
     * @return a MenuOption with an empty action
     */
    public static MenuOption emptyAction(final String desc) {
        return new MenuOption(() -> desc, g -> { });
    }

    /**
     * Static factory method for the home menu option.
     * @param input the input handler
     * @return a MenuOption to go back to the home menu
     */
    public static MenuOption home(final InputHandler input) {
        return new MenuOption(() -> "Home", g -> {
            g.setNewChapter();
            g.setMenu(new StartMenu(input, g));
        });
    }

    /**
     * Static factory method for the next chapter menu option.
     * @param input the input handler
     * @return a MenuOption to go to the next chapter
     */
    public static MenuOption nextChapter(final InputHandler input) {
        return new MenuOption(() -> "Next Chapter", g -> {
            g.nextChapter();
            g.setMenu(new PauseMenu(input, g));
        });
    }
}
