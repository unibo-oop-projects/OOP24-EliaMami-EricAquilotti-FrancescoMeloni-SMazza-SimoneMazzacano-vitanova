package it.unibo.controller;

import it.unibo.model.chapter.Chapter;
import it.unibo.model.human.stats.HumanStats;
import it.unibo.model.human.stats.StatType;
import it.unibo.model.skillpoint.SkillPoint;
import it.unibo.view.menu.Menu;

/**
 * Interface that models a Game engine.
 */
public interface Game {
    /**
     * Starts the gameplay.
     */
    void startGameplay();

    /**
     * Set current menu.
     * @param menu the menu to set.
     */
    void setMenu(Menu menu);

    /**
     * Pauses the gameplay.
     * @param paused true if the game is paused, false otherwise.
     */
    void setGameplayState(boolean paused);

    /**
     * Exits the game.
     */
    void exit();

    /**
     * Restarts the current chapter.
     */
    void resetCurrentChapter();

    /**
     * Sets the new chapter and clears the screen.
     */
    void setNewChapter();

    /**
     * Clears the screen by removing everything, except of the map.
     */
    void clearScreen();

    /**
     * This method return player stats.
     * @return player's stats.
     */
    HumanStats getPlayerStats();

    /**
     * This method check if there are enough skill point to increase the selected stat, if that's true then increase the stat.
     * @param stat the type of stat to increase.
     */
    void checkAndIncreaseStats(StatType stat);

    /**
     * Set the next chapter.
     */
    void setNextChapter();

    /**
     * Set the first chapter.
     */
    void setFirstChapter();

    /**
     * This method returns the current chapter.
     * @return the current chapter.
     */
    Chapter getChapter();

    /**
     * Save the game.
     */
    void saveGame();

    /**
     * This method returns the skill points.
     * @return the skill points.
     */
    SkillPoint getSkillPoint();
}
