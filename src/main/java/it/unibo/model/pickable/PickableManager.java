package it.unibo.model.pickable;

import java.util.List;

/**
 * Models a pickableManager that will handle all operations for pickable
 */
public interface PickableManager {
    /**
     * Spawn a random pickable in the map. 
     */
    void spawnPickable();

    /**
     * Solve all collisions between pickable and player.
     */
    void solvePickableCollisions();

    /**
     * Resets the effects of the pickables taken by the player.
     */
    void resetExpiredEffects();

    /**
     * Method that returns the pickables in the map.
     * @return the pickables in the map.
     */
    List<Pickable> getPickables();

    /**
     * Method that clear the pickables.
     */
    void resetPickables();

    /**
     * Method that clear the activated pickables.
     */
    void resetActivatedPickables();

    /**
     * Set the spawnPickableRate to his default value.
     */
    void setSpawnPickableRate();

}
