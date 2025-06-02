package it.unibo.view.sprite;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import it.unibo.common.DirectionEnum;
import it.unibo.common.SicknessEnum;
import it.unibo.model.tile.wavefunction.TileType;

/**
 * Set of sprites for the tiles and humans.
 */
public enum Sprite {
    /**
     * Player facing up.
     */
    PLAYER_UP_1("human/player/up_1.png"),
    /**
     * Player facing up.
     */
    PLAYER_UP_2("human/player/up_2.png"),
    /**
     * Player facing right.
     */
    PLAYER_RIGHT_1("human/player/right_1.png"),
    /**
     * Player facing right.
     */
    PLAYER_RIGHT_2("human/player/right_2.png"),
    /**
     * Player facing down.
     */
    PLAYER_DOWN_1("human/player/down_1.png"),
    /**
     * Player facing down.
     */
    PLAYER_DOWN_2("human/player/down_2.png"),
    /**
     * Player facing left.
     */
    PLAYER_LEFT_1("human/player/left_1.png"),
    /**
     * Player facing left.
     */
    PLAYER_LEFT_2("human/player/left_2.png"),
    /**
     * Player facing up with a sick face.
     */
    PLAYER_UP_SICK_1("human/player/sick/up_1_sick.png"),
    /**
     * Player facing up with a sick face.
     */
    PLAYER_UP_SICK_2("human/player/sick/up_2_sick.png"),
    /**
     * Player facing right with a sick face.
     */
    PLAYER_RIGHT_SICK_1("human/player/sick/right_1_sick.png"),
    /**
     * Player facing right with a sick face.
     */
    PLAYER_RIGHT_SICK_2("human/player/sick/right_2_sick.png"),
    /**
     * Player facing down with a sick face.
     */
    PLAYER_DOWN_SICK_1("human/player/sick/down_1_sick.png"),
    /**
     * Player facing down with a sick face.
     */
    PLAYER_DOWN_SICK_2("human/player/sick/down_2_sick.png"),
    /**
     * Player facing left with a sick face.
     */
    PLAYER_LEFT_SICK_1("human/player/sick/left_1_sick.png"),
    /**
     * Player facing left with a sick face.
     */
    PLAYER_LEFT_SICK_2("human/player/sick/left_2_sick.png"),
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
     * Male human facing up with a sick face.
     */
    MALE_UP_SICK_1("human/male/sick/up_1_sick.png"),
    /**
     * Male human facing up with a sick face.
     */
    MALE_UP_SICK_2("human/male/sick/up_2_sick.png"),
    /**
     * Male human facing right with a sick face.
     */
    MALE_RIGHT_SICK_1("human/male/sick/right_1_sick.png"),
    /**
     * Male human facing right with a sick face.
     */
    MALE_RIGHT_SICK_2("human/male/sick/right_2_sick.png"),
    /**
     * Male human facing left with a sick face.
     */
    MALE_LEFT_SICK_1("human/male/sick/left_1_sick.png"),
    /**
     * Male human facing left with a sick face.
     */
    MALE_LEFT_SICK_2("human/male/sick/left_2_sick.png"),
    /**
     * Male human facing down with a sick face.
     */
    MALE_DOWN_SICK_1("human/male/sick/down_1_sick.png"),
    /**
     * Male human facing down with a sick face.
     */
    MALE_DOWN_SICK_2("human/male/sick/down_2_sick.png"),
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
     * Female human facing up with a sick face.
     */
    FEMALE_UP_SICK_1("human/female/sick/up_1_sick.png"),
    /**
     * Female human facing up with a sick face.
     */
    FEMALE_UP_SICK_2("human/female/sick/up_2_sick.png"),
    /**
     * Female human facing right with a sick face.
     */
    FEMALE_RIGHT_SICK_1("human/female/sick/right_1_sick.png"),
    /**
     * Female human facing right with a sick face.
     */
    FEMALE_RIGHT_SICK_2("human/female/sick/right_2_sick.png"),
    /**
     * Female human facing left with a sick face.
     */
    FEMALE_LEFT_SICK_1("human/female/sick/left_1_sick.png"),
    /**
     * Female human facing left with a sick face.
     */
    FEMALE_LEFT_SICK_2("human/female/sick/left_2_sick.png"),
    /**
     * Female human facing down with a sick face.
     */
    FEMALE_DOWN_SICK_1("human/female/sick/down_1_sick.png"),
    /**
     * Female human facing down with a sick face.
     */
    FEMALE_DOWN_SICK_2("human/female/sick/down_2_sick.png"),
    /**
     * Grass tile.
     */
    TILE_GRASS("tile/grass.png"),
    /**
     * Water tile.
     */
    TILE_WATER("tile/water.png"),
    /**
     * Rock tile.
     */
    TILE_ROCK("tile/rock.png"),
    /**
     * Upper coast tile.
     */
    TILE_COAST_UP("tile/coastUp.png"),
    /**
     * Right coast tile.
     */
    TILE_COAST_RIGHT("tile/coastRight.png"),
    /**
     * Down coast tile.
     */
    TILE_COAST_DOWN("tile/coastDown.png"),
    /**
     * Left coast tile.
     */
    TILE_COAST_LEFT("tile/coastLeft.png"),
    /**
     * Upper-Right coast tile.
     */
    TILE_COAST_UP_RIGHT("tile/coastUpRight.png"),
    /**
     * Right-Down coast tile.
     */
    TILE_COAST_RIGHT_DOWN("tile/coastRightDown.png"),
    /**
     * Down-Left coast tile.
     */
    TILE_COAST_DOWN_LEFT("tile/coastDownLeft.png"),
    /**
     * Upper-Left coast tile.
     */
    TILE_COAST_UP_LEFT("tile/coastUpLeft.png"),
    /**
     * Upper-Right2 coast tile.
     */
    TILE_COAST_UP_RIGHT2("tile/coastUpRight2.png"),
    /**
     * Right-Down2 coast tile.
     */
    TILE_COAST_RIGHT_DOWN2("tile/coastRightDown2.png"),
    /**
     * Down-Left2 coast tile.
     */
    TILE_COAST_DOWN_LEFT2("tile/coastDownLeft2.png"),
    /**
     * Upper-Left2 coast tile.
     */
    TILE_COAST_UP_LEFT2("tile/coastUpLeft2.png"),
    /**
     * Pickable speed boost.
     */
    PICKABLE_SPEED_BOOST("powerUp/speedBoost.png"),
    /**
     * Pickable sickness resistence boost.
     */
    PICKABLE_SICKNESS_RESISTENCE("powerUp/sicknessResistence.png"),
     /**
     * Pickable reproduction boost.
     */
    PICKABLE_REPRODUCTION_BOOST("powerUp/reproductionBoost.png");


