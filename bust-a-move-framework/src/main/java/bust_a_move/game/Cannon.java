/*
 * File: cannon.java
 * Class: Cannon
 *
 * Version: 0.0.3
 * Date: September 12th, 2016
 */

package bust_a_move.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import bust_a_move.bubble.Bubble;
import bust_a_move.bubble.powerup.PowerUp;
import bust_a_move.gamestate.GameConfig;
import bust_a_move.log.Log;

/**
 * The Cannon class represents a cannon entity.
 * @author Maurice Willemsen
 */

public class Cannon {
	/**
	 * X coordinate of the cannon.
	 */
	private static final int X = 320;
	/**
	 * Y coordinate of the cannon.
	 */
	private static final int Y = 530;
	/**
	 * X coordinate of the next bubble.
	 */
	private static final int X_LAUNCH = (int) (375 - Bubble.DIAMETER / 2);
	/**
	 * Y coordinate of the next bubble.
	 */
	private static final int Y_LAUNCH = (int) (555 - Bubble.DIAMETER / 2);
	/**
	 * X coordinate of the current bubble.
	 */
	private static final int X_LOAD = (int) (320 - Bubble.DIAMETER / 2);
	/**
	 * Y coordinate of the current bubble.
	 */
	private static final int Y_LOAD = (int) (530 - Bubble.DIAMETER / 2);
	/**
	 * The length of the cannon barrel.
	 */
	private int length = GameConfig.DEFAULT_CANNON_LENGTH;
	/**
	 * The color of the cannon barrel.
	 */
	private Color cannonColour;
	/**
	 * The angle of the cannon.
	 */
	private int angle = 0;
	/**
	 * Current bubble in the cannon.
	 */
	private Bubble currBubble;
	/**
	 * Next bubble in line.
	 */
	private Bubble nextBubble;
	/**
	 * Game reference.
	 */
	private GameData game;
	/**
	 * Time delta between now and last time a bubble is shot.
	 */
	private int TIME_SHOT_FIRED;
	/**
	 * Time delta between now and last time the right arrow key was pressed.
	 */
	private int TIME_KEY_RIGHT;
	/**
	 * Time delta between now and last time the left arrow key was pressed.
	 */
	private int TIME_KEY_LEFT;
	/**
	 * Idle time after which the cannon will shot automatically.
	 */
	private static final int TIME_TO_SHOOT = 5000;
	/**
	 * A warning is displayed at the last milliseconds before automatic
	 * shooting.
	 */
	private static final int TIME_DISPLAY_FIRE_WARNING = 1500;
	/**
	 * Flag to enable warning display.
	 */
	private boolean displayWarning;
	/**
	 * Right angle limit.
	 */
	private static final int R_ANGLE_LIMIT = 60;
	/**
	 * Left angle limit.
	 */
	private static final int L_ANGLE_LIMIT = -60;
	/**
	 * Input scan delay.
	 */
	private static final int SCAN_DELAY = 10;
	/**
	 * Angle offset.
	 */
	private static final int ANGLE_OFFSET = 90;
	/**
	 * X coordinate of auto shoot warning message.
	 */
	private static final int WARNING_X = 225;
	/**
	 * Y coordinate of auto shoot warning message.
	 */
	private static final int WARNING_Y = 500;

	private final int offset;

	private final int playernr;

	/**
	 * Creates Cannon instance.
	 * @param g game object for reference
	 */
	public Cannon(final GameData g, int offset, int nr) {
		Log.log(this, "Cannon initialised");
		this.cannonColour = Color.red;
		this.game = g;
		this.offset = offset;
		this.playernr = nr;
		this.nextBubble = getNextBubble();
		this.loadNextBubble();
		this.loadNextBubble();
	}

	/**
	 * Load next bubble to current bubble and asks for a new bubble.
	 */
	private void loadNextBubble() {
		this.currBubble = this.nextBubble;
		this.nextBubble = this.getNextBubble();
		this.currBubble.setX(Cannon.X_LOAD + this.offset);
		this.currBubble.setY(Cannon.Y_LOAD);
		this.game.getBubbles().add(this.nextBubble);
	}

	/**
	 * Determines next bubble.
	 * @return newly created bubble
	 */
	private Bubble getNextBubble() {
		Log.log(this, "Next bubble loaded to cannon");
		Bubble newBubble = Bubble.randomColor(
				(float) Cannon.X_LAUNCH + this.offset, (float) Cannon.Y_LAUNCH,
				game.getArena().getBubbleStorage().getColorsOnArena(), true);
		if (GameConfig.ENABLE_POWERUPS) {
			newBubble = PowerUp.apply(newBubble);
		}
		return newBubble;
	}

