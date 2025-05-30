package it.unibo.controller;

import it.unibo.model.chapter.Chapter;
import it.unibo.model.human.HumanStats;
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
     * @param menu
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
    void restartCurrentChapter();
    /**
     * Sets the new chapter and clears the screen.
     */
    void setNewChapter();
    /**
     * Clears the screen by removing everything, except of the map.
     */
    void clearScreen();
    /**
     * This method gets player from all humans and return his stats.
     * @return player's stats.
     */
    HumanStats getPlayerStats();
    /**
     * This method sets skill points value if skill points isn't already initialized.
     * @param value the value to initialize skill points to.
     */
    void setSkillPoint(int value);
    /**
     * This method returns skill points value.
     * @return skill point's value.
     */
    int getSkillPoint();
    /**
     * This method update the variable skill point if skill point is greater than zero.
     */
    void updateSkillPoint();
    /**
     * Set the next chapter.
     */
    void nextChapter();
    /**
     * This method returns the current chapter.
     * @return the current chapter.
     */
    Chapter getChapter();
}
