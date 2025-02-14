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
     * Player facing up.
     */
    PLAYER_UP_1("human/male/up_1.png"),
    /**
     * Player facing up.
     */
    PLAYER_UP_2("human/male/up_2.png"),
    /**
     * Player facing right.
     */
    PLAYER_RIGHT_1("human/male/right_1.png"),
    /**
     * Player facing right.
     */
    PLAYER_RIGHT_2("human/male/right_2.png"),
    /**
     * Player facing down.
     */
    PLAYER_DOWN_1("human/male/down_1.png"),
    /**
     * Player facing down.
     */
    PLAYER_DOWN_2("human/male/down_2.png"),
    /**
     * Player facing left.
     */
    PLAYER_LEFT_1("human/male/left_1.png"),
    /**
     * Player facing left.
     */
    PLAYER_LEFT_2("human/male/left_2.png"),
    /**
     * Male human facing up.
     */
    MALE_UP_1("human/male/up_1.png"),
    /**
     * Male human facing up.
     */
    MALE_UP_2("human/male/up_2.png"),
    /**
     * Male human facing right.
     */
    MALE_RIGHT_1("human/male/right_1.png"),
    /**
     * Male human facing right.
     */
    MALE_RIGHT_2("human/male/right_2.png"),
    /**
     * Male human facing down.
     */
    MALE_DOWN_1("human/male/down_1.png"),
    /**
     * Male human facing down.
     */
    MALE_DOWN_2("human/male/down_2.png"),
    /**
     * Male human facing left.
     */
    MALE_LEFT_1("human/male/left_1.png"),
    /**
     * Male human facing left.
     */
    MALE_LEFT_2("human/male/left_2.png"),
    /**
     * Female human facing up.
     */
    FEMALE_UP_1("human/female/up_1.png"),
    /**
     * Female human facing up.
     */
    FEMALE_UP_2("human/female/up_2.png"),
    /**
     * Female human facing right.
     */
    FEMALE_RIGHT_1("human/female/right_1.png"),
    /**
     * Female human facing right.
     */
    FEMALE_RIGHT_2("human/female/right_2.png"),
    /**
     * Female human facing down.
     */
    FEMALE_DOWN_1("human/female/down_1.png"),
    /**
     * Female human facing down.
     */
    FEMALE_DOWN_2("human/female/down_2.png"),
    /**
     * Female human facing left.
     */
    FEMALE_LEFT_1("human/female/left_1.png"),
    /**
     * Female human facing left.
     */
    FEMALE_LEFT_2("human/female/left_2.png"),
    /**
     * Grass tile.
     */
    TILE_GRASS("tile/grass.png"),
    /**
     * Water tile.
     */
    TILE_WATER("tile/water.png");

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
