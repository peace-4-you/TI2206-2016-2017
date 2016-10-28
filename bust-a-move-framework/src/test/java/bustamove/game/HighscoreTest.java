package bustamove.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class HighscoreTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private Highscore hs;
	
	private File highscoreTestFile = new File("res/highscoreTest.txt");
	
	@Before
	public void setUp() {
		removeFile();
		hs = new Highscore();
		hs.setFile(highscoreTestFile.getPath());
		hs.load();
	}
	
	@After
	public void after() {
		removeFile();
	}
	
	private void removeFile() {
		highscoreTestFile.delete();
	}
	
	@Test
	public void testEmptyHighscoreScoreName() {
		hs.load();
		assertEquals(null, hs.getName(0));
		assertEquals(0, hs.getScore(0));
		removeFile();
	}
	
	@Test
	public void testHighscoreNegativePosition() {
		hs.load();
		assertEquals(hs.getName(-1), null);
		assertEquals(hs.getScore(-1), 0);
		removeFile();
	}
	
	@Test
	public void testHighscoreAddEntry() {
		hs.load();
		String name = "player1";
		int score = 1;
		hs.addEntry(name, score);
		assertEquals(hs.getName(0), name);
		assertEquals(hs.getScore(0), score);
		removeFile();
	}
	
	@Test
	public void testHighscoreEntryPosition() {
		hs.load();
		int size = 10;
		int[] scores = new int[size];
		String[] names = new String[size];
		for (int i = 0; i < size; i++) {
			scores[i] = i;
			names[i] = "name" + i;
			hs.addEntry(names[i], scores[i]);
		}
		
		for (int i = 0; i < size; i++) {
			int iNeg = Math.abs(i - (size - 1));
			assertEquals(hs.getScore(iNeg), scores[i]);
			assertEquals(hs.getName(iNeg), names[i]);
		}
		removeFile();
	}
	

	@Test
	public void testHighscoreAddEntryAbove() {
		hs.load();
		int score1 = 1000;
		int score2 = 100;
		String name1 = "OP";
		String name2 = "NOOB";
		hs.addEntry(name2, score2);
		hs.addEntry(name1, score1);
		assertEquals(hs.getName(0), name1);
		assertEquals(hs.getScore(0), score1);
		assertEquals(hs.getName(1), name2);
		assertEquals(hs.getScore(1), score2);
		removeFile();
	}

	@Test
	public void testHighscoreAddEntryBelow() {
		hs.load();
		int score1 = 1000;
		int score2 = 100;
		String name1 = "OP";
		String name2 = "NOOB";
		hs.addEntry(name1, score1);
		hs.addEntry(name2, score2);
		assertEquals(hs.getName(0), name1);
		assertEquals(hs.getScore(0), score1);
		assertEquals(hs.getName(1), name2);
		assertEquals(hs.getScore(1), score2);
		removeFile();
	}

	@Test
	public void testHighscoreSaveAndLoad() {
		hs.load();
		hs.addEntryAndSave("OP", 1000);
		
		hs.load();
		assertEquals(hs.getName(0), "OP");
		assertEquals(hs.getScore(0), 1000);
		
		removeFile();
	}
	
	@Test
	public void testAddWrongStringEntry() {
		hs.load();
		hs.addEntry("OP,5000,9000");
		assertEquals(hs.getScore(0), 0);
		assertEquals(hs.getName(0), null);
		
		removeFile();
	}
	
	@Test
	public void testSaveEntryPrintWriterNull() {
		hs.load();
		Entry<Integer, ArrayList<String>> entry;
		ArrayList<String> names = new ArrayList<String>();
		names.add("OP");
		entry = new SimpleImmutableEntry<Integer, ArrayList<String>>(1000, names);
		PrintWriter pw = null;
		assertEquals(0, hs.saveEntry(pw, entry));
		assertEquals(0,hs.saveEntry(pw, null));
		try {
			pw = new PrintWriter(highscoreTestFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(0, hs.saveEntry(pw, null));
		pw.close();
		removeFile();
	}
	
	@Test
	public void testAddEntrySameScore() {
		hs.load();
		// OP got score first, so is higher placed
		hs.addEntry("OP", 1000);
		hs.addEntry("Also OP", 1000);
		
		assertEquals(hs.getName(0), "OP");
		assertEquals(hs.getScore(0), 1000);
		assertEquals(hs.getName(1), "Also OP");
		assertEquals(hs.getScore(1), 1000);
		
		removeFile();
	}

}
