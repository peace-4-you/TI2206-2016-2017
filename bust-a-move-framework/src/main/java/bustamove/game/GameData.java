/*
 * File: GameData.java
 * Class: GameData
 *
 * Version: 1
 * Date: October 11, 2016
 */


package bustamove.game;

import java.util.Iterator;
import java.util.LinkedList;

import bustamove.bubble.BubbleStorage;
import bustamove.bubble.Collision;
import bustamove.bubble.PopBehaviour;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.bubble.Bubble;
import bustamove.bubble.Bubble.State;
import bustamove.system.Log;
import bustamove.util.PlayerObserver;
import bustamove.player.Player;
import bustamove.screen.config.GameConfig;

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
     * Cannon, Player, Collision, BubbleStorage instance for the game.
     */
    private Cannon cannon;
    private Player player;
    private Collision collide;
    private BubbleStorage bubbleStorage;
    private PopBehaviour popBehaviour;
    /**
     * Arraylist of bubbles and their speed.
     */
    private LinkedList<Bubble> bubbleslist;
    private double bubblespeed;
    /**
     * position for the arena.
     */
    private static final int ARENA_X = 180;
    private static final int ARENA_Y = 0;
    private static final int ARENA_HEIGHT = 531;
    private static final int ARENA_WIDTH = 280;
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
        Log.getInstance().log(this, "GameData initialised");
        this.offset = drawingoffset;
        this.playerNr = modelnr;
        this.state = GameDataState.RUNNING;
        this.bubbleslist = new LinkedList<Bubble>();
        this.player = new Player("Player1", this.offset, this.playerNr);
        this.player.registerObserver((PlayerObserver) this);

        this.bubbleStorage = new BubbleStorage(ARENA_X + this.offset,
                ARENA_Y);
        this.popBehaviour = new PopBehaviour(player.getScore());
        popBehaviour.setBubbleStorage(bubbleStorage);
        this.collide = new Collision(ARENA_X + this.offset,
                ARENA_X + this.offset + ARENA_WIDTH, ARENA_Y);
        collide.setPopBehaviour(popBehaviour);
        this.cannon = new Cannon(this, this.offset, this.playerNr);
        this.setBubbleSpeed(GameConfig.DEFAULT_BUBBLE_SPEED);
        collide.setBubbleStorage(bubbleStorage);
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
            popBehaviour.setGameHead(this);
            Iterator<Bubble> ite = bubbleslist.iterator();
            while (ite.hasNext()) {
                Bubble b1 = ite.next();
                b1.move();
                if (b1.getState() == State.FIRING) {
                    collide.checkCollision(b1);
                }
                if (b1.getState() == State.LANDED) {
                    ite.remove();
                }
            }
            if (bubbleStorage.isFull()) {
                this.state = GameDataState.LOSE;
            } else if (bubbleStorage.isEmpty()) {
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
        // Draw the arena borders
        g.drawRect(ARENA_X + this.offset, ARENA_Y, ARENA_WIDTH,
                ARENA_HEIGHT);
        float yPosLine = (float) (bubbleStorage.getHeight()
                * GameConfig.ROW_OFFSET + ARENA_Y);
        g.drawLine(ARENA_X + this.offset, yPosLine, ARENA_X + this.offset
                + ARENA_WIDTH, yPosLine);
        cannon.draw(g);
        player.draw(g);
        bubbleStorage.draw(g);
        for (Bubble bubble : this.bubbleslist) {
            if (bubble.getState() != State.LANDED) {
                bubble.draw(g);
            }
        }
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
     * Method to return the collision object inside gamedata class.
     *
     * @return Collision object inside gamedata class
     */
    public final Collision getCollision() {
        return this.collide;
    }

    /**
     * Getter for PopBehaviour.
     *
     * @return PopBehaviour object.
     */
    public final PopBehaviour getPopBehaviour() {
        return popBehaviour;
    }

    /**
     * Method to return the cannon object inside gamedata class.
     *
     * @return Cannon object inside gamedata class
     */
    public final BubbleStorage getBubbleStorage() {
        return this.bubbleStorage;
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
    public final LinkedList<Bubble> getBubbles() {
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

    /**
     * Registers an observer of the player of this GameData.
     * @param o The observer.
     */
    public final void registerPlayerObservers(final PlayerObserver o) {
        player.registerObserver(o);
    }
}