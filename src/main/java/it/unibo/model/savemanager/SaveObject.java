package it.unibo.model.savemanager;

import java.io.Serializable;
import java.util.List;

/**
 * Class that describe the object that i'm going to save.
 */
public final class SaveObject implements Serializable {
    private static final long serialVersionUID = 2L;
    private final int chapterNumber;
    private final List<Integer> playerUpgrade;

    /**
     * Constructor to inizialize the class.
     * @param chapterNumber set the chapterNumber.
     * @param playerUpgrade set the playerUpgrade.
     */
    public SaveObject(final int chapterNumber, final List<Integer> playerUpgrade) {
        this.chapterNumber = chapterNumber;
        this.playerUpgrade = List.copyOf(playerUpgrade);
    }

    /**
     * Method that returns the playerStats.
     * @return the player's stat.
     */
    public List<Integer> getPlayerUpgrade() {
        return List.copyOf(playerUpgrade);
    }

    /**
     * Method that returns the chapter number.
     * @return the chapter number.
     */
    public int getChapterNumber() {
        return chapterNumber;
    }
}
