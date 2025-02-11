package it.unibo.model.chapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of a game map.
 */
public final class MapImpl implements Map {
    private static final String ROOT_MAP = "it/unibo/view/maps/";
    private final int[][] tileIds;

    /**
     * Initialize tileIds and loads the map from a file.
     */
    public MapImpl() {
        this.tileIds = new int[ScreenImpl.SCREEN_ROW][ScreenImpl.SCREEN_COL];
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
