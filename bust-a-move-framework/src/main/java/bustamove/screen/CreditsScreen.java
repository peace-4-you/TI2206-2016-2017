/*
 * File: CreditsScreen.java
 *
 * Class: CreditsScreen
 *
 * Version: 0.0.1
 *
 * Date: September 28th, 2016
 */
package bustamove.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.screen.attributes.Button;
import bustamove.screen.attributes.Text;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;

/**
 * Generates a Screen with the game credits.
 *
 * @author Maurice Willemsen
 */
public class CreditsScreen extends Screen {
    /**
     * All members in a string array.
     */
    private static final String[] MEMBERS = {"Winer Bao", "Calvin Nhieu",
            "Justin Segond", "Maurice Willemsen", "Jason Xie"};

    /**
     * Getter method: for the GameState ID.
     *
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.CREDITS_SCREEN;
    }

    /**
     * Called when BasicGameState initializes.
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
                           final StateBasedGame stateBasedGame)
            throws SlickException {
        initBasicScreen(game);
        Button mainmenu = new Button("Main menu", GameConfig.FIRST_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        mainmenu.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
        Text line = new Text("-----", GameConfig.SECOND_LINE);
        Text project = new Text("TuDelft - TI2206", GameConfig.THIRD_LINE);
        Text group = new Text("\"Group 3\"", GameConfig.FOURTH_LINE);
        Text members = new Text("-----", GameConfig.FIFTH_LINE);
        for (int i = 0; i < MEMBERS.length; i++) {
            Text member = new Text(MEMBERS[i], GameConfig.SIXT_LINE
                    + GameConfig.LINE_DIST * i);
            getTexts().add(member);
        }
        Text line2 = new Text("-----", GameConfig.ELEVENTH_LINE);
        Text copyright = new Text("© 2016", GameConfig.TWELFTH_LINE);
        getTexts().add(line);
        getTexts().add(project);
        getTexts().add(group);
        getTexts().add(members);
        getTexts().add(line2);
        getTexts().add(copyright);
        getButtons().add(mainmenu);
        super.center();
    }
}