    private static final String ROOT_SPRITES = "it/unibo/view/sprites/";
    private final BufferedImage image;
    private static final Map<HumanType, Map<DirectionEnum, Map<Boolean, Sprite[]>>> SPRITE_CHARACTERS_MAP = 
    new EnumMap<>(HumanType.class);
    private static final Map<TileType, Sprite> SPRITE_TILES_MAP = new EnumMap<>(TileType.class);
    private static final Map<PowerUpType, Sprite> SPRITE_POWER_UP = new EnumMap<>(PowerUpType.class);
    static {
        for (final HumanType type : HumanType.values()) {
            final Map<DirectionEnum, Map<Boolean, Sprite[]>> directionMap = new EnumMap<>(DirectionEnum.class);
            for (final DirectionEnum direction : DirectionEnum.values()) {
                final Map<Boolean, Sprite[]> sicknessMap = new HashMap<>();
                for (final SicknessEnum sickness : SicknessEnum.values()) {
                    sicknessMap.put(
                        sickness == SicknessEnum.SICK,
                        Arrays.stream(values())
                        .filter(s -> s.name().startsWith(type.name() + "_" + direction.name() + "_"
                             + (sickness == SicknessEnum.SICK ? sickness.name() : "")))
                        .toArray(Sprite[]::new)
                    );
                }
                directionMap.put(
                    direction,
                    sicknessMap
                );
            }
            SPRITE_CHARACTERS_MAP.put(type, directionMap);
        }
        for (final TileType tileType : TileType.values()) {
            SPRITE_TILES_MAP.put(tileType, valueOf(tileType.toString()));
        }
        for (final PowerUpType powerUpType : PowerUpType.values()) {
            SPRITE_POWER_UP.put(powerUpType, valueOf(powerUpType.toString()));
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
     * @param isHumanSick true if the human is sick, false otherwise.
     * @return the correct sprite if the human is moving.
     */
    public static Optional<Sprite> getSprite(final HumanType type, final DirectionEnum direction,
    final boolean isHumanSick, final int frame) {
        if (direction == DirectionEnum.NONE) {
            return Optional.empty();
        }
        return Optional.of(SPRITE_CHARACTERS_MAP.get(type).get(direction).get(isHumanSick)[frame % 2]);
    }

    /**
     * Returns the {@code Sprite} of the corresponding type given, can only be {@code TileType} or {@code PowerUpType}.
     * @throws IllegalArgumentException if the specified enum type has no constant with the specified name,
     * or the specified class object does not represent an enum type.
     * @param <T> the type ({@code TileType} or {@code PowerUpType}) we want to get the sprite of.
     * @param t the enumType we want to get the sprite of.
     * @return the tile's sprite.
     */
    public static <T> Sprite getSprite(final T t) {
        if (t instanceof TileType) {
            return SPRITE_TILES_MAP.get(t);
        } else if (t instanceof PowerUpType) {
            return SPRITE_POWER_UP.get(t);
        }
        throw new IllegalArgumentException("The specified class object does not represent an enum type");
    }
}
