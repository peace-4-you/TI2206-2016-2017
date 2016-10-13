package bustamove.player;

/**
 * Interface for an observable player object.
 * @author Maurice Willemsen
 */
public interface PlayerObservable {
    /**
     * Register a observer to the observable.
     * @param o A playerObserver object.
     */
    void registerObserver(PlayerObserver o);

    /**
     * Remove a observer from the observable.
     * @param o A playerObserver object.
     */
    void removeObserver(PlayerObserver o);

    /**
     * Notify's all observers.
     */
    void notifyObserver();

    /**
     * Notify's about the amount of observables.
     * @param amount integer of the amount of observables.
     */
    void notifyAmountObserver(int amount);
}
