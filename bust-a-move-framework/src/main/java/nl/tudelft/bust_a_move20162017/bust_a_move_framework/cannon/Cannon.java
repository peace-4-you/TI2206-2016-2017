package nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon;

public class Cannon {

	private int ANGLE;

	private int CurrBubbleColour;

	private int NextBubbleColour;
	
	public int X;
	
	public int Y;

	public void main() {
		this.ANGLE = 0;
		this.NextBubbleColour = getNextColour();
		this.X = 320;
		this.Y = 0;
		this.loadNextBubble();	
	}

	//Bubble Checking
	private void loadNextBubble() {
		this.CurrBubbleColour = this.NextBubbleColour;
		this.NextBubbleColour = this.getNextColour();
	}
	
	private int getNextColour(){
		int nextColor = 1; //Needs to refer to function outside the class
		return nextColor;		
	}

	
	//Fire
	public void fire() {
		this.loadNextBubble();
	}
	
	

	//Turn Cannon
	public void stepUp() {
		if (this.ANGLE <= 60) {
			this.ANGLE++;
		}
	}
	
	public void stepDown() {
		if (this.ANGLE >= 60) {
			this.ANGLE--;
		}
	}
	
	//Gets
	public int getAngle(){
		return this.ANGLE;
	}
	
	public int nextBubbleColor(){
		return this.ANGLE;
	}
	
	public int currBubbleColor(){
		return this.CurrBubbleColour;
	}
	
}
