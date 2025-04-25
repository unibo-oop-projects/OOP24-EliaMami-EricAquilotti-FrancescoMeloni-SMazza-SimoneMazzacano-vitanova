package it.unibo.view.menu;

import java.util.function.Consumer;

import it.unibo.controller.Game;

/**
 * Class that handles the menu options, wraps the description and the action to be performed.
 * @param desc the description of the menu option
 * @param action the action to be performed when the option is selected by the user
 */
public record MenuOption(String desc, Consumer<Game> action) {
    /**
     * Static factory method for the MenuOption class.
     * @param desc the description of the menu option
     * @param action the action to be performed when the option is selected by the user
     */
    public static MenuOption of(final String desc, final Consumer<Game> action) {
        return new MenuOption(desc, action);
    }

    /**
     * @return a MenuOption to quit the game
     */
    public static MenuOption quit() {
        return new MenuOption("Quit", Game::exit);
    }

    /**
     * Static factory method for an empty action.
     * @param desc the description of the menu option
     * @return a MenuOption with an empty action
     */
    public static MenuOption emptyAction(final String desc) {
        return new MenuOption(desc, g -> { });
    }

    /**
     * Executes the action associated with the menu option.
     * @param g the game controller
     */
    public void execute(final Game g) {
        action.accept(g);
    }
}
