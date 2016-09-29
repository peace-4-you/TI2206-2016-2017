/*
 * File: game.java
 * Class: Game
 *
 * Version: 0.0.3
 * Date: September 12th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.game;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon.Cannon;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.Button;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.GameState;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.log.Log;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.Arena;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * The Game class represents a game entity.
 *
 * @author Maurice Willemsen
 */

public class Game extends BasicGameState implements Observer {

    private int LEVEL;
    private int score;


    public Cannon cannon;
    public ArrayList<Bubble> bubbleslist;
    public Player player;
    public Log log;
    public Arena arena;
    private Button pause;
    private StateBasedGame sbg;

    public Game() {
        this.log = new Log();
        log.log(this, "Game initialised");
    }

    public void initialisePlayer() {
        this.player = new Player("Player1");
        this.player.score.addAsObserver(this);
    }

    /**
     * Starts game, creates new arena, cannon, BubbleFactory instance, and a bubblelist array
     */

    private void startGame() {
        log.log(this, "Game Started");
        this.bubbleslist = new ArrayList<Bubble>();
        this.arena = new Arena(180, 0, 531, 280);
        this.cannon = new Cannon(this);

    }

    /**
     * Ends game, changes state to WON
     */

    private void wonGame() {
        log.log(this, "Game Won");
        sbg.enterState(GameState.WIN_SCREEN, new FadeOutTransition(), new FadeInTransition());
    }

    /**
     * Ends game, changes state to FAILED
     */

    private void failedGame() {
        log.log(this, "Game Failed");
        sbg.enterState(GameState.DEFEAT_SCREEN, new FadeOutTransition(), new FadeInTransition());
    }

    /**
     * Pauses game, changes state to FAILED
     */

    private void pauseGame() {
        log.log(this, "Game Paused");
        sbg.enterState(GameState.PAUSE_SCREEN, new FadeOutTransition(), new FadeInTransition());
    }

    /**
     * Asks for new level and new start of game.
     */

    private void startNewLevel() {
        this.levelUp();
        this.startGame();
    }

    /**
     * Level up
     */

    private void levelUp() {
        log.log(this, "Game Levels Up");
        this.LEVEL++;
    }

    /**
     * Called when BasicGameState initializes
     */

    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        this.sbg = sbg;
        this.LEVEL = 1;
        this.pause = new Button("Pause", 507, 50, 100, 30);
        this.player.reset();
        this.startGame();
    }

    /**
     * Updates the BasicGameState
     */

    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        this.sbg = sbg;
        this.cannon.update(container, delta);

        // ??? What's this for?
        /*if (container.getInput().isKeyPressed(Input.KEY_1)) {
            arena.getCollision().checkBubblesToDrop();
        }*/

        for (Bubble b1 : bubbleslist) {
            b1.move();
            if (b1.getState() == Bubble.State.FIRING) {
                arena.checkCollision(b1);
            }
        }
        if (arena.getBubbleStorage().isFull()) {
            this.failedGame();
        }
        if (arena.getBubbleStorage().isEmpty()) {
            this.wonGame();
        }

        if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (pause.isInBounds(container.getInput())) {
                this.pauseGame();
            }
        }
    }

    /**
     * Renders the BasicGameState
     */

    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setColor(Color.gray);
        g.fillRect(0, 530, 640, 580);
        g.setColor(Color.white);
        g.drawString("Level:" + this.LEVEL, 10, 130);
        g.drawString("Score:" + this.score, 10, 70);
        cannon.draw(g);
        player.draw(g);
        arena.draw(g);
        pause.draw(g);
        for (Bubble bubble : this.bubbleslist) {
            if (bubble.getState() != Bubble.State.LANDED) {
                bubble.draw(g);
            }
        }


    }

    /**
     * @return integer of BasicGameState number
     */

    public int getID() {
        return 3;
    }

    /**
     * Method to return the arena object inside game class.
     *
     * @return Arena object inside game class
     */

    public Arena getArena() {
        return this.arena;
    }

    /**
     * Updates the Observer
     */

    public void update(Observable o, Object arg) {
        this.score = (Integer) arg;
    }
}