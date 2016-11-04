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
 *
 * @author Jason Xie, Maurice Willemsen
 */
public class DoubleNameScreen extends NameScreen {
    /**
     * Getter method for Id.
     *
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.NAMES_SCREEN;
    }

    /**
     * Called when BasicGameState initializes.
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
                           final StateBasedGame stateBasedGame)
            throws SlickException {
        initNameScreen(game, stateBasedGame);
        Text player1text = new Text("Player 1: ", GameConfig.SECOND_LINE);
        Text player2text = new Text("Player 2: ", GameConfig.FOURTH_LINE);
        getNamefields()[0] = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.THIRD_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        getNamefields()[1] = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.FIFTH_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        getNamefields()[0].setText("Player1");
        getNamefields()[1].setText("Player2");
        getNamefields()[0].setMaxLength(GameConfig.MAX_NAME_LENGTH);
        getNamefields()[1].setMaxLength(GameConfig.MAX_NAME_LENGTH);
        Button play = new Button("Play", GameConfig.TENTH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
        play.addAction(new Runnable() {
            public void run() {
                try {
                    App.getGame().start2Player(getDifficulty());
                } catch (SlickException e) {
                    e.printStackTrace();
                }
                App.getGame().getGameData().get(0).getPlayer()
                        .setName(getNamefields()[0].getText());
                getNamefields()[0].setText("Player 1");
                App.getGame().getGameData().get(1).getPlayer()
                        .setName(getNamefields()[1].getText());
                getNamefields()[1].setText("Player 2");
            }
        });
        play.addGameStateChangeAction(stateBasedGame,
                GameState.GAME_ACTIVE);
        getButtons().add(play);
        getTexts().add(player1text);
        getTexts().add(player2text);
        getTexts().add(getDifficultyText());
        getTextFields().add(getNamefields()[0]);
        getTextFields().add(getNamefields()[1]);
        super.center();
    }
}
