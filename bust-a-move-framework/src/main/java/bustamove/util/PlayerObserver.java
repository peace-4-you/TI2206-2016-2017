package bustamove.util;

/**
 * Interface for a player observerobject.
 * @author Maurice Willemsen
 */
public interface PlayerObserver {
    /**
     * Updated by Observable, sets amount of playerdata.
     * @param playeramount integer about amount of players
     */
    void update(int playeramount);

    /**
     * Updated by Observable. Sets data.
     * @param number integer of player number
     * @param name String of player name
     * @param score integer of player score
     * @param dropped integer of dropper bubbles
     * @param popped integer of popped bubbles
     */
    void update(int number, String name, int score, int dropped, int popped);
}
