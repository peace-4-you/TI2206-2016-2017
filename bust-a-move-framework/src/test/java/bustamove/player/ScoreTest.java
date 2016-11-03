package bustamove.player;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Score Tester
 * @author Maurice Willemsen
 * @version 17.10.2016
 */
@RunWith(Parameterized.class)
public class ScoreTest {

	private Player testPlayer;
	private Score score;
    
    @Parameterized.Parameters
    public static Object[] input() {
        return new Object[][]{{new Player("testPlayer",1)}};
    }

    public ScoreTest(Player player) {
    	testPlayer = player;
    	score = new Score(testPlayer);
    }
    	   
    @Test
    public void setScore() {
    	score.setScore(100);
    	score.setScore(200);
    	assertThat("Score should be set to latest set Score", score.getScore(), equalTo(200));	
    }
    
    @Test
    public void resetScore() {
    	score.reset();
    	assertThat("Score should be reset", score.getScore(), equalTo(0));		
    }
    
    @Test
    public void addScore() {
    	score.setScore(100);
    	score.addScore(135);
    	assertThat("Score should be added to current score", score.getScore(), equalTo(235));		
    }
    
    @Test
    public void scoreBubblesPopped() {
    	score.setScore(100);
    	score.scoreBubblesPopped(3);
    	assertThat("Score should be added to current score", score.getScore(), equalTo(130));		
    }
    
    @Test
    public void scoreBubblesDropped() {
    	score.setScore(100);
    	score.scoreBubblesDropped(3);
    	assertThat("Score should be added to current score", score.getScore(), equalTo(260));		
    }
    
    @Test
    public void scoreBubblesPoppedNegative() {
    	score.setScore(100);
    	score.scoreBubblesPopped(-1);
    	assertThat("No score should be added", score.getScore(), equalTo(100));		
    }
    
    @Test
    public void scoreBubblesDroppedNegative() {
    	score.setScore(100);
    	score.scoreBubblesDropped(-1);
    	assertThat("No Score should be added", score.getScore(), equalTo(100));		
    } 
    
    @Test
    public void GetBubblesDropped() {
    	score.reset();
    	score.scoreBubblesDropped(3);
    	score.scoreBubblesDropped(3);
    	assertThat("Dropped should be added with 6", score.getBubblesDropped(), equalTo(6));		
    } 
    
    @Test
    public void GetBubblesPopped() {
    	score.reset();
    	score.scoreBubblesPopped(3);
    	score.scoreBubblesPopped(3);
    	assertThat("Popped should be added with 6", score.getBubblesPopped(), equalTo(6));		
    } 
}
