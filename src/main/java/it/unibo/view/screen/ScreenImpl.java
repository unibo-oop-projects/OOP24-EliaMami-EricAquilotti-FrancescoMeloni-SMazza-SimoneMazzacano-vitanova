package it.unibo.view.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.common.Position;
import it.unibo.common.Text;
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

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCALE = 5;
    private static final int ORIGINAL_TILE_SIZE = 16;
    /**
     * Base window width, screen width.
     */
    public static final int BASE_WINDOW_WIDTH = (int) SCREEN_SIZE.getWidth();
    /**
     * Base window height, screen height.
     */
    public static final int BASE_WINDOW_HEIGHT = (int) SCREEN_SIZE.getHeight();
    /**
     * The pixel size of a tile scaled.
     */
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private int centerX;
    private int centerY;

    private int xOffset;
    private int yOffset;
    private final JFrame window = new JFrame();
    private final transient ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    // Marked as transient because they don't need to be serialized.
    private transient Optional<List<Human>> humansToDraw = Optional.empty();
    private transient Optional<List<Text>> textToDraw = Optional.empty();
    private transient Optional<Map> mapToDraw = Optional.empty();
    private transient Optional<List<Text>> menuText = Optional.empty();
    // Buffered Image for optimized rendering
    private transient BufferedImage bufferedImage;
    private transient Graphics2D bufferGraphics;


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
        updateCenter();
        this.setOffset(centerX, centerY);
        initializeBuffer();
        executor.scheduleAtFixedRate(this::repaint, 0, 16, TimeUnit.MILLISECONDS); // ~60 FPS
    }

    private void initializeBuffer() {
        final int width = window.getWidth();
        final int height = window.getHeight();
        if (bufferedImage == null || bufferedImage.getWidth() != width || bufferedImage.getHeight() != height) {
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            bufferGraphics = bufferedImage.createGraphics();
        }
    }

    @Override
    public void loadMap(final Map map) {
        mapToDraw = Optional.of(map);
    }

    @Override
    public void loadHumans(final List<Human> humans) {
        humansToDraw = Optional.of(humans);
    }

    private void removeTextByPosition(final Text text) {
        textToDraw.ifPresent(list -> list.removeIf(toDrawTxt -> toDrawTxt.position().equals(text.position())));
    }

    @Override
    public void loadText(final String text, final Position position, final Color color, final int size) {
        final Text txt = new Text(text, position, color, size);
        removeTextByPosition(txt);
        textToDraw.ifPresentOrElse(list -> list.add(txt), () -> {
             textToDraw = Optional.of(new ArrayList<>(List.of(txt)));
        });
    }

    @Override
    public void loadMenu(final List<Text> texts) {
        menuText = Optional.of(texts);
    }

    private void updateCenter() {
        centerX = window.getWidth() / 2 - TILE_SIZE / 2;
        centerY = window.getHeight() / 2 - TILE_SIZE / 2;
    }

    private void drawText(final Optional<List<Text>> texts, final boolean isJustifiedCenter) {
        final Optional<List<Text>> copyTexts = texts.map(ArrayList::new); // to avoid concurrent modifications on iterated list
        copyTexts.ifPresent(list -> list.forEach(text -> {
            final Font f = new Font("Verdana", Font.BOLD, text.size());
            bufferGraphics.setColor(text.color());
            bufferGraphics.setFont(f);

            final int textWidth = calculateTextWidth(f, text, bufferGraphics);
            final int justifiedXPosition = this.centerX - (textWidth / 2);

            bufferGraphics.drawString(text.content(), isJustifiedCenter ? justifiedXPosition : (int) text.position().x(), 
            (int) text.position().y() + (isJustifiedCenter ? this.centerY : 0));
        }));
    }

    private int calculateTextWidth(final Font font, final Text text, final Graphics2D g) {
        final FontMetrics fontMetrics = g.getFontMetrics(font);
        return fontMetrics.stringWidth(text.content());
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

        drawText(textToDraw, false);
        drawText(menuText, true);
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
}
