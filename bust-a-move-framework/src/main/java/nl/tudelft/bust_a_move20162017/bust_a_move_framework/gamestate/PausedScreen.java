package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

/**
 * Created by Jason Xie on 15/09/2016.
 */
public class PausedScreen extends BasicGameState {

    private Button resume;
    private Button quit;

    public int getID() {
        return 4;
    }

    public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
        //TODO: Come up with better name and text for buttons.
        resume = new Button("Resume", 170, 120, 30);
        resume.centerButton(game);
        quit = new Button("Quit", 205, 120, 30);
        quit.centerButton(game);
    }

    public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        String text = "Game Paused.";
        float textwidth = resume.font.getWidth(text);
        resume.font.drawString(320 - textwidth / 2, 30, text, Color.white);

        text = "Player: " + App.game.player.getName();
        textwidth = resume.font.getWidth(text);
        resume.font.drawString(320 - textwidth / 2, 90, text, Color.white);

        text = "Score: " + App.game.player.score.getScore();
        textwidth = resume.font.getWidth(text);
        resume.font.drawString(320 - textwidth / 2, 120, text, Color.white);
        resume.draw(graphics);
        quit.draw(graphics);
    }

    public void update(GameContainer game, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (resume.isInBounds(input)) {
                stateBasedGame.enterState(GameState.GAME_ACTIVE, new FadeOutTransition(), new FadeInTransition());
            }
            if (quit.isInBounds(input)) {
                stateBasedGame.enterState(GameState.MAIN_MENU, new FadeOutTransition(), new FadeInTransition());
            }
        }

    }
}
