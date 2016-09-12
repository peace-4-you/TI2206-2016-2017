package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Bubble {

	public static final double DIAMETER = 20;
	public static final double SPEED = 5;
	private static final Color RED_COLOR = new Color(Color.red);
	private static final Color BLUE_COLOR = new Color(Color.blue);
	private static final Color YELLOW_COLOR = new Color(Color.yellow);
	private static final Color GREEN_COLOR = new Color(Color.green);
	private State state;
	private double x;
	private double y;
	private double xSpeed;
	private double ySpeed;
	private int angle;
	private ColorChoice color;
	public Color drawColor = Color.cyan;

	public enum State {
		STILL, FIRING, POPPING, DROPPING
	}

	public enum ColorChoice {
		RED, BLUE, YELLOW, GREEN
	}

	public Bubble(double x, double y, ColorChoice color) {
		this.x = x;
		this.y = y;
		this.xSpeed = 0;
		this.ySpeed = 0;
		this.angle = 0;
		this.color = color;
		this.state = State.STILL;

		switch (color) {
		case RED:
			this.drawColor = Bubble.RED_COLOR;
			break;
		case BLUE:
			this.drawColor = Bubble.BLUE_COLOR;
			break;
		case YELLOW:
			this.drawColor = Bubble.YELLOW_COLOR;
			break;
		case GREEN:
			this.drawColor = Bubble.GREEN_COLOR;
			break;
		}

	}

	public void move() {
		switch (this.state) {
		case STILL:
			break;
		case FIRING:
			this.setX(this.getX() + this.getXSpeed());
			this.setY(this.getY() + this.getYSpeed());
			break;
		case POPPING:
			break;
		case DROPPING:
			this.setX(this.getX() + this.getXSpeed());
			this.setY(this.getY() + this.getYSpeed());
			break;
		}
	}

	public void draw(Graphics g) {
		g.setColor(this.drawColor);
		g.fillOval((int) this.x, (int) this.y, (int) Bubble.DIAMETER, (int) Bubble.DIAMETER);
	}


	public void fire(int angle) {
		this.setAngle(angle);
		this.setState(State.FIRING);
		this.setXSpeed(Math.cos(Math.toRadians(angle + 90)) * Bubble.SPEED);
		this.setYSpeed(-Math.sin(Math.toRadians(angle + 90)) * Bubble.SPEED);
	}

	public double getX() {
		return this.x;
	}

	void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}


	public double getXSpeed() {
		return this.xSpeed;
	}


	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}


	public double getYSpeed() {
		return this.ySpeed;
	}


	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}


	public ColorChoice getColor() {
		return this.color;
	}


	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

}