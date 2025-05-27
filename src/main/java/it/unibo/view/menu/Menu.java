package it.unibo.view.menu;

/**
 * Interface that handles the menu options, content and input.
 */
public interface Menu {
    /**
     * 
     *  updates the menu state.
     */
    void update();

    /**
     * 
     * @return the menu content to show.
     */
    MenuContent getContent();
}
