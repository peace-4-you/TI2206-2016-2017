package bustamove.player;

/**
 * A class representing a single achievement.
 * @author Justin
 */
public final class Achievement {

    /**
     * The condition for obtaining an achievement.
     */
    protected enum AchievementType {
        TIMES_WON, WIN_TIME, SCORE
    }

    /**
     * The name of this achievement.
     */
    private String name;

    /**
     * The data that triggers the achievement.
     */
    private int data;

    /**
     * The type of the achievement.
     */
    private AchievementType type;

    /**
     * Creates an achievement that is based how many times
     * a game was won.
     *
     * @param n The name of this achievement.
     * @param t The type of this achievement.
     */
    private Achievement(final String n, final AchievementType t) {
        name = n;
        type = t;
    }

    /**
     * Sets integer data for this class.
     *
     * @param d The integer data.
     */
    public void setData(int d) {
        data = d;
    }

    /**
     * Gets the name of this achievement.
     *
     * @return The name of this achievement.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the data of this achievement.
     *
     * @return The data.
     */
    public int getData() {
        return data;
    }

    /**
     * Gets the type of this achievement.
     *
     * @return The type.
     */
    public AchievementType getType() {
        return type;
    }

    /**
     * Creates a new Achievement object that is a time achievement.
     *
     * @param name The name of the achievement.
     * @param time The time of this achievement.
     * @return The achievement.
     */
    public static Achievement timeAchievement(String name, int time) {
        Achievement a = new Achievement(name, AchievementType.WIN_TIME);
        a.setData(time);
        return a;
    }

    /**
     * Creates a new Achievement object that is a time won achievement.
     *
     * @param name         The name of the achievement.
     * @param timesWon     The times won of this achievement.
     * @return The achievement.
     */
    public static Achievement timesWonAchievement(String name,
            int timesWon) {
        Achievement a = new Achievement(name,
                AchievementType.TIMES_WON);
        a.setData(timesWon);
        return a;
    }

    /**
     * Creates a new Achievement object that is a score achievement.
     *
     * @param name     The name of the achievement.
     * @param score The score of this achievement.
     * @return The achievement.
     */
    public static Achievement scoreAchievement(String name, int score) {
        Achievement a = new Achievement(name, AchievementType.SCORE);
        a.setData(score);
        return a;
    }
}
