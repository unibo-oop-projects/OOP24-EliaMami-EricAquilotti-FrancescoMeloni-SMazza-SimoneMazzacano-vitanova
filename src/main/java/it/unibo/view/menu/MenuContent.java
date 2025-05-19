package it.unibo.view.menu;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class that represents the content of a menu.
 * @param title the title of the menu
 * @param subtitle the subtitle of the menu
 * @param options the list of options in the menu
 * @param textSize the size of the text in the menu
 */
public record MenuContent(String title, String subtitle, List<String> options, Optional<Integer> textSize) {
    /**
     * Constructor for the MenuContent class.
     */
    public MenuContent {
        options = Collections.unmodifiableList(options);
    }

    /**
     * Constructor for the MenuContent class.
     * @param title the title of the menu
     * @param subtitle the subtitle of the menu
     * @param options the list of options in the menu
     */
    public MenuContent(final String title, final String subtitle, final List<String> options) {
        this(title, subtitle, options, Optional.empty());
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
