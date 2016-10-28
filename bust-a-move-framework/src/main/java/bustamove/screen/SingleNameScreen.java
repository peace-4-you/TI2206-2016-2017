/*
 * File: MainMenu.java
 * Class: MainMenu
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */

package bustamove.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;
import bustamove.screen.attributes.Button;
import bustamove.screen.attributes.Text;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;

/**
 * Generates a MainMenu as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class SingleNameScreen extends NameScreen {
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
        initNameScreen(game, stateBasedGame);
        Text nameText = new Text("Player: ", GameConfig.THIRD_LINE);
        getNamefields()[0] = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.FOURTH_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        getNamefields()[0].setText("Player");
        getNamefields()[0].setMaxLength(GameConfig.MAX_NAME_LENGTH);
        Button play = new Button("Play", GameConfig.SEVENTH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
        play.addAction(new Runnable() {
            public void run() {
                App.getGame().start1Player();
                App.getGame().getGameData().get(0).getPlayer()
                .setName(getNamefields()[0].getText());
                getNamefields()[0].setText("Player");
            }
        });
        play.addGameStateChangeAction(stateBasedGame,
                GameState.GAME_ACTIVE);
        getTexts().add(nameText);
        getButtons().add(play);
        getTextFields().add(getNamefields()[0]);
        super.center();
    }
}
