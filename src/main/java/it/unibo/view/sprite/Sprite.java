package it.unibo.view.sprite;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import it.unibo.common.DirectionEnum;
import it.unibo.model.tile.TileType;

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
    private static final Map<HumanType, Map<DirectionEnum, Sprite[]>> SPRITE_CHARACTERS_MAP = new EnumMap<>(HumanType.class);
    private static final Map<TileType, Sprite> SPRITE_TILES_MAP = new EnumMap<>(TileType.class);
    static {
        for (final HumanType type : HumanType.values()) {
            final Map<DirectionEnum, Sprite[]> directionMap = new EnumMap<>(DirectionEnum.class);
            for (final DirectionEnum direction : DirectionEnum.values()) {
                directionMap.put(
                    direction,
                    Arrays.stream(values())
                    .filter(s -> s.name().startsWith(type.name() + "_" + direction.name() + "_"))
                    .toArray(Sprite[]::new)
                    );
                }
            SPRITE_CHARACTERS_MAP.put(type, directionMap);
        }
        for (final TileType tileType : TileType.values()) {
            SPRITE_TILES_MAP.put(tileType, valueOf(tileType.toString()));
        }
    }

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

    /**
     * 
     * @param type the type of human we want to get the sprite of.
     * @param direction the direction the human is facing.
     * @param frame the sprite animation frame.
     * @return the correct sprite if the human is moving.
     */
    public static Optional<Sprite> getSprite(final HumanType type, final DirectionEnum direction, final int frame) {
        if (direction == DirectionEnum.NONE) {
            return Optional.empty();
        }
        return Optional.of(SPRITE_CHARACTERS_MAP.get(type).get(direction)[frame % 2]);
    }

    /**
     * @throws IllegalArgumentException if the specified enum type has no constant with the specified name,
     * or the specified class object does not represent an enum type.
     * @param tileType the tile type we want to get the sprite of.
     * @return the correct sprite if the human is moving.
     */
    public static Sprite getSprite(final TileType tileType) {
        return SPRITE_TILES_MAP.get(tileType);
    }
}
