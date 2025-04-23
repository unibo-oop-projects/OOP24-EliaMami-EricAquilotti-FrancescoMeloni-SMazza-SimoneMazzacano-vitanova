package it.unibo.view.menu;

import it.unibo.common.Position;
import it.unibo.common.Text;
import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


/**
 * Class that handles the menu options, text and input.
 */
public abstract class AbstractMenu implements Menu {
    private static final int TIMER_VALUE = 10;
    private static final int TEXT_VERTICAL_SPACING = 100;
    private static final int TEXT_SIZE = 60;
    private static final int TEXT_HORIZONTAL_OFFSET = 0;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final int MENU_TOGGLE_KEY = KeyEvent.VK_ESCAPE;

    private final List<String> optionsDescriptions;
    private final List<Consumer<Game>> optionsBehaviors;
    private int selectedOptionIndex;
    private boolean isHidden;
    private int timer = TIMER_VALUE;
    private final InputHandler input;
    private final Game game;

    /**
     * @return the input handler
     */
    protected InputHandler getInput() {
        return input;
    }

    /**
     * @return the game controller
     */
    protected Game getGame() {
        return game;
    }

    /**
     * Constructor for the AbstractMenu class.
     * @param input the input handler
     * @param game the game controller
     * @param options the list of options
     * @param optionsBehaviours the list of behaviors for each option
     * @param isInitiallyHidden the initial state of the menu
     */
    protected AbstractMenu(final InputHandler input, final Game game, final List<String> options,
    final List<Consumer<Game>> optionsBehaviours, final boolean isInitiallyHidden) {
        this.input = input;
        this.game = game;
        this.optionsDescriptions = options;
        this.optionsBehaviors = optionsBehaviours;
        this.isHidden = isInitiallyHidden;
    }

    @Override
    public final void update() {
        if (timer > 0) {
            timer--;
        } else if (input.isKeyPressed(MENU_TOGGLE_KEY)) {
            toggleMenu();
            timer = TIMER_VALUE;
        } else if (!this.isHidden && input.isKeyPressed(KeyEvent.VK_DOWN)
         && selectedOptionIndex + 1 < optionsDescriptions.size()) {
            this.selectedOptionIndex++;
            timer = TIMER_VALUE;
        } else if (!this.isHidden && input.isKeyPressed(KeyEvent.VK_UP) && selectedOptionIndex > 0) {
            this.selectedOptionIndex--;
            timer = TIMER_VALUE;
        } else if (!this.isHidden && input.isKeyPressed(KeyEvent.VK_ENTER)) {
            this.optionsBehaviors.get(selectedOptionIndex).accept(game);
            toggleMenu();
        }
    }
    private String applySelectedFormat(final String text) {
        return "> " + text + " <";
    }

    /**
     * Toggles the menu visibility.
     * Could be ovverriden in subclasses to implement custom behavior.
     */
    protected void toggleMenu() {
        isHidden = !isHidden;
    }

    @Override
    public final List<Text> getText() {
        if (!isHidden) {
            int verticalOffset = 0;
            final List<Text> textToShow = new ArrayList<>();
            for (int index = 0; index < optionsDescriptions.size(); index++) {
                final String formattedText = selectedOptionIndex == index 
                    ? applySelectedFormat(optionsDescriptions.get(index)) : optionsDescriptions.get(index);

                textToShow.add(new Text(formattedText, 
                new Position(TEXT_HORIZONTAL_OFFSET, verticalOffset), TEXT_COLOR, TEXT_SIZE));
                verticalOffset += TEXT_VERTICAL_SPACING;
            }
            return textToShow;
        }
        return List.of();
    }
}
