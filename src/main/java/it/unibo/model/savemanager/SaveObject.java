package it.unibo.model.savemanager;

import java.util.List;

/**
 * Interface that models the SaveObject.
 */
public interface SaveObject {

    /**
     * Method that returns the playerStats.
     * @return the player's stat.
     */
    List<Integer> getPlayerUpgrade();

    /**
     * Method that returns the chapter number.
     * @return the chapter number.
     */
    int getChapterNumber();

}
