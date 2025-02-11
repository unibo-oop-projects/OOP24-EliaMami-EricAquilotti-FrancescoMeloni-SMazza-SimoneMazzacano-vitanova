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
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private static final int TEXT_SIZE = 50;
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
            int x = 0;
            int y = 0;
            for (final int[] row : map.getTileIds()) {
                for (final int num : row) {
                    final BufferedImage image = TileManager.getTile(num).getSprite().getImage();
                    g2.drawImage(image, x, y, TILE_SIZE, TILE_SIZE, null);
                    x += TILE_SIZE;
                    if (x == SCREEN_WIDTH) {
                        x = 0;
                        y += TILE_SIZE;
                    }
                }
            }
        }
        if (humanToDraw.isPresent()) {
            final Human human = humanToDraw.get();
            final int x = human.getPosition().x();
            final int y = human.getPosition().y();
            g2.drawImage(human.getSprite().getImage(), x, y, TILE_SIZE, TILE_SIZE, null);
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setOffset'");
    }
}
