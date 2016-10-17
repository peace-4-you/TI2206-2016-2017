package bustamove.player;

public class TestObserver implements PlayerObserver {
	
	int amount;
	int number;
	String name;
	int score;

	public void update(int playeramount) {
		this.amount = playeramount;		
	}

	public void update(int number, String name, int score) {
		this.number = number;
		this.name = name;
		this.score = score;		
	}	
}
