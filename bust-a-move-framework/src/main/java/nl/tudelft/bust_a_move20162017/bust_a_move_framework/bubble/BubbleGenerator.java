package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.newdawn.slick.Color;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble.ColorChoice;

public class BubbleGenerator {

	public Bubble spawn(int i, int j, int k) {
		// TODO Auto-generated method stub
		return new Bubble(i,j,ColorChoice.RED);
	}

}
