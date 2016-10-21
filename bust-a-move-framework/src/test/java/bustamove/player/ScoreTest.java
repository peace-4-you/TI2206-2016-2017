package bustamove.player;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Before;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Score Tester
 * @author Maurice Willemsen
 * @version 17.10.2016
 */
@RunWith(Parameterized.class)
public class ScoreTest {

	private Score score;
    
    @Parameterized.Parameters
    public static Object[] input() {
        return new Object[][]{{new Score(new Player("testPlayer",0,1))}};
    }

    public ScoreTest(Object expected) {
    	score = (Score) expected;
    }

    @Before
    public void setUp() {
    }
	
    @Test
    public void getScore() {
    	score.setScore(100);
    	assertThat("Score should be added", score.getScore(), equalTo(100));		
    }
    
    @Test
    public void setScore() {
    	score.setScore(100);
    	assertThat("Score should be changed", score.getScore(), equalTo(100));
		
    }
    
    @Test
    public void resetScore() {
    	score.reset();
    	assertThat("Score should be changed", score.getScore(), equalTo(0));		
    }
    
    @Test
    public void addScore() {
    	score.setScore(100);
    	score.addScore(135);
    	assertThat("Score should be added", score.getScore(), equalTo(235));		
    }
    
    @Test
    public void scoreBubblesPopped() {
    	score.setScore(100);
    	score.scoreBubblesPopped(3);
    	assertThat("Score should be added", score.getScore(), equalTo(130));		
    }
    
    @Test
    public void scoreBubblesDropped() {
    	score.setScore(100);
    	score.scoreBubblesDropped(3);
    	assertThat("Score should be added", score.getScore(), equalTo(260));		
    }
    
    @Test
    public void scoreBubblesPoppedNegative() {
    	score.setScore(100);
    	score.scoreBubblesPopped(-1);
    	assertThat("Score should be added", score.getScore(), equalTo(100));		
    }
    
    @Test
    public void scoreBubblesDroppedNegative() {
    	score.setScore(100);
    	score.scoreBubblesDropped(-1);
    	assertThat("Score should be added", score.getScore(), equalTo(100));		
    } 
}
