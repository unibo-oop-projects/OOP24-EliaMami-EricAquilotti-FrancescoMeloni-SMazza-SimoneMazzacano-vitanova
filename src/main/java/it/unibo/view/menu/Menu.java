package it.unibo.view.menu;

/**
 * Interface that handles the menu options, text and input.
 */
public interface Menu {
    /**
     * 
     *  updates the menu state.
     */
    void update();

    /**
     * 
     * @return the list of text to show.
     */
    MenuContent getContent();
}
