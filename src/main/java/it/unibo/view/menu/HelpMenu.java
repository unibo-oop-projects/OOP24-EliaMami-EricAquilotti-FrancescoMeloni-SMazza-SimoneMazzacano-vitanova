package it.unibo.view.menu;

import java.util.Collections;
import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;

/**
 * Class that handles the help menu options, to show the controls to the user.
 */
public class HelpMenu extends AbstractMenu {
    private static final String HELP_TEXT = """
        W - move forward\n
        S - move backward\n
        D - move right\n
        A - move left\n
        Esc - opens pause menu
        """;

    /**
     * Constructor for the HelpMenu class.
     * @param input the input handler
     * @param game the game controller
     */
    public HelpMenu(final InputHandler input, final Game game) {
        super(input, game, Collections.singletonList("Back"), Collections.singletonList(g -> g.setMenu(new StartMenu(input, g))),
         false, HELP_TEXT, "Help");
    }

    @Override
    protected void toggleMenu() { }
}
