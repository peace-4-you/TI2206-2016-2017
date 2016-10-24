package bustamove.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;
import bustamove.util.PlayerObserver;

public abstract class ScoreScreen extends Screen implements PlayerObserver{
    /**
     * Amount of players.
     */
    protected int players = 0;
    /**
     * Contains the player's name.
     */
    private Text[] playername;
    /**
     * Contains the player's score.
     */
    private Text[] playerscore;

    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public abstract int getID();
    
    /**
     * Called when BasicGameState initializes.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public void init(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
    	super.init(game,stateBasedGame);
        playername = new Text[2];
        playerscore = new Text[2];   
        playername[0] = new Text("Player: ", GameConfig.SECOND_LINE);        
        playername[1] = new Text("Player: ", GameConfig.SIXT_LINE);
        playerscore[0] = new Text("Score: ", GameConfig.THIRD_LINE);
        playerscore[1] = new Text("Score: ", GameConfig.SEVENTH_LINE);     
        Button main = new Button("Go to Main Menu", GameConfig.FIFTH_LINE,
                GameConfig.WIDTH3, GameConfig.HEIGHT);
        main.addAction(new Runnable() {
            public void run() {
                try {
                    App.getGame().destroyGame();
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
        });
        main.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
        texts.add(playername[0]);
        texts.add(playername[1]);
        texts.add(playerscore[0]);
        texts.add(playerscore[1]);  
        buttons.add(main);
    } 
    
    /**
     * Updated by Observable. Sets data
     * @param number integer of player number
     * @param name String of player name
     * @param score integer of player score
     */
    public final void update(final int number, final String name,
                             final int score) {
        if (number == 1 || number == 2) {
            this.playername[number-1].setText("Player: " + name);
            this.playerscore[number-1].setText("Score: " + score);
        } 
        this.center();
    }

    /**
     * Updated by Observable, sets amount of playerdata.
     * @param playeramount integer about amount of players
     */
    public final void update(final int playeramount) {
    	players=playeramount;
        if (playeramount==1){
        	texts.remove(playername[1]);
        	texts.remove(playerscore[1]);
        }
        else if(playeramount==2){
        	texts.add(playername[1]);
        	texts.add(playerscore[1]);
        }
    }	
}
