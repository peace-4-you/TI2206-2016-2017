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

import bustamove.screen.config.GameConfig;
import bustamove.system.Log;

/**
 * The highscore class represents the class that writes and loads
 * the highscores.
 *
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
    private String file;

    /**
     * Constructs a new High score object.
     */
    public Highscore() {
    }

    /**
     * Loads the high scores from the specified high scores file.
     */
    public void load() {
        Log.getInstance().log(this, "Loading highscores...");
        highscores = new TreeMap<Integer, ArrayList<String>>();
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(isr);
            String entry;
            while ((entry = reader.readLine()) != null) {
                addEntry(entry);
            }
            reader.close();
            isr.close();
        } catch (FileNotFoundException e) {
            Log.getInstance().log(this, "File not found.");
        } catch (IOException e) {
            Log.getInstance().log(this, "Error loading highscores.");
        }
    }

    /**
     * Tries to parse a String and add it to the high scores.
     *
     * @param entry The string to parse a high score from.
     */
    private void addEntry(final String entry) {
        String[] parts = entry.split(",");
        if (parts.length != 2) {
            Log.getInstance().log(this,
                    "High score entry has wrong parameters.");
            return;
        }
        int score = 0;
        String name = parts[0];
        try {
            score = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            Log.getInstance().log(this, "Error loading high score entry: ["
                    + entry + "], score is not an int.");
        }
        addEntry(name, score);
    }

    /**
     * Adds a high score entry.
     *
     * @param name  The player's name.
     * @param score The score they achieved.
     */
    public void addEntry(final String name, final int score) {
        if (!highscores.containsKey(score)) {
            highscores.put(score, new ArrayList<String>());
        }
        highscores.get(score).add(name);
    }

    /**
     * Adds and entry to high scores and saves the highscores to a file.
     *
     * @param name  The player's name
     * @param score The score they achieved.
     */
    public void addEntryAndSave(final String name, final int score) {
        addEntry(name, score);
        save();
    }

    /**
     * Saves the high scores to the specified file.
     */
    public void save() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file, "UTF-8");
            Entry<Integer, ArrayList<String>> entry = highscores.lastEntry();
            int saved = 0;
            while (entry != null && saved < GameConfig.HIGHSCORE_ENTRIES) {
                saved += saveEntry(writer, entry);
                entry = highscores.lowerEntry(entry.getKey());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            Log.getInstance().log(this,
                    "File not found while writing. (Should never happen)");
        } catch (UnsupportedEncodingException e) {
            Log.getInstance().log(this, "Encoding UTF-8 not supported.");
        }
    }

    /**
     * Saves an TreeMap entry to a file.
     *
     * @param writer The PrintWriter to write the file to.
     * @param entry  The entry to write to the file
     * @return an int with how many entries where saved to the file.
     */
    private int saveEntry(final PrintWriter writer,
                          final Entry<Integer, ArrayList<String>> entry) {
        if (writer == null || entry == null) {
            Log.getInstance().log(this, "Writer of entry null");
            return 0;
        }
        int added = 0;
        for (String name : entry.getValue()) {
            writer.println(name + "," + entry.getKey());
        }
        return added;
    }

    /**
     * Gets the name of the person at the given highscore position, e.g.
     * 0 for highest ranked player.
     *
     * @param position The position of the player
     * @return The name of the player
     */
    public int getScore(final int position) {
        if (position < 0) {
            Log.getInstance().log(this,
                    "Highscore position can not be negative.");
            return 0;
        }
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
     *
     * @param position The position of the player
     * @return The name of the player
     */
    public String getName(final int position) {
        if (position < 0) {
            Log.getInstance().log(this,
                    "Highscore position can not be negative.");
            return null;
        }
        Entry<Integer, ArrayList<String>> entry = highscores.lastEntry();
        int currentPosition = -1;
        while (entry != null) {
            currentPosition += entry.getValue().size();
            if (position <= currentPosition) {
                return entry.getValue().get(currentPosition - position);
            }
            entry = highscores.lowerEntry(entry.getKey());
        }
        return null;
    }

    /**
     * Sets the file to load the highscores from.
     *
     * @param fileName The file name to load the data from.
     */
    public void setFile(final String fileName) {
        file = fileName;
    }
}