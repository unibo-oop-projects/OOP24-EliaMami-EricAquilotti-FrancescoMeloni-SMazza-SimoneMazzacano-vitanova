package it.unibo.view.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.Map;
import it.unibo.model.human.Human;
import it.unibo.model.tile.TileManager;

/**
 * Class that handles all the rendering on the screen.
 */
public final class ScreenImpl extends JPanel implements Screen {
    private static final long serialVersionUID = 2L;

    private static final int SCALE = 5;
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int TEXT_SIZE = 50;
    /**
     * The pixel size of a tile scaled.
     */
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    /**
     * Number of tiles in a row.
     */
    public static final int SCREEN_COL = 16;
    /**
     * Number of tiles in a column.
     */
    public static final int SCREEN_ROW = 12;
    /**
     * Width of the screen obtained by the size of a tile and the amount of
     * tiles in a row.
     */
    public static final int SCREEN_WIDTH = TILE_SIZE * SCREEN_COL;
    /**
     * Height of the screen obtained by the size of a tile and the amount of
     * tiles in a column.
     */
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROW;
    private static final int CENTER_X = SCREEN_WIDTH / 2 - TILE_SIZE / 2;
    private static final int CENTER_Y = SCREEN_HEIGHT / 2 - TILE_SIZE / 2;

    private int xOffset;
    private int yOffset;

    // Marked as transient because they don't need to be serialized.
    private transient Optional<Human> humanToDraw = Optional.empty();
    private transient Optional<String> textToDraw = Optional.empty();
    private transient Optional<Map> mapToDraw = Optional.empty();

    /**
     * 
     * @param inputHandler
     */
    public ScreenImpl(final InputHandler inputHandler) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        final JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Vita Nova");
        window.add(this);
        window.addKeyListener(inputHandler);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    @Override
    public void renderMap(final Map map) {
        mapToDraw = Optional.of(map);
        repaint();
    }

    @Override
    public void renderHuman(final Human human) {
        humanToDraw = Optional.of(human);
        repaint();
    }

    @Override
    public void renderText(final String text) {
        textToDraw = Optional.of(text);
        repaint();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final Graphics2D g2 = (Graphics2D) g;

        if (mapToDraw.isPresent()) {
            final Map map = mapToDraw.get();
            final int[][] tileIds = map.getTileIds();
            for (int r = 0; r < map.getTileIds().length; r++) {
                final int[] row = tileIds[r];
                for (int c = 0; c < row.length; c++) {
                    final int num = row[c];
                    final int mapX = r * TILE_SIZE;
                    final int mapY = c * TILE_SIZE;
                    final int screenX = mapX - xOffset + CENTER_X;
                    final int screenY = mapY - yOffset + CENTER_Y;

                    // Draw only what's visible.
                    if (screenX + TILE_SIZE >= 0
                        && screenX - TILE_SIZE < SCREEN_WIDTH
                        && screenY + TILE_SIZE >= 0
                        && screenY - TILE_SIZE < SCREEN_HEIGHT) {

                        final BufferedImage image = TileManager.getTile(num).getSprite().getImage();
                        g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
                    }
                }
            }
        }
        if (humanToDraw.isPresent()) {
            final Human human = humanToDraw.get();
            g2.drawImage(human.getSprite().getImage(), CENTER_X, CENTER_Y, TILE_SIZE, TILE_SIZE, null);
        }
        if (textToDraw.isPresent()) {
            final String text = textToDraw.get();
            final Font f = new Font("Verdana", 1, 32);
            g2.setColor(Color.RED);
            g2.setFont(f);
            g2.drawString(text, TEXT_SIZE, TEXT_SIZE);
        }

        g2.dispose();
    }

    @Override
    public void setOffset(final int xOffset, final int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
