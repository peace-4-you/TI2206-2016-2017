package bustamove.player;

import org.junit.After;
import org.junit.Test;

import bustamove.screen.config.GameConfig;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;

public class StatisticsTest {

	String fileLocation = "./res/TestStatistics.bin";
	
	@After
	public void afterDone() {
		File f = new File(fileLocation);
		f.delete();
	}
	
	@Test
	public void testWin() {
		Statistics s = new Statistics();
		assertEquals(s.getTimesWon(), 0);
		s.win();
		assertEquals(s.getTimesWon(), 1);
		s.win();
		s.win();
		assertEquals(s.getTimesWon(), 3);
	}
	
	@Test
	public void testSetGetTimeWon() {
		Statistics s = new Statistics();
		s.setTimeWon(100);
		assertEquals(s.getFastestWinTime(), 100);
		s.setTimeWon(25);
		assertEquals(s.getFastestWinTime(), 25);
		s.setTimeWon(50);
		assertEquals(s.getFastestWinTime(), 25);
	}

	@Test
	public void testWinTimeWriteRead() {
		Statistics s1 = new Statistics(fileLocation);
		s1.setTimeWon(50);
		s1.write();
		Statistics s2 = Statistics.read(fileLocation);
		assertEquals(s2.getFastestWinTime(), 50);
	}
	
	@Test
	public void testTimesWonWriteRead() {
		Statistics s1 = new Statistics(fileLocation);
		s1.win();
		s1.win();
		s1.win();
		s1.write();
		Statistics s2 = Statistics.read(fileLocation);
		assertEquals(s2.getTimesWon(), 3);
	}
	
	@Test
	public void testReadFileNotFoundNoCrash() {
		Statistics s = Statistics.read("./res/nonexisting.bin");
		assertEquals(s.getFastestWinTime(), Integer.MAX_VALUE);
		assertEquals(s.getTimesWon(), 0);
	}
	@Test
	public void testReadWrongFileNoCrash() {
		Statistics s = Statistics.read(GameConfig.HIGHSCORE_FILE);
		assertEquals(s.getFastestWinTime(), Integer.MAX_VALUE);
		assertEquals(s.getTimesWon(), 0);
	}
}
