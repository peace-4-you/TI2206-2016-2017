/*
 * File: Highscore.java
 * Class: Highscore
 */

package bustamove.game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import bustamove.gamestate.GameConfig;
import bustamove.system.Log;

/**
 * The highscore class represents the class that writes and loads
 * the highscores.
 * @author Justin Segond
 */
public final class Highscore {

    /**
     * Stores the high scores in a TreeMap object. The TreeMap object
     * contains the score as an Integer and an ArrayList<String> of
     * names that got the same score.
     */
    private TreeMap<Integer, ArrayList<String>> highscores;
    /**
     * The location of the high scores file.
     */
    private static final String FILE = "res/highscores.txt";
    /**
     * The singleton instance of the high scores.
     */
    private static Highscore highscore = new Highscore();

    /**
     * Gets the singleton High score object.
     * @return The singleton High score object.
     */
    public static Highscore getInstance() {
        return highscore;
    }

    /**
     * Constructs a new High score object.
     */
    private Highscore() {
        highscores = new TreeMap<Integer, ArrayList<String>>();
        load();
    }

    /**
     * Loads the high scores from the specified high scores file.
     */
    private void load() {
        Log.log("Loading highscores...");
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            isr = new InputStreamReader(new FileInputStream(FILE), "UTF-8");
            reader = new BufferedReader(isr);
            String entry;
            while ((entry = reader.readLine()) != null) {
                addEntry(entry);
            }
            reader.close();
            isr.close();
        } catch (FileNotFoundException e) {
            Log.log("Highscore file not found.");
        } catch (IOException e) {
            Log.log("Error loading highscores.");
        }
    }

    /**
     * Tries to parse a String and add it to the high scores.
     * @param entry The string to parse a high score from.
     */
    private void addEntry(final String entry) {
        String[] parts = entry.split(",");
        if (parts.length != 2) {
            Log.log("High score entry has wrong parameters.");
        }
        int score = 0;
        String name = parts[0];
        try {
            score = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            Log.log("Error loading high score entry: [" + entry
                    + "], score is not an int.");
        }
        addEntry(name, score);
    }

    /**
     * Adds a high score entry.
     * @param name The player's name.
     * @param score The score they achieved.
     */
    private void addEntry(final String name, final int score) {
        if (!highscores.containsKey(score)) {
            highscores.put(score, new ArrayList<String>());
        }
        highscores.get(score).add(name);
    }

    /**
     * Adds and entry to high scores and saves the highscores to a file.
     * @param name The player's name
     * @param score The score they achieved.
     */
    public void addEntryAndSave(final String name, final int score) {
        addEntry(name, score);
        save();
    }

    /**
     * Saves the high scores to the specified file.
     */
    private void save() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(FILE, "UTF-8");
            Entry<Integer, ArrayList<String>> entry = highscores.lastEntry();
            int saved = 0;
            while (entry != null && saved < GameConfig.HIGHSCORE_ENTRIES) {
                saved += saveEntry(writer, entry);
                entry = highscores.lowerEntry(entry.getKey());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves an TreeMap entry to a file.
     * @param writer The PrintWriter to write the file to.
     * @param entry The entry to write to the file
     * @return an int with how many entries where saved to the file.
     */
    private int saveEntry(final PrintWriter writer,
            final Entry<Integer, ArrayList<String>> entry) {
        int added = 0;
        for (String name : entry.getValue()) {
            if (saveEntry(writer, name, entry.getKey())) {
                added++;
            }
        }
        return added;
    }

    /**
     * Saves an entry to a file.
     * @param writer The printwriter to write to.
     * @param name The name of the player.
     * @param score The score they achieved.
     * @return A boolean for if it was successfully saved (true)
     * and false otherwise.
     */
    private boolean saveEntry(final PrintWriter writer,
            final String name, final int score) {
        writer.println(name + "," + score);
        return true;
    }

    /**
     * Gets the name of the person at the given highscore position, e.g.
     * 0 for highest ranked player.
     * @param position The position of the player
     * @return The name of the player
     */
    public int getScore(final int position) {
        Entry<Integer, ArrayList<String>> entry = highscores.lastEntry();
        int currentPosition = 0;
        while (entry != null) {
            currentPosition += entry.getValue().size();
            if (position < currentPosition) {
                return entry.getKey();
            }
            entry = highscores.lowerEntry(entry.getKey());
        }
        return 0;
    }

    /**
     * Gets the name of the person at the given highscore position, e.g.
     * 0 for highest ranked player.
     * @param position The position of the player
     * @return The name of the player
     */
    public String getName(final int position) {
        Entry<Integer, ArrayList<String>> entry = highscores.lastEntry();
        int currentPosition = -1;
        while (entry != null) {
            currentPosition += entry.getValue().size();
            if (position <= currentPosition) {
                return entry.getValue().get(currentPosition - position);
            }
            entry = highscores.lowerEntry(entry.getKey());
        }
        return "";
    }

}
