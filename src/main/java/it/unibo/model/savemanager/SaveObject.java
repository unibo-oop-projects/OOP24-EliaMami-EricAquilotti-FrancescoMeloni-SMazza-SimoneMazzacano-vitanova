package it.unibo.model.savemanager;

import java.io.Serializable;

import it.unibo.model.human.HumanStats;

/**
 * Class that describe the object that i'm going to save.
 */
public final class SaveObject implements Serializable {
    private static final long serialVersionUID = 2L;
    private final int chapterNumber;
    private final HumanStats playerStats;

    /**
     * Constructor to inizialize the class.
     * @param chapterNumber set the chapterNumber.
     * @param playerStats set the playerStats.
     */
    public SaveObject(final int chapterNumber, final HumanStats playerStats) {
        this.chapterNumber = chapterNumber;
        this.playerStats = playerStats;
    }

    /**
     * Method that returns the playerStats.
     * @return the player's stat.
     */
    public HumanStats getPlayerStats() {
        return playerStats;
    }

    /**
     * Method that returns the chapter number.
     * @return the chapter number.
     */
    public int getChapterNumber() {
        return chapterNumber;
    }
}