	/**
	 * Fires current bubble and loads next bubble.
	 */
	public final void fire() {
		Log.log(this, "Cannon fired a bubble");
		this.currBubble.updateGame(game);
		this.currBubble.fire(this.angle);
		this.loadNextBubble();
		this.TIME_SHOT_FIRED = 0;
		this.displayWarning = false;
	}

	/**
	 * Updates the Cannon angle per input step.
	 */
	public final void stepUp() {
		if (this.angle <= R_ANGLE_LIMIT) {
			this.angle += 1;
		}
	}

	/**
	 * Updates the Cannon angle per input step.
	 */
	public final void stepDown() {
		if (this.angle >= L_ANGLE_LIMIT) {
			this.angle -= 1;
		}
	}

	/**
	 * Update the cannon.
	 * @param container game container
	 * @param delta time passed
	 */
	public final void update(final GameContainer container, final int delta,
			final GameData gamehead) {
		TIME_SHOT_FIRED += delta;
		game = gamehead;
		if (this.playernr == 1) {
			if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
				if (container.getInput().isKeyPressed(Input.KEY_RIGHT)) {
					Log.log(this, "Cannon moving to the right");
				}
				this.TIME_KEY_RIGHT += delta;
				if (this.TIME_KEY_RIGHT > SCAN_DELAY) {
					stepDown();
					this.TIME_KEY_RIGHT = 0;
				}
			} else {
				this.TIME_KEY_RIGHT = 0;
			}
			if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
				if (container.getInput().isKeyPressed(Input.KEY_LEFT)) {
					Log.log(this, "Cannon moving to the left");
				}
				this.TIME_KEY_LEFT += delta;
				if (this.TIME_KEY_LEFT > SCAN_DELAY) {
					stepUp();
					this.TIME_KEY_LEFT = 0;
				}
			} else {
				this.TIME_KEY_LEFT = 0;
			}
			if (container.getInput().isKeyPressed(Input.KEY_UP)) {
				fire();
			}
		} else if (this.playernr == 2) {
			if (container.getInput().isKeyDown(Input.KEY_D)) {
				if (container.getInput().isKeyPressed(Input.KEY_D)) {
					Log.log(this, "Cannon moving to the right");
				}
				this.TIME_KEY_RIGHT += delta;
				if (this.TIME_KEY_RIGHT > SCAN_DELAY) {
					stepDown();
					this.TIME_KEY_RIGHT = 0;
				}
			} else {
				this.TIME_KEY_RIGHT = 0;
			}
			if (container.getInput().isKeyDown(Input.KEY_A)) {
				if (container.getInput().isKeyPressed(Input.KEY_A)) {
					Log.log(this, "Cannon moving to the left");
				}
				this.TIME_KEY_LEFT += delta;
				if (this.TIME_KEY_LEFT > SCAN_DELAY) {
					stepUp();
					this.TIME_KEY_LEFT = 0;
				}
			} else {
				this.TIME_KEY_LEFT = 0;
			}
			if (container.getInput().isKeyPressed(Input.KEY_W)) {
				fire();
			}
		}

		if (this.TIME_SHOT_FIRED > TIME_TO_SHOOT - TIME_DISPLAY_FIRE_WARNING) {
			displayWarning = true;
		}
		if (this.TIME_SHOT_FIRED > TIME_TO_SHOOT) {
			Log.log(this, "Time elapsed, " + "shooting automatically");
			fire();
			TIME_KEY_RIGHT = 0;
			displayWarning = false;
		}
	}

	/**
	 * Draws the graphics on the screen.
	 * @param g graphics
	 */
	public final void draw(final Graphics g) {
		g.setColor(this.cannonColour);
		g.drawLine(Cannon.X + this.offset, Cannon.Y,
				(int) (Cannon.X + this.offset
						+ Math.cos(Math.toRadians(this.angle + ANGLE_OFFSET))
								* this.length),
				(int) (Cannon.Y
						- Math.sin(Math.toRadians(this.angle + ANGLE_OFFSET))
								* this.length));
		if (displayWarning) {
			g.drawString("Hurry up!", WARNING_X + this.offset,
					WARNING_Y);
		}
	}

	/**
	 * Getter method: length.
	 * @return int Cannon length
	 */
	public final int getLength() {
		return this.length;
	}

	/**
	 * Setter method: length.
	 * @param l Cannon length
	 */
	public final void setLength(int l) {
		this.length = l;
	}
}
