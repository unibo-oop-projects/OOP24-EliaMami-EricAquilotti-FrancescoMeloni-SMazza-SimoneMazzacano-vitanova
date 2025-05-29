package it.unibo.model.pickable;

import java.util.List;

/**
 * 
 */
public interface PickableManager {
    /**
     * 
     */
    void spawnPickable();

    /**
     * 
     */
    void solvePickableCollisions();

    /**
     * 
     */
    void resetExpiredEffects();

    /**
     * 
     * @return the pickables on the map.
     */
    List<Pickable> getPickables();

    /**
     * 
     */
    void resetPickables();

    /**
     * 
     */
    void resetActivatedPickables();

    /**
     * 
     */
    void setSpawnPickableRate();

}
