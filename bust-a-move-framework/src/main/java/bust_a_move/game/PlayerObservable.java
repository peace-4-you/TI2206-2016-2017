package bust_a_move.game;

public interface PlayerObservable {
	void registerObserver(PlayerObserver o);
	void removeObserver(PlayerObserver o);
	void notifyObserver();
	void notifyAmountObserver(int amount);
}
