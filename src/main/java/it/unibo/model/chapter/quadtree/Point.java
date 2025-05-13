package it.unibo.model.chapter.quadtree;

import it.unibo.common.Position;

/**
 * Models a Point in a bidimensional plane with some data attached to it.
 * @param position the point position.
 * @param data the data attached to the point.
 * @param <T> the type of data.
 */
public record Point<T>(Position position, T data) {
}
