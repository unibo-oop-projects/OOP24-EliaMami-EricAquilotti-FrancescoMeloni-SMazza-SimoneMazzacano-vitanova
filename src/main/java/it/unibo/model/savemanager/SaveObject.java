package it.unibo.model.savemanager;

import java.util.List;

/**
 * Interface that models the SaveObject.
 */
public interface SaveObject {

    /**
     * Method that returns a list of the player's upgrades.
     * @return a list of integers representing the player's upgrades.
     */
    List<Integer> getPlayerUpgrade();

    /**
     * Method that returns the chapter number.
     * @return the chapter number.
     */
    int getChapterNumber();

}
