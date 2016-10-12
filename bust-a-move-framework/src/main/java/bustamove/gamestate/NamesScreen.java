/*
 * File: MainMenu.java
 * Class: MainMenu
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */

package bustamove.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import bustamove.App;

/**
 * Generates a MainMenu as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class NamesScreen extends BasicGameState {
    /**
     * Play Button.
     */
    private Button play;
    /**
     * Mainmenu Button.
     */
    private Button mainmenu;
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
        player1text = new Text("Player 1: ", GameConfig.FIRST_LINE);
        player1text.centerText(game);
        player2text = new Text("Player 2: ", GameConfig.FOURTH_LINE);
        player2text.centerText(game);
        play = new Button("Play", GameConfig.SEVENTH_LINE, GameConfig.WIDTH1,
                GameConfig.HEIGHT);
        play.centerButton(game);
        player1name = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.SECOND_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        player1name.setText("Player1");
        player2name = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.FIFTH_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        player2name.setText("Player2");
        mainmenu = new Button("Main Menu", GameConfig.EIGTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        mainmenu.centerButton(game);

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
        play.draw(graphics);
        player1name.render(game, graphics);
        player2name.render(game, graphics);
        mainmenu.draw(graphics);
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
            if (play.isInBounds(input)) {
                App.getGame().start2Player();
                App.getGame().getGameData().get(0).getPlayer()
                        .setName(this.player1name.getText());
                App.getGame().getGameData().get(1).getPlayer()
                        .setName(this.player2name.getText());
                stateBasedGame.enterState(GameState.GAME_ACTIVE,
                        new FadeOutTransition(), new FadeInTransition());
            }
            if (mainmenu.isInBounds(input)) {
                stateBasedGame.getState(GameState.GAME_ACTIVE).init(game,
                        stateBasedGame);
                stateBasedGame.enterState(GameState.MAIN_MENU,
                        new FadeOutTransition(), new FadeInTransition());
            }
        }
    }
}
