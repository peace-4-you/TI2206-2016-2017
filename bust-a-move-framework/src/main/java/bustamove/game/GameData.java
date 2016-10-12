/*
 * File: GameData.java
 * Class: GameData
 *
 * Version: 1
 * Date: October 11, 2016
 */


package bustamove.game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;
import bustamove.bubble.Bubble;
import bustamove.gamestate.GameConfig;
import bustamove.system.Log;
import bustamove.player.Player;
import bustamove.player.PlayerObserver;

/**
 * The Game class represents a single game model.
 *
 * @author Maurice Willemsen
 * @version 11.10.2016
 */
public class GameData implements PlayerObserver {
    /**
     * Enum for the state of the game model.
     */
    public enum GameDataState {
        RUNNING, WON, LOSE
    }

    private GameDataState state;
    /**
     * Score and Name of the player, updated by observer.
     */
    private int scorePlayer;
    private String namePlayer;
    /**
     * Cannon, Arena and Player instance for the game.
     */
    private Cannon cannon;
    private Player player;
    private Arena arena;
    /**
     * Arraylist of bubbles and their speed.
     */
    private ArrayList<Bubble> bubbleslist;
    private double bubblespeed;
    /**
     * position for the arena.
     */
    private static final int ARENA_X = 180;
    private static final int ARENA_Y = 0;
    private static final int ARENA_WIDTH = 531;
    private static final int ARENA_HEIGHT = 280;
    /**
     * GameModel number and the drawing offset.
     */
    private int offset;
    private int playerNr;

    /**
     * Constructor for the GameData.
     *
     * @param drawingoffset integer of the drawing offset
     * @param modelnr       integer of the GameData model
     */
    public GameData(final int drawingoffset, final int modelnr) {
        Log.log(this, "GameData initialised");
        this.offset = drawingoffset;
        this.playerNr = modelnr;
        this.state = GameDataState.RUNNING;
        this.bubbleslist = new ArrayList<Bubble>();
        this.player = new Player("Player1", this.offset, this.playerNr);
        this.player.registerObserver((PlayerObserver) App.getPauseScreen());
        this.player.registerObserver((PlayerObserver) App.getDefeatScreen());
        this.player.registerObserver((PlayerObserver) App.getVictoryScreen());
        this.player.registerObserver((PlayerObserver) this);
        this.arena = new Arena(ARENA_X + this.offset, ARENA_Y,
                GameData.ARENA_WIDTH, GameData.ARENA_HEIGHT, player.getScore());
        this.cannon = new Cannon(this, this.offset, this.playerNr);
        this.setBubbleSpeed(GameConfig.DEFAULT_BUBBLE_SPEED);
    }

    /**
     * Updates the BasicGameState.
     *
     * @param container the game container
     * @param sbg       the state based game
     * @param delta     delta of time exceeded
     * @throws SlickException any type of slick exception
     */
    public final void update(final GameContainer container,
                             final StateBasedGame sbg,
                             final int delta) throws SlickException {
        if (this.state == GameDataState.RUNNING) {
            this.cannon.update(container, delta, this);
            arena.getCollision().setGameHead(this);
            for (Bubble b1 : bubbleslist) {
                b1.move();
                if (b1.getState() == Bubble.State.FIRING) {
                    arena.checkCollision(b1);
                }
            }
            if (arena.getBubbleStorage().isFull()) {
                this.state = GameDataState.LOSE;
            } else if (arena.getBubbleStorage().isEmpty()) {
                this.state = GameDataState.WON;
            }
        }
    }

    /**
     * Renders the BasicGameState.
     *
     * @param container the game container
     * @param sbg       the state based game
     * @param g         Graphics object
     * @throws SlickException any type of slick exception
     */
    public final void render(final GameContainer container,
                             final StateBasedGame sbg,
                             final Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.drawString("Score:" + this.scorePlayer,
                GameConfig.MARGIN_LEFT + this.offset, GameConfig.SECOND_LINE);
        g.drawString("Name:" + this.namePlayer, GameConfig.MARGIN_LEFT
                + this.offset, GameConfig.FIRST_LINE);
        g.drawString("Fire speed: x"
                        + (double) Math.round(this.bubblespeed / 2),
                GameConfig.MARGIN_LEFT + this.offset,
                GameConfig.THIRD_LINE);
        cannon.draw(g);
        player.draw(g);
        arena.draw(g);
        for (Bubble bubble : this.bubbleslist) {
            if (bubble.getState() != Bubble.State.LANDED) {
                bubble.draw(g);
            }
        }
    }

    /**
     * Method to return the arena object inside gamedata class.
     *
     * @return Arena object inside gamedata class
     */
    public final Arena getArena() {
        return this.arena;
    }

    /**
     * Method to return the player object inside gamedata class.
     *
     * @return Player object inside gamedata class
     */
    public final Player getPlayer() {
        return this.player;
    }

    /**
     * Method to return the cannon object inside gamedata class.
     *
     * @return Cannon object inside gamedata class
     */
    public final Cannon getCannon() {
        return this.cannon;
    }

    /**
     * Method to return the gamedatastate object inside gamedata class.
     *
     * @return GameDataState object inside gamedata class
     */
    public final GameDataState getState() {
        return this.state;
    }

    /**
     * Method to return the bubblespeed inside gamedata class.
     *
     * @return Double bubblespeed object inside gamedata class
     */
    public final double getBubbleSpeed() {
        return this.bubblespeed;
    }

    /**
     * Method to return the bubbleslist inside gamedata class.
     *
     * @return ArrayList of bubbles inside gamedata class
     */
    public final ArrayList<Bubble> getBubbles() {
        return this.bubbleslist;
    }

    /**
     * Method to set the BubbleSpeed.
     *
     * @param speed double to set speed to
     */
    public final void setBubbleSpeed(final double speed) {
        this.bubblespeed = speed;
    }

    /**
     * Empty update method from PlayerObserver interface.
     *
     * @param playerAmount integer about amount of players
     */
    public final void update(final int playerAmount) {
        // Empty
    }

    /**
     * Updated by Observable. Sets data
     *
     * @param number integer of player number
     * @param name   String of player name
     * @param score  integer of player score
     */
    public final void update(final int number, final String name,
                             final int score) {
        if (number == playerNr) {
            this.scorePlayer = score;
            this.namePlayer = name;
        }
    }
}
