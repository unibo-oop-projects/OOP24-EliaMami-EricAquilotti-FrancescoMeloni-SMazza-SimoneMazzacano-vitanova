package it.unibo.view.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;
import it.unibo.model.human.Player;
import it.unibo.model.tile.Tile;

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
    private transient Optional<List<Human>> humansToDraw = Optional.empty();
    private transient Optional<String> textToDraw = Optional.empty();
    private transient Optional<Map> mapToDraw = Optional.empty();
    // Buffered Image for optimized rendering
    private transient BufferedImage bufferedImage;
    private transient Graphics2D bufferGraphics;

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

        initializeBuffer();
    }

    private void initializeBuffer() {
        bufferedImage = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = bufferedImage.createGraphics();
    }

    @Override
    public void renderMap(final Map map) {
        mapToDraw = Optional.of(map);
        repaint();
    }

    @Override
    public void renderHumans(final List<Human> humans) {
        humansToDraw = Optional.of(humans);
        repaint();
    }

    @Override
    public void renderText(final String text) {
        textToDraw = Optional.of(text);
        repaint();
    }

    private void clearBuffer() {
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void redrawBuffer() {
        if (bufferGraphics == null) {
            initializeBuffer();
        }

        clearBuffer();

        mapToDraw.ifPresent(map -> {
            final Tile[][] tiles = map.getTiles();
            for (int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles[r].length; c++) {
                    final Tile tile = tiles[r][c];
                    final int mapX = r * TILE_SIZE;
                    final int mapY = c * TILE_SIZE;
                    final Position screenPosition = screenPosition(new Position(mapX, mapY));
                    if (validScreenPosition(screenPosition)) {
                        final BufferedImage image = tile.getSprite().getImage();
                        bufferGraphics.drawImage(
                            image,
                            (int) screenPosition.x(),
                            (int) screenPosition.y(),
                            TILE_SIZE,
                            TILE_SIZE,
                            null
                        );
                    }
                }
            }
        });

        humansToDraw.ifPresent(humans -> {
            for (final Human human : humans) {
                if (!validScreenPosition(screenPosition(human.getPosition()))) {
                    continue;
                }
                if (human instanceof Player) {
                    drawPlayer(bufferGraphics, (Player) human);
                } else {
                    drawHuman(bufferGraphics, human);
                }
            }
        });

        textToDraw.ifPresent(text -> {
            final Font f = new Font("Verdana", Font.BOLD, 32);
            bufferGraphics.setColor(Color.RED);
            bufferGraphics.setFont(f);
            bufferGraphics.drawString(text, TEXT_SIZE, TEXT_SIZE);
        });
    }

    private Position screenPosition(final Position position) {
        return new Position(position.x() - xOffset + CENTER_X, position.y() - yOffset + CENTER_Y);
    }

    private boolean validScreenPosition(final Position position) {
        return position.x() + TILE_SIZE >= 0
            && position.x() - TILE_SIZE < SCREEN_WIDTH
            && position.y() + TILE_SIZE >= 0
            && position.y() - TILE_SIZE < SCREEN_HEIGHT;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        redrawBuffer();
        final Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bufferedImage, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void setOffset(final int xOffset, final int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    private void drawHuman(final Graphics2D g2, final Human human) {
        final Position screenPosition = screenPosition(human.getPosition());
        g2.drawImage(
            human.getSprite().getImage(),
            (int) screenPosition.x(),
            (int) screenPosition.y(),
            TILE_SIZE,
            TILE_SIZE,
            null
        );
    }

    private void drawPlayer(final Graphics2D g2, final Player player) {
        g2.drawImage(player.getSprite().getImage(), CENTER_X, CENTER_Y, TILE_SIZE, TILE_SIZE, null);
    }
}
