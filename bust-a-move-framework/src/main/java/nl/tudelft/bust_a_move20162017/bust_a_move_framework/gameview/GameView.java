package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gameview;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arenaview.ArenaView;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannonview.CannonView;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.playerview.PlayerView;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamemodel.GameModel;

public class GameView implements Observer {
	
	private int WIDTH;
	private int HEIGHT;
	private GameModel.State state;
	
	private CannonView cannonview;
	private ArenaView arenaview;
	private PlayerView playerview;
	
	private Color backGroundcolor;
	private Graphics g;
	
	
	/**
	 * Creates Game instance
	 *
	 * @param WIDTH
	 *            int value for screen width
	 * @param HEIGHT
	 *            int value for screen height
	 * @param backgroundColour
	 *            Color value for background
	 */
	
	public GameView(){
		this.WIDTH = 640;
		this.HEIGHT = 480;
		this.backGroundcolor = new Color(0xFFFFFF);
		this.g = g;
	}
	
	public void update(Observable o, Object arg ) {
		this.state = o.state;
	}
	
	public void draw(Graphics g) {
		switch (this.state) {
		case START:
			// TODO Show startscreen
			break;
		case PLAYING:
			//cannonview.drawCannon(this.g);
			// TODO Show playscreen including arena and cannon
			break;
		case WON:
			// TODO Show wonscreen including score, level, and button for next
			// level
			break;
		case FAILED:
			// TODO Show failedscreen including score, level, and button for
			// restart level
			break;
		}
	}

}
