package it.unibo.model.human.sickness;

import it.unibo.model.human.Human;

/**
 * Models a sickness manager that manages the sickness of a human.
 * It is used to apply the sickness effect to a human, check its status and solve the spread of sickness.
 */
public interface SicknessManager {
    /**
     * Applies the sickness effect to the player.
     * @param player the player to apply the sickness effect to.
     * @param currentPopulationSize the population size of the player's chapter
     */
    void applyToPlayer(Human player, int currentPopulationSize);
    /**
     * Checks if the human is still sick or not.
     * @param human the human to check the status of.
     */
    void checkStatus(Human human);
    /**
     * Solves the spread of sickness between humans and their child.
     * @param male the male human
     * @param female the female human
     * @param child the child human
     * @param currentPopulationSize the population size of the humans chapter
     */
    void solveSpread(Human male, Human female, Human child, int currentPopulationSize);
}
