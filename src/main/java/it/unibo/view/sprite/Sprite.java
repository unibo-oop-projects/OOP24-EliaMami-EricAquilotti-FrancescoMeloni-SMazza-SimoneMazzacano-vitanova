package it.unibo.view.sprite;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Set of sprites for the tiles and humans.
 */
public enum Sprite {

    /**
     * Male uman facing up.
     */
    PLAYER_UP_1("male_up_1.png"),
    /**
     * Male uman facing up.
     */
    PLAYER_UP_2("male_up_2.png"),
    /**
     * Male uman facing right.
     */
    PLAYER_RIGHT_1("male_right_1.png"),
    /**
     * Male uman facing right.
     */
    PLAYER_RIGHT_2("male_right_2.png"),
    /**
     * Male uman facing down.
     */
    PLAYER_DOWN_1("male_down_1.png"),
    /**
     * Male uman facing down.
     */
    PLAYER_DOWN_2("male_down_2.png"),
    /**
     * Male uman facing left.
     */
    PLAYER_LEFT_1("male_left_1.png"),
    /**
     * Male uman facing left.
     */
    PLAYER_LEFT_2("male_left_2.png");

    private static final String ROOT_SPRITES = "it/unibo/view/sprites/";
    private final BufferedImage image;

    Sprite(final String path) {
        try {
            this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(ROOT_SPRITES + path));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load sprite: " + path, e);
        }
    }

    /**
     * 
     * @return a copy of the image.
     */
    public BufferedImage getImage() {
        final ColorModel cm = image.getColorModel();
        final boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        final WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
