package bustamove.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AchievementsTest {

	
	@Test
	public void testLoadAchievementsNoCrash() {
		Statistics s = new Statistics();
		Achievements a = new Achievements(s);
		a.loadAchievements();
		assertEquals(a.getAchievementName(0), "Fast win");
	}
	
	@Test
	public void testTimeAchievementObtained() {
		Statistics s = new Statistics();
		Achievements a = new Achievements(s);
		a.addAchievement(Achievement.timeAchievement("testAchievement", 500));
		assertEquals(a.hasAchievement(0), false);
		s.setTimeWon(500);
		assertEquals(a.hasAchievement(0), true);
	}
	
	@Test
	public void testScoreAchievementObtained() {
		Statistics s = new Statistics();
		Achievements a = new Achievements(s);
		a.addAchievement(Achievement.scoreAchievement("testAchievement", Integer.MAX_VALUE));
		a.addAchievement(Achievement.scoreAchievement("always", 0));
		assertEquals(a.hasAchievement(0), false);
		assertEquals(a.hasAchievement(1), true);
	}
	
	@Test
	public void testTimesWonAchievementObtained() {
		Statistics s = new Statistics();
		Achievements a = new Achievements(s);
		a.addAchievement(Achievement.timesWonAchievement("test", 2));
		assertEquals(a.hasAchievement(0), false);
		s.win();
		assertEquals(a.hasAchievement(0), false);
		s.win();
		assertEquals(a.hasAchievement(0), true);
	}
	
	@Test
	public void testAchievementNotExisting() {
		Statistics s = new Statistics();
		Achievements a = new Achievements(s);
		assertEquals(a.getAchievementName(0), "");
		assertEquals(a.isAchievement(0), false);
		assertEquals(a.getAchievementDescription(0), "");
		assertEquals(a.hasAchievement(0), false);
	}
	
	@Test
	public void testTimeAchievementDescription() {
		Statistics s = new Statistics();
		Achievements a = new Achievements(s);
		a.addAchievement(Achievement.timeAchievement("name", 10));
		assertEquals(a.getAchievementDescription(0), "Win within 10 s.");
	}
	@Test
	public void testScoreAchievementDescription() {
		Statistics s = new Statistics();
		Achievements a = new Achievements(s);
		a.addAchievement(Achievement.scoreAchievement("name", 10));
		assertEquals(a.getAchievementDescription(0), "Get >= 10 score.");
	}
	@Test
	public void testTimesWonAchievementDescription() {
		Statistics s = new Statistics();
		Achievements a = new Achievements(s);
		a.addAchievement(Achievement.timesWonAchievement("name", 10));
		assertEquals(a.getAchievementDescription(0), "Win 10 games.");
	}
}
