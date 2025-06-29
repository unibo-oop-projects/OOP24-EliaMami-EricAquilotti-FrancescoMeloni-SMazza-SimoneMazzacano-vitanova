package it.unibo.view.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.PopulationCounter;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
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
    private static final int BASE_WINDOW_WIDTH = (int) SCREEN_SIZE.getWidth();
    private static final int BASE_WINDOW_HEIGHT = (int) SCREEN_SIZE.getHeight();
    private static final double SCALE_WIDTH_FACTOR = 1920.0;
    private static final double SCALE_HEIGHT_FACTOR = 1080.0;
    private static final int BASE_SCALE = 5;

    // Marked as transient because they don't need to be serialized.
    private transient double centerX;
    private transient double centerY;
    private transient double xOffset;
    private transient double yOffset;
    private final transient JFrame window = new JFrame();
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
     * Creates a new Screen with the needed parameters.
     * 
     * @param inputHandler the input handler controlled by the user key pressed.
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
        // this.setOffset(centerX, centerY);
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
    public void loadPickable(final List<Pickable> pickables) {
        pickableToDraw = pickables.stream().toList();
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

    private int getScale() {
        return (int) Math.max(1.0, Math.round(BASE_SCALE * Math.min(
            window.getHeight() / SCALE_HEIGHT_FACTOR,
            window.getWidth() / SCALE_WIDTH_FACTOR
        )));
    }

    private void updateCenter() {
        final int scale = getScale();
        centerX = window.getWidth() / 2 - MapImpl.TILE_SIZE * scale / 2;
        centerY = window.getHeight() / 2 - MapImpl.TILE_SIZE * scale / 2;
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
                    final double mapX = r * MapImpl.TILE_SIZE;
                    final double mapY = c * MapImpl.TILE_SIZE;
                    final Position screenPosition = screenPosition(new Position(mapX, mapY));
                    drawImage(bufferGraphics, tile.getSprite().getImage(), screenPosition);
                }
            }
        });

        for (final Pickable pickable : pickableToDraw) {
            drawImage(bufferGraphics, pickable.getSprite().getImage(), screenPosition(pickable.getPosition()));
        }

        Optional<Human> player = Optional.empty();
        for (final Human human : humansToDraw) {
            if (human.getType() == HumanType.PLAYER) {
                player = Optional.of(human);
                continue;
            }
            final Position screenPosition = screenPosition(human.getPosition());
            drawImage(bufferGraphics, human.getSprite().getImage(), screenPosition);
        }
        // Draw the human last
        player.ifPresent(p -> drawImage(bufferGraphics, p.getSprite().getImage(), new Position(centerX, centerY)));
    }

    private Position screenPosition(final Position position) {
        final int scale = getScale();
        return new Position(position.x() * scale - xOffset + centerX, position.y() * scale - yOffset + centerY);
    }

    private boolean validScreenPosition(final Position position) {
        final int tileSize = MapImpl.TILE_SIZE * getScale();
        return position.x() + tileSize >= 0
            && position.x() - tileSize < window.getWidth()
            && position.y() + tileSize >= 0
            && position.y() - tileSize < window.getHeight();
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
        final int scale = getScale();
        this.xOffset = xOffset * scale;
        this.yOffset = yOffset * scale;
    }

    private void drawImage(final Graphics2D g2, final BufferedImage image, final Position position) {
        if (validScreenPosition(position)) {
            final int scale = getScale();
            g2.drawImage(
                image,
                (int) Math.round(position.x()),
                (int) Math.round(position.y()),
                MapImpl.TILE_SIZE * scale,
                MapImpl.TILE_SIZE * scale,
                null
            );
        }
    }
}
