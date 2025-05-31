package it.unibo.view.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JFrame;
import javax.swing.JPanel;
import it.unibo.common.Position;
import it.unibo.common.Text;
import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.PopulationCounter;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;
import it.unibo.model.pickable.Pickable;
import it.unibo.model.tile.Tile;
import it.unibo.view.menu.MenuContent;
import it.unibo.view.menudisplay.MenuDisplay;
import it.unibo.view.population.PopulationCounterDisplay;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.timerdisplay.TimerDisplay;

/**
 * Class that handles all the rendering on the screen.
 */
public final class ScreenImpl extends JPanel implements Screen {
    private static final long serialVersionUID = 1287309L;

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCALE = 5;
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int BASE_WINDOW_WIDTH = (int) SCREEN_SIZE.getWidth();
    private static final int BASE_WINDOW_HEIGHT = (int) SCREEN_SIZE.getHeight();
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
    private final transient List<Text> textToDraw = new ArrayList<>();
    private transient List<Human> humansToDraw = new ArrayList<>();
    private transient List<Pickable> pickableToDraw = new ArrayList<>();
    private transient Optional<Map> mapToDraw = Optional.empty();
    private transient MenuContent menuContent = MenuContent.empty();
    private transient Optional<Duration> timerValue = Optional.empty();
    private transient Optional<PopulationCounter> populationCounter = Optional.empty();
    // Buffered Image for optimized rendering
    private transient BufferedImage bufferedImage;
    private transient Graphics2D bufferGraphics;
    private final transient TimerDisplay timerLabel = new TimerDisplay();
    private final transient PopulationCounterDisplay populationCounterLabel = new PopulationCounterDisplay();
    private final transient TopPanel topPanelContainer = new TopPanel(timerLabel, populationCounterLabel);
    private final transient MenuDisplay menuDisplay = new MenuDisplay();

    /**
     * 
     * @param inputHandler
     */
    public ScreenImpl(final InputHandler inputHandler) {
        final var color = new Color(4, 160, 180); 
        this.setBackground(color);
        this.setDoubleBuffered(true);
        this.setLayout(new GridBagLayout());
        addInnerComponents();

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
        new javax.swing.Timer(16, e -> repaint()).start(); // ~60 FPS
    }

    private void addInnerComponents() {
        final GridBagConstraints gbc = new GridBagConstraints();
        final float topPanelHeightPercentage = 0.1f; // 10% of the height
        final float menuDisplayHeightPercentage = 0.9f; // 90% of the height
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = topPanelHeightPercentage;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(topPanelContainer, gbc);

        gbc.gridy = 1;
        gbc.weighty = menuDisplayHeightPercentage;
        this.add(menuDisplay, gbc);
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
        pickableToDraw = new ArrayList<>();
    }

    @Override
    public void loadMap(final Map map) {
        mapToDraw = Optional.of(map);
    }

    @Override
    public void loadHumans(final List<Human> humans) {
        humansToDraw = humans.stream().toList();
    }

    @Override
    public void loadPickablePowerUp(final List<Pickable> pickablePowerUps) {
        pickableToDraw = pickablePowerUps.stream().toList();
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
    public void loadMenu(final MenuContent content) {
        if (menuContent.equals(content)) {
            return;
        }
        menuContent = content;
        menuDisplay.update(content);
    }

    @Override
    public void loadTimer(final Optional<Duration> timerValue) {
        if (timerValue.equals(this.timerValue)) {
            return;
        }
        this.timerValue = timerValue;
        timerValue.ifPresentOrElse(timerLabel::update, timerLabel::clear);
    }

    @Override
    public void loadPopulationCounter(final Optional<PopulationCounter> populationCounter) {
        if (populationCounter.equals(this.populationCounter)) {
            return;
        }
        this.populationCounter = populationCounter;
        populationCounter.ifPresentOrElse(populationCounterLabel::update, populationCounterLabel::clear);
    }

    private void updateCenter() {
        centerX = window.getWidth() / 2 - TILE_SIZE / 2;
        centerY = window.getHeight() / 2 - TILE_SIZE / 2;
    }

    private void drawText(final List<Text> texts) {
        final List<Text> copyTexts = texts.stream().toList(); // to avoid concurrent modifications on iterated list
        copyTexts.forEach(text -> {
            final Font f = new Font("Verdana", Font.BOLD, text.size());
            bufferGraphics.setColor(text.color());
            bufferGraphics.setFont(f);
            bufferGraphics.drawString(text.content(), (int) text.position().x(), 
            (int) text.position().y());
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

        for (final Pickable pickable : pickableToDraw) {
            drawImage(bufferGraphics, pickable.getSprite().getImage(), screenPosition(pickable.getPosition()));
        }

        for (final Human human : humansToDraw) {
                final Position screenPosition = (human.getType() == HumanType.PLAYER)
                    ? new Position(centerX, centerY)
                    : screenPosition(human.getPosition());
                drawImage(bufferGraphics, human.getSprite().getImage(), screenPosition);

                bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                final Position screenPositionCircle = screenPosition(human.getStats().getReproductionAreaRadius().getCenter());
                final double diam = 2.0 * human.getStats().getReproductionAreaRadius().getRadius();
                final Shape circle = new Ellipse2D.Double(
                    screenPositionCircle.x() - diam / 2,
                    screenPositionCircle.y() - diam / 2, diam,
                    diam
                );
                if (human.getStats().isSick()) {
                    bufferGraphics.setColor(Color.GREEN);
                } else {
                    bufferGraphics.setColor(Color.RED);
                }
                bufferGraphics.draw(circle);
        }

        drawText(textToDraw);
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
