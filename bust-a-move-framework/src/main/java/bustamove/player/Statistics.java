package bustamove.player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import bustamove.screen.config.GameConfig;
import bustamove.system.Log;

/**
 * A class representing all the statistics of this game.
 * @author Justin
 */
public class Statistics implements Serializable {

    /**
     * The version of this serializable object.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The amount of times a game has been won.
     */
    private int timesWon = 0;
    /**
     * The fastest win time in seconds.
     */
    private int winTime = Integer.MAX_VALUE;
    /**
     * The location of the file.
     */
    private String fileLocation;
    /**
     * Constructs a new Statistics object with the file location
     * specified in the GameConfig.
     */
    public Statistics() {
        fileLocation = GameConfig.STATISTICS_FILE;
    }
    /**
     * Constructs a new Statistics object with the specified file location.
     *
     * @param fileLoc The location of the file.
     */
    public Statistics(String fileLoc) {
        fileLocation = fileLoc;
    }
    /**
     * Gets the fastest time a player has won the game.
     *
     * @return The fastest time a player has won.
     */
    public final int getFastestWinTime() {
        return winTime;
    }
    /**
     * Gets the amount of times a player has won.
     *
     * @return The amount of times won.
     */
    public final int getTimesWon() {
        return timesWon;
    }
    /**
     * Adds a win to the statistics.
     */
    public final void win() {
        timesWon += 1;
    }
    /**
     * Sets a new fastest win time.
     *
     * @param newWinTime The new time.
     */
    public final void setTimeWon(final int newWinTime) {
        if (newWinTime < winTime) {
            winTime = newWinTime;
        }
    }
    /**
     * Writes this object to the file specified in the configs.
     */
    public final void write() {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileLocation);
            ObjectOutputStream out =
                    new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Reads this object from a file specified in the configs.
     *
     * @param fileLocation The location of the file.
     * @return The statistics read or a new one if none found.
     */
    public static Statistics read(String fileLocation) {
        try {
            Statistics s = null;
            FileInputStream fileIn =
                    new FileInputStream(fileLocation);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            s = (Statistics) in.readObject();
            in.close();
            fileIn.close();
            return s;
        } catch (FileNotFoundException e) {
            Log.getInstance().log(Statistics.class,
                    "Statistics file not found.");
        } catch (ClassNotFoundException e) {
            Log.getInstance().log(Statistics.class,
                    "Statistics class not recognized in file.");
        } catch (IOException e) {
            Log.getInstance().log(Statistics.class,
                    "Statistics class not recognized in file.");
        }
        return new Statistics(fileLocation);
    }
}
