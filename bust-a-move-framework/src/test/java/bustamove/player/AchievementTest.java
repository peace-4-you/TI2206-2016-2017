package bustamove.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bustamove.player.Achievement.AchievementType;

public class AchievementTest {

	
	@Test
	public void testTimeAchievementAttributes() {
		Achievement time = Achievement.timeAchievement("Hello", 100);
		assertEquals(time.getData(), 100);
		assertEquals(time.getName(), "Hello");
		assertEquals(time.getType(), AchievementType.WIN_TIME);
	}

	@Test
	public void testTimesWonAchievementAttributes() {
		Achievement wins = Achievement.timesWonAchievement("Hello", 50);
		assertEquals(wins.getData(), 50);
		assertEquals(wins.getName(), "Hello");
		assertEquals(wins.getType(), AchievementType.TIMES_WON);
	}

	@Test
	public void testScoreAchievementAttributes() {
		Achievement wins = Achievement.scoreAchievement("Hello", 50);
		assertEquals(wins.getData(), 50);
		assertEquals(wins.getName(), "Hello");
		assertEquals(wins.getType(), AchievementType.SCORE);
	}
}
