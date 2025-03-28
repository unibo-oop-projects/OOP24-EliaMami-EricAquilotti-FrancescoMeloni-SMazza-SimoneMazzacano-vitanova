package it.unibo.view.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;
import it.unibo.model.tile.Tile;
import it.unibo.view.sprite.HumanType;

/**
 * Class that handles all the rendering on the screen.
 */
public final class ScreenImpl extends JPanel implements Screen {
    private static final long serialVersionUID = 2L;

    private static final int SCALE = 5;
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int BASE_WINDOW_WIDTH = 1920;
    private static final int BASE_WINDOW_HEIGHT = 1080;
    /**
     * The pixel size of a tile scaled.
     */
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private int centerX;
    private int centerY;

    private int xOffset;
    private int yOffset;
    private final JFrame window = new JFrame();

    // Marked as transient because they don't need to be serialized.
    private transient Optional<List<Human>> humansToDraw = Optional.empty();
    private transient Optional<Text> textToDraw = Optional.empty();
    private transient Optional<Map> mapToDraw = Optional.empty();
    // Buffered Image for optimized rendering
    private transient BufferedImage bufferedImage;
    private transient Graphics2D bufferGraphics;

    private record Text(String content, Position position, Color color, int size) {
    }

    /**
     * 
     * @param inputHandler
     */
    public ScreenImpl(final InputHandler inputHandler) {
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        window.setLayout(new BorderLayout());
        window.setSize(BASE_WINDOW_WIDTH, BASE_WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Vita Nova");
        window.setResizable(true);
        window.add(this, BorderLayout.CENTER);
        window.addKeyListener(inputHandler);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        initializeBuffer();
    }

    private void initializeBuffer() {
        bufferedImage = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = bufferedImage.createGraphics();
    }

    @Override
    public void loadMap(final Map map) {
        mapToDraw = Optional.of(map);
    }

    @Override
    public void loadHumans(final List<Human> humans) {
        humansToDraw = Optional.of(humans);
    }

    @Override
    public void loadText(final String text, final Position position, final Color color, final int size) {
        textToDraw = Optional.of(new Text(text, position, color, size));
    }

    private void updateCenter() {
        centerX = window.getWidth() / 2 - TILE_SIZE / 2;
        centerY = window.getHeight() / 2 - TILE_SIZE / 2;
    }

    private void redrawBuffer() {
        // Always initialize the buffer because the size of the window may have changed.
        initializeBuffer();
        updateCenter();
        mapToDraw.ifPresent(map -> {
            final Tile[][] tiles = map.getTiles();
            for (int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles[r].length; c++) {
                    final Tile tile = tiles[r][c];
                    final int mapX = r * TILE_SIZE;
                    final int mapY = c * TILE_SIZE;
                    final Position screenPosition = screenPosition(new Position(mapX, mapY));
                    drawImage(bufferGraphics, tile.getSprite().getImage(), screenPosition);
                }
            }
        });

        humansToDraw.ifPresent(humans -> {
            for (final Human human : humans) {
                final Position screenPosition = (human.getType() == HumanType.PLAYER)
                    ? new Position(centerX, centerY)
                    : screenPosition(human.getPosition());
                drawImage(bufferGraphics, human.getSprite().getImage(), screenPosition);
            }
        });

        textToDraw.ifPresent(text -> {
            final Font f = new Font("Verdana", Font.BOLD, text.size);
            bufferGraphics.setColor(text.color);
            bufferGraphics.setFont(f);
            bufferGraphics.drawString(text.content, (int) text.position.x(), (int) text.position.y());
        });
    }

    private Position screenPosition(final Position position) {
        return new Position(position.x() - xOffset + centerX, position.y() - yOffset + centerY);
    }

    private boolean validScreenPosition(final Position position) {
        return position.x() + TILE_SIZE >= 0
            && position.x() - TILE_SIZE < window.getWidth()
            && position.y() + TILE_SIZE >= 0
            && position.y() - TILE_SIZE < window.getHeight();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        redrawBuffer();
        final Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bufferedImage, 0, 0, null);
    }

    @Override
    public void setOffset(final int xOffset, final int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    private void drawImage(final Graphics2D g2, final BufferedImage image, final Position position) {
        if (validScreenPosition(position)) {
            g2.drawImage(
                image,
                (int) Math.round(position.x()),
                (int) Math.round(position.y()),
                TILE_SIZE,
                TILE_SIZE,
                null
            );
        }
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(this::repaint);
    }
}
