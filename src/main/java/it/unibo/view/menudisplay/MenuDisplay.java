package it.unibo.view.menudisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import it.unibo.view.menu.MenuContent;

/**
 * Handles menu rendering for the view.
 */
public final class MenuDisplay extends JPanel {
    private static final long serialVersionUID = 5L;

    private static final int DEFAULT_FONT_SIZE = 60;
    private static final Font FONT = new Font("Verdana", Font.BOLD, DEFAULT_FONT_SIZE);
    private static final float LINE_SPACING = 0.4f;
    private final JTextPane textPane = new JTextPane();
    private transient Optional<Dimension> initialSize = Optional.empty();
    private float currentTextSize = DEFAULT_FONT_SIZE;

    /**
     * Constructor for the menu display.
     */
    public MenuDisplay() {
        this.setLayout(new SpringLayout());
        this.setOpaque(false);
        initializeComponents();
        this.add(textPane);
        addLayoutConstraints();
    }

    private void initializeComponents() {
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.setFont(FONT);
        textPane.setForeground(Color.WHITE);
        textPane.setOpaque(false);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                if (initialSize.isEmpty()) {
                    initialSize = Optional.of(MenuDisplay.this.getSize());
                }
                adjustFontSize();
            }
        });

        final SimpleAttributeSet centerAttr = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttr, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(centerAttr, LINE_SPACING);
        textPane.setParagraphAttributes(centerAttr, false);
    }

    private void setFontSize(final float size) {
        final Font base = textPane.getFont();
        textPane.setFont(base.deriveFont(size));
    }

    private void adjustFontSize() {
        final float resizedByHeight = currentTextSize * this.getHeight() / this.initialSize.get().height;
        final float resizedByWidth = currentTextSize * this.getWidth() / this.initialSize.get().width;
        setFontSize(Math.min(resizedByHeight, resizedByWidth));
    }

    private void addLayoutConstraints() {
        final SpringLayout layout = (SpringLayout) this.getLayout();
        layout.putConstraint(
            SpringLayout.HORIZONTAL_CENTER,
            textPane,
            0,
            SpringLayout.HORIZONTAL_CENTER,
            this
        );
        layout.putConstraint(
            SpringLayout.VERTICAL_CENTER,
            textPane,
            0,
            SpringLayout.VERTICAL_CENTER,
            this
        );
    }

    /**
     * Sets the menu content to be displayed.
     * @param menu the menu content to be displayed
     */
    public void update(final MenuContent menu) {
        final String toDraw = Stream.concat(
            Stream.of(menu.title(), menu.subtitle()).filter(s -> !s.isEmpty()),
            menu.options().stream()
        ).collect(Collectors.joining("\n"));

        currentTextSize = menu.textSize().map(Integer::floatValue).orElse((float) DEFAULT_FONT_SIZE);
        SwingUtilities.invokeLater(() -> {
            adjustFontSize();
            textPane.setText(toDraw);
        });
    }
}
