package it.unibo.view.menu;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class that represents the content of a menu.
 */
public final class MenuContent {
    private final String title;
    private final String subtitle;
    private final List<String> options;
    private final Optional<Integer> textSize;

    private MenuContent(final String title, final String subtitle, final List<String> options,
                        final Optional<Integer> textSize) {
        this.title = title;
        this.subtitle = subtitle;
        this.options = Collections.unmodifiableList(options);
        this.textSize = textSize;
    }

    private MenuContent(final String title, final String subtitle, final List<String> options) {
        this(title, subtitle, options, Optional.empty());
    }

    /**
     * Gets the title of the menu.
     * @return the title of the menu
     */
    public String title() {
        return title;
    }
    /**
     * Gets the subtitle of the menu.
     * @return the subtitle of the menu
     */
    public String subtitle() {
        return subtitle;
    }
    /**
     * Gets the list of options in the menu.
     * @return the list of options in the menu
     */
    public List<String> options() {
        return options;
    }
    /**
     * Gets the text size of the menu.
     * @return an Optional containing the text size if it is set, otherwise an empty Optional
     */
    public Optional<Integer> textSize() {
        return textSize;
    }

    /**
    * Creates a new MenuContent object with the specified title, subtitle, and options.
     * @param title the title of the menu
     * @param subtitle the subtitle of the menu
     * @param options the list of options in the menu
     * @return a new MenuContent object with the specified title, subtitle, and options
     */
    public static MenuContent of(final String title, final String subtitle, final List<String> options) {
        return new MenuContent(title, subtitle, options, Optional.empty());
    }

    /**
     * Creates a new MenuContent object with empty title, subtitle and options.
     * @return a menu content with empty title, subtitle and options.
     */
    public static MenuContent empty() {
        return new MenuContent("", "", Collections.emptyList());
    }

    /**
     * Creates a new MenuContent object with the specified text size.
     * @param textSize the text size to be set
     * @return a new MenuContent object with the specified text size
     */
    public MenuContent withTextSize(final int textSize) {
        return new MenuContent(this.title, this.subtitle, this.options, Optional.of(textSize));
    }
}
