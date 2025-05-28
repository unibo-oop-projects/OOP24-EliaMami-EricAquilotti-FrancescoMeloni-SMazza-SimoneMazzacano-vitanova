package it.unibo.view.menu;

import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;


/**
 * Class that handles the menu options, text and input.
 */
public abstract class AbstractMenu implements Menu {
    private static final int TIMER_VALUE = 10;
    private static final int MENU_TOGGLE_KEY = KeyEvent.VK_ESCAPE;

    private final List<MenuOption> options;
    private final String title;
    private final Supplier<String> subtitle;
    private int selectedOptionIndex;
    private boolean isVisible;
    private int timer = TIMER_VALUE;
    private final InputHandler input;
    private final Game game;

    /**
     * 
     * @return the input handler
     */
    protected final InputHandler getInput() {
        return input;
    }

    /**
     * 
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
    final boolean isInitiallyVisible, final Supplier<String> subtitle, final String title) {
        this.title = title;
        this.subtitle = subtitle;
        this.input = input;
        this.game = game;
        this.options = options;
        this.isVisible = isInitiallyVisible;
        getGame().setGameplayState(isInitiallyVisible);
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
        this(input, game, options, isInitiallyVisible, () -> subtitle, title);
    }

    @Override
    public final void update() {
        if (timer > 0) {
            timer--;
        } else if (input.isKeyPressed(MENU_TOGGLE_KEY)) {
            toggleMenu();
            timer = TIMER_VALUE;
        } else if (this.isVisible && (input.isKeyPressed(KeyEvent.VK_DOWN) || input.isKeyPressed(KeyEvent.VK_S))
                && selectedOptionIndex + 1 < options.size()) {
            selectedOptionIndex++;
            timer = TIMER_VALUE;
        } else if (this.isVisible && (input.isKeyPressed(KeyEvent.VK_UP) || input.isKeyPressed(KeyEvent.VK_W)) 
                && selectedOptionIndex > 0) {
            selectedOptionIndex--;
            timer = TIMER_VALUE;
        } else if (this.isVisible && (input.isKeyPressed(KeyEvent.VK_ENTER) || input.isKeyPressed(KeyEvent.VK_SPACE))) {
            onExit();
            timer = TIMER_VALUE;
            options.get(selectedOptionIndex).action().accept(getGame());
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

    /**
     * Called when the menu is exited.
     * Default behavior is to set the visibility to false and update the game state.
     * Subclasses can override this method to provide custom behavior.
     */
    protected void onExit() {
        isVisible = false;
        getGame().setGameplayState(false);
    }

    private String formatOptionText(final int index) {
        return selectedOptionIndex == index 
            ? applySelectedFormat(options.get(index).desc().get()) 
            : options.get(index).desc().get();
    }

    /**
     * Gets the content of the menu.
     * Can be overridden by subclasses to provide custom behavior (e.g. custom text size).
     * @return the content of the menu
     */
    @Override
    public MenuContent getContent() {
        if (isVisible) {
            final List<String> list = IntStream.range(0, options.size())
                .mapToObj(this::formatOptionText).toList();
            return MenuContent.of(title, subtitle.get(), list);
        }
        return MenuContent.empty();
    }
}
