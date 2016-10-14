/*
 * File: MainMenu.java
 *
 * Class: MainMenu
 *
 * Version: 0.0.3
 *
 * Date: September 26th, 2016
 */


package bustamove.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Generates a MainMenu as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class MainMenu extends BasicGameState {
    /**
     * Play with 1 player Button.
     */
    private Button play1player;
    /**
     * Play with 2 players Button.
     */
    private Button play2players;
    /**
     * Quit Button.
     */
    private Button quit;

    /**
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.MAIN_MENU;
    }

    /**
     * Called when BasicGameState initializes.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
        play1player = new Button("1 Player", GameConfig.SECOND_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        play1player.centerButton(game);
        play2players = new Button("2 Players", GameConfig.THIRD_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        play2players.centerButton(game);
        quit = new Button("Quit", GameConfig.FOURTH_LINE, GameConfig.WIDTH1,
                GameConfig.HEIGHT);
        quit.centerButton(game);
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
        play1player.draw(graphics);
        play2players.draw(graphics);
        quit.draw(graphics);
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
            if (play1player.isInBounds(input)) {
                stateBasedGame.enterState(GameState.NAME_SCREEN,
                        new FadeOutTransition(), new FadeInTransition());
            }
            if (play2players.isInBounds(input)) {
                stateBasedGame.enterState(GameState.NAMES_SCREEN,
                        new FadeOutTransition(), new FadeInTransition());
            }
            if (quit.isInBounds(input)) {
                game.exit();
            }
        }
    }
}
