package it.unibo.model.chapter.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Implementation of a game map.
 */
public final class MapImpl implements Map {
    /**
     * Number of tiles in a map row.
     */
    public static final int MAP_COL = 32;
    /**
     * Number of tiles in a map column.
     */
    public static final int MAP_ROW = 32;
    private static final String ROOT_MAP = "it/unibo/view/maps/";
    private final int[][] tileIds;

    /**
     * Initialize tileIds and loads the map from a file.
     */
    public MapImpl() {
        this.tileIds = new int[MAP_ROW][MAP_COL];
        loadMap("test.txt");
    }

    private void loadMap(final String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(ROOT_MAP + path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            int row = 0;
            while (line != null) {
                final int[] nums = Arrays.stream(line.split(" "))
                .mapToInt(Integer::valueOf).toArray();
                tileIds[row] = Arrays.copyOf(nums, nums.length);
                line = br.readLine();
                row++; 
            }

        } catch (IOException e) {
            throw new IllegalStateException("Could not read the file", e);
        }
    }

    @Override
    public int[][] getTileIds() {
        return Arrays.stream(tileIds).map(int[]::clone).toArray(int[][]::new);
    }
}
