package it.unibo.view.menu;

import it.unibo.common.Position;
import it.unibo.common.Text;
import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that handles the menu options, text and input.
 */
public abstract class AbstractMenu implements Menu {
    private static final int TIMER_VALUE = 10;
    private static final int TEXT_VERTICAL_SPACING = 100;
    private static final int TEXT_SIZE = 60;
    private static final int TEXT_SUBTITLE_SIZE = 40;
    private static final int TEXT_HORIZONTAL_OFFSET = 0;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final int MENU_TOGGLE_KEY = KeyEvent.VK_ESCAPE;

    private final List<MenuOption> options;
    private final String title;
    private final String subtitle;
    private int selectedOptionIndex;
    private boolean isVisible;
    private int timer = TIMER_VALUE;
    private final InputHandler input;
    private final Game game;

    /**
     * @return the input handler
     */
    protected final InputHandler getInput() {
        return input;
    }

    /**
     * @return the game controller
     */
    protected final Game getGame() {
        return game;
    }

    /**
     * Constructor for the AbstractMenu class.
     * @param input the input handler
     * @param game the game controller
     * @param options the list of MenuOption
     * @param isInitiallyVisible the initial state of the menu
     * @param subtitle the subtitle of the menu
     * @param title the title of the menu
     */
    protected AbstractMenu(final InputHandler input, final Game game, final List<MenuOption> options,
    final boolean isInitiallyVisible, final String subtitle, final String title) {
        this.title = title;
        this.subtitle = subtitle;
        this.input = input;
        this.game = game;
        this.options = options;
        this.isVisible = isInitiallyVisible;
        getGame().setGameplayState(isInitiallyVisible);
    }

    @Override
    public final void update() {
        if (timer > 0) {
            timer--;
        } else if (input.isKeyPressed(MENU_TOGGLE_KEY)) {
            toggleMenu();
            timer = TIMER_VALUE;
        } else if (this.isVisible && input.isKeyPressed(KeyEvent.VK_DOWN)
         && selectedOptionIndex + 1 < options.size()) {
            this.selectedOptionIndex++;
            timer = TIMER_VALUE;
        } else if (this.isVisible && input.isKeyPressed(KeyEvent.VK_UP) && selectedOptionIndex > 0) {
            this.selectedOptionIndex--;
            timer = TIMER_VALUE;
        } else if (this.isVisible && input.isKeyPressed(KeyEvent.VK_ENTER)) {
            onExit();
            this.options.get(selectedOptionIndex).execute(game);
        }
    }

    private String applySelectedFormat(final String text) {
        return "> " + text + " <";
    }

    /**
     * Toggles the menu.
     * Default behavior is to do nothing.
     * Subclasses can override this method to provide custom behavior.
     */
    protected void toggleMenu() { }

    /**
     * Toggles the visibility of the menu.
     */
    protected final void toggleVisibility() {
        isVisible = !isVisible;
    }

    /**
     * @return the whether the menu is visible or not
     */
    protected final boolean visibility() {
        return isVisible;
    }

    private void onExit() {
        isVisible = false;
        getGame().setGameplayState(false);
    }

    private void addText(final List<Text> textToShow, final String text, final int verticalOffset, final int textSize) {
        textToShow.add(new Text(text, 
        new Position(TEXT_HORIZONTAL_OFFSET, verticalOffset), TEXT_COLOR, textSize));
    }

    @Override
    public final List<Text> getText() {
        if (isVisible) {
            int verticalOffset = TEXT_VERTICAL_SPACING; // upper border of the menu options
            final List<Text> textToShow = new ArrayList<>();
            addText(textToShow, title, verticalOffset, TEXT_SIZE);
            verticalOffset += TEXT_VERTICAL_SPACING;
            if (!subtitle.isEmpty()) {
                addText(textToShow, subtitle, verticalOffset, TEXT_SUBTITLE_SIZE);
                verticalOffset += TEXT_VERTICAL_SPACING;
            }

            for (int index = 0; index < options.size(); index++) {
                final String formattedText = selectedOptionIndex == index 
                    ? applySelectedFormat(options.get(index).desc()) : options.get(index).desc();

                addText(textToShow, formattedText, verticalOffset, TEXT_SIZE);
                verticalOffset += TEXT_VERTICAL_SPACING;
            }
            return textToShow;
        }
        return List.of();
    }
}
