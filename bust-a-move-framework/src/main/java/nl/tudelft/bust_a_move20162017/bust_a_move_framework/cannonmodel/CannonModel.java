/*
 * File: cannonmodel.java
 * Class: CannonModel
 *
 * Version: 0.0.1
 * Date: September 8th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannonmodel;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubblemodel.BubbleModel;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubblegenerator.BubbleGenerator;
import java.util.Observable;

/**
 * The Cannon class represents a cannon entity.
 *
 * @author Maurice Willemsen
 */

public class CannonModel extends Observable {

	private int ANGLE;
	private BubbleModel currBubble;
	private BubbleModel nextBubble;

	/**
	 * Creates Cannon instance
	 */

	public CannonModel() {
		this.ANGLE = 0;
		this.nextBubble = getNextBubble();
		this.loadNextBubble();
		this.updateObservers();
	}

	/**
	 * updates subscribed Observers
	 */
	
	public void updateObservers() {
		this.notifyObservers();
	}

	/**
	 * Load next bubble to current bubble and asks for a new bubble.
	 */

	private void loadNextBubble() {
		this.currBubble = this.nextBubble;
		this.nextBubble = this.getNextBubble();
	}

	/**
	 * Determines next bubble
	 */

	private BubbleModel getNextBubble() {
		BubbleGenerator nextBubbleGen = new BubbleGenerator();
		BubbleModel nextBubble = nextBubble.spawn(100, 10, 1);
		nextBubble.spawn(100, 10, 1);
		return nextBubble;
	}

	/**
	 * Fires current bubble and loads next bubble
	 */

	public void fire() {
		this.currBubble.fire();
		this.loadNextBubble();
		this.updateObservers();
	}

	/**
	 * updates the Cannon angle per inputstep
	 */

	public void stepUp() {
		if (this.ANGLE <= 60) {
			this.ANGLE++;
			this.updateObservers();
		}
	}

	/**
	 * updates the Cannon angle per inputstep
	 */

	public void stepDown() {
		if (this.ANGLE >= -60) {
			this.ANGLE--;
			this.updateObservers();
		}
	}

}
