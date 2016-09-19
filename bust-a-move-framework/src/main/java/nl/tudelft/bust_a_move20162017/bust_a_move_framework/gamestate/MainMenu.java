package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


/**
 * Created by Jason Xie on 15/09/2016.
 */
public class MainMenu extends BasicGameState {

    private Button play;

    public int getID() {
        return 2;
    }

    public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
        play = new Button("Play",140, 100, 30);
        play.centerButton(game);
    }

    public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        play.draw(graphics);

    }

    public void update(GameContainer game, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (play.isInBounds(input)) {
            	stateBasedGame.getState(GameState.GAME_ACTIVE).init(game, stateBasedGame);
                stateBasedGame.enterState(GameState.GAME_ACTIVE,new FadeOutTransition(), new FadeInTransition());
            }
        }

    }
}
