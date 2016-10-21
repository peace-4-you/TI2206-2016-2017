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
public class NamesScreen extends BasicGameState {
    /**
     * All the buttons.
     */
    private ArrayList<Button> buttons;
    /**
     * Name Textfield player 1.
     */
    private TextField player1name;
    /**
     * Name Textfield player 2.
     */
    private TextField player2name;
    /**
     * Name Text player1.
     */
    private Text player1text;
    /**
     * Name Text player2.
     */
    private Text player2text;

    /**
     * Getter method for Id.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.NAMES_SCREEN;
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
        player1text = new Text("Player 1: ", GameConfig.FIRST_LINE);
        player1text.centerText(game);
        player2text = new Text("Player 2: ", GameConfig.FOURTH_LINE);
        player2text.centerText(game);
        player1name = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.SECOND_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        player1name.setText("Player1");
        player2name = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.FIFTH_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        player2name.setText("Player2");
        Button play = new Button("Play", GameConfig.SEVENTH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
        play.centerButton(game);
        Button main = new Button("Main Menu", GameConfig.EIGTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        main.centerButton(game);
        main.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
        play.addAction(new Runnable() {
            public void run() {
                try {
                    App.getGame().start2Player();
                } catch (SlickException e) {
                    e.printStackTrace();
                }
                App.getGame().getGameData().get(0).getPlayer()
                .setName(player1name.getText());
                App.getGame().getGameData().get(1).getPlayer()
                .setName(player2name.getText());
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
        player1text.draw(graphics);
        player2text.draw(graphics);
        player1name.render(game, graphics);
        player2name.render(game, graphics);
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
