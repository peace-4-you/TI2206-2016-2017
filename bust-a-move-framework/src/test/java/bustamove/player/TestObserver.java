package bustamove.player;

import bustamove.util.PlayerObserver;

public class TestObserver implements PlayerObserver {
	
	public int amount;
	public int number;
	public String name;
	public int score;
	public int dropped;
	public int popped;

	public void update(int playeramount) {
		this.amount = playeramount;		
	}

	public void update(int number, String name, int score,int dropped,int popped) {
		this.number = number;
		this.name = name;
		this.score = score;	
		this.popped = popped;
		this.dropped = dropped;
	}	
}
