package it.unibo.model.saveobject;

import java.io.Serializable;
import java.util.List;

/**
 * Class that describe the object that i'm going to save.
 */
public final class SaveObjectImpl implements Serializable, SaveObject {
    private static final long serialVersionUID = 2L;
    /** 
     * The chapter number to save.
     */
    private final int chapterNumber;
    /** 
     * The list of player upgrade to save.
     */
    private final List<Integer> playerUpgrade;

    /**
     * Constructor to inizialize the class.
     * @param chapterNumber set the chapterNumber.
     * @param playerUpgrade set the playerUpgrade.
     */
    public SaveObjectImpl(final int chapterNumber, final List<Integer> playerUpgrade) {
        this.chapterNumber = chapterNumber;
        this.playerUpgrade = List.copyOf(playerUpgrade);
    }

    @Override
    public List<Integer> getPlayerUpgrade() {
        return List.copyOf(playerUpgrade);
    }

    @Override
    public int getChapterNumber() {
        return chapterNumber;
    }
}
