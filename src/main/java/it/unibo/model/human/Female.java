package it.unibo.model.human;

/**
 * Models a female human that can reproduce if it's ready and a collision with a
 * male happens.
 */
public interface Female extends Human {
    /**
     * 
     * @param other the male to collide with.
     * @return true if the collision happens.
     */
    boolean collide(Male other);
}
