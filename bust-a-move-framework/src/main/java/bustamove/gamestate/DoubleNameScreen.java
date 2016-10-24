/*
 * File: MainMenu.java
 * Class: MainMenu
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */

package bustamove.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;

/**
 * Generates a MainMenu as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class DoubleNameScreen extends NameScreen {
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
    	super.init(game, stateBasedGame);;
        Text player1text = new Text("Player 1: ", GameConfig.FIRST_LINE);
        Text player2text = new Text("Player 2: ", GameConfig.FOURTH_LINE);
        namefields[1] = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.FIFTH_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        namefields[1].setText("Player2"); 
        Button play = new Button("Play", GameConfig.SEVENTH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);  
        play.addAction(new Runnable() {
            public void run() {
                try {
                    App.getGame().start2Player();
                } catch (SlickException e) {
                    e.printStackTrace();
                }
                App.getGame().getGameData().get(0).getPlayer()
                .setName(namefields[0].getText());
                App.getGame().getGameData().get(1).getPlayer()
                .setName(namefields[1].getText());
            }
        });
        play.addGameStateChangeAction(stateBasedGame,
                GameState.GAME_ACTIVE);
        buttons.add(play);        
        texts.add(player1text);
        texts.add(player2text);
        textfields.add(namefields[1]);
        super.center();
    }
}
