package it.unibo.model.human;

import it.unibo.common.Direction;

/**
 * Models the player i.e. the Male Human that the user can interact with. It can
 * reproduce and get powerups and get sick.
 */
public interface Player extends Male {
    /**
     * 
     * Changes the direction of the player.
     * @param newDirection the new direction to assign.
     */
    void setDirection(Direction newDirection);

    /**
     * Sets the speed multiplier based on buffs or nerfs got during the game.
     * @param speedMult the new speed multiplier.
     */
    void setSpeedMultiplier(float speedMult);

    /**
     * Sets the hit radious multiplier based on buffs or nerfs got during the
     * game.
     * @param hitRadiousMult
     */
    void setHitRadiousMultiplier(float hitRadiousMult);
}
