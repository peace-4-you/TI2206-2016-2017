package bustamove.screen;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.player.Achievements;
import bustamove.player.Statistics;
import bustamove.screen.attributes.Button;
import bustamove.screen.attributes.Text;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;
import bustamove.system.Log;
/**
 * An achievement screen object displaying all achievements.
 * @author Justin
 */
public class AchievementsScreen extends Screen {

    private Achievements achievements;

    /**
     * Creates a new achievements screen.
     *
     * @param s  The statistics.
     */
    public AchievementsScreen(Statistics s) {
        achievements = new Achievements(s);
        achievements.loadAchievements();
    }

    /**
     * Called when BasicGameState initializes.
     *
     * @param gc  the game container
     * @param sbg the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        initBasicScreen(gc);
        Button b = new Button("Back", GameConfig.FIRST_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        b.addGameStateChangeAction(sbg, GameState.MAIN_MENU);
        b.centerButton(gc);
        getButtons().add(b);
    }

    /**
     * Called when BasicGameState is entered.
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void enter(final GameContainer game,
                            final StateBasedGame stateBasedGame)
            throws SlickException {
        getTexts().clear();
        int i = 0;
        while (achievements.isAchievement(i)) {
            Color c = null;
            if (achievements.hasAchievement(i)) {
                c = Color.green;
                Log.getInstance().log(this, "Has achievement!");
            } else {
                c = Color.red;
            }
            Text title = new Text(
                    achievements.getAchievementName(i), GameConfig.MARGIN_LEFT,
                    i * GameConfig.HEIGHT + GameConfig.SECOND_LINE, c);
            Text desc = new Text(
                    achievements.getAchievementDescription(i),
                    GameConfig.WIDTH3,
                    i * GameConfig.HEIGHT + GameConfig.SECOND_LINE);
            getTexts().add(title);
            getTexts().add(desc);
            i++;
        }
    }

    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.ACHIEVEMENTS_SCREEN;
    }

}
