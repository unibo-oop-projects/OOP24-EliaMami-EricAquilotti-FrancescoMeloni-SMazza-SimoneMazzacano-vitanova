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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import it.unibo.common.Position;
import it.unibo.common.Text;
import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.PopulationCounter;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;
import it.unibo.model.tile.Tile;
import it.unibo.view.population.PopulationCounterDisplay;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.timerdisplay.TimerDisplay;

/**
 * Class that handles all the rendering on the screen.
 */
public final class ScreenImpl extends JPanel implements Screen {
    private static final long serialVersionUID = 2L;

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCALE = 5;
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int TEXT_VERTICAL_SPACING = 25;
    private static final int TEXT_LATERAL_BORDER = 200;
    private static final int TOP_MARGIN = 100;
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
    private int textVerticalOffset;
    private final JFrame window = new JFrame();
    private final transient ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private enum TextAlignment {
        NONE,
        CENTER,
        RIGHT
    }

    // Marked as transient because they don't need to be serialized.
    private final transient List<Text> textToDraw = new ArrayList<>();
    private transient List<Human> humansToDraw = new ArrayList<>();
    private transient Optional<Map> mapToDraw = Optional.empty();
    private transient List<Text> menuText = new ArrayList<>();
    private transient Optional<Duration> timerValue = Optional.empty();
    private transient Optional<PopulationCounter> populationCounter = Optional.empty();
    // Buffered Image for optimized rendering
    private transient BufferedImage bufferedImage;
    private transient Graphics2D bufferGraphics;
    private final transient TimerDisplay timerLabel = new TimerDisplay();
    private final transient JPanel topPanel = new JPanel(new SpringLayout());

    /**
     * 
     * @param inputHandler
     */
    public ScreenImpl(final InputHandler inputHandler) {
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setLayout(new BorderLayout());
        initializeInnerComponents();

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

    // This is necessary to reinitialize the transient lists after deserialization
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        humansToDraw = new ArrayList<>();
        menuText = new ArrayList<>();
    }

    private void initializeInnerComponents() {
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(TOP_MARGIN, 0, 0, 0));
        topPanel.add(timerLabel);

        final SpringLayout layout = (SpringLayout) topPanel.getLayout();
        layout.putConstraint(
            SpringLayout.HORIZONTAL_CENTER,
            timerLabel,
            0,
            SpringLayout.HORIZONTAL_CENTER,
            topPanel
        );
        this.add(topPanel);
    }

    @Override
    public void loadMap(final Map map) {
        mapToDraw = Optional.of(map);
    }

    @Override
    public void loadHumans(final List<Human> humans) {
        humansToDraw = humans.stream().toList();
    }

    private void removeTextByPosition(final Text text) {
        textToDraw.removeIf(toDrawTxt -> toDrawTxt.position().equals(text.position()));
    }

    @Override
    public void loadText(final String text, final Position position, final Color color, final int size) {
        final Text txt = new Text(text, position, color, size);
        removeTextByPosition(txt);
        textToDraw.add(txt);
    }

    @Override
    public void loadMenu(final List<Text> texts) {
        menuText = texts.stream().toList();
    }

    @Override
    public void loadTimer(final Optional<Duration> timerValue) {
        this.timerValue = timerValue;
    }

    @Override
    public void loadPopulationCounter(final Optional<PopulationCounter> populationCounter) {
        this.populationCounter = populationCounter;
    }

    /**
     * 
     * @return the y offset that the justified texts are applied to.
     *  It's fixed to 1/6 of the screen height.
     */
    private int computeTextUpperBorder() {
        return this.centerY / 3;
    }

    private void updateCenter() {
        centerX = window.getWidth() / 2 - TILE_SIZE / 2;
        centerY = window.getHeight() / 2 - TILE_SIZE / 2;
    }

    private int calculateTextWidth(final Font font, final Text text, final Graphics2D g) {
        final FontMetrics fontMetrics = g.getFontMetrics(font);
        return fontMetrics.stringWidth(text.content());
    }

    private int adjustedXPosition(final int xPosition, final int textWidth, final TextAlignment alignment) {
        if (alignment == TextAlignment.CENTER) {
            return Math.max(this.centerX - (textWidth / 2), 0);
        } else if (alignment == TextAlignment.RIGHT) {
            return Math.max(this.window.getWidth() - textWidth - TEXT_LATERAL_BORDER, 0);
        }
        return xPosition;
    }

    private void drawLine(final Font f, final Text lineText, final TextAlignment alignment) {
        final int textWidth = calculateTextWidth(f, lineText, bufferGraphics);
        final int adjustedXPosition = adjustedXPosition((int) lineText.position().x(), textWidth, alignment);

        bufferGraphics.drawString(lineText.content(), adjustedXPosition, 
        (int) lineText.position().y() + (alignment != TextAlignment.NONE ? computeTextUpperBorder() : 0) + textVerticalOffset);
    }

    private void drawText(final List<Text> texts, final TextAlignment alignment) {
        final List<Text> copyTexts = texts.stream().toList(); // to avoid concurrent modifications on iterated list
        copyTexts.forEach(text -> {
            final Font f = new Font("Verdana", Font.BOLD, text.size());
            bufferGraphics.setColor(text.color());
            bufferGraphics.setFont(f);

            final String[] lines = text.content().split("\\R");
            for (final String line : lines) {
                final Text lineText = new Text(line, text.position(), text.color(), text.size());
                drawLine(f, lineText, alignment);

                if (lines.length > 1) {
                    incrementVerticalOffset();
                }
            }
        });
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

        for (final Human human : humansToDraw) {
                final Position screenPosition = (human.getType() == HumanType.PLAYER)
                    ? new Position(centerX, centerY)
                    : screenPosition(human.getPosition());
                drawImage(bufferGraphics, human.getSprite().getImage(), screenPosition);
            }

        resetVerticalOffset();
        timerValue.ifPresent(timerLabel::update);
        populationCounter.map(PopulationCounterDisplay::text)
              .ifPresent(text -> drawText(List.of(text), TextAlignment.RIGHT));
        drawText(textToDraw, TextAlignment.NONE);
        drawText(menuText, TextAlignment.CENTER);
    }

    private void resetVerticalOffset() {
        this.textVerticalOffset = 0;
    }

    private void incrementVerticalOffset() {
        this.textVerticalOffset += TEXT_VERTICAL_SPACING;
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
