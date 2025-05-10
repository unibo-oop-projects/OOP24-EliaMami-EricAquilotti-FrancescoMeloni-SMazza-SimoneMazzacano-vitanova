package it.unibo.view.menudisplay;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import it.unibo.common.Text;

public final class MenuDisplay extends JPanel {
    private final JTextPane textPane = new JTextPane();
    final Font font = new Font("Verdana", Font.BOLD, 60);
    public MenuDisplay() {
        this.setLayout(new SpringLayout());
        this.setOpaque(false);
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.setFont(font);
        textPane.setForeground(Color.WHITE);
        textPane.setOpaque(false);
        SimpleAttributeSet centerAttr = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttr, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(centerAttr, 0.4f);
        textPane.setParagraphAttributes(centerAttr, false);

        this.add(textPane);
        addCostraints();
    }

    private void addCostraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
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

    public void update(final List<Text> texts) {
        final StringBuilder allTxt = new StringBuilder();
        texts.forEach(text -> {
            allTxt.append(text.content() + "\n");
        });
        textPane.setText(allTxt.toString());
    }
}
