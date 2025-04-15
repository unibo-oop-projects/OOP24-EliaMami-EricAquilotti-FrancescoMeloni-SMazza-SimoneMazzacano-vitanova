package it.unibo.common;

import java.awt.Color;
/**
 * Class that represents a text to be drawn on the screen.
 * @param content the content of the text
 * @param position the position of the text
 * @param color the color of the text
 * @param size the size of the text
 */
public record Text(String content, Position position, Color color, int size) {
}
