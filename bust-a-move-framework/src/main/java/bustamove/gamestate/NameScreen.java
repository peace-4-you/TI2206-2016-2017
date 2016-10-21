/*
 * File: MainMenu.java
 * Class: MainMenu
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */

package bustamove.gamestate;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;

/**
 * Generates a MainMenu as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class NameScreen extends BasicGameState {
    /**
     * All the buttons.
     */
    private ArrayList<Button> buttons;
    /**
     * Name Textfield.
     */
    private TextField namefield;
    /**
     * Name Text.
     */
    private Text nameText;

    /**
     * Getter method for GameState id.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.NAME_SCREEN;
    }

    /**
     * Called when BasicGameState initializes.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
        buttons = new ArrayList<Button>();
        nameText = new Text("Player: ", GameConfig.FIRST_LINE);
        nameText.centerText(game);
        namefield = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.THIRD_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        namefield.setText("Player1");
        Button play = new Button("Play", GameConfig.FIFTH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
        play.centerButton(game);
        Button main = new Button("Main Menu", GameConfig.SIXT_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        main.centerButton(game);
        main.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
        play.addAction(new Runnable() {
            public void run() {
                App.getGame().start1Player();
                App.getGame().getGameData().get(0).getPlayer()
                .setName(namefield.getText());
            }
        });
        play.addGameStateChangeAction(stateBasedGame,
                GameState.GAME_ACTIVE);
        buttons.add(play);
        buttons.add(main);
    }

    /**
     * Renders the BasicGameState.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @param graphics Graphics object
     * @throws SlickException any type of slick exception
     */
    public final void render(final GameContainer game,
            final StateBasedGame stateBasedGame, final Graphics graphics)
            throws SlickException {
        nameText.draw(graphics);
        namefield.render(game, graphics);
        for (Button b : buttons) {
            b.draw(graphics);
        }
    }

    /**
     * Updates the BasicGameState.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @param i delta of time exceeded
     * @throws SlickException any type of slick exception
     */
    public final void update(final GameContainer game,
            final StateBasedGame stateBasedGame, final int i)
            throws SlickException {
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            for (Button b : buttons) {
                if (b.isInBounds(input)) {
                    b.click();
                }
            }
        }
    }
}
