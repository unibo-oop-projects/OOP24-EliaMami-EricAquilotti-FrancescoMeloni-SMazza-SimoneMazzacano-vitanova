package it.unibo.view.menu;

import it.unibo.common.Position;
import it.unibo.common.Text;
import it.unibo.controller.Game;
import it.unibo.controller.InputHandler;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that handles the menu options, text and input.
 */
public abstract class AbstractMenu implements Menu {
    private static final int TIMER_VALUE = 10;
    private static final int SPACING = 100;
    private static final int TEXT_SIZE = 60;
    enum OptionType {
        PLAY("Play"), 
        QUIT("Quit");

        private final String description;
        OptionType(final String description) {
            this.description = description;
        }
        public String getDescription() {
            return description;
        }
        public OptionType next() {
            return values()[(this.ordinal() + 1) % numberOfOptions()];
        }
        public static int numberOfOptions() {
            return values().length;
        }
    }
    private final Position[] positions = {new Position(0, 0), new Position(0, SPACING)};
    private OptionType selectedOption = OptionType.PLAY;
    private boolean isHidden = true;
    private int timer = TIMER_VALUE;
    private final List<Text> textToShow = new ArrayList<>();
    protected final InputHandler input;
    protected final Game game;

    /**
     * Constructor for the MenuImpl class.
     * @param input the input handler
     * @param game the game controller
     */
    public AbstractMenu(final InputHandler input, final Game game) {
        this.input = input;
        this.game = game;
    }

    public AbstractMenu(final InputHandler input, final Game game, boolean isHidden) {
        this(input, game);
        this.isHidden = isHidden;
    }


    @Override
    public void update() {
        if (timer > 0) {
            timer--;
        } else {
            if (input.isKeyPressed(KeyEvent.VK_E)) {
                toggleMenu();
                timer = TIMER_VALUE;
            } else if (!this.isHidden && input.isKeyPressed(KeyEvent.VK_DOWN) && selectedOption == OptionType.PLAY) {
                this.selectedOption = this.selectedOption.next();
                timer = TIMER_VALUE;
            } else if (!this.isHidden && input.isKeyPressed(KeyEvent.VK_UP) && selectedOption == OptionType.QUIT) {
                this.selectedOption = this.selectedOption.next();
                timer = TIMER_VALUE;
            } else if (!this.isHidden && input.isKeyPressed(KeyEvent.VK_ENTER)) {
                if (selectedOption == OptionType.QUIT) {
                    game.exit();
                } else if (selectedOption == OptionType.PLAY) {
                    play();
                    toggleMenu();
                }
            }
        }
    }
    private String applySelectedFormat(final String text) {
        return "> " + text + " <";
    }
    private void clearTextAtPosition(final Position p) {
        textToShow.removeIf(txt -> txt.position().equals(p));
    }
    private void addText(final Text text) {
        clearTextAtPosition(text.position());
        textToShow.add(text);
    }
    private void toggleMenu() {
        isHidden = !isHidden;
        if (isHidden) {
            for (final Position pos : positions) {
                clearTextAtPosition(pos);
            }
        }
    }

    protected abstract void play();

    @Override
    public List<Text> getText() {
        if (!isHidden) {
            for (final OptionType option : OptionType.values()) {
                addText(new Text(selectedOption == option 
                ? applySelectedFormat(option.getDescription()) : option.getDescription(), 
                positions[option.ordinal()], Color.WHITE, TEXT_SIZE));
            }
            return new ArrayList<>(textToShow);
        }
        return List.of();
    }
}
