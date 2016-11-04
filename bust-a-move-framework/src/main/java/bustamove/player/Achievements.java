package bustamove.player;

import java.util.LinkedList;

import bustamove.game.Highscore;
import bustamove.player.Achievement.AchievementType;
import bustamove.screen.config.GameConfig;

/**
 * A class representing all achievement objects.
 * @author Justin
 */
public class Achievements {

    /**
     * The statistics of this game.
     */
    private Statistics stats;

    /**
     * The list that stores all achievements.
     */
    private LinkedList<Achievement> achievements;

    /**
     * Constructs a new achievements object.
     * @param s The statistics
     */
    public Achievements(Statistics s) {
        achievements = new LinkedList<Achievement>();
        stats = s;
    }
    /**
     * Loads all the achievements.
     */
    public final void loadAchievements() {
        // Time achievements
        addAchievement(Achievement.timeAchievement(
                "Fast win", GameConfig.ACHIEVEMENT_FAST_WIN));
        addAchievement(Achievement.timeAchievement(
                "Very fast win", GameConfig.ACHIEVEMENT_VERY_FAST_WIN));
        addAchievement(Achievement.timeAchievement(
                "Insane fast win", GameConfig.ACHIEVEMENT_INSANE_FAST_WIN));
        addAchievement(Achievement.timeAchievement(
                "Cheating", GameConfig.ACHIEVEMENT_CHEATER));
        // Times won achievements
        addAchievement(Achievement.timesWonAchievement(
                "Beginner", GameConfig.ACHIEVEMENT_BEGINNER));
        addAchievement(Achievement.timesWonAchievement(
                "Getting better", GameConfig.ACHIEVEMENT_GETTING_BETTER));
        addAchievement(Achievement.timesWonAchievement(
                "Professional", GameConfig.ACHIEVEMENT_PROFESSIONAL));
        addAchievement(Achievement.timesWonAchievement(
                "Master", GameConfig.ACHIEVEMENT_MASTER));
        // Score achievements
        addAchievement(Achievement.scoreAchievement("A thousand",
                GameConfig.ACHIEVEMENT_THOUSAND));
        addAchievement(Achievement.scoreAchievement("10k",
                GameConfig.ACHIEVEMENT_10K));
        addAchievement(Achievement.scoreAchievement("50k",
                GameConfig.ACHIEVEMENT_50K));
        addAchievement(Achievement.scoreAchievement("Millionaire",
                GameConfig.ACHIEVEMENT_1M));
    }
    /**
     * Adds an achievement to the list.
     * @param a The achievement.
     */
    public final void addAchievement(Achievement a) {
        achievements.add(a);
    }
    /**
     * Checks if a player has achieved a time achievement.
     *
     * @param t The achievement
     * @return True if achieved, false otherwise.
     */
    private boolean hasTimeAchievement(Achievement t) {
        int time = (Integer) t.getData();
        return stats.getFastestWinTime() <= time;
    }
    /**
     * Checks if a player has achieved a times won achievement.
     *
     * @param t The achievement
     * @return True if achieved, false otherwise.
     */
    private boolean hasTimesWonAchievement(Achievement t) {
        return stats.getTimesWon() >= t.getData();
    }
    /**
     * Checks if a player has achieved a score achievement.
     *
     * @param t The achievement
     * @return True if achieved, false otherwise.
     */
    private boolean hasScoreAchievement(Achievement t) {
        Highscore hs = new Highscore();
        hs.setFile(GameConfig.HIGHSCORE_FILE);
        hs.load();
        return hs.getScore(0) >= t.getData();
    }
    /**
     * Checks if a certain achievement is indeed an achievement.
     *
     * @param index The position of the achievement.
     * @return         True if it is indeed an achievement.
     */
    public final boolean isAchievement(int index) {
        try {
            achievements.get(index);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
    /**
     * Checks if the player has achieved the achievement.
     *
     * @param index The index of the achievement.
     * @return         True if the player has achieved this achievement,
     *                 false otherwise.
     */
    public final boolean hasAchievement(int index) {
        if (!isAchievement(index)) {
            return false;
        }
        Achievement a = achievements.get(index);
        if (a.getType() == AchievementType.SCORE) {
            return hasScoreAchievement(a);
        } else if (a.getType() == AchievementType.TIMES_WON) {
            return hasTimesWonAchievement(a);
        } else if (a.getType() == AchievementType.WIN_TIME) {
            return hasTimeAchievement(a);
        }
        return false;
    }
    /**
     * Gets the name of a specified achievement if it exists.
     *
     * @param index The index of the achievement.
     * @return The name if the achievement exists, null otherwise.
     */
    public final String getAchievementName(int index) {
        if (!isAchievement(index)) {
            return "";
        }
        return achievements.get(index).getName();
    }
    /**
     * Gives a text description of an achievement.
     *
     * @param index The index of the achievement.
     * @return The text description.
     */
    public final String getAchievementDescription(int index) {
        if (!isAchievement(index)) {
            return "";
        }
        Achievement a = achievements.get(index);
        String s = null;
        if (a.getType() == AchievementType.SCORE) {
            s = "Get >= " + a.getData() + " score.";
        } else if (a.getType() == AchievementType.TIMES_WON) {
            s = "Win " + a.getData() + " games.";
        } else if (a.getType() == AchievementType.WIN_TIME) {
            s = "Win within " + a.getData() + " s.";
        }
        return s;
    }
}
