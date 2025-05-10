package it.unibo.view.menu;

import java.util.Collections;
import java.util.List;

/**
 * Class that represents the content of a menu.
 * @param title the title of the menu
 * @param subtitle the subtitle of the menu
 * @param options the list of options in the menu
 */
public record MenuContent(String title, String subtitle, List<String> options) {
    /**
     * Constructor to ensure immutability of the options list.
     */
    public MenuContent {
        options = Collections.unmodifiableList(options);
    }

    /**
     * Creates a new MenuContent object with empty title, subtitle and options.
     * @return a menu content with empty title, subtitle and options.
     */
    public static MenuContent empty() {
        return new MenuContent("", "", Collections.emptyList());
    }
}
