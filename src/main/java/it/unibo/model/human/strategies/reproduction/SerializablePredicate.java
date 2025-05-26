package it.unibo.model.human.strategies.reproduction;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * Marker interface to make serializable predicate.
 */
public interface SerializablePredicate<T> extends Predicate<T>, Serializable {}
