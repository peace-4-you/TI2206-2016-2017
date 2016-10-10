package bust_a_move.game;

public interface PlayerObserver {
	public void update(int playeramount);
	public void update(int number,String name, int score);
}
