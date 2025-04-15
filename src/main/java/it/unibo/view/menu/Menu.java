package it.unibo.view.menu;

import java.util.List;

import it.unibo.common.Text;
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
    List<Text> getText();
}
