package bustamove.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;

public abstract class NameScreen extends Screen {
    /**
     * Textfields for input names
     */
	protected TextField[] namefields;
	
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
        namefields = new TextField[2];
        namefields[0] = new TextField(game, game.getDefaultFont(),
                GameConfig.CENTRAL, GameConfig.THIRD_LINE, GameConfig.WIDTH3,
                GameConfig.HEIGHT);
        namefields[0].setText("Player1");  
        Button main = new Button("Go to Main Menu", GameConfig.EIGTH_LINE,
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
        textfields.add(namefields[0]);  
        buttons.add(main);
        super.center();
    } 
}
